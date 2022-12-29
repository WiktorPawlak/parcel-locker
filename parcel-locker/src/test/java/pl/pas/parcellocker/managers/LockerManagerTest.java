package pl.pas.parcellocker.managers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.pas.parcellocker.config.TestsConfig;
import pl.pas.parcellocker.exceptions.LockerManagerException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LockerManagerTest extends TestsConfig {

    private final LockerManager lockerManager = new LockerManager(lockerRepository);

    @BeforeEach
    void clear() {
        lockerRepository.clear();
    }

    @Test
    void Should_CreateLocker() {
        lockerManager.createLocker("LDZ69", "Gawronska 9, Lodz 12-123", 10);
        assertNotNull(lockerManager.getLocker("LDZ69"));
    }

    @Test
    void Should_ThrowException_WhenGivenLockerNameIsDuplicated() {
        lockerManager.createLocker("LDZ11", "Gawronska 12, Lodz 12-123", 10);
        assertThrows(LockerManagerException.class, () -> lockerManager.createLocker("LDZ11", "Gawronska 22, Lodz 12-123", 10));
    }
}
