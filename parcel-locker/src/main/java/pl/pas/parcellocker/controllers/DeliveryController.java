package pl.pas.parcellocker.controllers;

import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.pas.parcellocker.controllers.dto.DeliveryListDto;
import pl.pas.parcellocker.controllers.dto.DeliveryParcelDto;
import pl.pas.parcellocker.exceptions.DeliveryManagerException;
import pl.pas.parcellocker.managers.DeliveryManager;
import pl.pas.parcellocker.model.delivery.Delivery;

import java.util.UUID;

@Path(value = "/deliveries")
public class DeliveryController {

  @Inject private DeliveryManager deliveryManager;

  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getDelivery(@PathParam("id") UUID id) {
    try {
      return Response.ok().entity(deliveryManager.getDelivery(id)).build();
    } catch (NoResultException e) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/list")
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
    } catch (DeliveryManagerException e) {
      return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
    }
  }

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/parcel")
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
    } catch (DeliveryManagerException e) {
      return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
    }
  }

  @PUT
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/{id}/put-in")
  public Response putInLocker(
      @PathParam("id") UUID deliveryId,
      @QueryParam("lockerId") String lockerId,
      @QueryParam("accessCode") String accessCode) {
    try {
      deliveryManager.putInLocker(deliveryId, lockerId, accessCode);
      return Response.ok().build();
    } catch (ValidationException | NullPointerException e) {
      return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    } catch (DeliveryManagerException e) {
      return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
    }
  }

  @PUT
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/{id}/take-out")
  public Response takeOutDelivery(
      @PathParam("id") UUID deliveryId,
      @QueryParam("telNumber") String telNumber,
      @QueryParam("accessCode") String accessCode) {
    try {
      deliveryManager.takeOutDelivery(deliveryId, telNumber, accessCode);
      return Response.ok().build();
    } catch (ValidationException | NullPointerException e) {
      return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    } catch (DeliveryManagerException e) {
      return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
    }
  }

}
