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
        WebTarget webTarget = client.target("http://localhost:8080/parcel-locker-rest-1.0-SNAPSHOT/api/clients")
                .queryParam("telNumber", searchValue);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();
        currentUsers = response.readEntity(new GenericType<>() {
        });
    }

    public String archiveUser(UserDto user) {
        WebTarget target = client.target("http://localhost:8080/parcel-locker-rest-1.0-SNAPSHOT/api/clients")
                .path("{id}").path("/archive");

        target.resolveTemplate("id", user.getTelNumber()).request().put(Entity.text(""));
        return "allUsers";
    }

    public String unarchiveUser(UserDto user) {
        WebTarget target = client.target("http://localhost:8080/parcel-locker-rest-1.0-SNAPSHOT/api/clients")
                .path("{id}").path("/unarchive");

        target.resolveTemplate("id", user.getTelNumber()).request().put(Entity.text(""));
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