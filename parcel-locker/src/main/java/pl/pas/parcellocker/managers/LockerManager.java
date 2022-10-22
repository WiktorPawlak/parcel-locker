package pl.pas.parcellocker.managers;

import pl.pas.parcellocker.exceptions.LockerManagerException;
import pl.pas.parcellocker.model.Locker;
import pl.pas.parcellocker.repositories.LockerRepository;

import java.util.List;
import java.util.Optional;

public class LockerManager {

    private final LockerRepository lockerRepository;

    public LockerManager() {
        lockerRepository = new LockerRepository();
    }

    public Locker createLocker(String name, int depositBoxCount) {
        checkIfDuplicatedName(name);

        Locker locker = new Locker(name, depositBoxCount);
        lockerRepository.add(locker);
        return locker;
    }

    private void checkIfDuplicatedName(String name) {
        List<Locker> sameNameLockers = lockerRepository.findBy(locker -> locker.getName().equals(name));
        if (sameNameLockers.size() > 0)
            throw new LockerManagerException("Locker with given name already exists.");
    }

    public Optional<Locker> getLocker(String name) {
        return lockerRepository.findBy(locker -> locker.getName().equals(name)).stream().findFirst();
    }
}
