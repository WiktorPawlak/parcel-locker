package pl.pas.parcellocker.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.pas.parcellocker.config.TestsConfig;
import pl.pas.parcellocker.model.Client;

import java.util.List;
import java.util.UUID;

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
        c1 = new Client("Maciej", "Nowak", "123452137");
        c2 = new Client("Maciej", "Kowal", "123456");
        c3 = new Client("Maciej", "Kowal", "1234567");
        c4 = new Client("Maciej", "Kowal", "12345678");
        c5 = new Client("Maciej", "Kowal", "123456789");
    }

    @Test
    void whenFindByTelNumberShouldReturnClientWithAppropriateTelNumber() {
        clientRepository.add(c1);
        assertNotNull(clientRepository.findByTelNumber(c1.getTelNumber()));
    }

    @Test
    void shouldArchiveClient() {
        clientRepository.add(c2);
        clientRepository.archive(c2.getId());

        assertTrue(clientRepository.findById(c2.getId()).isArchived());
    }

    @Test
    void shouldAddClientIntoDB() {
       clientRepository.add(c3);

       assertNotNull(clientRepository.findById(c3.getId()));
    }

    @Test
    void shouldReturnAllClients() {
        clientRepository.add(c4);
        clientRepository.add(c5);

        assertTrue(clientRepository.findAll().size() > 0);
    }

}
