package pl.pas.parcellocker.managers;

import org.junit.jupiter.api.Test;
import pl.pas.parcellocker.config.TestsConfig;
import pl.pas.parcellocker.exceptions.LockerManagerException;
import pl.pas.parcellocker.model.client.Client;
import pl.pas.parcellocker.model.delivery.Delivery;
import pl.pas.parcellocker.model.locker.Locker;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LockerManagerTest extends TestsConfig {

    private final LockerManager lockerManager = new LockerManager(lockerRepository);

    @Test
    void Should_CreateLocker() {
        Locker locker = lockerManager.createLocker("LDZ69", "Gawronska 9, Lodz 12-123", 10);
        assertEquals(lockerManager.getLocker("LDZ69").getIdentityNumber(), locker.getIdentityNumber());
    }

    @Test
    void Should_RemoveLocker() {
        Locker locker = lockerManager.createLocker("LDZ12", "Gawronska 9, Lodz 12-123", 10);
        lockerManager.removeLocker("LDZ12");
        assertThrows(LockerManagerException.class, () -> lockerManager.getLocker("LDZ12"));
    }

    @Test
    void Should_ThrowException_WhenThereIsAllocationOnLocker() {
        Locker locker = lockerManager.createLocker("LDZ12", "Gawronska 9, Lodz 12-123", 10);

        DeliveryManager deliveryManager = new DeliveryManager(deliveryRepository, lockerRepository);
        Client shipper1 = new Client("Oscar", "Trel", "321312312");
        Client receiver1 = new Client("Bartosh", "Siekan", "123123123");
        clientRepository.add(shipper1);
        clientRepository.add(receiver1);
        Delivery delivery = deliveryManager.makeParcelDelivery(
            BigDecimal.TEN, 10, 20, 30, 10, false, shipper1, receiver1, locker
        );
        deliveryManager.putInLocker(delivery, "123");

        assertThrows(LockerManagerException.class, () -> lockerManager.removeLocker("LDZ12"));

        deliveryManager.takeOutDelivery(locker, receiver1, "123");
    }

    @Test
    void Should_ThrowException_WhenGivenLockerNameIsDuplicated() {
        lockerManager.createLocker("LDZ11", "Gawronska 12, Lodz 12-123", 10);
        assertThrows(LockerManagerException.class, () -> lockerManager.createLocker("LDZ11", "Gawronska 22, Lodz 12-123", 10));
    }
}
