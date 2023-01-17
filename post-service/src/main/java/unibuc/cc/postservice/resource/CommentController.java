package unibuc.cc.postservice.resource;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import unibuc.cc.postservice.model.BlogPost;
import unibuc.cc.postservice.model.Comment;
import unibuc.cc.postservice.model.UserAccount;
import unibuc.cc.postservice.model.dto.CommentDTO;
import unibuc.cc.postservice.repository.BlogPostRepository;
import unibuc.cc.postservice.repository.CommentRepository;
import unibuc.cc.postservice.repository.UserAccountRepository;
import unibuc.cc.postservice.service.RabbitMQSender;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("comments")
@Transactional
public class CommentController {

    private final CommentRepository commentRepository;
    private final UserAccountRepository userAccountRepository;
    private final BlogPostRepository blogPostRepository;

    private final RabbitMQSender rabbitMQSender;

    public CommentController(CommentRepository commentRepository, UserAccountRepository userAccountRepository, BlogPostRepository blogPostRepository, RabbitMQSender rabbitMQSender) {
        this.commentRepository = commentRepository;
        this.userAccountRepository = userAccountRepository;
        this.blogPostRepository = blogPostRepository;
        this.rabbitMQSender = rabbitMQSender;
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public CommentDTO create(@RequestBody CommentDTO request) {
        UserAccount userAccount = userAccountRepository.findById(request.getUserAccountId())
                .orElseThrow(() -> new RuntimeException("User not found!"));
        BlogPost blogPost = blogPostRepository.findById(request.getBlogPostId())
                .orElseThrow(() -> new RuntimeException("Post not found!"));
        Comment comment = Comment.builder()
                .userAccount(userAccount)
                .blogPost(blogPost)
                .tag(request.getTag())
                .title(request.getTitle())
                .text(request.getText())
                .build();
        Comment savedEntity = commentRepository.save(comment);
        Map<String, Object> map = Map.of("event", "comment-created", "data", "{blogPostId:" + savedEntity.getBlogPost().getBlogId() +
                '}');
        rabbitMQSender.send(map);
        return new CommentDTO(savedEntity);
    }

    @PutMapping("/{id}")
    public Comment updateComment(@PathVariable long id, @RequestBody Comment request) {
        rabbitMQSender.send(request);
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment with id: " + id + " does not exist"));
        comment.setText(request.getText());
        commentRepository.save(comment);
        return comment;
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment with id: " + id + " does not exist"));
        rabbitMQSender.send(comment);
        commentRepository.delete(comment);
    }

}
