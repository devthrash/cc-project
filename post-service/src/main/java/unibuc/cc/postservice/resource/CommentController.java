package unibuc.cc.postservice.resource;

import jakarta.transaction.Transactional;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import unibuc.cc.postservice.model.Comment;
import unibuc.cc.postservice.repository.CommentRepository;
import unibuc.cc.postservice.service.RabbitMQSender;

@RestController
@RequestMapping("comments")
@Transactional
public class CommentController {

    private final CommentRepository commentRepository;

    private final RabbitMQSender rabbitMQSender;

    public CommentController(CommentRepository commentRepository, RabbitMQSender rabbitMQSender) {
        this.commentRepository = commentRepository;
        this.rabbitMQSender = rabbitMQSender;
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Comment create(@RequestBody Comment request) {
        rabbitMQSender.send(request);
        return commentRepository.save(request);
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
