package pl.pas.parcellocker.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.pas.parcellocker.config.TestsConfig;
import pl.pas.parcellocker.model.Client;

import static org.junit.jupiter.api.Assertions.*;

class ClientRepositoryTest extends TestsConfig {
    public ClientRepository clientRepository;
    public Client c1;
    public Client c2;
    public Client c3;
    public Client c4;
    public Client c5;

    @BeforeEach
    void setup() {
        clientRepository = new ClientRepository();
        c1 = new Client("Maciej", "Nowak", "12345");
        c2 = new Client("Maciej", "Kowal", "123456");
        c3 = new Client("Maciej", "Kowal", "1234567");
        c4 = new Client("Maciej", "Kowal", "12345678");
        c5 = new Client("Maciej", "Kowal", "123456789");
    }

    @Test
    void whenFindByTelNumberShouldReturnClientWithAppropriateTelNumber() {
        long expectedClientId = clientRepository.save(c1);

        assertNotNull(clientRepository.findByTelNumber(c1.getTelNumber()));
    }

    @Test
    void shouldArchiveClient() {
        long expectedClientId = clientRepository.save(c2);
        clientRepository.archive(expectedClientId);

        assertTrue(clientRepository.findById(expectedClientId).isArchived());
    }

    @Test
    void shouldAddClientIntoDB() {
       long expectedClientId = clientRepository.save(c3);

       assertNotNull(clientRepository.findById(expectedClientId));
    }

    @Test
    void shouldReturnAllClients() {
        clientRepository.save(c4);
        clientRepository.save(c5);

        assertTrue(clientRepository.findAll().size() > 0);
    }

}
