package pl.pas.parcellocker.api;


import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import pl.pas.parcellocker.api.dto.LockerDto;


@Path(value = "/lockers")
public interface LockerController {

    @GET
    @Path("/{identityNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    Response getLocker(@PathParam("identityNumber") String identityNumber);

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    Response addLocker(@Valid LockerDto lockerDto);

    @DELETE
    @Path("/{identityNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    Response removeLocker(@PathParam("identityNumber") String identityNumber);

}
