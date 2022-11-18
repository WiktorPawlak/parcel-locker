package pl.pas.parcellocker.repositories.hibernate;

import pl.pas.parcellocker.model.locker.Locker;
import pl.pas.parcellocker.model.locker.LockerRepository;

import java.util.Optional;

public class LockerRepositoryHibernate extends HibernateRepository<Locker> implements LockerRepository {
    public LockerRepositoryHibernate() {
        super(Locker.class);
    }

    public Optional<Locker> findByIdentityNumber(String identityNumber) {
        return findBy(locker -> locker.getIdentityNumber().equals(identityNumber)).stream().findFirst();
    }
}
