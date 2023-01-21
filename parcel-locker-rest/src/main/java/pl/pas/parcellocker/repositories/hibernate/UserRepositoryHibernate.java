package pl.pas.parcellocker.repositories.hibernate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;
import pl.pas.parcellocker.exceptions.RepositoryException;
import pl.pas.parcellocker.model.user.User;
import pl.pas.parcellocker.model.user.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static pl.pas.parcellocker.repositories.hibernate.EntityManagerUtil.getEntityManager;


public class UserRepositoryHibernate extends HibernateRepository<User> implements UserRepository {

    public UserRepositoryHibernate() {
        super(User.class);
    }

    public void archive(UUID id) {
        try {
            EntityManager entityManager = getEntityManager();

            entityManager.getTransaction().begin();
            entityManager.find(User.class, id).setActive(false);
            entityManager.getTransaction().commit();

        } catch (PersistenceException e) {
            throw new RepositoryException(e);
        }
    }

    public void unarchive(UUID id) {
        try {
            EntityManager entityManager = getEntityManager();

            entityManager.getTransaction().begin();
            entityManager.find(User.class, id).setActive(true);
            entityManager.getTransaction().commit();

        } catch (PersistenceException e) {
            throw new RepositoryException(e);
        }
    }

    public Optional<User> findByTelNumber(String telNumber) {
        try {
            return Optional.of((User) getEntityManager()
                .createQuery("select u from User u where u.telNumber = :telNumber")
                .setParameter("telNumber", telNumber)
                .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public List<User> findByTelNumberPart(String telNumberPart) {
        String wildCardTelNumber = "%" + telNumberPart + "%";
        return (List<User>) getEntityManager()
            .createQuery("select u from User u where u.telNumber like :wildCardTelNumber")
            .setParameter("wildCardTelNumber", wildCardTelNumber)
            .getResultList();
    }

    @Override
    public Optional<User> findUserById(final UUID uuid) {
        return Optional.of((User) getEntityManager()
            .createQuery("select u from User u where u.id = :uuid")
            .setParameter("uuid", uuid)
            .getSingleResult());
    }

}
