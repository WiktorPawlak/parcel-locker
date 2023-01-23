package pl.pas.parcellocker.beans;

import static pl.pas.parcellocker.DeliveriesUtils.updateDeliveries;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pas.parcellocker.delivery.http.HttpClient;
import pl.pas.parcellocker.model.delivery.Delivery;

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
