package dolinh.mblog.post;

import dolinh.mblog.category.Category;
import dolinh.mblog.category.CategoryRepository;
import dolinh.mblog.media.Media;
import dolinh.mblog.media.MediaRepository;
import dolinh.mblog.user.AppUser;
import dolinh.mblog.user.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final AppUserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final MediaRepository mediaRepository;
    private final PostVersionRepository postVersionRepository;
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

    @Transactional
    public PostResponse updatePost(UpdatePostRequest req, UUID postId){
        Post post = postRepository.findPostById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found!"));

        PostVersion postVersion = new PostVersion();
        UUID oldCreatedById = post.getAuthor().getId();
        String oldTitle = post.getTitle();
        String oldExcerpt = post.getExcerpt();
        String oldContent = post.getContent();
        postVersion.setPost(post);
        postVersion.setCreatedById(oldCreatedById);
        postVersion.setTitle(oldContent);
        postVersion.setExcerpt(oldExcerpt);
        postVersion.setContent(oldContent);
        postVersion.setCreatedAt(LocalDateTime.now());
        postVersionRepository.save(postVersion);

        if(req.authorId() != null) {
            AppUser author = userRepository.findAppUserById(req.authorId())
                    .orElseThrow(() -> new RuntimeException("User not found!"));
            post.setAuthor(author);
        }
        if(req.categoryId() != null){
            Category category = categoryRepository.findCategoryById(req.categoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found!"));
            post.setCategory(category);
        }
        if(req.featuredImageId() != null){
            Media media = mediaRepository.findMediaById(req.featuredImageId())
                    .orElseThrow(() -> new RuntimeException("Upload feature image have some mistake!"));
            post.setFeaturedImageId(media);
        }
        if(StringUtils.hasText(req.title())){
            post.setTitle(req.title());
        }
        if(StringUtils.hasText(req.slug())){
            post.setSlug(req.slug());
        }
        if(StringUtils.hasText(req.excerpt())){
            post.setExcerpt(req.excerpt());
        }
        if(StringUtils.hasText(req.content())){
            post.setContent(req.content());
        }
        if(StringUtils.hasText(req.status())){
            post.setStatus(req.status());
        }
        if(StringUtils.hasText(req.visibility())){
            post.setVisibility(req.visibility());
        }
        Post savedPost = postRepository.save(post);
        return new PostResponse(
                savedPost.getAuthor().getId(),
                savedPost.getCategory().getId(),
                savedPost.getTitle(),
                savedPost.getSlug(),
                savedPost.getExcerpt(),
                savedPost.getContent(),
                savedPost.getStatus(),
                savedPost.getVisibility()
        );
    }
    public Page<PostResponse> getAllPost(int page, int size, String sortBy, String sortDir){
        String status = "PUBLISHED";
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return postRepository.findAllByStatus(status,pageable);
    }
}
