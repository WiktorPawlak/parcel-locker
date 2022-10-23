package pl.pas.parcellocker.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.pas.parcellocker.config.TestsConfig;
import pl.pas.parcellocker.model.Client;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClientRepositoryTest extends TestsConfig {
    private Client c1;
    private Client c2;
    private Client c3;
    private Client c4;
    private Client c5;

    @BeforeEach
    void setup() {
        c1 = new Client("Maciej", "Nowak", "123452137");
        c2 = new Client("Maciej", "Kowal", "123456");
        c3 = new Client("Maciej", "Kowal", "1234567");
        c4 = new Client("Maciej", "Kowal", "12345678");
        c5 = new Client("Maciej", "Kowal", "123456789");
    }

    @Test
    void Should_ReturnClientWithAppropriateTelNumber_WhenFindByTelNumberCalled() {
        clientRepository.add(c1);
        assertNotNull(clientRepository.findByTelNumber(c1.getTelNumber()));
    }

    @Test
    void Should_ArchiveClient_WhenRepositoryArchiveMethodCalled() {
        clientRepository.add(c2);
        clientRepository.archive(c2.getId());

        assertTrue(clientRepository.get(c2.getId()).isArchived());
    }

    @Test
    void Should_AddClient_WhenAddMethodCalled() {
       clientRepository.add(c3);

       assertNotNull(clientRepository.get(c3.getId()));
    }

    @Test
    void Should_ReturnAllClients_WhenFindAllCalled() {
        clientRepository.add(c4);
        clientRepository.add(c5);

        assertTrue(clientRepository.findAll().size() > 1);
    }

}
