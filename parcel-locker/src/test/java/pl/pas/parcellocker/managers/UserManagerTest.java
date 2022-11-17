package pl.pas.parcellocker.managers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.NoResultException;
import pl.pas.parcellocker.config.TestsConfig;
import pl.pas.parcellocker.exceptions.ClientManagerException;
import pl.pas.parcellocker.model.user.Administrator;
import pl.pas.parcellocker.model.user.User;
import pl.pas.parcellocker.security.PermissionValidator;

class UserManagerTest extends TestsConfig {

    private final PermissionValidator permissionValidator = new PermissionValidator(clientRepository);
    private final UserManager userManager = new UserManager(clientRepository, permissionValidator);

    private final String TEST_NAME = "Bartosh";
    private final String TEST_SURNAME = "Byniowski";
    private final String TEST_TEL_NUMBER = "123456789";
    private final String TEST_WRONG_TEL_NUMBER = "987654321";

    private User admin;

    @BeforeEach
    void init() {
        admin = new Administrator("Administrator", "surname", "666789123");
        clientRepository.add(admin);
    }

    @AfterEach
    void finisher() {
        clientRepository.findAll().forEach(clientRepository::remove);
    }

    @Test
    void Should_RegisterClient() {
        userManager.registerClient(admin.getId(), TEST_NAME, TEST_SURNAME, TEST_TEL_NUMBER);
        assertEquals(TEST_TEL_NUMBER, userManager.getUser(TEST_TEL_NUMBER).getTelNumber());
    }

    @Test
    void Should_UnregisterClient() {
        var user = clientRepository.get(UUID.fromString("00000000-0000-0000-0000-000000000003"));
        userManager.registerClient(user.getId(), TEST_NAME, TEST_SURNAME, TEST_TEL_NUMBER);
        assertTrue(userManager.getUser(TEST_TEL_NUMBER).isActive());

        userManager.unregisterClient(user.getId(), userManager.getUser(TEST_TEL_NUMBER));
        assertFalse(userManager.getUser(TEST_TEL_NUMBER).isActive());
    }

    @Test
    void Should_GetClient() {
        var user = clientRepository.get(UUID.fromString("00000000-0000-0000-0000-000000000003"));
        userManager.registerClient(user.getId(), TEST_NAME, TEST_SURNAME, TEST_TEL_NUMBER);
        assertEquals(userManager.getUser(TEST_TEL_NUMBER).getFirstName(), TEST_NAME);
        assertThrows(NoResultException.class, () -> userManager.getUser(TEST_WRONG_TEL_NUMBER));
    }

    @Test
    void Should_ThrowException_WhenInvalidValuesPassed() {
        var user = clientRepository.get(UUID.fromString("00000000-0000-0000-0000-000000000003"));
        userManager.registerClient(user.getId(), TEST_NAME, TEST_SURNAME, TEST_TEL_NUMBER);
        assertThrows(ClientManagerException.class, () -> userManager.getUser(""));
        assertThrows(ClientManagerException.class, () -> userManager.unregisterClient(user.getId(), null));
    }

}
