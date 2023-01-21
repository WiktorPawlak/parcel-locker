package pl.pas.parcellocker.model.user;

import lombok.*;
import pl.pas.parcellocker.beans.dto.UserDto;
import pl.pas.parcellocker.model.EntityModel;

@Getter
@Setter
@AllArgsConstructor
public abstract class User extends EntityModel {

    private String firstName;
    private String lastName;
    private String telNumber;
    private boolean active;

    public User(UserDto user) {
        this.id = user.getId();
        this.telNumber = user.getTelNumber();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.active = user.isActive();
    }

    public User() {}

}
