package pl.pas.parcellocker.config;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import pl.pas.parcellocker.repositories.ClientMongoRepository;
import pl.pas.parcellocker.repositories.DeliveryMongoRepository;
import pl.pas.parcellocker.repositories.LockerMongoRepository;
import pl.pas.parcellocker.repositories.ProducerHandler;
import pl.pas.parcellocker.repositories.kafka.ProducerHandlerImpl;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestsConfig {

    public final ProducerHandler producerHandler = new ProducerHandlerImpl();

    protected final ClientMongoRepository clientRepository = new ClientMongoRepository();
    protected final DeliveryMongoRepository deliveryRepository = new DeliveryMongoRepository(producerHandler);
    protected final LockerMongoRepository lockerRepository = new LockerMongoRepository();


    @AfterAll
    void finisher() {
        deliveryRepository.findAll().forEach(delivery -> deliveryRepository.delete(delivery.getId()));
        clientRepository.findAll().forEach(client -> clientRepository.delete(client.getId()));
        lockerRepository.findAll().forEach(locker -> lockerRepository.delete(locker.getId()));
    }

}
