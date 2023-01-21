package pl.pas.parcellocker.managers;

import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.pas.parcellocker.config.TestsConfig;
import pl.pas.parcellocker.exceptions.ClientManagerException;
import pl.pas.parcellocker.model.user.Administrator;
import pl.pas.parcellocker.model.user.User;
import pl.pas.parcellocker.security.PermissionValidator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserManagerTest extends TestsConfig {

    private final PermissionValidator permissionValidator = new PermissionValidator(clientRepository);
    private final UserManager userManager = new UserManager(clientRepository, permissionValidator);

    private final String TEST_NAME = "Bartosh";
    private final String TEST_SURNAME = "Byniowski";
    private final String TEST_TEL_NUMBER = "123456789";
    private final String TEST_WRONG_TEL_NUMBER = "987654321";
    private final String TEST_PASSWORD = "P@ssw0rd";

    private User admin;

    @BeforeEach
    void init() {
        admin = new Administrator("Administrator", "surname", "666789123", "P@ssw0rd");
        clientRepository.add(admin);
    }

    @AfterEach
    void finisher() {
        clientRepository.findAll().forEach(clientRepository::remove);
    }

    @Test
    void Should_RegisterClient() {
        userManager.registerClient(TEST_NAME, TEST_SURNAME, TEST_TEL_NUMBER, TEST_PASSWORD);
        assertEquals(TEST_TEL_NUMBER, userManager.getUser(TEST_TEL_NUMBER).getTelNumber());
    }

    @Test
    void Should_UnregisterClient() {
        userManager.registerClient(TEST_NAME, TEST_SURNAME, TEST_TEL_NUMBER, TEST_PASSWORD);
        assertTrue(userManager.getUser(TEST_TEL_NUMBER).isActive());

        userManager.unregisterClient(admin.getId(), userManager.getUser(TEST_TEL_NUMBER));
        assertFalse(userManager.getUser(TEST_TEL_NUMBER).isActive());
    }

    @Test
    void Should_GetClient() {
        userManager.registerClient(TEST_NAME, TEST_SURNAME, TEST_TEL_NUMBER, TEST_PASSWORD);
        assertEquals(userManager.getUser(TEST_TEL_NUMBER).getFirstName(), TEST_NAME);
        assertThrows(NoResultException.class, () -> userManager.getUser(TEST_WRONG_TEL_NUMBER));
    }

    @Test
    void Should_ThrowException_WhenInvalidValuesPassed() {
        userManager.registerClient(TEST_NAME, TEST_SURNAME, TEST_TEL_NUMBER, TEST_PASSWORD);
        assertThrows(ClientManagerException.class, () -> userManager.getUser(""));
        assertThrows(ClientManagerException.class, () -> userManager.unregisterClient(admin.getId(), null));
    }

}
