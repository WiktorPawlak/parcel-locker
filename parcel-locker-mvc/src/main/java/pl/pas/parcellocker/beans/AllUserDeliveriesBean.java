package pl.pas.parcellocker.beans;

import java.io.Serializable;
import java.util.List;

import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Named;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import lombok.Getter;
import lombok.Setter;
import pl.pas.parcellocker.beans.dto.UserDto;
import pl.pas.parcellocker.delivery.http.HttpClient;
import pl.pas.parcellocker.model.delivery.Delivery;

@Getter
@Setter
@Named
@ConversationScoped
public class AllUserDeliveriesBean implements Serializable {

    List<Delivery> deliveries;
    UserDto currentUser;
    HttpClient httpClient = new HttpClient();

    public void initCurrentUserDeliveries() {
        Response response = httpClient.get("/deliveries/current");
        deliveries = response.readEntity(new GenericType<>() {
        });
    }
}
