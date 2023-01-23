package pl.pas.parcellocker.beans;

import java.io.Serializable;
import java.util.List;

import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Named;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import lombok.Getter;
import lombok.Setter;
import pl.pas.parcellocker.delivery.http.HttpClient;
import pl.pas.parcellocker.model.delivery.Delivery;
import pl.pas.parcellocker.model.locker.Locker;

@Getter
@Setter
@Named
@ConversationScoped
public class AllLockerDeliveriesBean implements Serializable {

    List<Delivery> deliveries;
    Locker currentLocker;
    Client client = ClientBuilder.newClient();
    HttpClient httpClient = new HttpClient();

    public void initCurrentLockerDeliveries() {
        Response response = httpClient.get("/deliveries/locker/" + currentLocker.getIdentityNumber());

        deliveries = response.readEntity(new GenericType<>() {
        });
    }
}
