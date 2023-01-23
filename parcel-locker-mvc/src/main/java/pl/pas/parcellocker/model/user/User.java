package pl.pas.parcellocker.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.pas.parcellocker.beans.dto.UserDto;
import pl.pas.parcellocker.model.EntityModel;

@Getter
@Setter
@AllArgsConstructor
public abstract class User extends EntityModel {

    private String telNumber;
    private String password;
    private String firstName;
    private String lastName;
    private boolean active;

    public User(UserDto user) {
        this.id = user.getId();
        this.telNumber = user.getTelNumber();
        this.password = user.getPassword();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.active = user.isActive();
    }

    public User() {
    }

}
