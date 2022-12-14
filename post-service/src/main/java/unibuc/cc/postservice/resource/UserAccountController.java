package unibuc.cc.postservice.resource;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unibuc.cc.postservice.model.UserAccount;
import unibuc.cc.postservice.repository.UserAccountRepository;

@RestController
@RequestMapping("users")
public class UserAccountController {

    private final UserAccountRepository userAccountRepository;

    public UserAccountController(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public UserAccount create(@RequestBody UserAccount request) {
        return userAccountRepository.save(request);
    }
}
