package pl.pas.parcellocker.model;

import com.datastax.oss.driver.api.mapper.annotations.ClusteringColumn;
import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pas.parcellocker.configuration.SchemaConst;

import java.math.BigDecimal;
import java.util.UUID;

@Entity(defaultKeyspace = SchemaConst.PARCEL_LOCKER_NAMESPACE)
@CqlName(value = "delivery_by_id")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Delivery {

    @PartitionKey
    private UUID entityId;
    @CqlName("shipper_id")
    private UUID shipper;
    @CqlName("receiver_id")
    private UUID receiver;
    private DeliveryStatus status;
    @CqlName("package_id")
    private UUID pack;
    @CqlName("locker_id")
    private UUID locker;
    @ClusteringColumn
    private boolean isArchived;

    public Delivery(BigDecimal basePrice,
                    double width,
                    double length,
                    double height,
                    double weight,
                    boolean isFragile,
                    Client shipper,
                    Client receiver,
                    Locker locker) {
        this(shipper, receiver, locker);

        //this.pack = new Parcel(basePrice, width, length, height, weight, isFragile);

        this.pack = UUID.randomUUID();
    }

    public Delivery(BigDecimal basePrice,
                    boolean isPriority,
                    Client shipper,
                    Client receiver,
                    Locker locker
    ) {
        this(shipper, receiver, locker);

        //this.pack = new List(basePrice, isPriority);
        this.pack = UUID.randomUUID();
    }

    private Delivery(Client shipper,
                     Client receiver,
                     Locker locker
    ) {
        this.entityId = UUID.randomUUID();
        this.shipper = shipper.getEntityId();
        this.receiver = receiver.getEntityId();
        this.locker = locker.getEntityId();
        this.status = DeliveryStatus.READY_TO_SHIP;
    }

    public BigDecimal getCost() {
        return BigDecimal.TEN;
    }

}
