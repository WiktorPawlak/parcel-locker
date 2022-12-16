package pl.pas.parcellocker.repositories;//package pl.pas.parcellocker.repositories;

import org.junit.jupiter.api.Test;
import pl.pas.parcellocker.model.Client;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClientSessionTest {

    @Test
    void test2() {
        Client client = new Client("Mati", "Strze", "2323424");
        try (ClientRepository clientRepository = new ClientRepository()) {
            clientRepository.save(client);
            assertEquals(client, clientRepository.findById(client.getEntityId()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
