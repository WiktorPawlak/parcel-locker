package pl.pas.parcellocker.api;


import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.pas.parcellocker.api.dto.DeliveryListDto;
import pl.pas.parcellocker.api.dto.DeliveryParcelDto;


@Path(value = "/deliveries")
public interface DeliveryControllerApi {

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getDelivery(@PathParam("id") UUID id);

    @GET
    @Path("/current")
    @Produces(MediaType.APPLICATION_JSON)
    Response getCurrentDeliveries(@QueryParam("telNumber") String telNumber);

    @GET
    @Path("/received")
    @Produces(MediaType.APPLICATION_JSON)
    Response getReceivedDelivery(@QueryParam("telNumber") String telNumber);

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/list")
    Response addListDelivery(@Valid DeliveryListDto deliveryListDto);

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/parcel")
    Response addParcelDelivery(@Valid DeliveryParcelDto deliveryParcelDto);

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/put-in")
    Response putInLocker(@PathParam("id") UUID deliveryId, @QueryParam("lockerId") String lockerId, @QueryParam("accessCode") String accessCode);

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}/take-out")
    Response takeOutDelivery(@PathParam("id") UUID deliveryId, @QueryParam("telNumber") String telNumber, @QueryParam("accessCode") String accessCode);
}
