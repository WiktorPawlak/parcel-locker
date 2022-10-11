package pl.pas.parcellocker.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

public abstract class Package {
    public final BigDecimal basePrice;

    public Package(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public abstract BigDecimal getCost();

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("basePrice: ", basePrice)
                .toString();
    }
}
