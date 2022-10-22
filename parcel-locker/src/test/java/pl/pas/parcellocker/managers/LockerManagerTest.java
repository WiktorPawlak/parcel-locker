package pl.pas.parcellocker.managers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.pas.parcellocker.config.TestsConfig;
import pl.pas.parcellocker.model.Locker;

import javax.sound.sampled.FloatControl;

import static org.junit.jupiter.api.Assertions.*;

class LockerManagerTest extends TestsConfig {

    private static LockerManager lockerManager;

    @BeforeAll
    static void setup() {
        lockerManager = new LockerManager();
    }

    @Test
    void createAndGetLocker() {
        lockerManager.createLocker("LDZ01", 10);
        assertTrue(lockerManager.getLocker("LDZ01").isPresent());
    }
}
