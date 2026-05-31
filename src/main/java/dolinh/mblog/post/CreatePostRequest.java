package dolinh.mblog.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreatePostRequest(
        @NotNull UUID authorId,
        @NotNull UUID categoryId,
        @NotBlank String title,
        @NotBlank String slug,
        String excerpt,
        @NotBlank String content,
        UUID featuredImageId,
        @NotBlank String status,
        @NotBlank String visibility
) {
}
