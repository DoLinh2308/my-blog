package dolinh.mblog.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TagRepository extends JpaRepository<Tag, UUID> {
    Boolean existsTagByName(String name);
    Boolean existsTagBySlug(String slug);
    Optional<Tag> findTagByName(String name);
}
