package dolinh.mblog.auth;

import dolinh.mblog.auth.dto.AuthResponse;
import dolinh.mblog.auth.dto.LoginRequest;
import dolinh.mblog.auth.dto.RegisterRequest;
import dolinh.mblog.security.CustomUserDetails;
import dolinh.mblog.security.JwtConfig;
import dolinh.mblog.user.AppUser;
import dolinh.mblog.user.AppUserRepository;
import dolinh.mblog.user.Role;
import dolinh.mblog.user.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AppUserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtConfig jwtConfig;
    private final AuthenticationManager authenticationManager;

    private String sha256(String input){
        return passwordEncoder.encode(input);
    }

    @Transactional
    public void register(RegisterRequest req){
        if(
                userRepository.existsAppUsersByUsername(req.username())
                || userRepository.existsAppUsersByEmail(req.email())
        ){
            throw new RuntimeException("Username or email already use!");
        }

        AppUser user = new AppUser();
        String hashPash = sha256(req.password());
        user.setUsername(req.username());
        user.setEmail(req.email());
        user.setPassword(hashPash);
        Role roles = roleRepository.findRoleByName("USER")
                        .orElseThrow(() -> new RuntimeException("Role not found"));

        user.setRoles(Set.of(roles));

        userRepository.save(user);
    }

    public AuthResponse login(LoginRequest req){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.username(),
                        req.password()
                )
        );
        AppUser user = userRepository.findAppUserByUsername(req.username())
                .orElseThrow(() -> new RuntimeException("User not found!"));

        CustomUserDetails userDetails = new CustomUserDetails(user);

        String accessToken = jwtConfig.generateAccessToken(userDetails);

        return new AuthResponse(
                accessToken,
                "Bearer"
        );
    }
}
