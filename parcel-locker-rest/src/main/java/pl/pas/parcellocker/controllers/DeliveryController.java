package pl.pas.parcellocker.controllers;

import java.util.UUID;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import pl.pas.parcellocker.controllers.dto.DeliveryListDto;
import pl.pas.parcellocker.controllers.dto.DeliveryParcelDto;
import pl.pas.parcellocker.exceptions.DeliveryManagerException;
import pl.pas.parcellocker.exceptions.LockerException;
import pl.pas.parcellocker.exceptions.LockerManagerException;
import pl.pas.parcellocker.managers.DeliveryManager;
import pl.pas.parcellocker.model.delivery.Delivery;

@Path(value = "/deliveries")
public class DeliveryController {

    @Context
    private SecurityContext securityContext;

    @Inject
    private DeliveryManager deliveryManager;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMINISTRATOR"})
    public Response getDelivery(@PathParam("id") UUID id) {
        try {
            return Response.ok().entity(deliveryManager.getDelivery(id)).build();
        } catch (NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMINISTRATOR"})
    public Response getAllDeliveries() {
        try {
            return Response.ok().entity(deliveryManager.getAllDeliveries()).build();
        } catch (NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/current")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"CLIENT", "MODERATOR", "ADMINISTRATOR"})
    public Response getCurrentDeliveries() {
        try {
            String clientTelNumber = securityContext.getUserPrincipal().getName();
            return Response.ok().entity(deliveryManager.getAllCurrentClientDeliveries(clientTelNumber)).build();
        } catch (NoResultException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/received")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"CLIENT", "MODERATOR", "ADMINISTRATOR"})
    public Response getReceivedDelivery() {
        try {
            String clientTelNumber = securityContext.getUserPrincipal().getName();
            return Response.ok()
                .entity(deliveryManager.getAllReceivedClientDeliveries(clientTelNumber))
                .build();
        } catch (NoResultException | DeliveryManagerException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/locker/{identityNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"MODERATOR", "ADMINISTRATOR"})
    public Response getLockerDeliveries(@PathParam("identityNumber") String identityNumber) {
        try {
            return Response.ok()
                .entity(deliveryManager.getAllLockerDeliveries(identityNumber))
                .build();
        } catch (NoResultException | DeliveryManagerException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/list")
    @RolesAllowed({"CLIENT", "MODERATOR", "ADMINISTRATOR"})
    public Response addListDelivery(@Valid DeliveryListDto deliveryListDto) {
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

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/parcel")
    @RolesAllowed({"CLIENT", "MODERATOR", "ADMINISTRATOR"})
    public Response addParcelDelivery(@Valid DeliveryParcelDto deliveryParcelDto) {
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

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/put-in")
    @RolesAllowed({"CLIENT", "MODERATOR", "ADMINISTRATOR"})
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
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (LockerException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/take-out")
    @RolesAllowed({"CLIENT", "MODERATOR", "ADMINISTRATOR"})
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
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (LockerException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    @RolesAllowed({"ADMINISTRATOR"})
    public Response removeDelivery(@PathParam("id") UUID deliveryId) {
        try {
            deliveryManager.removeDelivery(deliveryId);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (ValidationException | NullPointerException e) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        } catch (LockerManagerException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
}
