package pl.pas.parcellocker.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static pl.pas.parcellocker.configuration.ListConfig.ADDITIONAL_COST;
import static pl.pas.parcellocker.configuration.ListConfig.RATIO;

@Getter
@Setter
@EqualsAndHashCode
public class List extends Package {

    private boolean priority;

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
}
