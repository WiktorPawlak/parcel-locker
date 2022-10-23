package pl.pas.parcellocker.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ListTest {

    private static Package prio;
    private static Package nonprio;
    private static final BigDecimal basePrice = BigDecimal.TEN;

    @BeforeAll
    static void setup() {
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
