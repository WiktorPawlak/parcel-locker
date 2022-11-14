package pl.pas.parcellocker.repositories.hibernate;

import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import pl.pas.parcellocker.config.UnitTestsConfig;
import pl.pas.parcellocker.model.client.Client;
import pl.pas.parcellocker.model.delivery.Delivery;
import pl.pas.parcellocker.model.delivery.DeliveryStatus;
import pl.pas.parcellocker.model.locker.Locker;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DeliveryRepositoryHibernateTest extends UnitTestsConfig {

    private Client c1;
    private Client c2;
    private Locker l1;

    @BeforeAll
    void setup() {
        l1 = new Locker("LDZ01", "Gawronska 12, Lodz 12-123", 10);
        c1 = new Client("Maciej", "Nowak", "12345");
        c2 = new Client("Maciej", "Kowal", "123456");
        clientRepository.add(c1);
        clientRepository.add(c2);
        lockerRepository.add(l1);
    }

    @Test
    void Should_CreateDelivery() {
        Delivery delivery = new Delivery(BigDecimal.TEN, true, c1, c2, l1);
        deliveryRepository.add(delivery);
        delivery = deliveryRepository.get(delivery.getId());
        assertEquals(deliveryRepository.get(delivery.getId()), delivery);
    }

    @Test
    void Should_UpdateDelivery() {
        Delivery delivery = new Delivery(BigDecimal.TEN, true, c1, c2, l1);
        deliveryRepository.add(delivery);
        assertEquals(deliveryRepository.get(delivery.getId()).getStatus(), DeliveryStatus.READY_TO_SHIP);
        delivery.setStatus(DeliveryStatus.READY_TO_PICKUP);
        deliveryRepository.update(delivery);
        assertEquals(deliveryRepository.get(delivery.getId()).getStatus(), DeliveryStatus.READY_TO_PICKUP);
    }

    @Test
    void Should_DeleteDelivery() {
        Delivery delivery = new Delivery(BigDecimal.TEN, true, c1, c2, l1);
        deliveryRepository.add(delivery);
        assertEquals(deliveryRepository.get(delivery.getId()), delivery);
        deliveryRepository.remove(delivery);
        assertThrows(NoResultException.class, () -> deliveryRepository.get(delivery.getId()));
    }

    @Test
    void Should_ArchiveDelivery() {
        Delivery delivery = new Delivery(BigDecimal.TEN, true, c1, c2, l1);
        deliveryRepository.add(delivery);
        assertFalse(deliveryRepository.get(delivery.getId()).isArchived());
        deliveryRepository.archive(delivery.getId());
        assertTrue(deliveryRepository.get(delivery.getId()).isArchived());
    }

    @Test
    void Should_ReturnAllDeliveries() {
        Delivery delivery = new Delivery(BigDecimal.TEN, true, c1, c2, l1);
        Delivery delivery1 = new Delivery(BigDecimal.ONE, false, c2, c1, l1);
        deliveryRepository.add(delivery);
        deliveryRepository.add(delivery1);
        assertTrue(deliveryRepository.findAll().contains(delivery));
        assertTrue(deliveryRepository.findAll().contains(delivery1));
    }
}
