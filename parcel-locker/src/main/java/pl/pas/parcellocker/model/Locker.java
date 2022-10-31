package pl.pas.parcellocker.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.codecs.pojo.annotations.BsonProperty;
import pl.pas.parcellocker.exceptions.LockerException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@NoArgsConstructor
@EqualsAndHashCode
public class Locker extends EntityModel {

    @BsonProperty("identityNumber")
    private String identityNumber;

    @BsonProperty("address")
    private String address;

    private List<DepositBox> depositBoxes;

    public Locker(@BsonProperty("identityNumber") String identityNumber,
                  @BsonProperty("address") String address,
                  int boxAmount
    ) {
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
                return depositBox.getId();
            }
        }
        throw new LockerException("Not able to put package with id = " +
            delivery.getId() + " into locker " + this.getIdentityNumber() + ".");
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
            if (depositBox.getId().equals(id)) {
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
