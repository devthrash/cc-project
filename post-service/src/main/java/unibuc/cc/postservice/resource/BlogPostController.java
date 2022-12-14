package unibuc.cc.postservice.resource;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unibuc.cc.postservice.model.BlogPost;
import unibuc.cc.postservice.repository.BlogPostRepository;
import unibuc.cc.postservice.service.RabbitMQSender;

@RestController
@RequestMapping("blogs")
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
        rabbitMQSender.send(request);
        return blogPostRepository.save(request);
    }

}
