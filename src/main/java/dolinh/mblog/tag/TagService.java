package dolinh.mblog.tag;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    public void create(CreateTagRequest req){
        if(tagRepository.existsTagByName(req.name())){
            throw new RuntimeException("Tag already existed!");
        }
        if(tagRepository.existsTagBySlug(req.slug())){
            throw new RuntimeException("Tag already existed!");
        }
        Tag newTag  = new Tag();
        newTag.setName(req.name());
        newTag.setSlug(req.slug());
        newTag.setCreatedAt(LocalDateTime.now());
        tagRepository.save(newTag);
    }
}
