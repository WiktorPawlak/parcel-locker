package pl.pas.parcellocker.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
public abstract class Package extends AbstractEntity {

    public BigDecimal basePrice;

    public Package(UUID id, BigDecimal basePrice) {
        super(id);
        this.basePrice = basePrice;
    }

    public Package(BigDecimal basePrice) {
        super(UUID.randomUUID());
        this.basePrice = basePrice;
    }

    public abstract BigDecimal getCost();
}
