package pl.pas.parcellocker.model;

import java.math.BigDecimal;
import java.util.UUID;

public class Delivery {

    private UUID id;
    private Client shipper;
    private Client receiver;
    private DeliveryStatus status;
    private Package pack;
    private Locker locker;

    public Delivery(BigDecimal basePrice,
                    double width,
                    double height,
                    double length,
                    double weight,
                    boolean isFragile,
                    Client shipper,
                    Client receiver,
                    Locker locker) {
        this(shipper, receiver, locker);

        this.pack = new Parcel(width, length, height, weight, isFragile, basePrice);
    }

    public Delivery(BigDecimal basePrice,
                    boolean isPriority,
                    Client shipper,
                    Client receiver,
                    Locker locker) {
        this(shipper, receiver, locker);

        this.pack = new List(basePrice, isPriority);
    }

    private Delivery(Client shipper,
                     Client receiver,
                     Locker locker) {
        this.id = UUID.randomUUID();
        this.shipper = shipper;
        this.receiver = receiver;
        this.locker = locker;
    }

    public BigDecimal getCost() {
        return pack.getCost();
    }

    public UUID getId() {
        return id;
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

    public Package getPack() {
        return pack;
    }

    public Locker getLocker() {
        return locker;
    }

    public void setStatus(DeliveryStatus status) {
        this.status = status;
    }
}
