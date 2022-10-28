package pl.pas.parcellocker.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ListTest {

    private Package prio;
    private Package nonprio;
    private final BigDecimal basePrice = BigDecimal.TEN;

    @BeforeAll
    void setup() {
        prio = new List(basePrice, true);
        nonprio = new List(basePrice, false);
    }

    @Test
    void Should_ReturnGivenCost_WhenListIsPriority() {
        BigDecimal TWO = new BigDecimal("2");
        BigDecimal THREE = new BigDecimal("3");
        assertEquals(prio.getCost(), (basePrice.divide(TWO, RoundingMode.FLOOR).add(THREE)));
    }

    @Test
    void Should_ReturnGivenCost_WhenListIsNonPriority() {
        BigDecimal TWO = new BigDecimal("2");
        assertEquals(nonprio.getCost(), (basePrice.divide(TWO, RoundingMode.FLOOR)));
    }
}
