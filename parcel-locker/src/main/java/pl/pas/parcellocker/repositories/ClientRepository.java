package pl.pas.parcellocker.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import pl.pas.parcellocker.exceptions.NotFoundException;
import pl.pas.parcellocker.model.Client;

public class ClientRepository extends Repository<Client> {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("ParcelLocker");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void save() {

        EntityManager entityManager = getEntityManager();

        Client client = new Client("Mati", "S", "123131423");

        entityManager.getTransaction().begin();

        entityManager.persist(client);
        entityManager.flush();

        entityManager.getTransaction().commit();
    }

    public Client findByTelNumber(String telNumber) {
        return objects.stream()
            .filter(client -> client.getTelNumber().equals(telNumber))
            .findFirst()
            .orElseThrow(() -> new NotFoundException("Client Not Found"));
    }
}
