package pl.pas.parcellocker;

import java.math.BigDecimal;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import pl.pas.parcellocker.model.delivery.Delivery;
import pl.pas.parcellocker.model.delivery.DeliveryRepository;
import pl.pas.parcellocker.model.locker.Locker;
import pl.pas.parcellocker.model.locker.LockerRepository;
import pl.pas.parcellocker.model.user.Administrator;
import pl.pas.parcellocker.model.user.Client;
import pl.pas.parcellocker.model.user.Moderator;
import pl.pas.parcellocker.model.user.User;
import pl.pas.parcellocker.model.user.UserRepository;


@ApplicationPath("/api")
public class InitApplication extends Application {

    @Inject
    private UserRepository userRepository;

    @Inject
    private LockerRepository lockerRepository;

    @Inject
    private DeliveryRepository deliveryRepository;

    User admin;
    User moderator;
    User johnzon;
    User yasson;
    User geronimoShipper;

    Locker locker;

    Delivery delivery1;
    Delivery delivery2;
    Delivery delivery3;
    Delivery delivery4;
    Delivery delivery5;
    Delivery delivery6;


    @PostConstruct
    void init() {

        if (!userRepository.findByTelNumber("666666666").isPresent()) {
            admin = new Administrator("admin", "admin", "666666666", "P@ssw0rd");
            moderator = new Moderator("moderator", "moderator", "546777890", "P@ssw0rd");
            johnzon = new Client("Johnzon", "Jackson", "420213769", "P@ssw0rd");
            yasson = new Client("Yasson", "Jackson", "123456789", "P@ssw0rd");
            geronimoShipper = new Client("GeronimoShipper", "Jackson", "234345456", "P@ssw0rd");
            userRepository.add(admin);
            userRepository.add(moderator);
            userRepository.add(johnzon);
            userRepository.add(yasson);
            userRepository.add(geronimoShipper);
        }

        if (!lockerRepository.findByIdentityNumber("LODZ_01").isPresent()) {
            locker = new Locker("LODZ_01", "XD", 10);
            lockerRepository.add(locker);
            initDeliveries();
        }
    }

    private void initDeliveries() {
        delivery1 = new Delivery(BigDecimal.TEN, true, (Client) geronimoShipper, (Client) johnzon, locker);
        delivery2 = new Delivery(BigDecimal.TEN, true, (Client) geronimoShipper, (Client) yasson, locker);

        delivery3 = new Delivery(BigDecimal.TEN, 10, 10, 10, 10, true, (Client) geronimoShipper, (Client) johnzon, locker);
        delivery4 = new Delivery(BigDecimal.TEN, 10, 10, 10, 10, true, (Client) geronimoShipper, (Client) yasson, locker);

        delivery5 = new Delivery(BigDecimal.TEN, 10, 10, 10, 10, true, (Client) geronimoShipper, (Client) admin, locker);
        delivery6 = new Delivery(BigDecimal.TEN, true, (Client) geronimoShipper, (Client) admin, locker);
    }
}
