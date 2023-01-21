package pl.pas.parcellocker.model.user;


import pl.pas.parcellocker.beans.dto.UserDto;

public class Administrator extends User {

    public Administrator() {}

    public Administrator(UserDto user) {
        super(user);
    }
}
