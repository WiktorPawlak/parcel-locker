package pl.pas.parcellocker.beans;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.Cookie;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.Getter;
import lombok.Setter;
import pl.pas.parcellocker.beans.dto.CredentialsDto;

import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


@ViewScoped
@Named
@Getter
@Setter
public class AuthBean implements Serializable {

    @Inject
    AuthorizationStore authorizationStore;

    CredentialsDto credentials = new CredentialsDto();

    Client client = ClientBuilder.newClient();

    public String logIn() {
        WebTarget webTarget = this.client.target("http://localhost:8080/parcel-locker-rest-1.0-SNAPSHOT/api/auth/login");
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.post(Entity.json(this.credentials));
        Cookie cookie = response.getCookies().get("jwt");
        Map<String, Object> properties = new HashMap<>();
        properties.put("maxAge", 31536000);
        properties.put("path", "/");
        FacesContext.getCurrentInstance().getExternalContext().addResponseCookie(cookie.getName(), cookie.getValue(), properties);
        return "index";
    }

    public String logOut() {
        Map<String, Object> cookieMap = jakarta.faces.context.FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap();
        jakarta.servlet.http.Cookie cookie = (jakarta.servlet.http.Cookie) cookieMap.get("jwt");
        Map<String, Object> properties = new HashMap<>();
        properties.put("maxAge", 0);
        properties.put("path", "/");
        if (cookie != null) {
            cookie.setMaxAge(0);
            FacesContext.getCurrentInstance().getExternalContext().addResponseCookie(cookie.getName(), cookie.getValue(), properties);
        }
        return "index";
    }
}