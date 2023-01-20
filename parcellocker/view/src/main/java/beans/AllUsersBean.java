package beans;

import static delivery.http.ModulePaths.CLIENTS_PATH;

import java.io.Serializable;
import java.util.List;

import delivery.http.ClientHttp;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pas.parcellocker.model.user.User;

@Named
@ViewScoped
@Getter
@Setter
@NoArgsConstructor
public class AllUsersBean extends Conversational implements Serializable {

    @Inject
    transient ClientHttp moduleExecutor;

    @Inject
    EditClientBean editClientBean;

    List<User> currentUsers;

    String searchValue;

    @PostConstruct
    public void initCurrentProducts() {
        moduleExecutor.setPathForRemoteCall(CLIENTS_PATH);
        moduleExecutor.getTarget().request(MediaType.APPLICATION_JSON).get().readEntity(new GenericType<List<User>>() {
        });
        //currentUsers = (List<User>) clientController.getAllClients().getEntity();
    }

    public void searchUsers() {
        currentUsers = moduleExecutor.getTarget().queryParam(searchValue).request(MediaType.APPLICATION_JSON).get().readEntity(new GenericType<>() {
        });
        //currentUsers = (List<User>) clientController.getClientsByPhoneNumberPattern(searchValue);
    }

    public String unregisterUser(User user) {
        moduleExecutor.getTarget().request(MediaType.APPLICATION_JSON).put(Entity.json(user.getTelNumber()));
        //clientController.unregisterClient(user.getTelNumber());
        return "allUsers";
    }

    public String editUser(User user) {
        beginNewConversation();
        editClientBean.setCurrentUser(user);
        editClientBean.setUserType(user.getClass().getSimpleName());
        editClientBean.edit();
        return "editUser";
    }

}
