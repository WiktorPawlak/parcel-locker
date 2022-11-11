package pl.pas.parcellocker.managers;

import org.junit.jupiter.api.Test;
import pl.pas.parcellocker.config.TestsConfig;
import pl.pas.parcellocker.exceptions.LockerManagerException;
import pl.pas.parcellocker.model.Client;
import pl.pas.parcellocker.model.Delivery;
import pl.pas.parcellocker.model.Locker;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LockerManagerTest extends TestsConfig {

    private static final LockerManager lockerManager = new LockerManager();

    @Test
    void Should_CreateLocker() {
        lockerManager.createLocker("LDZ69", "Gawronska 9, Lodz 12-123", 10);
        assertTrue(lockerManager.getLocker("LDZ69").isPresent());
    }

    @Test
    void Should_RemoveLocker() {
        lockerManager.createLocker("LDZ12", "Gawronska 9, Lodz 12-123", 10);
        lockerManager.removeLocker("LDZ12");
        assertFalse(lockerManager.getLocker("LDZ12").isPresent());
    }

    @Test
    void Should_ThrowException_WhenThereIsAllocationOnLocker() {
        Locker locker = lockerManager.createLocker("LDZ12", "Gawronska 9, Lodz 12-123", 10);

        DeliveryManager deliveryManager = new DeliveryManager();
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
