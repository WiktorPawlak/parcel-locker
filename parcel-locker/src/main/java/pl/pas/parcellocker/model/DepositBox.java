package pl.pas.parcellocker.model;

import com.datastax.oss.driver.api.mapper.annotations.CqlName;
import com.datastax.oss.driver.api.mapper.annotations.Entity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.pas.parcellocker.configuration.SchemaConst;

import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@Entity(defaultKeyspace = SchemaConst.PARCEL_LOCKER_NAMESPACE)
@CqlName("deposit_box")
public class DepositBox {

    private UUID entityId;
    private UUID deliveryId;
    private Boolean isEmpty;
    private String accessCode;
    private String telNumber;

    public DepositBox() {
        this.entityId = UUID.randomUUID();
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
