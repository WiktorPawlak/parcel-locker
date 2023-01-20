package pl.pas.parcellocker.model.locker;

import lombok.Data;
import pl.pas.parcellocker.model.EntityModel;

import java.util.List;

@Data
public class Locker extends EntityModel {

    private String identityNumber;
    private String address;

    private List<DepositBox> depositBoxes;

}
