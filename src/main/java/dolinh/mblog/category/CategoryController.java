package dolinh.mblog.category;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody CreateCategoryRequest req){
        categoryService.create(req);
        return ResponseEntity.ok(null);
    }
}
