package pl.pas.parcellocker.model.delivery;

import java.math.BigDecimal;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.pas.parcellocker.model.EntityModel;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Access(AccessType.FIELD)
public abstract class Package extends EntityModel {
    public BigDecimal basePrice;

    protected Package(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public abstract BigDecimal getCost();
}
