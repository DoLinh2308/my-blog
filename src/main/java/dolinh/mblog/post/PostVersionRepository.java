package dolinh.mblog.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PostVersionRepository extends JpaRepository<PostVersion, UUID> {
}
