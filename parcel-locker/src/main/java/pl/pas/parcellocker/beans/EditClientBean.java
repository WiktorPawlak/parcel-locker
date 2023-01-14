package pl.pas.parcellocker.beans;

import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;
import pl.pas.parcellocker.managers.UserManager;
import pl.pas.parcellocker.model.user.User;

import java.io.Serializable;

@Named
@ConversationScoped
@Getter
@Setter
public class EditClientBean implements Serializable {

    @Inject
    UserManager userManager;

    User currentUser;

    String userType;

    public String edit() {
        currentUser = Utils.prepareUserBasedOnType(currentUser, userType);
        userManager.edit(currentUser.getId(), currentUser);
        return "allUsers";
    }
}
