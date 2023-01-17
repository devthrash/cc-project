package unibuc.cc.postservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import unibuc.cc.postservice.model.UserAccount;
import unibuc.cc.postservice.repository.UserAccountRepository;

@SpringBootApplication
public class PostServiceApplication {

    private final UserAccountRepository userAccountRepository;

    public PostServiceApplication(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(PostServiceApplication.class, args);
    }

    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        userAccountRepository.save(new UserAccount( "Ion", "Popescu", "ion.popescu@gmail.com"));
    }
}
