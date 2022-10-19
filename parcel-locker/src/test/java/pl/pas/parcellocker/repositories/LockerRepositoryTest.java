package pl.pas.parcellocker.repositories;

import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.pas.parcellocker.config.TestsConfig;
import pl.pas.parcellocker.model.Client;
import pl.pas.parcellocker.model.Delivery;
import pl.pas.parcellocker.model.DeliveryStatus;
import pl.pas.parcellocker.model.Locker;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.locks.Lock;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LockerRepositoryTest extends TestsConfig {
    public LockerRepository lockerRepository;
    public Locker l1;

    @BeforeEach
    void setup() {
        lockerRepository = new LockerRepository();
        l1 = new Locker(10);
        lockerRepository.add(l1);
    }

    @Test
    void createLocker() {
        assertDoesNotThrow(() -> lockerRepository.get(l1.getId()));
    }

    @Test
    void removeLocker() {
        int count = lockerRepository.size();
        lockerRepository.remove(l1);
        assertTrue(count > lockerRepository.size());
    }
}
