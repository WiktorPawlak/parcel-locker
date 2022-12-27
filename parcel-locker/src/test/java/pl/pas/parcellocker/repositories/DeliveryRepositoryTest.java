package pl.pas.parcellocker.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.pas.parcellocker.model.Client;
import pl.pas.parcellocker.model.Delivery;
import pl.pas.parcellocker.model.Locker;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryRepositoryTest {

    DeliveryRepository deliveryRepository = new DeliveryRepository();
    Delivery delivery1;
    Delivery delivery2;

    @BeforeEach
    void setup() {
        Client shipper = new Client("Mati", "Kowal", "12345678");
        Client receiver = new Client("Tedi", "Tos", "2414124");
        Locker locker = new Locker("LDZ05", "test-address", 10);
        delivery1 = new Delivery(new BigDecimal("10"), true, shipper, receiver, locker);
        delivery2 = new Delivery(new BigDecimal("10"), true, shipper, receiver, locker);
    }

    @Test
    void save() {
        deliveryRepository.save(delivery1);
    }

    @Test
    void findAllById() {
    }
}
