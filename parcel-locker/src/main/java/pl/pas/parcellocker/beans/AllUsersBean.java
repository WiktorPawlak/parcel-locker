package pl.pas.parcellocker.beans;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import pl.pas.parcellocker.managers.UserManager;
import pl.pas.parcellocker.model.user.User;

import java.util.List;

@Named
@ViewScoped
@Getter
@Setter
public class AllUsersBean extends Conversational {

    @Inject
    UserManager userManager;

    @Inject
    EditClientBean editClientBean;

    List<User> currentUsers;

    String searchValue;

    @PostConstruct
    public void initCurrentProducts() {
        currentUsers = userManager.findAll();
    }

    public void searchUsers() {
        currentUsers = userManager.getUsersByPartialTelNumber(searchValue);
    }

    public String unregisterUser(User user) {
        userManager.unregisterClient(user);
        return "allUsers";
    }

    public String editUser(User user) {
        beginNewConversation();
        editClientBean.setCurrentUser(user);
        editClientBean.setUserType(user.getClass().getSimpleName());
        return "editUser";
    }

}
