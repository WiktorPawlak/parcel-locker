package pl.pas.parcellocker.configuration;

import java.math.BigDecimal;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ListConfig {
    public static final BigDecimal RATIO = new BigDecimal("2");
    public static final BigDecimal ADDITIONAL_COST = new BigDecimal("3");
}
