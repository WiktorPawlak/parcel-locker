package pl.pas.parcellocker.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.pas.parcellocker.model.Client;
import pl.pas.parcellocker.model.Delivery;
import pl.pas.parcellocker.model.Locker;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryRepositoryTest {
    public DeliveryRepository deliveryRepository;
    public Client c;
    public Client c1;
    public Delivery d1;
    public Delivery d2;
    public Locker l1;

    @BeforeEach
    void setup() {
        deliveryRepository = new DeliveryRepository();
        l1 = new Locker(10);
        c = new Client("Maciej", "Nowak", "606123654");
        c1 = new Client("Maciej", "Kowal", "606123654");
        d1 = new Delivery(BigDecimal.TEN, true, c, c1, l1);
        d2 = new Delivery(BigDecimal.TEN, true, c1, c, l1);
    }

    @Test
    void findByIdConformance() {
        deliveryRepository.add(d1);
        deliveryRepository.add(d2);
        assertEquals(d1, deliveryRepository.findById(d1.getId()));
    }
}
