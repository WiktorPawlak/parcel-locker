package pl.pas.parcellocker.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class LockerTest {

    Locker locker = new Locker(10);
    UUID deliveryId = new UUID(5,5);

    @Test
    void shouldReturnNumberOfEmptyDepositBoxes() {
        assertEquals(10, locker.countEmpty());
    }

    @Test
    void whenPutInShouldUpdateDepositBoxFieldsAndSetIsEmptyAsFalse() {
        String depositBoxId = locker.putIn(deliveryId,"123456789","k0z4k");

        DepositBox depositBox = locker.getDepositBox(depositBoxId);
        assertEquals("123456789", depositBox.getTelNumber());
        assertEquals("k0z4k", depositBox.getAccessCode());
        assertEquals(deliveryId, depositBox.getDeliveryId());
    }

}
