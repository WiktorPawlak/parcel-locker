package pl.pas.parcellocker.beans;


import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pas.parcellocker.beans.dto.*;
import pl.pas.parcellocker.delivery.http.ClientHttp;

import static pl.pas.parcellocker.delivery.http.ModulePaths.DELIVERIES_PATH;

@Named
@RequestScoped
@Getter
@Setter
@NoArgsConstructor
public class AddDeliveryBean {

    @Inject
    ClientHttp moduleExecutor;

    DeliveryDto currentDelivery = new DeliveryDto();

    ListDto currentList = new ListDto();

    ParcelDto currentParcel = new ParcelDto();

    String isPriority = "true";

    String isFragile = "false";

    @PostConstruct
    public void prepareModuleExecutor() {
        moduleExecutor.setPathForRemoteCall(DELIVERIES_PATH);
    }

    public String addParcel() {
        jakarta.ws.rs.client.Client client = ClientBuilder.newClient();
        currentParcel.isFragile = Boolean.parseBoolean(isFragile);

        WebTarget target = client.target(DELIVERIES_PATH + "/parcel");

        DeliveryParcelDto deliveryParcelDto = new DeliveryParcelDto(
                currentDelivery.shipperTel,
                currentDelivery.receiverTel,
                currentParcel,
                currentDelivery.lockerId
        );
        target.request().post(Entity.json(deliveryParcelDto));
        return null;
    }

    public String addList() {
        jakarta.ws.rs.client.Client client = ClientBuilder.newClient();

        currentList.isPriority = Boolean.parseBoolean(isPriority);
            WebTarget target = client.target(DELIVERIES_PATH + "/list");

        DeliveryListDto deliveryListDto =
          new DeliveryListDto(
              currentDelivery.shipperTel,
              currentDelivery.receiverTel,
              currentList,
              currentDelivery.lockerId);
        target.request().post(Entity.json(deliveryListDto));

        return null;
    }
}

