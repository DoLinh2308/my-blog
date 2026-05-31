package dolinh.mblog.comment;

import dolinh.mblog.post.Post;
import dolinh.mblog.post.PostRepository;
import dolinh.mblog.security.CustomUserDetails;
import dolinh.mblog.user.AppUser;
import dolinh.mblog.user.AppUserRepository;
import dolinh.mblog.utils.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final AppUserRepository userRepository;
    public void createComment(UUID postId, CreateCommentRequest req, Authentication authentication){
        Post post = postRepository.findPostById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found!"));

        String username = Objects.requireNonNull(authentication.getPrincipal()).toString();
        AppUser user = userRepository.findAppUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found!"));
        Comment comment = new Comment();
        comment.setAuthorId(user);
        comment.setPostId(post);
        if(req.parentId() != null) {
            comment.setParentId(req.parentId());
        }
        comment.setContent(req.content());
        comment.setStatus("PENDING");
        comment.setCreatedAt(LocalDateTime.now());
        commentRepository.save(comment);
    }
}
