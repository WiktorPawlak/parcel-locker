package pl.pas.parcellocker.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
public class Delivery extends AbstractEntity {

    private Client shipper;
    private Client receiver;
    private DeliveryStatus status;
    private Package pack;
    private Locker locker;
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
        super(UUID.randomUUID());
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
