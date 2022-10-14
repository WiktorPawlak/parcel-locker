package pl.pas.parcellocker.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static pl.pas.parcellocker.model.DeliveryStatus.READY_TO_PICKUP;
import static pl.pas.parcellocker.model.DeliveryStatus.READY_TO_SHIP;
import static pl.pas.parcellocker.model.DeliveryStatus.RECEIVED;

class DeliveryTest {

    public Delivery delivery1;
    public Delivery delivery2;
    public Client shipper1;
    public Client receiver1;
    public Package box;
    public Package list;
    public Locker locker;
    public BigDecimal basePrice = BigDecimal.TEN;

    @BeforeEach
    void setup() {
        locker = new Locker(20);
        shipper1 = new Client("Oscar", "Trel", "321312312");
        receiver1 = new Client("Bartosh", "Siekan", "123123123");
        delivery1 = new Delivery(basePrice, 10, 20, 30, 10, true, shipper1, receiver1, locker);
        box = new Parcel(basePrice, 10, 20, 30, 10, true);
        delivery2 = new Delivery(basePrice, true, shipper1, receiver1, locker);
        list = new List(basePrice, true);
    }

    @Test
    void deliveryParcelConstructor() {
        assertEquals(shipper1, delivery1.getShipper());
        assertEquals(receiver1, delivery1.getReceiver());
        assertEquals(READY_TO_SHIP, delivery1.getStatus());
        assertEquals(box.toString(), delivery1.getPack().toString());
        assertEquals(locker, delivery1.getLocker());
    }

    @Test
    void deliveryListConstructor() {
        assertEquals(shipper1, delivery2.getShipper());
        assertEquals(receiver1, delivery2.getReceiver());
        assertEquals(READY_TO_SHIP, delivery2.getStatus());
        assertEquals(list.toString(), delivery2.getPack().toString());
        assertEquals(locker, delivery2.getLocker());
    }

    @Test
    void deliverySetterConstructor() {
        assertEquals(READY_TO_SHIP, delivery1.getStatus());
        delivery1.setStatus(READY_TO_PICKUP);
        assertEquals(READY_TO_PICKUP, delivery1.getStatus());
        delivery1.setStatus(RECEIVED);
        assertEquals(RECEIVED, delivery1.getStatus());
    }
}
