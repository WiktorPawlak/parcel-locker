package pl.pas.parcellocker.beans;

import java.io.Serializable;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pas.parcellocker.delivery.http.HttpClient;
import pl.pas.parcellocker.model.locker.Locker;

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
    HttpClient httpClient = new HttpClient();

    @PostConstruct
    public void initCurrentProducts() {
        Response response = httpClient.get("/lockers");
        currentLockers = response.readEntity(new GenericType<>() {
        });
    }

    public int emptyBoxes(String identityNumber) {
        return httpClient.get("/lockers/" + identityNumber + "/empty").readEntity(Integer.class);
    }

    public String searchLockerDeliveries(Locker locker) {
        beginNewConversation();
        allLockerDeliveriesBean.setCurrentLocker(locker);
        allLockerDeliveriesBean.initCurrentLockerDeliveries();
        return "allLockerDeliveries";
    }

}