package pl.pas.parcellocker.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.pas.parcellocker.model.Locker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LockerMongoRepositoryTest {
    LockerRepository lockerRepository = new LockerRepository();

    Locker locker1;
    Locker locker2;

    @BeforeEach
    void setup() {
        locker1 = new Locker("LDZ05", "test-address1", 10);
        locker2 = new Locker("LDZ06", "test-address2", 20);
    }

    @Test
    void Should_AddDelivery() {
       lockerRepository.save(locker1);

       //assertEquals(getLockerFromRepo(locker1).getId(), locker1.getEntityId());
    }

//    @Test
//    void Should_UpdateDeliveries() {
//        String newAddress = "new-test-address";
//
//        lockerRepository.add(locker1);
//        locker1.setAddress(newAddress);
//        lockerRepository.update(locker1);
//
//        assertEquals(getLockerFromRepo(locker1).getAddress(), newAddress);
//    }
//
//    @Test
//    void Should_DeleteDelivery() {
//        lockerRepository.add(locker1);
//
//        lockerRepository.delete(locker1.getEntityId().getUUID());
//
//        assertNull(getLockerFromRepo(locker1));
//    }
//
//    @Test
//    void Should_ReturnAllDeliveries() {
//        lockerRepository.add(locker1);
//        lockerRepository.add(locker2);
//
//        assertTrue(lockerRepository.findAll().size() >= 2);
//    }
//
//    private Locker getLockerFromRepo(Locker locker) {
//        return lockerRepository.findById(locker.getId());
//    }

}
