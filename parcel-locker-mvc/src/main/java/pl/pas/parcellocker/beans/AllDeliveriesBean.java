package pl.pas.parcellocker.beans;


import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.Getter;
import lombok.Setter;
import pl.pas.parcellocker.beans.Conversational;
import pl.pas.parcellocker.model.delivery.Delivery;
import pl.pas.parcellocker.model.delivery.DeliveryStatus;

import java.io.Serializable;
import java.util.List;


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


    @PostConstruct
    public void initCurrentDeliveries() {
        WebTarget webTarget = client.target("http://localhost:8080/parcel-locker-rest-1.0-SNAPSHOT/api/deliveries");
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();
        currentDeliveries = response.readEntity(new GenericType<>() {
        });
    }

    public String delete(Delivery delivery) {
        WebTarget webTarget = client.target("http://localhost:8080/parcel-locker-rest-1.0-SNAPSHOT/api/deliveries")
                .path("{id}");
        Invocation.Builder invocationBuilder = webTarget
                .resolveTemplate("id", delivery.getId())
                .request();
        invocationBuilder.delete();

        return "allDeliveries";
    }

    public String putIn(Delivery delivery) {
        beginNewConversation();
        putInDeliveryBean.setCurrentDelivery(delivery);
        return "putInDelivery";
    }

    public String takeOut(Delivery delivery) {
        beginNewConversation();
        takeOutDeliveryBean.setCurrentDelivery(delivery);
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
