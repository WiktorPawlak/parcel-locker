package pl.pas.parcellocker.config;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestsConfig {

    protected final ClientRepositoryHibernate clientRepository = new ClientRepositoryHibernate();
    protected final DeliveryRepositoryHibernate deliveryRepository = new DeliveryRepositoryHibernate();
    protected final LockerRepositoryHibernate lockerRepository = new LockerRepositoryHibernate();

    @BeforeAll
    static void beforeAll() {
        PostgresContainerInitializer.start();
    }

    @AfterAll
    void finisher() {
        deliveryRepository.findAll().forEach(deliveryRepository::remove);
        clientRepository.findAll().forEach(clientRepository::remove);
        lockerRepository.findAll().forEach(lockerRepository::remove);
    }

}
