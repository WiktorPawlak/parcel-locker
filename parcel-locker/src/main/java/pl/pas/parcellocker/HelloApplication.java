package pl.pas.parcellocker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.pas.parcellocker.model.user.Administrator;
import pl.pas.parcellocker.model.user.User;
import pl.pas.parcellocker.model.user.UserRepository;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class HelloApplication {

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    void init() {
        User admin = new Administrator("admin", "admin", "admin");
        userRepository.add(admin);
    }
}
