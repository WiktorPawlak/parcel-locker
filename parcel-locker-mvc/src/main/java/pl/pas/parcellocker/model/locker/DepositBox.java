package pl.pas.parcellocker.model.locker;

import lombok.Data;
import pl.pas.parcellocker.model.EntityModel;
import pl.pas.parcellocker.model.delivery.Delivery;

@Data
public class DepositBox extends EntityModel {

    private Delivery delivery;
    private boolean isEmpty;
    private String accessCode;
    private String telNumber;

}
