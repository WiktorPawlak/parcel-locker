package pl.pas.parcellocker.model.user;


import pl.pas.parcellocker.beans.dto.UserDto;

public class Client extends User {

    public Client(UserDto user) {
        super(user);
    }

    public Client() {
        super();
    }
}
