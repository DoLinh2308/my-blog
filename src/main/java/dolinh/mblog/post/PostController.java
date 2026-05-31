package dolinh.mblog.post;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/api/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody CreatePostRequest req){
        postService.create(req);
        return ResponseEntity.ok(null);
    }
    @PatchMapping("/update-post/{id}")
    public ResponseEntity<PostResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdatePostRequest req
    ){
        return ResponseEntity.ok(postService.updatePost(req,id));
    }
}
