package pl.pas.parcellocker.model;

import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public abstract class Package {

    @PartitionKey
    private UUID entityId;

    public BigDecimal basePrice;

    public Package(UUID id, BigDecimal basePrice) {
        this.entityId = id;
        this.basePrice = basePrice;
    }

    public Package(BigDecimal basePrice) {
        this.entityId = UUID.randomUUID();
        this.basePrice = basePrice;
    }

    public abstract BigDecimal getCost();
}
