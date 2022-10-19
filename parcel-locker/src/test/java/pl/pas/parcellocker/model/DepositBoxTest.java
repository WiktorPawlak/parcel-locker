package pl.pas.parcellocker.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class DepositBoxTest {

    DepositBox depositBox = new DepositBox();

    @BeforeEach
    void setUp() {
        depositBox.setAccessCode("1234");
        depositBox.setTelNumber("12345");
    }

    @Test
    void whenAccessCodeAndTelNumberAreCorrectShouldCanAccess() {
        assertTrue(depositBox.canAccess("1234", "12345"));
    }

    @Test
    void whenAccessCodeAndTelNumberAreCorrectShouldCanNotAccess() {
        assertFalse(depositBox.canAccess("123", "1245"));
    }

    @Test
    void whenCleanShouldCleanDepositBoxFieldsAndSetIsEmptyAsTrue() {
        depositBox.clean();
        assertEquals("", depositBox.getAccessCode());
        assertEquals("", depositBox.getTelNumber());
        assertTrue(depositBox.isEmpty());
    }
}
