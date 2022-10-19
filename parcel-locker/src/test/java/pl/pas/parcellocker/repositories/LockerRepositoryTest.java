package pl.pas.parcellocker.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.pas.parcellocker.config.TestsConfig;
import pl.pas.parcellocker.model.Client;
import pl.pas.parcellocker.model.Locker;

import java.util.UUID;
import java.util.concurrent.locks.Lock;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LockerRepositoryTest extends TestsConfig {
    public LockerRepository lockerRepository;
    public Locker locker;

    @BeforeEach
    void setup() {
        lockerRepository = new LockerRepository();
        locker = new Locker(20);
    }

    @Test
    void shouldReturnAllClients() {
        lockerRepository.add(locker);
    }
}
