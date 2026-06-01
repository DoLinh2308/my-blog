package dolinh.mblog.post;

import java.util.UUID;

public record DetailPostResponse(
        String authorName,
        String categoryName,
        String title,
        String slug,
        String excerpt,
        String content,
        String status,
        String visibility
) {
}