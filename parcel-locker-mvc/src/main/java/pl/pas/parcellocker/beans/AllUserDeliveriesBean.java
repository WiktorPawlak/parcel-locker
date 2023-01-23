package pl.pas.parcellocker.beans;

import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Named;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import lombok.Getter;
import lombok.Setter;
import pl.pas.parcellocker.beans.dto.UserDto;
import pl.pas.parcellocker.model.delivery.Delivery;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Named
@ConversationScoped
public class AllUserDeliveriesBean implements Serializable {

    List<Delivery> deliveries;
    UserDto currentUser;
    Client client = ClientBuilder.newClient();
    HttpClient httpClient = new HttpClient();

    public void initCurrentUserDeliveries() {
        Response response = httpClient.get("deliveries/current", Map.of("telNumber", currentUser.getTelNumber()));
        deliveries = response.readEntity(new GenericType<>() {
        });
    }
}
