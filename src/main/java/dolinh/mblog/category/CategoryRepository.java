package dolinh.mblog.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Boolean existsCategoryByName(String name);
    Optional<Category> findCategoryByName(String name);
    Optional<Category> findCategoryById(UUID id);

}
