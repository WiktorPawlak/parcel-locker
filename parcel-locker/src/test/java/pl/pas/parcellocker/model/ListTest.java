package pl.pas.parcellocker.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

class ListTest {

    public Package prio;
    public Package nonprio;
    public BigDecimal basePrice = BigDecimal.TEN;

    @BeforeEach
    void setup() {
        prio = new List(basePrice, true);
        nonprio = new List(basePrice, false);
    }

    @Test
    void priorityListConformance() {
        BigDecimal TWO = new BigDecimal("2");
        BigDecimal THREE = new BigDecimal("3");
        assertEquals(prio.getCost(), (basePrice.divide(TWO, RoundingMode.FLOOR).add(THREE)));
        assertEquals("Priority letter cost: 8 basePrice: 10", prio.toString());
    }

    @Test
    void nonPriorityListConformance() {
        BigDecimal TWO = new BigDecimal("2");
        assertEquals(nonprio.getCost(), (basePrice.divide(TWO, RoundingMode.FLOOR)));
        assertEquals("Registered letter cost: 5 basePrice: 10", nonprio.toString());
    }
}
