package pl.pas.parcellocker.beans;

import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Named;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pas.parcellocker.model.delivery.Delivery;

import java.io.Serializable;

@Named
@ConversationScoped
@Getter
@Setter
@NoArgsConstructor
public class TakeOutDeliveryBean implements Serializable {

    Delivery currentDelivery;
    String accessCode;
    Client client = ClientBuilder.newClient();

    public String takeOut() {
        WebTarget target = client.target("http://localhost:8080/parcel-locker-rest-1.0-SNAPSHOT/api/deliveries")
                .path("{id}").path("/take-out");

        target.resolveTemplate("id", currentDelivery.getId())
                .queryParam("telNumber", currentDelivery.getReceiver().getTelNumber())
                .queryParam("accessCode", accessCode)
                .request().put(Entity.json(""));

        return "allDeliveries";
    }
}
