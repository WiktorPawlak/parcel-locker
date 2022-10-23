package pl.pas.parcellocker.managers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.pas.parcellocker.config.TestsConfig;
import pl.pas.parcellocker.exceptions.DeliveryManagerException;
import pl.pas.parcellocker.exceptions.LockerException;
import pl.pas.parcellocker.model.Client;
import pl.pas.parcellocker.model.Delivery;
import pl.pas.parcellocker.model.Locker;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeliveryManagerTest extends TestsConfig {

    private final DeliveryManager deliveryManager = new DeliveryManager();
    private Client shipper1;
    private Client receiver1;
    private Locker locker;
    private Delivery delivery;
    private final BigDecimal basePrice = BigDecimal.TEN;

    @BeforeEach
    void setup() {
        locker = new Locker("LDZ01", 20);
        shipper1 = new Client("Oscar", "Trel", "321312312");
        receiver1 = new Client("Bartosh", "Siekan", "123123123");
        clientRepository.add(shipper1);
        clientRepository.add(receiver1);
        lockerRepository.add(locker);
        delivery = deliveryManager.makeParcelDelivery(
            basePrice, 10, 20, 30, 10, false, shipper1, receiver1, locker
        );
    }

    @Test
    void Should_BlockDepositBox_WhenDeliveryPutInLocker() {
        int empty = locker.countEmpty();
        deliveryManager.putInLocker(delivery, "1234");
        assertEquals(empty - 1, locker.countEmpty());
    }

    @Test
    void Should_UnlockDepositBox_WhenDeliveryTookOut() {
        deliveryManager.putInLocker(delivery, "1234");
        int empty = locker.countEmpty();
        deliveryManager.takeOutDelivery(locker, receiver1, "1234");
        assertEquals(empty + 1, locker.countEmpty());
    }

    @Test
    void Should_ThrowException_WhenLockerIsFull() {
        Locker oneBoxLocker = new Locker("LDZ12", 1);
        lockerRepository.add(oneBoxLocker);
        Delivery testDelivery = deliveryManager.makeParcelDelivery(
            basePrice, 10, 20, 30, 10, false, shipper1, receiver1, oneBoxLocker
        );
        deliveryManager.putInLocker(testDelivery, "1234");
        assertThrows(LockerException.class, () -> deliveryManager.putInLocker(testDelivery, "1234"));
    }

    @Test
    void Should_ReturnAllClientDeliveries() {
        Delivery delivery1 = deliveryManager.makeParcelDelivery(
            basePrice, 10, 20, 30, 10, false, shipper1, receiver1, locker
        );
        assertEquals(delivery, deliveryManager.getAllClientDeliveries(receiver1).get(0));
        assertEquals(delivery1, deliveryManager.getAllClientDeliveries(receiver1).get(1));
    }

    @Test
    void Should_ReturnAllReceivedDeliveriesForGivenClient() {
        Delivery delivery1 = deliveryManager.makeParcelDelivery(
            basePrice, 10, 20, 30, 10, false, shipper1, receiver1, locker
        );
        deliveryManager.putInLocker(delivery1 ,"123");
        deliveryManager.takeOutDelivery(locker, receiver1,"123");
        assertTrue(0 < deliveryManager.getAllReceivedClientDeliveries(receiver1).size());
    }

    @Test
    void Should_ReturnCorrectBalanceForClientShipments() {
        assertEquals(new BigDecimal("15.000"), deliveryManager.checkClientShipmentBalance(shipper1));
    }

    @Test
    void Should_ThrowException_WhenInvalidValuesPassed() {
        assertThrows(DeliveryManagerException.class, () -> deliveryManager.checkClientShipmentBalance(null));
    }
}
