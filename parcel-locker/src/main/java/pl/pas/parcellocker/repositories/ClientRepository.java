package pl.pas.parcellocker.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import lombok.extern.slf4j.Slf4j;
import pl.pas.parcellocker.model.Client;

import java.util.UUID;

@Slf4j
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

    public void archive(UUID id) {
        try {
            EntityManager entityManager = getEntityManager();

            entityManager.getTransaction().begin();
            entityManager.find(Client.class, id).setArchive(true);
            entityManager.getTransaction().commit();
        } catch (PersistenceException e) {
            log.error(e.getMessage());
        }
    }

    public Client findByTelNumber(String telNumber) {
        return (Client) getEntityManager()
            .createQuery("select c from Client c where c.telNumber = :telNumber")
            .setParameter("telNumber", telNumber)
            .getSingleResult();
    }

}
