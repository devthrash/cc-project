package unibuc.cc.postservice.resource;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unibuc.cc.postservice.model.BlogPost;
import unibuc.cc.postservice.repository.BlogPostRepository;

@RestController
@RequestMapping("blogs")
public class BlogPostController {

    private final BlogPostRepository blogPostRepository;

    public BlogPostController(BlogPostRepository blogPostRepository) {
        this.blogPostRepository = blogPostRepository;
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public BlogPost create(@RequestBody BlogPost request) {
        return blogPostRepository.save(request);
    }

}
