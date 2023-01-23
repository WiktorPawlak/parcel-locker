package pl.pas.parcellocker.beans;

import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.Cookie;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Map;

import static pl.pas.parcellocker.beans.CookiePackageConverter.jakartaToJaxRs;

public class HttpClient {

    private final String SERVICE_CONTEXT = "http://localhost:8080/parcel-locker-rest-1.0-SNAPSHOT/api";
    private final String COOKIE_NAME = "jwt";
    Client client = ClientBuilder.newClient();

    public Cookie getCookie() {
        Map<String, Object> cookieMap = FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap();
        return (Cookie) cookieMap.get(COOKIE_NAME);
    }

    public Response get(String path) {
        WebTarget webTarget = client.target(SERVICE_CONTEXT + path);
        Invocation.Builder invocationBuilder = webTarget
                .request(MediaType.APPLICATION_JSON);

        Cookie cookie = getCookie();
        if (cookie != null) {
            invocationBuilder.cookie(jakartaToJaxRs(cookie));
        }

        return invocationBuilder.get();
    }
}
