package pl.pas.parcellocker.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.pas.parcellocker.model.EntityClass;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
public class Repository<T extends EntityClass> {

    protected static EntityManagerFactory emf = Persistence.createEntityManagerFactory("ParcelLocker");
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    private Class<T> entityClass;

    public T get(UUID id) {
        return (T) getEntityManager()
            .createQuery("select e from " + entityClass.getSimpleName() + " e where e.id = :id")
            .setParameter("id", id)
            .getSingleResult();
    }

    public void add(T object) {
        if (object != null) {
            try {
                EntityManager entityManager = getEntityManager();

                entityManager.getTransaction().begin();

                entityManager.persist(object);
                flushAndClear(entityManager);

                entityManager.getTransaction().commit();
            } catch (PersistenceException e) {
                log.error(e.getMessage());
            }
        }
    }

    public void remove(T object) {
        if (object != null) {
            try {
                EntityManager entityManager = getEntityManager();

                entityManager.getTransaction().begin();

                UUID objectId = object.getId();
                T retrievedObject = get(objectId);
                entityManager.remove(entityManager.merge(retrievedObject));
                flushAndClear(entityManager);

                entityManager.getTransaction().commit();
            } catch (PersistenceException e) {
                log.error(e.getMessage());
            }
        }
    }

    public String report() {
        StringBuilder stringBuilder = new StringBuilder();
        for (T object : findAll()) {
            stringBuilder.append(object.toString());
        }
        return stringBuilder.toString();
    }

    public int size() {
        return findAll().size();
    }

    public List<T> findBy(Predicate<T> predicate) {
        return (List<T>) getEntityManager()
            .createQuery("select e from " + entityClass.getSimpleName() + " e")
            .getResultList().stream()
            .filter(predicate)
            .collect(Collectors.toList());
    }

    public List<T> findAll() {
        return findBy((T) -> true);
    }


    void flushAndClear(EntityManager entityManager) {
        entityManager.flush();
        entityManager.clear();
    }
}
