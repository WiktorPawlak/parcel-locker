package pl.pas.parcellocker.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.pas.parcellocker.model.Client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClientRedisRepositoryTest {

    ClientRedisRepository clientRedisRepository = new ClientRedisRepository();
    Client client1;

    @BeforeEach
    void setup() {
        client1 = new Client("Mati", "Kowal", "12345678");
    }

    @Test
    void shouldAddClient() {
        clientRedisRepository.add(client1);

        assertEquals(getClientFromRepo(client1), client1);
    }

    @Test
    void shouldGetByTel() {
        clientRedisRepository.add(client1);

        assertEquals(clientRedisRepository.findByTelNumber(client1.getTelNumber()), client1);
    }

    @Test
    void shouldClearClients() {
        clientRedisRepository.add(client1);
        clientRedisRepository.clear();

        assertTrue(clientRedisRepository.findAllKeys().isEmpty());
    }

    private Client getClientFromRepo(Client client) {
        return clientRedisRepository.findById(client.getId());
    }
}
