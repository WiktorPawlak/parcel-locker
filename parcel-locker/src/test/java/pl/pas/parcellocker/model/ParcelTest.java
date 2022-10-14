package pl.pas.parcellocker.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.pas.parcellocker.exceptions.ParcelException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ParcelTest {

    public Package p;

    @BeforeEach
    void setup() {
        p = new Parcel(BigDecimal.TEN, 1,2, 3, 4, true);
    }

    @Test
    void constructorAndGettersConformance() {
        assertEquals(new BigDecimal("10.0"), p.getCost());
        assertEquals("Parcel 1.0x2.0x3.0 4.0kg cost: 10.0 basePrice: 10", p.toString());
    }

    @Test
    void constructorExceptionConformance() {
        assertThrows(ParcelException.class, () -> new Parcel(BigDecimal.TEN, 0, 2, 3, 4, true));
        assertThrows(ParcelException.class, () -> new Parcel(BigDecimal.TEN, 1, 0, 3, 4, true));
        assertThrows(ParcelException.class, () -> new Parcel(BigDecimal.TEN, 1, 2, 0, 4, true));
        assertThrows(ParcelException.class, () -> new Parcel(BigDecimal.TEN ,1, 2, 3, 0, true));
        assertThrows(ParcelException.class, () -> new Parcel(BigDecimal.TEN, 50, 2, 3, 4, true));
        assertThrows(ParcelException.class, () -> new Parcel(BigDecimal.TEN, 1, 50, 3, 4, true));
        assertThrows(ParcelException.class, () -> new Parcel(BigDecimal.TEN, 1, 2, 50, 4, true));
        assertThrows(ParcelException.class, () -> new Parcel(BigDecimal.TEN, 1, 2, 3, 50, true));
    }}
