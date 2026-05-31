package dolinh.mblog.post;

import java.util.UUID;

public record PostResponse(
        UUID authorId,
        UUID categoryId,
        String title,
        String slug,
        String excerpt,
        String content,
        String status,
        String visibility
) {
}
