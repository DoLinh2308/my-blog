package dolinh.mblog.user;

import dolinh.mblog.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(
            name = "private_id",
            insertable = false,
            updatable = false
    )
    private Long privateId;

    private String username;

    private String email;

    private String password;

    private String status;

    private Boolean isOnline;

    private String position;

    private String avatarMediaId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    private void prePersist(){
        if(this.createdAt == null)
            this.createdAt = LocalDateTime.now();
        if(this.updatedAt == null)
            this.updatedAt = LocalDateTime.now();
    }
}
