package pl.pas.parcellocker.config;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.TestInstance;
import pl.pas.parcellocker.repositories.hibernate.ClientRepositoryHibernate;
import pl.pas.parcellocker.repositories.hibernate.DeliveryRepositoryHibernate;
import pl.pas.parcellocker.repositories.hibernate.LockerRepositoryHibernate;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RepositoryConfig {
    protected final ClientRepositoryHibernate clientRepository = new ClientRepositoryHibernate();
    protected final DeliveryRepositoryHibernate deliveryRepository = new DeliveryRepositoryHibernate();
    protected final LockerRepositoryHibernate lockerRepository = new LockerRepositoryHibernate();

    @AfterAll
    void finisher() {
        deliveryRepository.findAll().forEach(deliveryRepository::remove);
        clientRepository.findAll().forEach(clientRepository::remove);
        lockerRepository.findAll().forEach(lockerRepository::remove);
    }
}