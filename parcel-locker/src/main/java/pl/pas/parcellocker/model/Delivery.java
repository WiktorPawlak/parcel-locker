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
                    UUID id,
                    float width,
                    float height,
                    float length,
                    float weight,
                    boolean isFragile,
                    Client shipper,
                    Client receiver,
                    Locker locker) {
        //TODO id should be generate here
        this.shipper = shipper;
        this.receiver = receiver;
        this.locker = locker;

        this.pack = new Parcel(width, length, height, weight, isFragile, basePrice);
    }

    public Delivery(BigDecimal basePrice,
                    UUID id,
                    boolean isPriority,
                    Client shipper,
                    Client receiver,
                    Locker locker) {
        //TODO id should be generate here
        this.shipper = shipper;
        this.receiver = receiver;
        this.locker = locker;

        this.pack = new List(basePrice, isPriority);
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
