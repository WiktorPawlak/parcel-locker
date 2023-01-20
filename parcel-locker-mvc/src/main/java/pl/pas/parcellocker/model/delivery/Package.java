package pl.pas.parcellocker.model.delivery;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.pas.parcellocker.model.EntityModel;

import java.math.BigDecimal;

@Data
public abstract class Package extends EntityModel {
    public BigDecimal basePrice;

}
