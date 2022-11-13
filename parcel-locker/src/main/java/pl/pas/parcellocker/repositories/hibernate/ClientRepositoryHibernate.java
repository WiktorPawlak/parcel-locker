package pl.pas.parcellocker.repositories.hibernate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import pl.pas.parcellocker.exceptions.RepositoryException;
import pl.pas.parcellocker.model.client.Client;
import pl.pas.parcellocker.model.client.ClientRepository;

import java.util.UUID;

import static pl.pas.parcellocker.repositories.hibernate.EntityManagerUtil.getEntityManager;

public class ClientRepositoryHibernate extends HibernateRepository<Client> implements ClientRepository {

    public ClientRepositoryHibernate() {
        super(Client.class);
    }

    public void archive(UUID id) {
        try {
            EntityManager entityManager = getEntityManager();

            entityManager.getTransaction().begin();
            entityManager.find(Client.class, id).setActive(false);
            entityManager.getTransaction().commit();

        } catch (PersistenceException e) {
            throw new RepositoryException(e);
        }
    }

    public Client findByTelNumber(String telNumber) {
        return (Client) getEntityManager()
            .createQuery("select c from Client c where c.telNumber = :telNumber")
            .setParameter("telNumber", telNumber)
            .getSingleResult();
    }

}
