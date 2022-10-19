package pl.pas.parcellocker.managers;

import pl.pas.parcellocker.model.Locker;
import pl.pas.parcellocker.repositories.LockerRepository;

public class LockerManager {

    private final LockerRepository lockerRepository;

    public LockerManager() {
        lockerRepository = new LockerRepository();
    }

    public Locker createLocker(int depositBoxCount) {
        Locker locker = new Locker(depositBoxCount);
        lockerRepository.add(locker);
        return locker;
    }
}
