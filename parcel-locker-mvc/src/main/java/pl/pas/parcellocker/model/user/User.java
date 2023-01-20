package pl.pas.parcellocker.model.user;

import lombok.Data;
import pl.pas.parcellocker.model.EntityModel;

@Data
public abstract class User extends EntityModel {

    private String firstName;
    private String lastName;
    private String telNumber;
    private boolean active;

    public User(User user) {
        this.id = user.getId();
        this.telNumber = user.getTelNumber();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.active = user.isActive();
    }

    public User() {}

}
