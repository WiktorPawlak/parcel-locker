package pl.pas.parcellocker.repositories;

import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.pas.parcellocker.config.TestsConfig;
import pl.pas.parcellocker.model.Client;
import pl.pas.parcellocker.model.Delivery;
import pl.pas.parcellocker.model.DeliveryStatus;
import pl.pas.parcellocker.model.Locker;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeliveryRepositoryTest extends TestsConfig {
    public DeliveryRepository deliveryRepository;
    public ClientRepository clientRepository;
    public LockerRepository lockerRepository;
    public Client c1;
    public Client c2;
    public Locker l1;

    @BeforeEach
    void setup() {
        deliveryRepository = new DeliveryRepository();
        clientRepository = new ClientRepository();
        lockerRepository = new LockerRepository();
        l1 = new Locker(10);
        c1 = new Client("Maciej", "Nowak", "12345");
        c2 = new Client("Maciej", "Kowal", "123456");
        clientRepository.add(c1);
        clientRepository.add(c2);
        lockerRepository.add(l1);
    }

    @Test
    void createDelivery() {
        Delivery delivery = new Delivery(BigDecimal.TEN, true, c1, c2, l1);
        deliveryRepository.add(delivery);
        delivery = deliveryRepository.get(delivery.getId());
        assertEquals(deliveryRepository.get(delivery.getId()), delivery);
    }

    @Test
    void updateDelivery() {
        Delivery delivery = new Delivery(BigDecimal.TEN, true, c1, c2, l1);
        deliveryRepository.add(delivery);
        assertEquals(deliveryRepository.get(delivery.getId()).getStatus(), DeliveryStatus.READY_TO_SHIP);
        delivery.setStatus(DeliveryStatus.READY_TO_PICKUP);
        deliveryRepository.update(delivery);
        assertEquals(deliveryRepository.get(delivery.getId()).getStatus(), DeliveryStatus.READY_TO_PICKUP);
    }

    @Test
    void deleteDelivery() {
        Delivery delivery = new Delivery(BigDecimal.TEN, true, c1, c2, l1);
        deliveryRepository.add(delivery);
        assertEquals(deliveryRepository.get(delivery.getId()), delivery);
        deliveryRepository.remove(delivery);
        assertThrows(NoResultException.class, () -> deliveryRepository.get(delivery.getId()));
    }

    @Test
    void archiveDelivery() {
        Delivery delivery = new Delivery(BigDecimal.TEN, true, c1, c2, l1);
        deliveryRepository.add(delivery);
        assertFalse(deliveryRepository.get(delivery.getId()).isArchived());
        deliveryRepository.archive(delivery.getId());
        assertTrue(deliveryRepository.get(delivery.getId()).isArchived());
    }

    @Test
    void findAllDeliveries() {
        Delivery delivery = new Delivery(BigDecimal.TEN, true, c1, c2, l1);
        Delivery delivery1 = new Delivery(BigDecimal.ONE, false, c2, c1, l1);
        deliveryRepository.add(delivery);
        deliveryRepository.add(delivery1);
        assertTrue(deliveryRepository.findAll().contains(delivery));
        assertTrue(deliveryRepository.findAll().contains(delivery1));
    }
}
