package pl.pas.parcellocker.beans;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pas.parcellocker.beans.dto.UserDto;
import pl.pas.parcellocker.delivery.http.HttpClient;

@Named
@ViewScoped
@Getter
@Setter
@NoArgsConstructor
public class AllUsersBean extends Conversational implements Serializable {

    @Inject
    AuthorizationStore authorizationStore;

    @Inject
    EditClientBean editClientBean;

    @Inject
    AllUserDeliveriesBean allUserDeliveries;

    List<UserDto> currentUsers;

    String searchValue;

    HttpClient httpClient = new HttpClient();


    @PostConstruct
    public void initCurrentProducts() {
        Response response = httpClient.get("/clients");
        currentUsers = response.readEntity(new GenericType<>() {
        });
    }

    public void searchUsers() {
        Response response = httpClient.get("/clients", Map.of("telNumber", searchValue));
        currentUsers = response.readEntity(new GenericType<>() {
        });
    }

    public String archiveUser(UserDto user) {
        httpClient.putTextBody("/clients/" + user.getTelNumber() + "/archive", "");
        return "allUsers";
    }

    public String unarchiveUser(UserDto user) {
        httpClient.putTextBody("/clients/" + user.getTelNumber() + "/unarchive", "");
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