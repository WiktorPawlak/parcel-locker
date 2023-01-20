package pl.pas.parcellocker;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import pl.pas.parcellocker.model.user.Administrator;
import pl.pas.parcellocker.model.user.User;
import pl.pas.parcellocker.model.user.UserRepository;

@ApplicationPath("/api")
public class HelloApplication extends Application {

    @Inject
    private UserRepository userRepository;

    @PostConstruct
    void init() {
        User admin = new Administrator("admin", "admin", "admin");
        userRepository.add(admin);
    }
}
