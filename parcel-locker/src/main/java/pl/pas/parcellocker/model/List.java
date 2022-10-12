package pl.pas.parcellocker.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static pl.pas.parcellocker.configuration.ListConfig.ADDITIONAL_COST;
import static pl.pas.parcellocker.configuration.ListConfig.RATIO;

public class List extends Package {
    private final boolean priority;

    public List(BigDecimal basePrice, boolean priority) {
        super(basePrice);

        this.priority = priority;
    }

    @Override
    public BigDecimal getCost() {
        BigDecimal cost = basePrice.divide(RATIO, RoundingMode.FLOOR);
        if (priority) cost = cost.add(ADDITIONAL_COST);
        return cost;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("priority", priority ? "Priority" : "Registered")
            .append(super.toString())
            .toString();
    }
}
