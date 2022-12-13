package pl.pas.parcellocker.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
public class DepositBox extends AbstractEntity {

    private UUID deliveryId;
    private Boolean isEmpty;
    private String accessCode;
    private String telNumber;

    public DepositBox() {
        super(UUID.randomUUID());
        isEmpty = true;
        telNumber = "";
        accessCode = "";
    }

    public boolean canAccess(String code, String telNumber) {
        return code.equals(this.accessCode)
            && telNumber.equals(this.telNumber)
            && !code.isEmpty()
            && !telNumber.isEmpty();
    }

    public void putIn(Delivery delivery, String telNumber, String accessCode) {
        this.accessCode = accessCode;
        this.isEmpty = false;
        this.telNumber = telNumber;
        this.deliveryId = delivery.getEntityId();
    }

    public void clean() {
        isEmpty = true;
        this.accessCode = "";
        this.telNumber = "";
    }

    public Boolean isEmpty() {
        return isEmpty;
    }
}
