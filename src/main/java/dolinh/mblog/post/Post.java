package dolinh.mblog.post;

import dolinh.mblog.category.Category;
import dolinh.mblog.media.Media;
import dolinh.mblog.user.AppUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "posts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "author_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_posts_author")
    )
    private AppUser author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "category_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_posts_category")
    )
    private Category category;
    private String title;
    private String slug;
    private String excerpt;
    @Column(columnDefinition = "TEXT")
    private String content;
    private String contentHtml;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "featured_image_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_posts_media")
    )
    private Media featuredImageId;
    private String status;
    private String visibility;
    private LocalDateTime publishedAt;
    private LocalDateTime scheduledAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private Integer viewCount;
}
