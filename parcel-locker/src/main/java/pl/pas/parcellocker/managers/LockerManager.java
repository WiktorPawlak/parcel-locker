package pl.pas.parcellocker.managers;

import pl.pas.parcellocker.exceptions.LockerManagerException;
import pl.pas.parcellocker.model.locker.Locker;
import pl.pas.parcellocker.model.locker.LockerRepository;
import pl.pas.parcellocker.repositories.hibernate.LockerRepositoryHibernate;

import java.util.List;
import java.util.Optional;

public class LockerManager {

    private final LockerRepository lockerRepository;

    public LockerManager(LockerRepository lockerRepository) {
        this.lockerRepository = lockerRepository;
    }

    public Locker createLocker(String identityNumber, String address, int depositBoxCount) {
        checkIfDuplicatedName(identityNumber);

        Locker locker = new Locker(identityNumber, address, depositBoxCount);
        lockerRepository.add(locker);
        return locker;
    }

    private void checkIfDuplicatedName(String name) {
        if (lockerRepository.findByIdentityNumber(name).isPresent())
            error("Locker with given name already exists.");
    }

    public Optional<Locker> getLocker(String identityNumber) {
        return lockerRepository.findByIdentityNumber(identityNumber);
    }

    public void removeLocker(String identityNumber) {
        Locker lockerToRemove = getLocker(identityNumber)
            .orElseThrow(() ->
                new LockerManagerException("Locker with given name doesn't exist.")
            );
        try {
            lockerRepository.remove(lockerToRemove);
        } catch (Exception e) {
            error("Couldn't remove locker.", e);
        }
    }

    private void error(String msg) {
        throw new LockerManagerException(msg);
    }

    private void error(String msg, Throwable throwable) {
        throw new LockerManagerException(msg, throwable);
    }
}
