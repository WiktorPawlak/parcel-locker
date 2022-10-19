package pl.pas.parcellocker.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.pas.parcellocker.config.TestsConfig;
import pl.pas.parcellocker.model.Client;
import pl.pas.parcellocker.model.Delivery;
import pl.pas.parcellocker.model.Locker;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeliveryRepositoryTest extends TestsConfig {
    public DeliveryRepository deliveryRepository;
    public Client c1;
    public Client c2;
    public Locker l1;

    @BeforeEach
    void setup() {
        deliveryRepository = new DeliveryRepository();
        l1 = new Locker(10);
        c1 = new Client("Maciej", "Nowak", "12345");
        c2 = new Client("Maciej", "Kowal", "123456");

    }

    @Test
    void createDelivery() {
        Delivery delivery = new Delivery(BigDecimal.TEN, true, c1, c2, l1);
        deliveryRepository.add(delivery);
        delivery = deliveryRepository.get(delivery.getId());
        assertEquals(deliveryRepository.get(delivery.getId()), delivery);
    }
}
