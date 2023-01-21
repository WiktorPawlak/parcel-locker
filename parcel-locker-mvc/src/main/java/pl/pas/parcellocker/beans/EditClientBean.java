package pl.pas.parcellocker.beans;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.client.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pas.parcellocker.beans.dto.UserDto;
import pl.pas.parcellocker.delivery.http.ClientHttp;
import pl.pas.parcellocker.model.user.User;

import java.io.Serializable;

import static pl.pas.parcellocker.delivery.http.ModulePaths.CLIENTS_PATH;

@Named
@ConversationScoped
@Getter
@Setter
@NoArgsConstructor
public class EditClientBean implements Serializable {

    @Inject
    transient ClientHttp moduleExecutor;

    UserDto currentUser;

    String userType;

    @PostConstruct
    public void prepareModuleExecutor() {
        moduleExecutor.setPathForRemoteCall(CLIENTS_PATH);
    }

    public String edit() {
        User userBasedOnType = Utils.prepareUserBasedOnType(currentUser, userType);
        moduleExecutor.getTarget().request().put(Entity.json(userBasedOnType));
        //clientController.editClient(currentUser.getId(), currentUser);
        return "allUsers";
    }
}