package pl.pas.parcellocker.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.Persistence;
import jakarta.persistence.RollbackException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.pas.parcellocker.config.TestsConfig;
import pl.pas.parcellocker.model.Client;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RepositoryTest extends TestsConfig {

    private final Repository<Client> clientRepository = new Repository<>(Client.class);
    private Client c;
    private Client c1;

    @BeforeEach
    void setup() {
        c = new Client("Maciej", "Nowak", "606123654");
        c1 = new Client("Maciej", "Kowalski", "606123654");
    }

    @Test
    void Should_AddAndGetClient() {
        clientRepository.add(c);
        assertEquals(c, clientRepository.get(c.getId()));
    }

    @Test
    void Should_RemoveClient() {
        clientRepository.add(c);
        clientRepository.remove(c);
        assertThrows(NoResultException.class, () -> clientRepository.get(c.getId()));
    }

    @Test
    void Should_FindClient_WhenPredicateGivenToFindByMethod() {
        clientRepository.add(c);
        clientRepository.add(c1);
        assertEquals(c1, clientRepository.findBy(client -> client.getLastName().equals("Kowalski")).get(0));
    }

    @Test
    void Should_ReturnAllClients() {
        clientRepository.add(c);
        clientRepository.add(c1);
        assertTrue(2 <= clientRepository.findAll().size());
        assertTrue(clientRepository.findAll().containsAll(List.of(c, c1)));
    }

    @Test
    void Should_TriggerOptimisticLock_WhenUnregisteringClientTwice() {
        EntityManagerFactory emf1 = Persistence.createEntityManagerFactory("parcel-locker-unit");
        EntityManager em1 = emf1.createEntityManager();

        EntityManager em2 = emf1.createEntityManager();

        clientRepository.add(c);

        Client client1 = em1.find(Client.class, c.getId());
        Client client2 = em2.find(Client.class, c.getId());

        em1.getTransaction().begin();
        client1.setArchive(true);
        em1.getTransaction().commit();

        RollbackException rollback = new RollbackException();
        try {
            em2.getTransaction().begin();
            client2.setArchive(true);
            em2.getTransaction().commit();
        } catch (RollbackException e) {
            rollback = e;
        }
        assertTrue(rollback.getCause() instanceof OptimisticLockException);
    }
}
