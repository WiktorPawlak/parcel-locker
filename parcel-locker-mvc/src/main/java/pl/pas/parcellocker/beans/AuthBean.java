package pl.pas.parcellocker.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

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


@ViewScoped
@Named
@Getter
@Setter
public class AuthBean implements Serializable {

    @Inject
    AuthorizationStore authorizationStore;

    CredentialsDto credentials = new CredentialsDto();

    HttpClient httpClient = new HttpClient();

    public String logIn() {
        Response response = httpClient.post("/auth/login", this.credentials);
        Cookie cookie = response.getCookies().get("jwt");
        Map<String, Object> properties = new HashMap<>();
        properties.put("maxAge", 31536000);
        properties.put("path", "/");
        FacesContext.getCurrentInstance().getExternalContext().addResponseCookie(cookie.getName(), cookie.getValue(), properties);
        return "index";
    }

    public String logOut() {
        httpClient.post("/auth/logout", Entity.json(""));
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
//    public String logOut() {
//        httpClient.post("/auth/logout", Entity.json(""));
//        Map<String, Object> properties = new HashMap<>();
//        properties.put("maxAge", 0);
//        properties.put("path", "/");
//        FacesContext.getCurrentInstance().getExternalContext().addResponseCookie("jwt", "", properties);
//        return "index";
//    }

}