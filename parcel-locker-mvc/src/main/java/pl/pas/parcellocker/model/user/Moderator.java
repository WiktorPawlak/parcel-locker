package pl.pas.parcellocker.model.user;


import pl.pas.parcellocker.beans.dto.UserDto;

public class Moderator extends User {
    public Moderator() {}

    public Moderator(UserDto user) {
        super(user);
    }
}
