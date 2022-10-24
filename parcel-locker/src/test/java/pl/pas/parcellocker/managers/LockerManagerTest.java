package pl.pas.parcellocker.managers;

import org.junit.jupiter.api.Test;
import pl.pas.parcellocker.config.TestsConfig;
import pl.pas.parcellocker.exceptions.LockerManagerException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LockerManagerTest extends TestsConfig {

    private static final LockerManager lockerManager = new LockerManager();;

    @Test
    void Should_CreateLocker() {
        lockerManager.createLocker("LDZ69", 10);
        assertTrue(lockerManager.getLocker("LDZ69").isPresent());
    }

    @Test
    void Should_ThrowException_WhenGivenLockerNameIsDuplicated() {
        lockerManager.createLocker("LDZ11", 10);
        assertThrows(LockerManagerException.class, () -> lockerManager.createLocker("LDZ11", 10));
    }
}
