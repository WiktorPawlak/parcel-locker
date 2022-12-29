package pl.pas.parcellocker.config;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.TestInstance;
import pl.pas.parcellocker.repositories.ClientRepository;
import pl.pas.parcellocker.repositories.DeliveryRepository;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestsConfig {

    protected final ClientRepository clientRepository = new ClientRepository();
    protected final DeliveryRepository deliveryRepository = new DeliveryRepository();

    @AfterAll
    void finisher() {
        deliveryRepository.clear();
        clientRepository.clear();
    }

}
