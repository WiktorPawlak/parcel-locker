package pl.pas.parcellocker.beans;

import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pas.parcellocker.beans.dto.DeliveryListDto;
import pl.pas.parcellocker.beans.dto.UserDto;
import pl.pas.parcellocker.model.delivery.Delivery;
import pl.pas.parcellocker.model.user.User;

import java.io.Serializable;
import java.util.List;

import static pl.pas.parcellocker.DeliveriesUtils.updateDeliveries;
import static pl.pas.parcellocker.delivery.http.ModulePaths.DELIVERIES_PATH;

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
    Client client = ClientBuilder.newClient();

    public String putIn() {
        WebTarget target = client.target("http://localhost:8080/parcel-locker-rest-1.0-SNAPSHOT/api/deliveries")
                .path("{id}").path("/put-in");

        target.resolveTemplate("id", currentDelivery.getId())
                .queryParam("lockerId", currentDelivery.getLocker().getIdentityNumber())
                .queryParam("accessCode", accessCode)
                .request().put(Entity.json(""));

        deliveries.clear();
        deliveries.addAll(updateDeliveries());

        return redirect;
    }

}
