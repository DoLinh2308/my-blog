package dolinh.mblog.category;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    public void create(CreateCategoryRequest req){
        if(categoryRepository.existsCategoryByName(req.name())){
            throw new RuntimeException("Category already created!");
        }
        Category category = new Category();
        if(req.parentId() != null){
            category = categoryRepository.findCategoryById(req.parentId())
                    .orElseThrow(() -> new RuntimeException("Parent id is not exists!"));
        }
        if(category != null){
            if (Objects.equals(req.name(), category.getName())) {
                throw new RuntimeException(
                        "It must not be the same as the parent name!"
                );
            }
        }
        Category newCategory = new Category();
        newCategory.setName(req.name());
        newCategory.setSlug(req.slug());
        newCategory.setDescription(req.description());
        newCategory.setCreatedAt(LocalDateTime.now());
        categoryRepository.save(newCategory);
    }
}
