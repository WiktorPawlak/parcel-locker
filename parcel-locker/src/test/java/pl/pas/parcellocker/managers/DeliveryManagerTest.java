//package pl.pas.parcellocker.managers;
//
//import jakarta.persistence.OptimisticLockException;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//import pl.pas.parcellocker.config.TestsConfig;
//import pl.pas.parcellocker.exceptions.DeliveryManagerException;
//import pl.pas.parcellocker.exceptions.LockerException;
//import pl.pas.parcellocker.exceptions.RepositoryException;
//import pl.pas.parcellocker.model.Client;
//import pl.pas.parcellocker.model.Delivery;
//import pl.pas.parcellocker.model.Locker;
//
//import java.math.BigDecimal;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//class DeliveryManagerTest extends TestsConfig {
//
//    private final DeliveryManager deliveryManager = new DeliveryManager();
//    private Client shipper1;
//    private Client receiver1;
//    private Locker locker;
//    private final BigDecimal basePrice = BigDecimal.TEN;
//
//    @BeforeAll
//    void setup() {
//        locker = new Locker("LDZ01", "Gawronska 12, Lodz 12-123", 20);
//        shipper1 = new Client("Oscar", "Trel", "321312312");
//        receiver1 = new Client("Bartosh", "Siekan", "123123123");
//       // clientRepository.add(shipper1);
//      //  clientRepository.add(receiver1);
//        lockerRepository.add(locker);
//    }
//
//    @AfterEach
//    void eachFinisher() {
//        deliveryRepository.findAll().forEach(deliveryRepository::remove);
//    }
//
//    @Test
//    void Should_BlockDepositBox_WhenDeliveryPutInLocker() {
//        Delivery delivery = deliveryManager.makeParcelDelivery(
//            basePrice, 10, 20, 30, 10, false, shipper1, receiver1, locker
//        );
//        int empty = locker.countEmpty();
//        deliveryManager.putInLocker(delivery, "12345");
//
//        assertEquals(empty - 1, locker.countEmpty());
//    }
//
//    @Test
//    void Should_UnlockDepositBox_WhenDeliveryTookOut() {
//        Delivery delivery = deliveryManager.makeParcelDelivery(
//            basePrice, 10, 20, 30, 10, false, shipper1, receiver1, locker
//        );
//        deliveryManager.putInLocker(delivery, "54321");
//        int empty = locker.countEmpty();
//        deliveryManager.takeOutDelivery(locker, receiver1, "54321");
//
//        assertEquals(empty + 1, locker.countEmpty());
//    }
//
//    @Test
//    void Should_ThrowException_WhenLockerIsFull() {
//        Locker oneBoxLocker = new Locker("LDZ12", "Gawronska 66, Lodz 12-123", 1);
//        lockerRepository.add(oneBoxLocker);
//
//        Delivery testDelivery = deliveryManager.makeParcelDelivery(
//            basePrice, 10, 20, 30, 10, false, shipper1, receiver1, oneBoxLocker
//        );
//        deliveryManager.putInLocker(testDelivery, "1111");
//
//        assertThrows(LockerException.class, () -> deliveryManager.putInLocker(testDelivery, "1111"));
//    }
//
//    @Test
//    void Should_ReturnAllClientDeliveries() {
//        Delivery delivery = deliveryManager.makeParcelDelivery(
//            basePrice, 10, 20, 30, 10, false, shipper1, receiver1, locker
//        );
//        Delivery delivery1 = deliveryManager.makeParcelDelivery(
//            basePrice, 10, 20, 30, 10, false, shipper1, receiver1, locker
//        );
//
//        assertEquals(delivery, deliveryManager.getAllClientDeliveries(receiver1).get(0));
//        assertEquals(delivery1, deliveryManager.getAllClientDeliveries(receiver1).get(1));
//    }
//
//    @Test
//    void Should_ReturnAllReceivedDeliveriesForGivenClient() {
//        Delivery delivery1 = deliveryManager.makeParcelDelivery(
//            basePrice, 10, 20, 30, 10, false, shipper1, receiver1, locker
//        );
//        deliveryManager.putInLocker(delivery1 ,"2222");
//        deliveryManager.takeOutDelivery(locker, receiver1,"2222");
//
//        assertTrue(0 < deliveryManager.getAllReceivedClientDeliveries(receiver1).size());
//    }
//
//    @Test
//    void Should_ReturnCorrectBalanceForClientShipments() {
//        Delivery delivery = deliveryManager.makeParcelDelivery(
//            basePrice, 10, 20, 30, 10, false, shipper1, receiver1, locker
//        );
//        deliveryManager.putInLocker(delivery ,"5555");
//        assertEquals(new BigDecimal("15.000"), deliveryManager.checkClientShipmentBalance(shipper1));
//    }
//
//    @Test
//    void Should_ThrowException_WhenInvalidValuesPassed() {
//        assertThrows(DeliveryManagerException.class, () -> deliveryManager.checkClientShipmentBalance(null));
//    }
//
//    @Test
//    void Should_ThrowOptimisticLockException_WhenSameDeliveryPutInLockerAtOnes() {
//        Delivery delivery = deliveryManager.makeParcelDelivery(
//            basePrice, 10, 20, 30, 10, false, shipper1, receiver1, locker
//        );
//        deliveryManager.putInLocker(delivery ,"1122");
//        assertThrows(OptimisticLockException.class, () -> {
//            try { deliveryManager.putInLocker(delivery ,"2211"); }
//            catch (RepositoryException e) { throw e.getCause(); }
//        });
//    }
//}
