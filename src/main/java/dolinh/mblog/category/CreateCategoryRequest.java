package dolinh.mblog.category;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record CreateCategoryRequest(
        @NotBlank String name,
        @NotBlank String slug,
        String description,
        UUID parentId
) {
}
