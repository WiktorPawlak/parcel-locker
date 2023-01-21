package pl.pas.parcellocker.beans;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pas.parcellocker.beans.dto.UserDto;
import pl.pas.parcellocker.model.locker.Locker;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
@Getter
@Setter
@NoArgsConstructor
public class AllLockersBean extends Conversational implements Serializable {

    @Inject
    AllLockerDeliveriesBean allLockerDeliveriesBean;
    List<Locker> currentLockers;
    Client client = ClientBuilder.newClient();

    @PostConstruct
    public void initCurrentProducts() {
        WebTarget webTarget = client.target("http://localhost:8080/parcel-locker-rest-1.0-SNAPSHOT/api/lockers");
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();
        currentLockers = response.readEntity(new GenericType<>() {
        });
    }

    public int emptyBoxes(String identityNumber) {
        WebTarget webTarget = client.target("http://localhost:8080/parcel-locker-rest-1.0-SNAPSHOT/api/lockers")
                .path("{id}").path("/empty");
        Invocation.Builder invocationBuilder = webTarget.resolveTemplate("id", identityNumber).request(MediaType.APPLICATION_JSON);
        return invocationBuilder.get().readEntity(Integer.class);
    }

    public String searchLockerDeliveries(Locker locker) {
        beginNewConversation();
        allLockerDeliveriesBean.setCurrentLocker(locker);
        allLockerDeliveriesBean.initCurrentLockerDeliveries();
        return "allLockerDeliveries";
    }

}