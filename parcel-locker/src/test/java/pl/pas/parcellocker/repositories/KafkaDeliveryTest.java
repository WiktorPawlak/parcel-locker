package pl.pas.parcellocker.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.pas.parcellocker.model.Client;
import pl.pas.parcellocker.model.Delivery;
import pl.pas.parcellocker.model.Locker;
import pl.pas.parcellocker.repositories.kafka.ProducerHandlerImpl;

import java.math.BigDecimal;

class KafkaDeliveryTest {
    DeliveryMongoRepository abstractMongoRepository = new DeliveryMongoRepository(new ProducerHandlerImpl());
    Delivery delivery1;

    @BeforeEach
    void setup() {
        Client shipper = new Client("Mati", "Kowal", "12345678");
        Client receiver = new Client("Tedi", "Tos", "2414124");
        Locker locker = new Locker("LDZ05", "test-address", 2);
        delivery1 = new Delivery(new BigDecimal("10"), true, shipper, receiver, locker);
    }

    @Test
    void Should_AddDelivery() {
        abstractMongoRepository.add(delivery1);
    }

}
