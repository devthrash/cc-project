package unibuc.cc.postservice.resource;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import unibuc.cc.postservice.model.BlogPost;
import unibuc.cc.postservice.repository.BlogPostRepository;
import unibuc.cc.postservice.service.RabbitMQSender;

import java.util.Map;

@RestController
@RequestMapping("blogs")
@Transactional
public class BlogPostController {

    private final BlogPostRepository blogPostRepository;

    private final RabbitMQSender rabbitMQSender;

    public BlogPostController(BlogPostRepository blogPostRepository, RabbitMQSender rabbitMQSender) {
        this.blogPostRepository = blogPostRepository;
        this.rabbitMQSender = rabbitMQSender;
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public BlogPost create(@RequestBody BlogPost request) {
        BlogPost post = blogPostRepository.save(request);

        Map<String, Object> map = Map.of("event", "post-created", "data", post);
        rabbitMQSender.send(map);

        return post;
    }

    @PutMapping("/{id}")
    public BlogPost updateBlogPost(@PathVariable long id, @RequestBody BlogPost request) {
        rabbitMQSender.send(request);
        BlogPost blogPost = blogPostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog Post with id: " + id + " does not exist"));
        blogPost.setDescription(request.getDescription());
        blogPostRepository.save(blogPost);
        return blogPost;
    }

    @DeleteMapping(value = "/{id}")
    public void deletePost(@PathVariable Long id) {
        BlogPost blogPost = blogPostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog Post with id: " + id + " does not exist"));
        rabbitMQSender.send(blogPost);
        blogPostRepository.delete(blogPost);
    }

    @GetMapping("/{id}")
    public BlogPost getBlogPost(@PathVariable long id) {
        return blogPostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog Post with id: " + id + " does not exist"));
    }

}
