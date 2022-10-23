package pl.pas.parcellocker.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DepositBoxTest {

    private final DepositBox depositBox = new DepositBox();

    @BeforeEach
    void setUp() {
        depositBox.setAccessCode("1234");
        depositBox.setTelNumber("12345");
    }

    @Test
    void Should_NotAccess_WhenAccessCodeAndTelNumberAreCorrect() {
        assertTrue(depositBox.canAccess("1234", "12345"));
    }

    @Test
    void Should_NotAccess_WhenAccessCodeAndTelNumberAreInCorrect() {
        assertFalse(depositBox.canAccess("123", "1245"));
    }

    @Test
    void Should_CleanDepositBoxFieldsAndSetIsEmptyAsTrue_WhenClean() {
        depositBox.clean();
        assertEquals("", depositBox.getAccessCode());
        assertEquals("", depositBox.getTelNumber());
        assertTrue(depositBox.isEmpty());
    }
}
