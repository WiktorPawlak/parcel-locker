package pl.pas.parcellocker.beans;

import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Named;
import jakarta.ws.rs.client.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pas.parcellocker.delivery.http.HttpClient;
import pl.pas.parcellocker.model.delivery.Delivery;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import static pl.pas.parcellocker.DeliveriesUtils.updateDeliveries;

@Named
@ConversationScoped
@Getter
@Setter
@NoArgsConstructor
public class PutInDeliveryBean extends Conversational implements Serializable {

    Delivery currentDelivery;
    List<Delivery> deliveries;
    String accessCode;
    String redirect;
    HttpClient httpClient = new HttpClient();

    public String putIn() {
        httpClient.put(
                "/deliveries/" + currentDelivery.getId() + "/put-in",
                Entity.json(""),
                Map.of("lockerId", currentDelivery.getLocker().getIdentityNumber(), "accessCode", accessCode));

        deliveries.clear();
        deliveries.addAll(updateDeliveries(redirect, currentDelivery));

        return redirect;
    }

}