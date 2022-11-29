package pl.pas.parcellocker.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@BsonDiscriminator
public class Delivery extends MongoEntityModel {

    @BsonProperty("shipper")
    private Client shipper;

    @BsonProperty("receiver")
    private Client receiver;

    @BsonProperty("status")
    private DeliveryStatus status;

    @BsonProperty(value = "pack", useDiscriminator = true)
    private Package pack;

    @BsonProperty("locker")
    private Locker locker;

    @BsonProperty("archived")
    private boolean isArchived;

    @BsonCreator
    public Delivery(@BsonProperty("_id") UniqueId id,
                    @BsonProperty("shipper") Client shipper,
                    @BsonProperty("receiver") Client receiver,
                    @BsonProperty("status") DeliveryStatus status,
                    @BsonProperty("pack") Package pack,
                    @BsonProperty("locker") Locker locker,
                    @BsonProperty("archived") boolean isArchived
    ) {
        super(id);
        this.shipper = shipper;
        this.receiver = receiver;
        this.status = status;
        this.pack = pack;
        this.locker = locker;
        this.isArchived = isArchived;
    }

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

        this.pack = new Parcel(basePrice, width, length, height, weight, isFragile);
    }

    public Delivery(BigDecimal basePrice,
                    boolean isPriority,
                    Client shipper,
                    Client receiver,
                    Locker locker
    ) {
        this(shipper, receiver, locker);

        this.pack = new List(basePrice, isPriority);
    }

    private Delivery(Client shipper,
                     Client receiver,
                     Locker locker
    ) {
        super(new UniqueId(UUID.randomUUID()));
        this.shipper = shipper;
        this.receiver = receiver;
        this.locker = locker;
        this.status = DeliveryStatus.READY_TO_SHIP;
    }

    public BigDecimal getCost() {
        return BigDecimal.TEN;
    }

    public Client getShipper() {
        return shipper;
    }

    public Client getReceiver() {
        return receiver;
    }

    public DeliveryStatus getStatus() {
        return status;
    }

    public Locker getLocker() {
        return locker;
    }

    public void setStatus(DeliveryStatus status) {
        this.status = status;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
    }

}
