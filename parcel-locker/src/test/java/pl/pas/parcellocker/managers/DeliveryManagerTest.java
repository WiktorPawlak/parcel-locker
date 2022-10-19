package pl.pas.parcellocker.managers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.pas.parcellocker.config.TestsConfig;
import pl.pas.parcellocker.exceptions.DeliveryManagerException;
import pl.pas.parcellocker.model.Client;
import pl.pas.parcellocker.model.Delivery;
import pl.pas.parcellocker.model.Locker;
import pl.pas.parcellocker.repositories.ClientRepository;
import pl.pas.parcellocker.repositories.LockerRepository;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryManagerTest extends TestsConfig {

    public DeliveryManager deliveryManager;
    public ClientRepository clientRepository;
    public LockerRepository lockerRepository;
    public Client shipper1;
    public Client receiver1;
    public Locker locker;
    public Delivery delivery;
    public BigDecimal basePrice = BigDecimal.TEN;

    @BeforeEach
    void setup() {
        deliveryManager = new DeliveryManager();
        clientRepository = new ClientRepository();
        lockerRepository = new LockerRepository();
        locker = new Locker(20);
        shipper1 = new Client("Oscar", "Trel", "321312312");
        receiver1 = new Client("Bartosh", "Siekan", "123123123");
        clientRepository.add(shipper1);
        clientRepository.add(receiver1);
        lockerRepository.add(locker);
        delivery = deliveryManager.makeParcelDelivery(basePrice, 10, 20, 30, 10, false, shipper1, receiver1, locker);
    }

    @Test
    void putInLockerConformance() {
        int empty = locker.countEmpty();
        deliveryManager.putInLocker(delivery, "1234");
        assertEquals(empty - 1, locker.countEmpty());
    }

    @Test
    void takeOutDeliveryConformance() {
        deliveryManager.putInLocker(delivery, "1234");
        int empty = locker.countEmpty();
        deliveryManager.takeOutDelivery(locker, receiver1, "1234");
        assertEquals(empty + 1, locker.countEmpty());
    }

    @Test
    void getAllClientDeliveriesConformance() {
        Delivery delivery1 = deliveryManager.makeParcelDelivery(basePrice, 10, 20, 30, 10, false, shipper1, receiver1, locker);
        assertEquals(delivery, deliveryManager.getAllClientDeliveries(receiver1).get(0));
        assertEquals(delivery1, deliveryManager.getAllClientDeliveries(receiver1).get(1));
    }

    @Test
    void getAllReceivedClientDeliveriesConformance() {
        Delivery delivery1 = deliveryManager.makeParcelDelivery(basePrice, 10, 20, 30, 10, false, shipper1, receiver1, locker);
        deliveryManager.putInLocker(delivery1 ,"123");
        deliveryManager.takeOutDelivery(locker, receiver1,"123");
        assertTrue(0 < deliveryManager.getAllReceivedClientDeliveries(receiver1).size());
    }

    @Test
    void checkClientShipmentBalanceConformance() {
        assertEquals(new BigDecimal("15.000"), deliveryManager.checkClientShipmentBalance(shipper1));
    }

    @Test
    void exceptionConformance() {
        assertThrows(DeliveryManagerException.class, () -> deliveryManager.checkClientShipmentBalance(null));
    }
}
