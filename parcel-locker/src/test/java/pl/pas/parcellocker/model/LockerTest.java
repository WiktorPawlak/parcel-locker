package pl.pas.parcellocker.model;

import org.junit.jupiter.api.Test;
import pl.pas.parcellocker.config.TestsConfig;
import pl.pas.parcellocker.repositories.LockerRepository;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class LockerTest extends TestsConfig {

    Client c1 = new Client("test", "test", "1231");
    Client c2 = new Client("test", "test", "1231");
    Locker locker = new Locker(10);
    Delivery delivery = new Delivery(BigDecimal.TEN, true, c1, c2, locker);

    LockerRepository lockerRepository = new LockerRepository();

    @Test
    void shouldReturnNumberOfEmptyDepositBoxes() {
        assertEquals(10, locker.countEmpty());
    }

    @Test
    void whenPutInShouldUpdateDepositBoxFieldsAndSetIsEmptyAsFalse() {
        lockerRepository.add(locker);
        UUID depositBoxId = locker.putIn(delivery,"123456789","k0z4k");

        DepositBox depositBox = locker.getDepositBox(depositBoxId);
        assertEquals("123456789", depositBox.getTelNumber());
        assertEquals("k0z4k", depositBox.getAccessCode());
        assertEquals(delivery.getId(), depositBox.getDeliveryId());
    }

}
