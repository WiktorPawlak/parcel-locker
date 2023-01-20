package pl.pas.parcellocker;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import pl.pas.parcellocker.model.locker.Locker;
import pl.pas.parcellocker.model.locker.LockerRepository;
import pl.pas.parcellocker.model.user.Administrator;
import pl.pas.parcellocker.model.user.Client;
import pl.pas.parcellocker.model.user.Moderator;
import pl.pas.parcellocker.model.user.User;
import pl.pas.parcellocker.model.user.UserRepository;


@ApplicationPath("/api")
public class HelloApplication extends Application {

    @Inject
    private UserRepository userRepository;

    @Inject
    private LockerRepository lockerRepository;

    @PostConstruct
    void init() {
        if (!userRepository.findByTelNumber("666666666").isPresent()) {
            User admin = new Administrator("admin", "admin", "666666666", "P@ssw0rd");
            User moderator = new Moderator("moderator", "moderator", "546777890", "P@ssw0rd");
            User client1 = new Client("Johnzon", "Jackson", "420213769", "P@ssw0rd");
            User client2 = new Client("Yasson", "Jackson", "123456789", "P@ssw0rd");
            User client3 = new Client("Geronimo", "Jackson", "234345456", "P@ssw0rd");
            userRepository.add(admin);
            userRepository.add(moderator);
            userRepository.add(client1);
            userRepository.add(client2);
            userRepository.add(client3);
        }

        if (!lockerRepository.findByIdentityNumber("LODZ_01").isPresent()) {
            lockerRepository.add(new Locker("LODZ_01", "XD", 10));
        }
    }
}
