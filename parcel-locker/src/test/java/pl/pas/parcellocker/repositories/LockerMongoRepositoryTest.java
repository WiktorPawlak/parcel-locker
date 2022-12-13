//package pl.pas.parcellocker.repositories;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import pl.pas.parcellocker.model.Delivery;
//import pl.pas.parcellocker.model.Locker;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNull;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static pl.pas.parcellocker.model.DeliveryStatus.RECEIVED;
//
//class LockerMongoRepositoryTest {
//    LockerMongoRepository abstractMongoRepository = new LockerMongoRepository();
//
//    Locker locker1;
//    Locker locker2;
//
//    @BeforeEach
//    void setup() {
//        locker1 = new Locker("LDZ05", "test-address1", 10);
//        locker2 = new Locker("LDZ06", "test-address2", 20);
//    }
//
//    @Test
//    void Should_AddDelivery() {
//       abstractMongoRepository.add(locker1);
//
//       assertEquals(getLockerFromRepo(locker1).getId(), locker1.getId());
//    }
//
//    @Test
//    void Should_UpdateDeliveries() {
//        String newAddress = "new-test-address";
//
//        abstractMongoRepository.add(locker1);
//        locker1.setAddress(newAddress);
//        abstractMongoRepository.update(locker1);
//
//        assertEquals(getLockerFromRepo(locker1).getAddress(), newAddress);
//    }
//
//    @Test
//    void Should_DeleteDelivery() {
//        abstractMongoRepository.add(locker1);
//
//        abstractMongoRepository.delete(locker1.getEntityId().getUUID());
//
//        assertNull(getLockerFromRepo(locker1));
//    }
//
//    @Test
//    void Should_ReturnAllDeliveries() {
//        abstractMongoRepository.add(locker1);
//        abstractMongoRepository.add(locker2);
//
//        assertTrue(abstractMongoRepository.findAll().size() >= 2);
//    }
//
//    private Locker getLockerFromRepo(Locker locker) {
//        return abstractMongoRepository.findById(locker.getId());
//    }
//
//}
