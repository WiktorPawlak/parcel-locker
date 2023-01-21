package pl.pas.parcellocker.beans;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pas.parcellocker.beans.dto.UserDto;
import pl.pas.parcellocker.delivery.http.ClientHttp;
import pl.pas.parcellocker.model.user.User;

import java.io.Serializable;
import java.util.List;

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

    List<UserDto> currentUsers;

    String searchValue;

    @PostConstruct
    public void initCurrentProducts() {
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target("http://localhost:8080/parcel-locker-rest-1.0-SNAPSHOT/api/clients");
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();
        currentUsers = response.readEntity(new GenericType<>() {});
    }

    public void searchUsers() {
//        currentUsers = moduleExecutor.getTarget().queryParam(searchValue).request(MediaType.APPLICATION_JSON).get().readEntity(new GenericType<>() {
//        });
        //currentUsers = (List<User>) clientController.getClientsByPhoneNumberPattern(searchValue);
    }

    public String unregisterUser(User user) {
//        moduleExecutor.getTarget().request(MediaType.APPLICATION_JSON).put(Entity.json(user.getTelNumber()));
        //clientController.unregisterClient(user.getTelNumber());
        return "allUsers";
    }

    public String editUser(UserDto user) {
        beginNewConversation();
        editClientBean.setCurrentUser(user);
        editClientBean.setUserType(user.getClass().getSimpleName());
        editClientBean.edit();
        return "editUser";
    }

}