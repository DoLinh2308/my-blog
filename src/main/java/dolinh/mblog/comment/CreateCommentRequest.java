package dolinh.mblog.comment;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record CreateCommentRequest(
        @NotBlank String content,
        UUID parentId
) {
}
