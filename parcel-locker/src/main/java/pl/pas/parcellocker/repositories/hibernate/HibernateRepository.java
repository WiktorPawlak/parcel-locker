package pl.pas.parcellocker.repositories.hibernate;

import static pl.pas.parcellocker.repositories.hibernate.EntityManagerUtil.getEntityManager;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.pas.parcellocker.exceptions.RepositoryException;
import pl.pas.parcellocker.model.EntityModel;

@Slf4j
@AllArgsConstructor
public class HibernateRepository<T extends EntityModel> {

    private Class<T> entityClass;

    public T get(UUID id) {
        return (T) getEntityManager()
            .createQuery("select e from " + entityClass.getSimpleName() + " e where e.id = :id")
            .setParameter("id", id)
            .getSingleResult();
    }

    public synchronized void add(T object) {
        if (object != null) {
            try {
                EntityManager entityManager = getEntityManager();

                entityManager.getTransaction().begin();

                entityManager.persist(object);
                flushAndClear(entityManager);

                entityManager.getTransaction().commit();

            } catch (PersistenceException e) {
                throw new RepositoryException(e);
            }
        }
    }

    public synchronized void update(T object) {
        try {
            EntityManager entityManager = getEntityManager();

            entityManager.getTransaction().begin();

            entityManager.merge(object);

            entityManager.getTransaction().commit();

        } catch (PersistenceException e) {
            throw new RepositoryException(e);
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
                throw new RepositoryException(e);
            }
        }
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
