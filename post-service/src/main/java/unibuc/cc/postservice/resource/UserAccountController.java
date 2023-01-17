package unibuc.cc.postservice.resource;

import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import unibuc.cc.postservice.model.UserAccount;
import unibuc.cc.postservice.repository.UserAccountRepository;
import unibuc.cc.postservice.service.RabbitMQSender;

import java.util.Map;

@RestController
@RequestMapping("users")
@Transactional
public class UserAccountController {

    private final UserAccountRepository userAccountRepository;

    private final RabbitMQSender rabbitMQSender;

    public UserAccountController(UserAccountRepository userAccountRepository, RabbitMQSender rabbitMQSender) {
        this.userAccountRepository = userAccountRepository;
        this.rabbitMQSender = rabbitMQSender;
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public UserAccount create(@RequestBody UserAccount request) {
        rabbitMQSender.send(request);
        return userAccountRepository.save(request);
    }

    @PutMapping("/{id}")
    public UserAccount update(@PathVariable long id, @RequestBody UserAccount request) {
        UserAccount userAccount = userAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User with id: " + id + " does not exist"));
        userAccount.setLastName(request.getLastName());
        UserAccount savedUser = userAccountRepository.save(userAccount);
        Map<String, Object> map = Map.of("event", "user-created", "data", savedUser);
        rabbitMQSender.send(map);
        return savedUser;
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id) {
        UserAccount userAccount = userAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User with id: " + id + " does not exist"));
        rabbitMQSender.send(userAccount);
        userAccountRepository.delete(userAccount);
    }
}
