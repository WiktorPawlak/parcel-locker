package pl.pas.parcellocker.beans;

import static pl.pas.parcellocker.delivery.http.ModulePaths.DELIVERIES_PATH;

import java.io.Serializable;
import java.util.List;

import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Named;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.Getter;
import lombok.Setter;
import pl.pas.parcellocker.beans.dto.UserDto;
import pl.pas.parcellocker.model.delivery.Delivery;

@Getter
@Setter
@Named
@ConversationScoped
public class AllUserDeliveriesBean implements Serializable {

    List<Delivery> deliveries;
    UserDto currentUser;
    Client client = ClientBuilder.newClient();

    public void initCurrentUserDeliveries() {
        WebTarget webTarget = client.target(DELIVERIES_PATH + "/current")
                .queryParam("telNumber", currentUser.getTelNumber());
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();
        deliveries = response.readEntity(new GenericType<>() {
        });
    }
}
