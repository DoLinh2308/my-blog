package dolinh.mblog.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
    Boolean existsPostByTitle(String title);
    Optional<Post> findPostByTitle(String title);
}
