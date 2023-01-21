package pl.pas.parcellocker.model.delivery;

import java.time.LocalDateTime;

import lombok.Data;
import pl.pas.parcellocker.model.EntityModel;
import pl.pas.parcellocker.model.locker.Locker;
import pl.pas.parcellocker.model.user.Client;

@Data
public class Delivery extends EntityModel {

    private Client shipper;

    private Client receiver;
    private DeliveryStatus status;

    private Package pack;

    private Locker locker;

    private LocalDateTime allocationStart;
    private LocalDateTime allocationStop;
    private boolean isArchived;

}
