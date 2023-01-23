package pl.pas.parcellocker.beans;

import jakarta.inject.Named;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.pas.parcellocker.beans.dto.UserDto;
import pl.pas.parcellocker.model.user.Administrator;
import pl.pas.parcellocker.model.user.Client;
import pl.pas.parcellocker.model.user.Moderator;
import pl.pas.parcellocker.model.user.User;

@Named
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Utils {

    public static String getClassName(Object object) {
        return object.getClass().getSimpleName();
    }

    public static String getUserType(UserDto userDto) {
        String type;
        if (userDto.isAdmin()) {
            type = "Administrator";
        } else if (userDto.isModerator()) {
            type = "Moderator";
        } else {
            type = "Client";
        }
        return type;
    }

    public static String getActiveStatus(UserDto user) {
        return user.isActive() ? "Aktywny" : "Nieaktywny";
    }

    public static User prepareUserBasedOnType(UserDto user, String type) {
        if (type.equals("Client")) {
            return new Client(user);
        } else if (type.equals("Moderator")) {
            return new Moderator(user);
        } else {
            return new Administrator(user);
        }
    }
}
