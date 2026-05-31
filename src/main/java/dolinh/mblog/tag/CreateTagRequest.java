package dolinh.mblog.tag;

import jakarta.validation.constraints.NotBlank;

public record CreateTagRequest (
        @NotBlank String name,
        @NotBlank String slug
){
}
