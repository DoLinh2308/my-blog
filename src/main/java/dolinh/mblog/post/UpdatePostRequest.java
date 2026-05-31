package dolinh.mblog.post;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdatePostRequest(
        UUID authorId,
        UUID categoryId,
        String title,
        String slug,
        String excerpt,
        String content,
        UUID featuredImageId,
        String status,
        String visibility
) {
}
