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

    @BsonProperty("delivery")
    private Delivery delivery;
    @BsonProperty("isEmpty")
    private boolean isEmpty;
    @BsonProperty("accessCode")
    private String accessCode;
    @BsonProperty("telNumber")
    private String telNumber;

    @BsonCreator
    public DepositBox(@BsonProperty("_id") UniqueId id,
                      @BsonProperty("delivery") Delivery delivery,
                      @BsonProperty("isEmpty") boolean isEmpty,
                      @BsonProperty("accessCode") String accessCode,
                      @BsonProperty("telNumber") String telNumber) {
        super(id);
        this.isEmpty = isEmpty;
        this.telNumber = telNumber;
        this.accessCode = accessCode;
        this.delivery = delivery;
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
        this.delivery = delivery;
    }

    public void clean() {
        isEmpty = true;
        this.accessCode = "";
        this.telNumber = "";
    }
}
