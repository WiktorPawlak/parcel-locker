package pl.pas.parcellocker.beans;

import java.io.Serializable;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;

@Named
@SessionScoped
@NoArgsConstructor
public class IdentityHandler implements Serializable {

    @Inject
    private HttpServletRequest request;

    public String userIdentification() {
        String msg;
        if (request.getUserPrincipal() == null) {
            msg = "Brak zalogowanego uzytkownika";
        } else {
            msg = "Zalogowany uzytkownik: " + request.getUserPrincipal().getName();
        }
        return msg;
    }

    public String getUserTelNumber() {
        return request.getUserPrincipal().getName();
    }

    public boolean isUserInRole(String role) {
        return request.isUserInRole(role);
    }

    public boolean isAdministrator() {
        return request.isUserInRole("ADMINISTRATOR");
    }

    public boolean isUserLogged() {
        return request.getUserPrincipal() != null;
    }

    public String getUserRole() {
        if (request.isUserInRole("ADMINISTRATOR")) {
            return "ADMINISTRATOR";
        } else if (request.isUserInRole("MODERATOR")) {
            return "MODERATOR";
        } else if (request.isUserInRole("CLIENT")) {
            return "CLIENT";
        } else {
            return "UNAUTHORIZED";
        }
    }
}
