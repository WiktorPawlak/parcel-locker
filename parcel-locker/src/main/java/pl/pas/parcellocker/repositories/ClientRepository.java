package pl.pas.parcellocker.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import pl.pas.parcellocker.model.Client;

import java.util.UUID;

public class ClientRepository extends Repository<Client> {

    public ClientRepository() {
        super(Client.class);
    }

    public Client findById(UUID id) {
        EntityManager entityManager = getEntityManager();

        Client client = entityManager.find(Client.class, id);
        entityManager.detach(client);

        return client;
    }

    public UUID save(Client client) {
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

    public void archive(UUID id) {
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
