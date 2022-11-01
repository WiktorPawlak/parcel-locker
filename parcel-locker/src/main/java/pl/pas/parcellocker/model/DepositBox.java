package pl.pas.parcellocker.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
public class DepositBox extends MongoEntityModel {

    @BsonProperty("deliveryId")
    private UniqueId deliveryId;
    @BsonProperty("isEmpty")
    private Boolean isEmpty;
    @BsonProperty("accessCode")
    private String accessCode;
    @BsonProperty("telNumber")
    private String telNumber;

    @BsonCreator
    public DepositBox(@BsonProperty("_id") UniqueId id,
                      @BsonProperty("delivery") UniqueId deliveryId,
                      @BsonProperty("isEmpty") Boolean isEmpty,
                      @BsonProperty("accessCode") String accessCode,
                      @BsonProperty("telNumber") String telNumber) {
        super(id);
        this.isEmpty = isEmpty;
        this.telNumber = telNumber;
        this.accessCode = accessCode;
        this.deliveryId = deliveryId;
    }

    public DepositBox() {
        super(new UniqueId());
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
