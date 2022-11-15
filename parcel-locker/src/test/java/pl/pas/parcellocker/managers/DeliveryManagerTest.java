package pl.pas.parcellocker.managers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import pl.pas.parcellocker.config.TestsConfig;
import pl.pas.parcellocker.exceptions.DeliveryManagerException;
import pl.pas.parcellocker.model.client.Client;
import pl.pas.parcellocker.model.delivery.Delivery;
import pl.pas.parcellocker.model.locker.Locker;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DeliveryManagerTest extends TestsConfig {

    private final DeliveryManager deliveryManager = new DeliveryManager(deliveryRepository, lockerRepository);
    private final BigDecimal basePrice = BigDecimal.TEN;
    private Client shipper1;
    private Client receiver1;
    private Locker locker;

    @BeforeAll
    void setup() {
        locker = new Locker("LDZ01", "Gawronska 12, Lodz 12-123", 20);
        shipper1 = new Client("Oscar", "Trel", "321312312");
        receiver1 = new Client("Bartosh", "Siekan", "123123123");
        clientRepository.add(shipper1);
        clientRepository.add(receiver1);
        lockerRepository.add(locker);
    }

    @AfterEach
    void eachFinisher() {
        deliveryRepository.findAll().forEach(deliveryRepository::remove);

    }

    @Test
    void Should_ThrowExceptionOnMakeDelivery_WhenAtLeastOneOfTheClientsIsInactive() {
        Client client = new Client("Mauris", "Kakel", UUID.randomUUID().toString().substring(0, 9));
        client.setActive(false);
        clientRepository.add(client);

        assertThrows(DeliveryManagerException.class, () -> deliveryManager.makeParcelDelivery(
            basePrice, 10, 20, 30, 10, false, client, receiver1, locker
        ));

        assertThrows(DeliveryManagerException.class, () -> deliveryManager.makeParcelDelivery(
            basePrice, 10, 20, 30, 10, false, shipper1, client, locker
        ));
    }

    @Test
    void Should_ThrowExceptionOnPutIn_WhenClientIsInactive() {
        Client client = new Client("Mauris", "Kakel", UUID.randomUUID().toString().substring(0, 9));
        clientRepository.add(client);

        Delivery delivery = deliveryManager.makeParcelDelivery(
            basePrice, 10, 20, 30, 10, false, client, receiver1, locker
        );

        changeClientActiveStatus(client, false);

        Delivery refreshedDelivery = deliveryRepository.get(delivery.getId());
        assertThrows(DeliveryManagerException.class, () -> deliveryManager.putInLocker(refreshedDelivery, "123"));
    }

    @Test
    void Should_ThrowExceptionOnTakeOut_WhenClientIsInactive() {
        Client client = new Client("Mauris", "Kakel", UUID.randomUUID().toString().substring(0, 9));
        clientRepository.add(client);

        Delivery delivery = deliveryManager.makeParcelDelivery(
            basePrice, 10, 20, 30, 10, false, shipper1, client, locker
        );

        deliveryManager.putInLocker(delivery, "123");

        changeClientActiveStatus(client, false);
        Client refreshedClient = clientRepository.get(client.getId());

        assertThrows(DeliveryManagerException.class, () -> deliveryManager.takeOutDelivery(locker, refreshedClient, "123"));

        changeClientActiveStatus(client, true);

        Client refreshedClient1 = clientRepository.get(client.getId());
        deliveryManager.takeOutDelivery(locker, refreshedClient1, "123");
    }

    private void changeClientActiveStatus(Client client, boolean active) {
        Client refreshedClient = clientRepository.get(client.getId());
        refreshedClient.setActive(active);
        clientRepository.update(refreshedClient);
    }

    @Test
    void Should_ThrowException_WhenDeliveryPutInAgain() {
        Delivery delivery = deliveryManager.makeParcelDelivery(
            basePrice, 10, 20, 30, 10, false, shipper1, receiver1, locker
        );

        deliveryManager.putInLocker(delivery, "54321");
        Delivery refreshedDelivery = deliveryRepository.get(delivery.getId());

        assertThrows(DeliveryManagerException.class, () -> deliveryManager.putInLocker(refreshedDelivery, "65433"));

        locker = lockerRepository.get(locker.getId());
        deliveryManager.takeOutDelivery(locker, receiver1, "54321");
    }

    @Test
    void Should_SetAllocationTime_WhenDeliveryPutInAndTookOut() {
        Delivery delivery = deliveryManager.makeParcelDelivery(
            basePrice, 10, 20, 30, 10, false, shipper1, receiver1, locker
        );

        assertNull(delivery.getAllocationStart());
        assertNull(delivery.getAllocationStop());

        deliveryManager.putInLocker(delivery, "54321");
        locker = lockerRepository.get(locker.getId());
        delivery = deliveryRepository.get(delivery.getId());
        assertNotNull(delivery.getAllocationStart());

        deliveryManager.takeOutDelivery(locker, receiver1, "54321");
        locker = lockerRepository.get(locker.getId());
        delivery = deliveryRepository.get(delivery.getId());
        assertNotNull(delivery.getAllocationStop());
    }

    @Test
    void Should_BlockDepositBox_WhenDeliveryPutInLocker() {
        Delivery delivery = deliveryManager.makeParcelDelivery(
            basePrice, 10, 20, 30, 10, false, shipper1, receiver1, locker
        );
        int empty = locker.countEmpty();
        deliveryManager.putInLocker(delivery, "12345");
        locker = lockerRepository.get(locker.getId());

        assertEquals(empty - 1, locker.countEmpty());

        deliveryManager.takeOutDelivery(locker, receiver1, "12345");
        locker = lockerRepository.get(locker.getId());
    }

    @Test
    void Should_UnlockDepositBox_WhenDeliveryTookOut() {
        Delivery delivery = deliveryManager.makeParcelDelivery(
            basePrice, 10, 20, 30, 10, false, shipper1, receiver1, locker
        );
        deliveryManager.putInLocker(delivery, "54321");
        locker = lockerRepository.get(locker.getId());
        int empty = locker.countEmpty();
        deliveryManager.takeOutDelivery(locker, receiver1, "54321");
        locker = lockerRepository.get(locker.getId());

        assertEquals(empty + 1, locker.countEmpty());
    }

    @Test
    void Should_ThrowException_WhenLockerIsFull() {
        Locker oneBoxLocker = new Locker("LDZ12", "Gawronska 66, Lodz 12-123", 1);
        lockerRepository.add(oneBoxLocker);

        Delivery testDelivery = deliveryManager.makeParcelDelivery(
            basePrice, 10, 20, 30, 10, false, shipper1, receiver1, oneBoxLocker
        );
        deliveryManager.putInLocker(testDelivery, "1111");
        locker = lockerRepository.get(oneBoxLocker.getId());

        assertThrows(DeliveryManagerException.class, () -> deliveryManager.putInLocker(testDelivery, "1111"));

        deliveryManager.takeOutDelivery(locker, receiver1, "1111");
        locker = lockerRepository.get(oneBoxLocker.getId());
    }

    @Test
    void Should_ReturnAllClientDeliveries() {
        Delivery delivery = deliveryManager.makeParcelDelivery(
            basePrice, 10, 20, 30, 10, false, shipper1, receiver1, locker
        );
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
        deliveryManager.putInLocker(delivery1, "2222");
        locker = lockerRepository.get(locker.getId());
        deliveryManager.takeOutDelivery(locker, receiver1, "2222");
        locker = lockerRepository.get(locker.getId());

        assertTrue(0 < deliveryManager.getAllReceivedClientDeliveries(receiver1).size());
    }

    @Test
    void Should_ReturnCorrectBalanceForClientShipments() {
        Delivery delivery = deliveryManager.makeParcelDelivery(
            basePrice, 10, 20, 30, 10, false, shipper1, receiver1, locker
        );
        deliveryManager.putInLocker(delivery, "5555");
        locker = lockerRepository.get(locker.getId());

        assertEquals(new BigDecimal("15.000"), deliveryManager.checkClientShipmentBalance(shipper1));

        deliveryManager.takeOutDelivery(delivery.getLocker(), receiver1, "5555");
        locker = lockerRepository.get(locker.getId());
    }

    @Test
    void Should_ThrowException_WhenInvalidValuesPassed() {
        assertThrows(DeliveryManagerException.class, () -> deliveryManager.checkClientShipmentBalance(null));
    }
}
