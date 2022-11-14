package pl.pas.parcellocker.repositories.hibernate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import pl.pas.parcellocker.config.UnitTestsConfig;
import pl.pas.parcellocker.model.locker.Locker;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LockerRepositoryHibernateTest extends UnitTestsConfig {
    private Locker l1;

    @BeforeAll
    void setup() {
        l1 = new Locker("LDZ01", "Gawronska 12, Lodz 12-123", 10);
        lockerRepository.add(l1);
    }

    @Test
    void Should_CreateLocker() {
        assertDoesNotThrow(() -> lockerRepository.get(l1.getId()));
    }

    @Test
    void Should_RemoveLocker() {
        int count = lockerRepository.size();
        lockerRepository.remove(l1);
        assertTrue(count > lockerRepository.size());
    }
}
