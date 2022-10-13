package pl.pas.parcellocker.managers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.pas.parcellocker.exceptions.ClientManagerException;
import pl.pas.parcellocker.exceptions.NotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class ClientManagerTest {

    public ClientManager clientManager;

    @BeforeEach
    void setup() {
        clientManager = new ClientManager();
    }

    @Test
    void registerClientConformance() {
        clientManager.registerClient("Bartosh", "Byniowski", "123456789");
        assertEquals("123456789", clientManager.getClient("123456789").getTelNumber());
    }

    @Test
    void unregisterClientConformance() {
        clientManager.registerClient("Bartosh", "Byniowski", "123456789");
        assertFalse(clientManager.getClient("123456789").isArchived());

        clientManager.unregisterClient(clientManager.getClient("123456789"));
        assertTrue(clientManager.getClient("123456789").isArchived());
    }

    @Test
    void getClientConformance() {
        clientManager.registerClient("Bartosh", "Byniowski", "123456789");
        assertEquals(clientManager.getClient("123456789").getFirstName(), "Bartosh");
        assertThrows(NotFoundException.class, () -> clientManager.getClient("987654321"));
    }

    @Test
    void exceptionConformance() {
        clientManager.registerClient("Bartosh", "Byniowski", "123456789");
        assertThrows(ClientManagerException.class, () -> clientManager.getClient(""));
        assertThrows(ClientManagerException.class, () -> clientManager.unregisterClient(null));
    }
}
