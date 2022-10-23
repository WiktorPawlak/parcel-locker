package pl.pas.parcellocker.config;

import org.junit.jupiter.api.BeforeAll;
import pl.pas.parcellocker.repositories.ClientRepository;
import pl.pas.parcellocker.repositories.DeliveryRepository;
import pl.pas.parcellocker.repositories.LockerRepository;

public class TestsConfig {

    protected final ClientRepository clientRepository = new ClientRepository();
    protected final DeliveryRepository deliveryRepository = new DeliveryRepository();
    protected final LockerRepository lockerRepository = new LockerRepository();

    @BeforeAll
    static void beforeAll() {
        PostgresContainerInitializer.start();
    }

}
