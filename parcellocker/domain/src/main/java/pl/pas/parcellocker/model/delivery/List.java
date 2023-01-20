package pl.pas.parcellocker.model.delivery;

import java.math.BigDecimal;
import java.math.RoundingMode;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import pl.pas.parcellocker.configuration.ListConfig;

@Entity
@NoArgsConstructor
@DiscriminatorColumn(name = "LIST")
@Access(AccessType.FIELD)
public class List extends Package {

    private boolean priority;

    public List(BigDecimal basePrice, boolean priority) {
        super(basePrice);

        this.priority = priority;
    }

    @Override
    public BigDecimal getCost() {
        BigDecimal cost = basePrice.divide(ListConfig.RATIO, RoundingMode.FLOOR);
        if (priority) cost = cost.add(ListConfig.ADDITIONAL_COST);
        return cost;
    }
}
