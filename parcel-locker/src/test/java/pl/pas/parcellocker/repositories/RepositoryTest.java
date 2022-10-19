package pl.pas.parcellocker.repositories;

import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.pas.parcellocker.config.TestsConfig;
import pl.pas.parcellocker.model.Client;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryTest extends TestsConfig {

    public Repository<Client> clientRepository = new Repository<>(Client.class);;
    public Client c;
    public Client c1;

    @BeforeEach
    void setup() {
        c = new Client("Maciej", "Nowak", "606123654");
        c1 = new Client("Maciej", "Kowalski", "606123654");
    }

    @Test
    void addAndGetConformance() {
        clientRepository.add(c);
        assertEquals(c, clientRepository.get(c.getId()));
    }

    @Test
    void removeConformance() {
        clientRepository.add(c);
        clientRepository.remove(c);
        assertThrows(NoResultException.class, () -> clientRepository.get(c.getId()));
    }

    @Test
    void findByConformance() {
        clientRepository.add(c);
        clientRepository.add(c1);
        clientRepository.add(c);
        assertEquals(c1, clientRepository.findBy(client -> client.getLastName().equals("Kowalski")).get(0));
    }

    @Test
    void findAllConformance() {
        clientRepository.add(c);
        clientRepository.add(c1);
        clientRepository.add(c);
        assertTrue(2 <= clientRepository.findAll().size());

    }
}
