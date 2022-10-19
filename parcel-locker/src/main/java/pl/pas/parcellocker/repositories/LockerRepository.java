package pl.pas.parcellocker.repositories;

import pl.pas.parcellocker.model.Locker;

public class LockerRepository extends Repository<Locker> {
    public LockerRepository() {
        super(Locker.class);
    }
}
