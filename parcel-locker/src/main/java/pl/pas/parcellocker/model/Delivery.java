package pl.pas.parcellocker.model;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "package_type",
    discriminatorType = DiscriminatorType.INTEGER)
@Access(AccessType.FIELD)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Delivery extends VersionModel implements EntityClass {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @EqualsAndHashCode.Exclude
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "shipper_id")
    private Client shipper;

    @EqualsAndHashCode.Exclude
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "receiver_id")
    private Client receiver;
    private DeliveryStatus status;

    @EqualsAndHashCode.Exclude
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "pack_ID")
    private Package pack;

    @EqualsAndHashCode.Exclude
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "locker_id")
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
        this.status = DeliveryStatus.READY_TO_SHIP;
    }

    public BigDecimal getCost() {
        return pack.getCost();
    }

    @Override
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

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
    }

}
