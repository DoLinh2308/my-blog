package dolinh.mblog.auth.dto;

public record AuthResponse(
        String accessToken,
        String authType
) {
}
