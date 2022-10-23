package pl.pas.parcellocker.managers;

import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.Test;
import pl.pas.parcellocker.config.TestsConfig;
import pl.pas.parcellocker.exceptions.ClientManagerException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClientManagerTest extends TestsConfig {

    private static final ClientManager clientManager = new ClientManager();

    private final String TEST_NAME = "Bartosh";
    private final String TEST_SURNAME = "Byniowski";
    private final String TEST_TEL_NUMBER = "123456789";
    private final String TEST_WRONG_TEL_NUMBER = "987654321";

    @Test
    void Should_RegisterClient() {
        clientManager.registerClient(TEST_NAME, TEST_SURNAME, TEST_TEL_NUMBER);
        assertEquals(TEST_TEL_NUMBER, clientManager.getClient(TEST_TEL_NUMBER).getTelNumber());
    }

    @Test
    void Should_UnregisterClient() {
        clientManager.registerClient(TEST_NAME, TEST_SURNAME, TEST_TEL_NUMBER);
        assertFalse(clientManager.getClient(TEST_TEL_NUMBER).isArchived());

        clientManager.unregisterClient(clientManager.getClient(TEST_TEL_NUMBER));
        assertTrue(clientManager.getClient(TEST_TEL_NUMBER).isArchived());
    }

    @Test
    void Should_GetClient() {
        clientManager.registerClient(TEST_NAME, TEST_SURNAME, TEST_TEL_NUMBER);
        assertEquals(clientManager.getClient(TEST_TEL_NUMBER).getFirstName(), TEST_NAME);
        assertThrows(NoResultException.class, () -> clientManager.getClient(TEST_WRONG_TEL_NUMBER));
    }

    @Test
    void Should_ThrowException_WhenInvalidValuesPassed() {
        clientManager.registerClient(TEST_NAME, TEST_SURNAME, TEST_TEL_NUMBER);
        assertThrows(ClientManagerException.class, () -> clientManager.getClient(""));
        assertThrows(ClientManagerException.class, () -> clientManager.unregisterClient(null));
    }
}
