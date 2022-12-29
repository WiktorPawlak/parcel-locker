package pl.pas.parcellocker.model;

import com.datastax.oss.driver.api.mapper.annotations.PartitionKey;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import pl.pas.parcellocker.exceptions.LockerException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Getter
@Setter
@EqualsAndHashCode
public class Locker {

    @PartitionKey
    private UUID entityId;
    private String identityNumber;
    private String address;
    private List<DepositBox> depositBoxes;

    public Locker(String identityNumber,
                  String address,
                  int boxAmount
    ) {
        this.entityId = UUID.randomUUID();
        try {
            if (boxAmount <= 0)
                throw new LockerException("Locker with 0 boxes can not be created!");
        } catch (LockerException e) {
            log.error(e.getMessage());
        }
        this.identityNumber = identityNumber;
        this.address = address;
        depositBoxes = new ArrayList<>();
        for (int i = 0; i < boxAmount; i++) {
            depositBoxes.add(new DepositBox());
        }
    }

    public UUID putIn(Delivery delivery, String telNumber, String accessCode) {

        for (DepositBox depositBox : depositBoxes) {
            if (depositBox.isEmpty()) {
                depositBox.putIn(delivery, telNumber, accessCode);
                return depositBox.getEntityId();
            }
        }
        throw new LockerException("Not able to put package with id = " +
            delivery.getEntityId() + " into locker " + this.getIdentityNumber() + ".");
    }

    public UUID takeOut(String telNumber, String code) {
        for (DepositBox depositBox : depositBoxes) {
            if (depositBox.canAccess(code, telNumber)) {
                depositBox.clean();
                return depositBox.getDeliveryId();
            }
        }
        throw new LockerException("Couldn't get any package out with access code: "
            + code
            + "and phone number: "
            + telNumber);
    }

    public int countEmpty() {
        int counter = 0;
        for (DepositBox depositBox : depositBoxes) {
            if (depositBox.isEmpty()) {
                counter++;
            }
        }
        return counter;
    }

    public DepositBox getDepositBox(UUID id) {
        for (DepositBox depositBox : depositBoxes) {
            if (depositBox.getEntityId().equals(id)) {
                return depositBox;
            }
        }
        return null;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public String getAddress() {
        return address;
    }
}
