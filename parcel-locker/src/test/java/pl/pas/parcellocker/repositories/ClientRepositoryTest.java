package pl.pas.parcellocker.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.pas.parcellocker.model.Client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClientRepositoryTest {
    ClientRepository clientRepository = new ClientRepository();
    Client client1;

    @BeforeEach
    void setup() {
        client1 = new Client("Mati", "Kowal", "12345678");
    }

    @Test
    void shouldAddClient() {
        clientRepository.save(client1);

        assertEquals(getClientFromRepo(client1), client1);
    }

    @Test
    void shouldUpdateClient() {
        clientRepository.save(client1);
        client1.setActive(false);
        clientRepository.update(client1);

        assertFalse(getClientFromRepo(client1).isActive());
    }

    @Test
    void shouldDeleteClient() {
        clientRepository.save(client1);

        clientRepository.delete(client1);

        assertNull(getClientFromRepo(client1));
    }

    @Test
    void shouldReturnAllClients() {
        clientRepository.save(client1);
        clientRepository.save(new Client("test", "test", "test"));

        assertTrue(clientRepository.findAll().getAvailableWithoutFetching() >= 2);
    }

    @Test
    void shouldReturnClientNyTelNumber() {
        Client wantedClient = new Client("name", "lastname", "1234");
        clientRepository.save(wantedClient);
        clientRepository.save(new Client("test", "test", "test"));

        assertEquals(wantedClient.getEntityId(), clientRepository.findByTelNumber("1234").getEntityId());
    }

    private Client getClientFromRepo(Client client) {
        return clientRepository.findById(client.getEntityId());
    }

}
