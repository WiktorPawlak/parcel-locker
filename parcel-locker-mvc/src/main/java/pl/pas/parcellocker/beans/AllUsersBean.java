package pl.pas.parcellocker.beans;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pas.parcellocker.beans.dto.UserDto;
import pl.pas.parcellocker.delivery.http.ClientHttp;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Named
@ViewScoped
@Getter
@Setter
@NoArgsConstructor
public class AllUsersBean extends Conversational implements Serializable {

    @Inject
    transient ClientHttp moduleExecutor;

    @Inject
    AuthorizationStore authorizationStore;

    @Inject
    EditClientBean editClientBean;

    @Inject
    AllUserDeliveriesBean allUserDeliveries;

    List<UserDto> currentUsers;

    String searchValue;

    Client client = ClientBuilder.newClient();

    HttpClient httpClient = new HttpClient();

    @PostConstruct
    public void initCurrentProducts() {
        Response response = httpClient.get("/clients");
        currentUsers = response.readEntity(new GenericType<>() {
        });
    }

    public void searchUsers() {
        Response response =  httpClient.get("/clients", Map.of("telNumber", searchValue));
        currentUsers = response.readEntity(new GenericType<>() {
        });
    }

    public String archiveUser(UserDto user) {
        Response response = httpClient.put("/clients/" +  user.getTelNumber() + "/archive", "");
        return "allUsers";
    }

    public String unarchiveUser(UserDto user) {
        httpClient.put("/clients/" +  user.getTelNumber() + "/unarchive", "");
        return "allUsers";
    }

    public String editUser(UserDto user) {
        beginNewConversation();
        editClientBean.setCurrentUser(user);
        editClientBean.edit();
        return "editUser";
    }

    public String searchUserDeliveries(UserDto user) {
        beginNewConversation();
        allUserDeliveries.setCurrentUser(user);
        allUserDeliveries.initCurrentUserDeliveries();
        return "allUserDeliveries";
    }

}