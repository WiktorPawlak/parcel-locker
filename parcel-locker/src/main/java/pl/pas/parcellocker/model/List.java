package pl.pas.parcellocker.model;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.TableGenerator;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

import static pl.pas.parcellocker.configuration.ListConfig.ADDITIONAL_COST;
import static pl.pas.parcellocker.configuration.ListConfig.RATIO;

@Entity
@NoArgsConstructor
@DiscriminatorColumn(name = "1")
public class List extends Package {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;
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

    @Override
    public String toString() {
        String ifPriority = priority ? "Priority" : "Registered";
        return ifPriority + " letter cost: " + this.getCost() + " " + super.toString();
    }
}
