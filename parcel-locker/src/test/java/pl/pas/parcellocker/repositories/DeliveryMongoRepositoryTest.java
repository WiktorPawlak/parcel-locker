package pl.pas.parcellocker.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.pas.parcellocker.model.Client;
import pl.pas.parcellocker.model.Delivery;
import pl.pas.parcellocker.model.Locker;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.pas.parcellocker.model.DeliveryStatus.RECEIVED;

class DeliveryMongoRepositoryTest {
    DeliveryMongoRepository abstractMongoRepository = new DeliveryMongoRepository();
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
    void Should_AddDelivery() {
       abstractMongoRepository.add(delivery1);

       assertEquals(getDeliveryFromRepo(delivery1).getLocker().getId(), delivery1.getLocker().getId());
    }

    @Test
    void Should_UpdateDeliveries() {
        abstractMongoRepository.add(delivery1);
        delivery1.setStatus(RECEIVED);
        abstractMongoRepository.update(delivery1);

        assertEquals(getDeliveryFromRepo(delivery1).getStatus(), RECEIVED);
    }

    @Test
    void Should_DeleteDelivery() {
        abstractMongoRepository.add(delivery1);

        abstractMongoRepository.delete(delivery1.getEntityId().getUUID());

        assertNull(getDeliveryFromRepo(delivery1));
    }

    @Test
    void Should_ReturnAllDeliveries() {
        abstractMongoRepository.add(delivery1);
        abstractMongoRepository.add(delivery2);

        assertTrue(abstractMongoRepository.findAll().size() >= 2);
    }

    private Delivery getDeliveryFromRepo(Delivery delivery) {
        return abstractMongoRepository.findById(delivery.getId());
    }

}
