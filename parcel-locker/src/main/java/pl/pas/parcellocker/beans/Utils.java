package pl.pas.parcellocker.beans;

import jakarta.inject.Named;
import pl.pas.parcellocker.model.user.Administrator;
import pl.pas.parcellocker.model.user.Client;
import pl.pas.parcellocker.model.user.Moderator;
import pl.pas.parcellocker.model.user.User;

@Named
public class Utils {

    public static String getClassName(Object object) {
        return object.getClass().getSimpleName();
    }

    public static String getActiveStatus(User user) {
        return user.isActive() ? "Aktywny" : "Nieaktywny";
    }

    public static User prepareUserBasedOnType(User user, String type) {
        if (type.equals("Client")) {
            return new Client(user);
        } else if (type.equals("Moderator")) {
            return new Moderator(user);
        } else {
            return new Administrator(user);
        }
    }
}
