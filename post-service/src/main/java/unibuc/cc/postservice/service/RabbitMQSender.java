package unibuc.cc.postservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Service;
import unibuc.cc.postservice.model.BlogPost;

@Slf4j
@Service
public class RabbitMQSender {
    private final AmqpTemplate rabbitTemplate;
    private final Queue queue;

    public RabbitMQSender(AmqpTemplate rabbitTemplate, Queue queue) {
        this.rabbitTemplate = rabbitTemplate;
        this.queue = queue;
    }

    public void send(BlogPost blogPost) {
        rabbitTemplate.convertAndSend(queue.getName(), blogPost);
        log.info("Sending Message to the Queue : " + blogPost.toString());
    }
}
