package pl.pas.parcellocker.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.pas.parcellocker.model.Client;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryTest {

    public Repository<Client> clientRepository;
    public Client c;
    public Client c1;

    @BeforeEach
    void setup() {
        clientRepository = new Repository<Client>();
        c = new Client("Maciej", "Nowak", "606123654");
        c1 = new Client("Maciej", "Kowal", "606123654");
    }

    @Test
    void addAndGetConformance() {
        clientRepository.add(c);
        assertEquals(c, clientRepository.get(0));
    }

    @Test
    void removeConformance() {
        clientRepository.add(c);
        clientRepository.remove(c);
        assertEquals(0, clientRepository.size());
    }

    @Test
    void reportConformance() {
        clientRepository.add(c);
        assertEquals("Maciej Nowak phone: 606123654 Actual", clientRepository.report());

    }

    @Test
    void sizeConformance() {
        clientRepository.add(c);
        clientRepository.add(c);
        clientRepository.add(c);
        assertEquals(3, clientRepository.size());

    }

    @Test
    void findByConformance() {
        clientRepository.add(c);
        clientRepository.add(c1);
        clientRepository.add(c);
        assertEquals(c1, clientRepository.findBy(client -> client.getLastName().equals("Kowal")).get(0));
    }

    @Test
    void findAllConformance() {
        clientRepository.add(c);
        clientRepository.add(c1);
        clientRepository.add(c);
        assertEquals(3, clientRepository.findAll().size());

    }
}
