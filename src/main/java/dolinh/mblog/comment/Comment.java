package dolinh.mblog.comment;

import dolinh.mblog.post.Post;
import dolinh.mblog.user.AppUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "comments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "post_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_comments_post")
    )
    private Post postId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "author_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_comments_user")
    )
    private AppUser authorId;

    private UUID parentId;

    private String content;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
