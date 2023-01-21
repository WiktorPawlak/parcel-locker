package pl.pas.parcellocker.beans;

import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Named;
import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pas.parcellocker.model.delivery.Delivery;

import java.io.Serializable;
import java.util.List;

import static pl.pas.parcellocker.DeliveriesUtils.updateDeliveries;

@Named
@ConversationScoped
@Getter
@Setter
@NoArgsConstructor
public class TakeOutDeliveryBean implements Serializable {

    Delivery currentDelivery;
    List<Delivery> deliveries;
    String accessCode;
    String redirect;
    Client client = ClientBuilder.newClient();

    public String takeOut() {
        WebTarget target = client.target("http://localhost:8080/parcel-locker-rest-1.0-SNAPSHOT/api/deliveries")
                .path("{id}").path("/take-out");

        target.resolveTemplate("id", currentDelivery.getId())
                .queryParam("telNumber", currentDelivery.getReceiver().getTelNumber())
                .queryParam("accessCode", accessCode)
                .request().put(Entity.json(""));

        deliveries.clear();
        deliveries.addAll(updateDeliveries());

        return redirect;
    }

}
