package dolinh.mblog.security;

import dolinh.mblog.user.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JwtConfig {
    private final SecretKey secret;
    private final long accessExpirationMinutes;
    public JwtConfig(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.access-expiration-minutes}") long accessExpirationMinutes
    ){
        this.secret = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessExpirationMinutes = accessExpirationMinutes;
    }

    public String generateAccessToken(UserDetails userDetails){
        Instant now = Instant.now();
        Instant exp = now.plusSeconds(accessExpirationMinutes * 60);
        Map<String, Object> claims = new HashMap<>();
        CustomUserDetails custom = (CustomUserDetails) userDetails;

        Set<String> roles = custom.user()
                .getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        claims.put("roles", roles);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now))
                .signWith(secret)
                .compact();
    }

    public Claims parse(String token){
        return Jwts.parser()
                .verifyWith(secret)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUsername(String token){
        return parse(token).getSubject();
    }

    public boolean isValid(String token){
        try {
            parse(token);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}
