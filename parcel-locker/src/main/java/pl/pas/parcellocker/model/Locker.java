package pl.pas.parcellocker.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;
import pl.pas.parcellocker.exceptions.LockerException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Entity
@NoArgsConstructor
@EqualsAndHashCode
public class Locker extends VersionModel implements EntityClass {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DepositBox> depositBoxes;

    public Locker(int boxAmount) {
        try {
            if (boxAmount <= 0)
                throw new LockerException("Locker with 0 boxes can not be created!");
        } catch (LockerException e) {
            log.error(e.getMessage());
        }
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
            } else {
                throw new LockerException("Not able to put package with id = " + depositBox + " into box.");
            }
        }
        return null;
    }

    public UUID takeOut(String telNumber, String code) {
        for (DepositBox depositBox : depositBoxes) {
            if (depositBox.canAccess(code, telNumber)) {
                depositBox.clean();
                return depositBox.getDeliveryId();
            }
        }
        try {
            throw new LockerException("Couldn't get any package out with access code: "
                    + code
                    + "and phone number: "
                    + telNumber);
        } catch (LockerException e){
            log.error(e.getMessage());
        }
        return null;
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
        for (DepositBox depositBox: depositBoxes) {
            if (depositBox.getId().equals(id)) {
                return depositBox;
            }
        }
        return null;
    }

    @Override
    public UUID getId() {
        return id;
    }
}
