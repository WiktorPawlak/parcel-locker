package pl.pas.parcellocker.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.pas.parcellocker.model.Client;

import static org.junit.jupiter.api.Assertions.*;

class ClientRepositoryTest {
    public ClientRepository clientRepository;
    public Client c;
    public Client c1;

    @BeforeEach
    void setup() {
        clientRepository = new ClientRepository();
        c = new Client("Maciej", "Nowak", "606123654");
        c1 = new Client("Maciej", "Kowal", "606123654");
    }

    @Test
    void findByTelNumber() {
        clientRepository.add(c);
        clientRepository.add(c1);
        assertEquals(c, clientRepository.findByTelNumber("606123654"));
    }
}
