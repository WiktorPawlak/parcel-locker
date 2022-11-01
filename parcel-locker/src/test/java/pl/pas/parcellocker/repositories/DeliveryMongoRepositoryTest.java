package pl.pas.parcellocker.repositories;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.pas.parcellocker.model.Client;
import pl.pas.parcellocker.model.Delivery;
import pl.pas.parcellocker.model.Locker;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class DeliveryMongoRepositoryTest {
    DeliveryMongoRepository abstractMongoRepository = new DeliveryMongoRepository();
    Delivery delivery1;

    @BeforeEach
    void setup() {
        Client shipper = new Client("Mati", "Kowal", "12345678");
        Client receiver = new Client("Tedi", "Tos", "2414124");
        Locker locker = new Locker("LDZ05", "test-address", 10);
        delivery1 = new Delivery(new BigDecimal("10"), true, shipper, receiver, locker);
    }

    @Test
    void shouldAddClient() {
       abstractMongoRepository.add(delivery1);
       log.info(delivery1.getShipper().getTelNumber());

       log.info(getDeliveryFromRepo(delivery1).getLocker().getDepositBoxes().toString());
        assertEquals(getDeliveryFromRepo(delivery1).getLocker().getId(), delivery1.getLocker().getId());
    }

//    @Test
//    void shouldUpdateClient() {
//        abstractMongoRepository.add(client1);
//        client1.setActive(false);
//        abstractMongoRepository.update(client1);
//
//        assertFalse(getDeliveryFromRepo(client1).isActive());
//    }
//
//    @Test
//    void shouldDeleteClient() {
//        abstractMongoRepository.add(client1);
//
//        abstractMongoRepository.delete(client1.getId());
//
//        assertNull(getDeliveryFromRepo(client1));
//    }
//
//    @Test
//    void shouldReturnAllClients() {
//        abstractMongoRepository.add(client1);
//        abstractMongoRepository.add(new Client("test", "test", "test"));
//
//        assertTrue(abstractMongoRepository.findAll().size() >= 2);
//    }

    private Delivery getDeliveryFromRepo(Delivery delivery) {
        return abstractMongoRepository.findById(delivery.getId());
    }

}
