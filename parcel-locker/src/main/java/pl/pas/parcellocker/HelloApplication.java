package pl.pas.parcellocker;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import pl.pas.parcellocker.model.user.Administrator;
import pl.pas.parcellocker.model.user.User;
import pl.pas.parcellocker.model.user.UserRepository;

@SpringBootApplication
public class HelloApplication {

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(HelloApplication.class, args);
    }

    @PostConstruct
    void init() {
        User admin = new Administrator("admin", "admin", "admin");
        userRepository.add(admin);
    }
}
