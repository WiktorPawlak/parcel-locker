package pl.pas.parcellocker.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import lombok.extern.slf4j.Slf4j;
import pl.pas.parcellocker.model.Locker;


@Slf4j
public class LockerRepository extends Repository<Locker> {

    public LockerRepository() {
        super(Locker.class);
    }
}
