package dolinh.mblog.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
    Boolean existsPostByTitle(String title);
    Optional<Post> findPostByTitle(String title);
    Optional<Post> findPostById(UUID id);
    Page<PostResponse> findAllByStatus(String status, Pageable pageable);
}
