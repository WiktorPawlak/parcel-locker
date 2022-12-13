package pl.pas.parcellocker.repositories;//package pl.pas.parcellocker.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.pas.parcellocker.model.Client;

class ClientSessionTest {

    @Test
    void test2() {
        ClientRepository clientRepository = new ClientRepository();
        Client client = new Client("Mati", "Strze", "2323424");
        clientRepository.save(client);

        Assertions.assertEquals(client, clientRepository.findById(client.getEntityId()));
    }
}
