package pl.pas.parcellocker.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import pl.pas.parcellocker.managers.UserManager;
import pl.pas.parcellocker.model.user.Client;
import pl.pas.parcellocker.model.user.User;

@Named
@RequestScoped
@Getter
@Setter
public class AddClientBean {

    @Inject
    UserManager userManager;

    User currentUser = new Client();

    String userType = "Client";

    public String add() {
        currentUser = Utils.prepareUserBasedOnType(currentUser, userType);
        userManager.registerClient(currentUser);
        return "newUser";
    }
}
