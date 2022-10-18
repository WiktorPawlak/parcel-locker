package pl.pas.parcellocker.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;
import pl.pas.parcellocker.model.Client;

import java.util.List;

public class ClientRepository extends Repository<Client> {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ParcelLocker");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<Client> findAll() {
        return (List<Client>) getEntityManager()
            .createQuery("select c from Client c")
            .getResultList();
    }

    public Client findById(long id) {
        EntityManager entityManager = getEntityManager();

        Client client = entityManager.find(Client.class, id);
        entityManager.detach(client);

        return client;
    }

    public long save(Client client) {
        try {
            EntityManager entityManager = getEntityManager();

            entityManager.getTransaction().begin();

            entityManager.persist(client);
            entityManager.flush();

            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
            //logger
        }
        return client.getId();
    }

    public void archive(long id) {
        try {
            EntityManager entityManager = getEntityManager();

            entityManager.getTransaction().begin();
            entityManager.find(Client.class, id).setArchive(true);
            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
            //loger
        }
    }

    public Client findByTelNumber(String telNumber) {
        return (Client) getEntityManager()
            .createQuery("select c from Client c where c.telNumber = :telNumber")
            .setParameter("telNumber", telNumber)
            .getSingleResult();
    }

}
