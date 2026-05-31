package dolinh.mblog.user;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, UUID> {
    @EntityGraph(attributePaths = "roles")
    Optional<AppUser> findAppUserByUsername(String username);
    Optional<AppUser> findAppUserByEmail(String email);
    boolean existsAppUsersByUsername(String username);
    boolean existsAppUsersByEmail(String email);

    Optional<AppUser> findAppUserById(UUID id);
}
