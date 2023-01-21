package pl.pas.parcellocker.controllers;

import jakarta.inject.Inject;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import pl.pas.parcellocker.controllers.dto.CredentialsDto;
import pl.pas.parcellocker.security.JwtUtils;
import pl.pas.parcellocker.security.UserIdentityStore;

import static jakarta.security.enterprise.identitystore.CredentialValidationResult.Status.VALID;

@Path(value = "/auth")
public class AuthController {

    @Inject
    UserIdentityStore userIdentityStore;

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(CredentialsDto credentials) {
        CredentialValidationResult validationResult = userIdentityStore.validate(
            new UsernamePasswordCredential(credentials.getTelNumber(), credentials.getPassword()));

        if (validationResult.getStatus().equals(VALID)) {
            String jwt = JwtUtils.createToken(
                validationResult.getCallerPrincipal().getName(),
                validationResult.getCallerGroups()
            );

            NewCookie jwtCookie = new NewCookie("jwt", "Bearer " + jwt, "/", null, null, 3600, true, true);

            return Response.accepted().cookie(jwtCookie).header("Authentication", "Bearer " + jwt).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

}
