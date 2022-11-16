package pl.pas.parcellocker.model.delivery;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import pl.pas.parcellocker.model.EntityModel;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
public abstract class Package extends EntityModel {
    public BigDecimal basePrice;

    public Package(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public abstract BigDecimal getCost();
}
