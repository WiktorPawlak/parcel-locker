package pl.pas.parcellocker.model.locker;

import java.util.Optional;
import java.util.UUID;

public interface LockerRepository {
    Locker get(UUID lockerId);
    void add(Locker locker);
    void remove(Locker locker);
    Optional<Locker> findByIdentityNumber(String identityNumber);
}
