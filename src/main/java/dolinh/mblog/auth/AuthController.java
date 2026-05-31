package dolinh.mblog.auth;

import dolinh.mblog.auth.dto.AuthResponse;
import dolinh.mblog.auth.dto.LoginRequest;
import dolinh.mblog.auth.dto.RegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterRequest req){
        authService.register(req);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest req){
            return ResponseEntity.ok(authService.login(req));
    }

    @GetMapping("/me")
    public Object me(Authentication authentication){
        return authentication;
    }
}
