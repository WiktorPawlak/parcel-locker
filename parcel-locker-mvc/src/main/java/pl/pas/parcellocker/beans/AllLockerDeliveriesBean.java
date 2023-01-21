package pl.pas.parcellocker.beans;

import jakarta.enterprise.context.ConversationScoped;
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
import pl.pas.parcellocker.beans.dto.UserDto;
import pl.pas.parcellocker.model.delivery.Delivery;
import pl.pas.parcellocker.model.locker.Locker;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.locks.Lock;

import static pl.pas.parcellocker.delivery.http.ModulePaths.DELIVERIES_PATH;

@Getter
@Setter
@Named
@ConversationScoped
public class AllLockerDeliveriesBean implements Serializable {

    List<Delivery> deliveries;
    Locker currentLocker;
    Client client = ClientBuilder.newClient();

    public void initCurrentLockerDeliveries() {
        WebTarget webTarget = client.target(DELIVERIES_PATH + "/locker")
                .path("{identityNumber}");
        Invocation.Builder invocationBuilder = webTarget
                .resolveTemplate("identityNumber", currentLocker.getIdentityNumber())
                .request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();
        deliveries = response.readEntity(new GenericType<>() {
        });
    }
}
