package pl.pas.parcellocker.beans;

import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Named;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
public class TakeOutDeliveryBean implements Serializable {

    Delivery currentDelivery;
    List<Delivery> deliveries;
    String accessCode;
    String redirect;
    Client client = ClientBuilder.newClient();
    HttpClient httpClient = new HttpClient();

    public String takeOut() {
        httpClient.put(
                "/deliveries/" + currentDelivery.getId() + "/take-out",
                "",
                Map.of("lockerId", currentDelivery.getReceiver().getTelNumber(), "accessCode", accessCode));
        deliveries.clear();
        deliveries.addAll(updateDeliveries());

        deliveries.clear();
        deliveries.addAll(updateDeliveries());

        return redirect;
    }

}
