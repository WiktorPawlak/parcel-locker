package pl.pas.parcellocker.model.locker;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import pl.pas.parcellocker.model.EntityModel;
import pl.pas.parcellocker.model.delivery.Delivery;

import java.util.UUID;

@Entity
@EqualsAndHashCode
public class DepositBox extends EntityModel {

    @ManyToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;
    private boolean isEmpty;
    private String accessCode;
    private String telNumber;

    public DepositBox() {
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
        this.delivery = delivery;
    }

    public void clean() {
        isEmpty = true;
        this.accessCode = "";
        this.telNumber = "";
        this.delivery = null;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }
}
