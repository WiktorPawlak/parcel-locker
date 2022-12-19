package pl.pas.parcellocker.model;

import com.datastax.oss.driver.api.mapper.annotations.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pas.parcellocker.configuration.SchemaConst;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static pl.pas.parcellocker.configuration.ListConfig.ADDITIONAL_COST;
import static pl.pas.parcellocker.configuration.ListConfig.RATIO;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity(defaultKeyspace = SchemaConst.PARCEL_LOCKER_NAMESPACE)
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
