package pl.pas.parcellocker.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.pas.parcellocker.model.DeliveryStatus.READY_TO_PICKUP;
import static pl.pas.parcellocker.model.DeliveryStatus.READY_TO_SHIP;
import static pl.pas.parcellocker.model.DeliveryStatus.RECEIVED;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DeliveryTest {

    private Delivery delivery1;
    private Delivery delivery2;
    private Client shipper1;
    private Client receiver1;
    private Locker locker;
    private final BigDecimal basePrice = BigDecimal.TEN;

    @BeforeAll
    void setup() {
        locker = new Locker("LDZ01", "Gawronska 12, Lodz 12-123", 20);
        shipper1 = new Client("Oscar", "Trel", "321312312");
        receiver1 = new Client("Bartosh", "Siekan", "123123123");
        delivery1 = new Delivery(basePrice, 10, 20, 30, 10, true, shipper1, receiver1, locker);
        delivery2 = new Delivery(basePrice, true, shipper1, receiver1, locker);
    }

    @Test
    void Should_ReturnCorrectObjectsForParcelDelivery_WhenGettersCalled() {
        assertEquals(shipper1, delivery1.getShipper());
        assertEquals(receiver1, delivery1.getReceiver());
        assertEquals(READY_TO_SHIP, delivery1.getStatus());
        assertEquals(locker, delivery1.getLocker());
    }

    @Test
    void Should_ReturnCorrectObjectsForListDelivery_WhenGettersCalled() {
        assertEquals(shipper1, delivery2.getShipper());
        assertEquals(receiver1, delivery2.getReceiver());
        assertEquals(READY_TO_SHIP, delivery2.getStatus());
        assertEquals(locker, delivery2.getLocker());
    }

    @Test
    void Should_SetterSetCorrectStatus() {
        assertEquals(READY_TO_SHIP, delivery1.getStatus());
        delivery1.setStatus(READY_TO_PICKUP);
        assertEquals(READY_TO_PICKUP, delivery1.getStatus());
        delivery1.setStatus(RECEIVED);
        assertEquals(RECEIVED, delivery1.getStatus());
    }
}
