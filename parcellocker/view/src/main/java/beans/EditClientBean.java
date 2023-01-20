package beans;

import static delivery.http.ModulePaths.CLIENTS_PATH;

import java.io.Serializable;

import delivery.http.ClientHttp;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.client.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pas.parcellocker.model.user.User;

@Named
@ConversationScoped
@Getter
@Setter
@NoArgsConstructor
public class EditClientBean implements Serializable {

    @Inject
    transient ClientHttp moduleExecutor;

    User currentUser;

    String userType;

    @PostConstruct
    public void prepareModuleExecutor() {
        moduleExecutor.setPathForRemoteCall(CLIENTS_PATH);
    }

    public String edit() {
        currentUser = Utils.prepareUserBasedOnType(currentUser, userType);
        moduleExecutor.getTarget().request().put(Entity.json(currentUser));
        //clientController.editClient(currentUser.getId(), currentUser);
        return "allUsers";
    }
}
