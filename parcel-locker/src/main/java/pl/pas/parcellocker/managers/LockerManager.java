package pl.pas.parcellocker.managers;

import pl.pas.parcellocker.exceptions.LockerManagerException;
import pl.pas.parcellocker.model.Locker;
import pl.pas.parcellocker.repositories.LockerRepository;

public class LockerManager {

    private final LockerRepository lockerRepository;

    public LockerManager(LockerRepository lockerRepository) {
        this.lockerRepository =lockerRepository;
    }

    public Locker createLocker(String identityNumber, String address, int depositBoxCount) {
        checkIfDuplicatedName(identityNumber);

        Locker locker = new Locker(identityNumber, address, depositBoxCount);
        lockerRepository.save(locker);
        return locker;
    }

    private void checkIfDuplicatedName(String name) {
        Locker sameNameLocker = lockerRepository.findByIdentityNumber(name);
        if (sameNameLocker != null)
            throw new LockerManagerException("Locker with given name already exists.");
    }

    public Locker getLocker(String name) {
        return lockerRepository.findByIdentityNumber(name);
    }
}
