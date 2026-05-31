package dolinh.mblog.post;

import dolinh.mblog.category.Category;
import dolinh.mblog.category.CategoryRepository;
import dolinh.mblog.media.Media;
import dolinh.mblog.media.MediaRepository;
import dolinh.mblog.user.AppUser;
import dolinh.mblog.user.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final AppUserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final MediaRepository mediaRepository;
    @Transactional
    public void create(CreatePostRequest req){
        if(postRepository.existsPostByTitle(req.title()))
        {
            throw new RuntimeException("Post already exists!");
        }
        AppUser author = userRepository.findAppUserById(req.authorId())
                .orElseThrow(() -> new RuntimeException("User not found!"));
        Category category = categoryRepository.findCategoryById(req.categoryId())
                .orElseThrow(() -> new RuntimeException("Category not found!"));
        Post newPost = new Post();
        newPost.setAuthor(author);
        newPost.setCategory(category);
        Media media = new Media();
        if(req.featuredImageId() != null){
            media = mediaRepository.findMediaById(req.featuredImageId())
                    .orElseThrow(() -> new RuntimeException("Upload feature image have some mistake!"));
            newPost.setFeaturedImageId(media);
        }
        newPost.setTitle(req.title());
        newPost.setSlug(req.slug());
        newPost.setContent(req.content());
        newPost.setExcerpt(req.excerpt());
        newPost.setStatus(req.status());
        newPost.setVisibility(req.visibility());
        if(Objects.equals(req.visibility(), "PUBLIC")){
            newPost.setPublishedAt(LocalDateTime.now());
        }
        newPost.setCreatedAt(LocalDateTime.now());
        postRepository.save(newPost);
    }
}
