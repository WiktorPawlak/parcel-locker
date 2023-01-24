package pl.pas.parcellocker;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.pas.parcellocker.delivery.http.HttpClient;
import pl.pas.parcellocker.model.delivery.Delivery;

import java.util.List;
import java.util.Map;

public class DeliveriesUtils {

    private static final HttpClient httpClient = new HttpClient();

    public static List<Delivery> updateDeliveries(String redirect, Delivery delivery) {
        Response response;
        if (redirect.equals("allLockerDeliveries")) {
            response = httpClient.get("/deliveries/locker/" + delivery.getLocker().getIdentityNumber());
        } else if (redirect.equals("allUserDeliveries")) {
            response = httpClient.get("/deliveries/current", Map.of("telNumber", delivery.getReceiver().getTelNumber()));
        } else if (redirect.equals("allSelfDeliveries")) {
            response = httpClient.get("/deliveries/current");
        }
        else {
            response = httpClient.get("/deliveries");
        }
        return response.readEntity(new GenericType<>() {
        });
    }
}
