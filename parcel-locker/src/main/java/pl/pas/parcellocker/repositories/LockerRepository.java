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

    public void update(Locker locker) {
        try {
            EntityManager entityManager = getEntityManager();

            entityManager.getTransaction().begin();
            entityManager.merge(locker);
            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
            log.error(e.getMessage());
        }
    }
}
