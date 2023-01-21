package pl.pas.parcellocker.managers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import pl.pas.parcellocker.config.TestsConfig;
import pl.pas.parcellocker.exceptions.LockerManagerException;
import pl.pas.parcellocker.model.delivery.Delivery;
import pl.pas.parcellocker.model.locker.Locker;
import pl.pas.parcellocker.model.user.Client;
import pl.pas.parcellocker.model.user.User;

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

        DeliveryManager deliveryManager =
            new DeliveryManager(deliveryRepository, lockerRepository, clientRepository);
        User shipper1 = new Client("Oscar", "Trel", "321312312", "P@ssw0rd");
        User receiver1 = new Client("Bartosh", "Siekan", "123123123", "P@ssw0rd");
        clientRepository.add(shipper1);
        clientRepository.add(receiver1);
        Delivery delivery =
            deliveryManager.makeParcelDelivery(
                BigDecimal.TEN,
                10,
                20,
                30,
                10,
                false,
                shipper1.getTelNumber(),
                receiver1.getTelNumber(),
                locker.getIdentityNumber());
        deliveryManager.putInLocker(delivery.getId(), delivery.getLocker().getIdentityNumber(), "123");

        assertThrows(LockerManagerException.class, () -> lockerManager.removeLocker("LDZ12"));

        deliveryManager.takeOutDelivery(delivery.getId(), receiver1.getTelNumber(), "123");
    }

    @Test
    void Should_ThrowException_WhenGivenLockerNameIsDuplicated() {
        lockerManager.createLocker("LDZ11", "Gawronska 12, Lodz 12-123", 10);
        assertThrows(
            LockerManagerException.class,
            () -> lockerManager.createLocker("LDZ11", "Gawronska 22, Lodz 12-123", 10));
    }
}
