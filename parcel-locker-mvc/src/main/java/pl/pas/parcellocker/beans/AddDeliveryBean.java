package pl.pas.parcellocker.beans;


import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.client.Entity;
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

    HttpClient httpClient = new HttpClient();

    @PostConstruct
    public void prepareModuleExecutor() {
        moduleExecutor.setPathForRemoteCall(DELIVERIES_PATH);
    }

    public String addParcel() {
        DeliveryParcelDto deliveryParcelDto = new DeliveryParcelDto(
                currentDelivery.shipperTel,
                currentDelivery.receiverTel,
                currentParcel,
                currentDelivery.lockerId
        );
        httpClient.post("/deliveries/parcel", Entity.json(deliveryParcelDto));
        return null;
    }

    public String addList() {
        DeliveryListDto deliveryListDto =
          new DeliveryListDto(
              currentDelivery.shipperTel,
              currentDelivery.receiverTel,
              currentList,
              currentDelivery.lockerId);
        httpClient.post("/deliveries/list", deliveryListDto);

        return null;
    }
}

