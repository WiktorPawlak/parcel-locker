package pl.pas.parcellocker.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.pas.parcellocker.model.Client;
import pl.pas.parcellocker.model.Delivery;
import pl.pas.parcellocker.model.Locker;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeliveryRepositoryTest {

    DeliveryRepository deliveryRepository = new DeliveryRepository();
    Delivery delivery1;
    Delivery delivery2;

    DeliveryRepositoryTest() {
        Client shipper = new Client("Mati", "Kowal", "12345678");
        Client receiver = new Client("Tedi", "Tos", "2414124");
        Locker locker = new Locker("LDZ05", "test-address", 10);
        delivery1 = new Delivery(new BigDecimal("10"), true, shipper, receiver, locker);
        delivery2 = new Delivery(new BigDecimal("10"), true, shipper, receiver, locker);
    }

    @BeforeEach
    void setup() {
        //deliveryRepository.clear();
    }

    @Test
    void shouldAddDelivery() {
        deliveryRepository.save(delivery1);

        assertEquals(deliveryRepository.findById(delivery1.getEntityId()), delivery1);
    }

    @Test
    void shouldFindDeliveryByDeliveryIdAndClientID() {
        deliveryRepository.save(delivery1);

        assertEquals(
            deliveryRepository.findById(delivery1.getEntityId()),
            deliveryRepository.findByClientId(delivery1.getReceiver()).stream().findFirst().get()
        );
    }

    @Test
    void shouldFindArchivedDeliveryByDeliveryIdAndClientID() {
        deliveryRepository.save(delivery1);
        deliveryRepository.save(delivery2);
        delivery1.setArchived(true);
        deliveryRepository.update(delivery1);

        assertEquals(
            deliveryRepository.findArchivedByClientId(delivery1.getReceiver()),
            List.of(delivery1)
        );

        delivery1.setArchived(false);
    }

    @Test
    void shouldUpdateDelivery() {
        deliveryRepository.save(delivery1);
        delivery1.setArchived(true);
        deliveryRepository.update(delivery1);

        assertTrue(deliveryRepository.findById(delivery1.getEntityId()).isArchived());
    }

    @Test
    void shouldReturnAllDeliveries() {
        deliveryRepository.save(delivery1);
        deliveryRepository.save(delivery2);

        assertTrue(deliveryRepository.findAll().size() >= 2);
    }

}
