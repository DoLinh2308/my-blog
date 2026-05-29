package dolinh.mblog.common;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

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
