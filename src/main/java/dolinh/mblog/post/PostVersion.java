package dolinh.mblog.post;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "post_versions")
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostVersion {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn()
    private Post post;
    private Integer version;
    private String title;
    private String content;
    private String excerpt;
    private LocalDateTime createdAt;
    private UUID createdById;
}
