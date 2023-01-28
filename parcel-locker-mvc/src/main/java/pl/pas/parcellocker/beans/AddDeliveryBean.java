package pl.pas.parcellocker.beans;


import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.client.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pas.parcellocker.beans.dto.DeliveryDto;
import pl.pas.parcellocker.beans.dto.DeliveryListDto;
import pl.pas.parcellocker.beans.dto.DeliveryParcelDto;
import pl.pas.parcellocker.beans.dto.ListDto;
import pl.pas.parcellocker.beans.dto.ParcelDto;
import pl.pas.parcellocker.delivery.http.HttpClient;

@Named
@RequestScoped
@Getter
@Setter
@NoArgsConstructor
public class AddDeliveryBean {

    @Inject
    IdentityHandler identityHandler;

    DeliveryDto currentDelivery = new DeliveryDto();

    ListDto currentList = new ListDto();

    ParcelDto currentParcel = new ParcelDto();

    String isPriority = "true";

    String isFragile = "false";

    HttpClient httpClient = new HttpClient();

    public String addParcel() {
        DeliveryParcelDto deliveryParcelDto = new DeliveryParcelDto(
                prepareShipperTel(),
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
                        prepareShipperTel(),
                        currentDelivery.receiverTel,
                        currentList,
                        currentDelivery.lockerId);
        httpClient.post("/deliveries/list", deliveryListDto);

        return null;
    }

    private String prepareShipperTel() {
        String shipperTel;
        if (identityHandler.isUserInRole("CLIENT")) {
            shipperTel = identityHandler.getUserTelNumber();
        } else {
            shipperTel = currentDelivery.shipperTel;
        }
        return shipperTel;
    }
}

