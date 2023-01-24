package pl.pas.parcellocker.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Cookie;
import jakarta.ws.rs.core.Response;
import lombok.Getter;
import lombok.Setter;
import pl.pas.parcellocker.beans.dto.CredentialsDto;
import pl.pas.parcellocker.delivery.http.HttpClient;
import pl.pas.parcellocker.security.CookieHandler;


@ViewScoped
@Named
@Getter
@Setter
public class AuthBean implements Serializable {

    @Inject
    private IdentityHandler roleHandler;
    CredentialsDto credentials = new CredentialsDto();

    HttpClient httpClient = new HttpClient();

    public String logIn() throws IOException {
        Response response = httpClient.post("/auth/login", this.credentials);
        Cookie cookie = response.getCookies().get("jwt");
        CookieHandler.save(cookie);
        reload();
        return "index";
    }

    public String logOut() {
        httpClient.post("/auth/logout", Entity.json(""));
        Map<String, Object> cookieMap = jakarta.faces.context.FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap();
        jakarta.servlet.http.Cookie cookie = (jakarta.servlet.http.Cookie) cookieMap.get("jwt");
        CookieHandler.remove(CookiePackageConverter.jakartaToJaxRs(cookie));
        roleHandler.setRoles(Set.of());
        roleHandler.setUserLogin(null);
        return "index";
    }

    private void reload() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect("index.xhtml");
    }
}
