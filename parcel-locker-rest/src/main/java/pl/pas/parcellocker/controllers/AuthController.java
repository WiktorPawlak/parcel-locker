package pl.pas.parcellocker.controllers;

import static jakarta.security.enterprise.identitystore.CredentialValidationResult.Status.VALID;
import static pl.pas.parcellocker.security.JwtUtils.JWT_COOKIE_NAME;
import static pl.pas.parcellocker.security.JwtUtils.createToken;

import jakarta.annotation.security.RolesAllowed;
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
import pl.pas.parcellocker.security.UserIdentityStore;

@Path(value = "/auth")
public class AuthController {

    public static final int COOKIE_AGE = 3600;

    @Inject
    UserIdentityStore userIdentityStore;

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"UNAUTHORIZED"})
    public Response login(CredentialsDto credentials) {
        CredentialValidationResult validationResult = userIdentityStore.validate(
            new UsernamePasswordCredential(credentials.getTelNumber(), credentials.getPassword()));

        if (validationResult.getStatus().equals(VALID)) {
            String jwt = createToken(
                validationResult.getCallerPrincipal().getName(),
                validationResult.getCallerGroups()
            );

            NewCookie jwtCookie = new NewCookie(JWT_COOKIE_NAME, "Bearer " + jwt, "/", null,
                null, COOKIE_AGE, true, true);

            return Response.accepted().cookie(jwtCookie).header("Authorization", "Bearer " + jwt).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @POST
    @Path("/logout")
    @RolesAllowed({"CLIENT", "MODERATOR", "ADMINISTRATOR"})
    public Response logout() {
        NewCookie jwtCookie = new NewCookie(JWT_COOKIE_NAME, "", "/", null, null, 0, true, true);
        return Response.ok("Logged out successfully").cookie(jwtCookie).build();
    }
}
