package pl.pas.parcellocker.beans;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import lombok.Getter;
import lombok.Setter;
import pl.pas.parcellocker.beans.dto.UserDto;
import pl.pas.parcellocker.delivery.http.HttpClient;
import pl.pas.parcellocker.model.delivery.Delivery;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Named
@ViewScoped
public class AllSelfDeliveriesBean implements Serializable {

    List<Delivery> deliveries;
    UserDto currentUser;
    HttpClient httpClient = new HttpClient();

    @PostConstruct
    public void initCurrentSelfDeliveries() {
        Response response = httpClient.get("/deliveries/current");
        deliveries = response.readEntity(new GenericType<>() {
        });
    }
}
