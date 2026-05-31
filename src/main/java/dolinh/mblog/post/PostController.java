package dolinh.mblog.post;

import dolinh.mblog.comment.CommentService;
import dolinh.mblog.comment.CreateCommentRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v1/api/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final CommentService commentService;
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
    @GetMapping
    public ResponseEntity<Page<PostResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir
    ){
        return ResponseEntity.ok(postService.getAllPost(page, size, sortBy, sortDir));
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<Void> createComment(
            @PathVariable UUID id,
            @Valid @RequestBody CreateCommentRequest req,
            Authentication authentication
    ){
        commentService.createComment(id, req, authentication);
        return ResponseEntity.ok(null);
    }
}
