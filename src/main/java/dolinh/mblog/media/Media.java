package dolinh.mblog.media;

import dolinh.mblog.user.AppUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "medias")
@Getter
@Setter
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    @JoinColumn(
            name = "uploader_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_medias_uploader")
    )
    private AppUser uploader;
    private String filename;
    private String storageKey;
    private String mimeType;
    private Integer size;
    private Long width;
    private Long height;
    private String altText;
    private LocalDateTime createdAt;
}
