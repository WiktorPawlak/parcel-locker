package pl.pas.parcellocker.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import pl.pas.parcellocker.config.TestsConfig;
import pl.pas.parcellocker.model.delivery.Delivery;
import pl.pas.parcellocker.model.locker.DepositBox;
import pl.pas.parcellocker.model.locker.Locker;
import pl.pas.parcellocker.model.user.Client;
import pl.pas.parcellocker.repositories.hibernate.LockerRepositoryHibernate;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LockerTest extends TestsConfig {

    private final LockerRepositoryHibernate lockerRepository = new LockerRepositoryHibernate();
    private Delivery delivery;
    private Locker locker;

    @BeforeAll
    void setup() {
        Client c1 = new Client("test", "test", "1231", "P@ssw0rd");
        Client c2 = new Client("test", "test", "1231", "P@ssw0rd");
        locker = new Locker("LDZ01", "Gawronska 12, Lodz 12-123", 10);
        delivery = new Delivery(BigDecimal.TEN, true, c1, c2, locker);
    }

    @Test
    void Should_ReturnNumberOfEmptyDepositBoxes() {
        Locker newLocker = new Locker("LDZ02", "Gawronska 26, Lodz 12-123", 10);
        assertEquals(10, newLocker.countEmpty());
    }

    @Test
    void Should_UpdateDepositBoxFieldsAndSetIsEmptyAsFalse_WhenPutIn() {
        lockerRepository.add(locker);
        UUID depositBoxId = locker.putIn(delivery, "123456789", "k0z4k");

        DepositBox depositBox = locker.getDepositBox(depositBoxId);
        assertEquals("123456789", depositBox.getTelNumber());
        assertEquals("k0z4k", depositBox.getAccessCode());
        assertEquals(delivery.getId(), depositBox.getDelivery().getId());
    }

}
