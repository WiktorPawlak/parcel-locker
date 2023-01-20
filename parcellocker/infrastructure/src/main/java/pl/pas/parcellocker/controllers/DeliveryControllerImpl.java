package pl.pas.parcellocker.controllers;

import java.util.UUID;

import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import pl.pas.parcellocker.api.DeliveryControllerApi;
import pl.pas.parcellocker.api.dto.DeliveryListDto;
import pl.pas.parcellocker.api.dto.DeliveryParcelDto;
import pl.pas.parcellocker.exceptions.DeliveryManagerException;
import pl.pas.parcellocker.exceptions.LockerException;
import pl.pas.parcellocker.managers.DeliveryManager;
import pl.pas.parcellocker.model.delivery.Delivery;

@Path(value = "/deliveries")
public class DeliveryControllerImpl implements DeliveryControllerApi {

    @Inject
    private DeliveryManager deliveryManager;

    public Response getDelivery(UUID id) {
        try {
            return Response.ok().entity(deliveryManager.getDelivery(id)).build();
        } catch (NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    public Response getCurrentDeliveries(String telNumber) {
        try {
            return Response.ok().entity(deliveryManager.getAllCurrentClientDeliveries(telNumber)).build();
        } catch (NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    public Response getReceivedDelivery(String telNumber) {
        try {
            return Response.ok()
                .entity(deliveryManager.getAllReceivedClientDeliveries(telNumber))
                .build();
        } catch (NoResultException | DeliveryManagerException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    public Response addListDelivery(DeliveryListDto deliveryListDto) {
        try {
            Delivery delivery =
                deliveryManager.makeListDelivery(
                    deliveryListDto.pack.basePrice,
                    deliveryListDto.pack.isPriority,
                    deliveryListDto.shipperTel,
                    deliveryListDto.receiverTel,
                    deliveryListDto.lockerId);
            return Response.status(Response.Status.CREATED).entity(delivery).build();
        } catch (ValidationException | NullPointerException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        } catch (DeliveryManagerException | NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    public Response addParcelDelivery(DeliveryParcelDto deliveryParcelDto) {
        try {
            Delivery delivery =
                deliveryManager.makeParcelDelivery(
                    deliveryParcelDto.pack.basePrice,
                    deliveryParcelDto.pack.width,
                    deliveryParcelDto.pack.height,
                    deliveryParcelDto.pack.length,
                    deliveryParcelDto.pack.weight,
                    deliveryParcelDto.pack.isFragile,
                    deliveryParcelDto.shipperTel,
                    deliveryParcelDto.receiverTel,
                    deliveryParcelDto.lockerId);
            return Response.status(Response.Status.CREATED).entity(delivery).build();
        } catch (ValidationException | NullPointerException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        } catch (DeliveryManagerException | NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    public Response putInLocker(UUID deliveryId, String lockerId, String accessCode) {
        try {
            deliveryManager.putInLocker(deliveryId, lockerId, accessCode);
            return Response.ok().build();
        } catch (ValidationException | NullPointerException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        } catch (DeliveryManagerException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (LockerException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        }
    }

    public Response takeOutDelivery(UUID deliveryId, String telNumber, String accessCode) {
        try {
            deliveryManager.takeOutDelivery(deliveryId, telNumber, accessCode);
            return Response.ok().build();
        } catch (ValidationException | NullPointerException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        } catch (DeliveryManagerException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (LockerException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        }
    }
}
