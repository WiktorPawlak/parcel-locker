package pl.pas.parcellocker.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import pl.pas.parcellocker.exceptions.ClientException;
import pl.pas.parcellocker.model.user.Administrator;
import pl.pas.parcellocker.model.user.Client;
import pl.pas.parcellocker.model.user.Moderator;
import pl.pas.parcellocker.model.user.User;

class UserTest {

    @ParameterizedTest(name = "when firstName = {0}, lastName = {1}, telNumber = {2} should throw exception")
    @CsvSource({
        "'',a,123456",
        "a,'',123",
        "a,a,''"
    })
    void Should_ThrowException_WhenIncorrectData(String firstName, String lastName, String telNumber) {
        assertThrows(ClientException.class, () -> new Client(firstName, lastName, telNumber));
    }

    @Test
    void Should_SuccessfullyConfirm_isAdmin() {
        User user = new Administrator("Janusz", "Tracz", "666666666");
        assertTrue(user.isAdmin());
        assertFalse(user.isModerator());
    }

    @Test
    void Should_SuccessfullyConfirm_isModerator() {
        User user = new Moderator("Janusz", "Tracz", "666666666");
        assertTrue(user.isModerator());
        assertFalse(user.isAdmin());
    }
}
