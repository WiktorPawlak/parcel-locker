package pl.pas.parcellocker.repositories.hibernate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.Persistence;
import jakarta.persistence.RollbackException;
import pl.pas.parcellocker.config.TestsConfig;
import pl.pas.parcellocker.model.client.Client;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RepositoryTest extends TestsConfig {

    private final HibernateRepository<Client> clientRepository = new HibernateRepository<>(Client.class);
    private Client c1;
    private Client c2;

    @BeforeEach
    void setup() {
        c1 = new Client("Tadeusz", "Kaczmarski", "606123654");
        c2 = new Client("Pawel", "Tubiel", "606444654");
    }

    @AfterEach
    void finisher() {
        clientRepository.findAll().forEach(clientRepository::remove);
    }

    @Test
    void Should_AddAndGetClient() {
        clientRepository.add(c1);
        assertEquals(c1, clientRepository.get(c1.getId()));
    }

    @Test
    void Should_RemoveClient() {
        clientRepository.add(c1);
        clientRepository.remove(c1);
        assertThrows(NoResultException.class, () -> clientRepository.get(c1.getId()));
    }

    @Test
    void Should_FindClient_WhenPredicateGivenToFindByMethod() {
        clientRepository.add(c1);
        clientRepository.add(c2);
        assertEquals(c2, clientRepository.findBy(client -> client.getLastName().equals("Tubiel")).get(0));
    }

    @Test
    void Should_ReturnAllClients() {
        clientRepository.add(c1);
        clientRepository.add(c2);
        assertTrue(2 <= clientRepository.findAll().size());
        assertTrue(clientRepository.findAll().containsAll(List.of(c1, c2)));
    }

    @Test
    void Should_TriggerOptimisticLock_WhenUnregisteringClientTwice() {
        EntityManagerFactory emf1 = Persistence.createEntityManagerFactory("parcel-locker-unit");
        EntityManager em1 = emf1.createEntityManager();

        EntityManager em2 = emf1.createEntityManager();

        clientRepository.add(c1);

        Client client1 = em1.find(Client.class, c1.getId());
        Client client2 = em2.find(Client.class, c1.getId());

        em1.getTransaction().begin();
        client1.setActive(false);
        em1.getTransaction().commit();

        RollbackException rollback = new RollbackException();
        try {
            em2.getTransaction().begin();
            client2.setActive(false);
            em2.getTransaction().commit();
        } catch (RollbackException e) {
            rollback = e;
        }
        assertTrue(rollback.getCause() instanceof OptimisticLockException);
    }
}
