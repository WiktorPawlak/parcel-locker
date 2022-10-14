package pl.pas.parcellocker.model;

import pl.pas.parcellocker.exceptions.LockerException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Locker {

    private List<DepositBox> depositBoxes;

    public Locker(int boxAmount) {
        try {
            if (boxAmount <= 0)
                throw new LockerException("Locker with 0 boxes can not be created!");
        } catch (LockerException e) {
            //TODO (logger)
        }
        depositBoxes = new ArrayList<>();
        for (int i = 0; i < boxAmount; i++) {
            depositBoxes.add(new DepositBox(String.valueOf(i)));
        }
    }

    public String putIn(UUID id, String telNumber, String accessCode) {

        for (DepositBox depositBox : depositBoxes) {
            if (depositBox.isEmpty()) {
                depositBox.putIn(id, telNumber, accessCode);
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
            //TODO logger
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

    public DepositBox getDepositBox(String id) {
        for (DepositBox depositBox: depositBoxes) {
            if (depositBox.getId().equals(id)) {
                return depositBox;
            }
        }
        return null;
    }
}
