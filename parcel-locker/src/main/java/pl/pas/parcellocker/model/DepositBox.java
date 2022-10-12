package pl.pas.parcellocker.model;

import java.util.UUID;

public class DepositBox {

    private UUID deliveryId;
    private String id;
    private boolean isEmpty;
    private String accessCode;
    private String telNumber;

    public DepositBox(String id) {
        this.id = id;
        isEmpty = false;
        telNumber = "";
        accessCode = "";
    }

    public boolean canAccess(String code, String telNumber) {
        if (code.equals(this.accessCode)
                && telNumber.equals(this.telNumber)
                && !code.isEmpty()
                && !telNumber.isEmpty()) {
            isEmpty = false;
            this.accessCode = "";
            this.telNumber = "";
            return true;
        } else {
            return false;
        }
    }

    public void clean() {
        isEmpty = false;
        this.accessCode = "";
        this.telNumber = "";
    }

    public UUID getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(UUID deliveryId) {
        this.deliveryId = deliveryId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setIsEmpty(boolean empty) {
        this.isEmpty = empty;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }
}
