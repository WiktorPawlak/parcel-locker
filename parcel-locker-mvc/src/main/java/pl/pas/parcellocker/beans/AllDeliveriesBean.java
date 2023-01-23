package pl.pas.parcellocker.beans;


import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import lombok.Getter;
import lombok.Setter;
import pl.pas.parcellocker.model.delivery.Delivery;
import pl.pas.parcellocker.model.delivery.DeliveryStatus;

import java.io.Serializable;
import java.util.List;

import static pl.pas.parcellocker.DeliveriesUtils.updateDeliveries;


@Named
@ViewScoped
@Getter
@Setter
public class AllDeliveriesBean extends Conversational implements Serializable {

    @Inject
    PutInDeliveryBean putInDeliveryBean;
    @Inject
    TakeOutDeliveryBean takeOutDeliveryBean;
    List<Delivery> currentDeliveries;
    Client client = ClientBuilder.newClient();
    HttpClient httpClient = new HttpClient();

    @PostConstruct
    public void initCurrentDeliveries() {
        currentDeliveries = updateDeliveries();
    }

    public String delete(Delivery delivery, String redirect, List<Delivery> deliveries) {
        httpClient.delete("/deliveries/" + delivery.getId());

        deliveries.clear();
        deliveries.addAll(updateDeliveries());
        return redirect;
    }

    public String putIn(Delivery delivery, String redirect, List<Delivery> deliveries) {
        beginNewConversation();
        putInDeliveryBean.setCurrentDelivery(delivery);
        putInDeliveryBean.setDeliveries(deliveries);
        putInDeliveryBean.setRedirect(redirect);
        return "putInDelivery";
    }

    public String takeOut(Delivery delivery, String redirect, List<Delivery> deliveries) {
        beginNewConversation();
        takeOutDeliveryBean.setCurrentDelivery(delivery);
        takeOutDeliveryBean.setDeliveries(deliveries);
        takeOutDeliveryBean.setRedirect(redirect);
        return "takeOutDelivery";
    }

    public DeliveryStatus getReadyToPickupStatus() {
        return DeliveryStatus.READY_TO_PICKUP;
    }

    public DeliveryStatus getReadyToShipStatus() {
        return DeliveryStatus.READY_TO_SHIP;
    }

    public DeliveryStatus getReceivedStatus() {
        return DeliveryStatus.RECEIVED;
    }

}
