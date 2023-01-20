package pl.pas.parcellocker.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import pl.pas.parcellocker.exceptions.ClientException;
import pl.pas.parcellocker.model.user.Client;

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
}
