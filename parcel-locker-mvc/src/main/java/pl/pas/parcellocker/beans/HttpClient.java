package pl.pas.parcellocker.beans;

import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.Cookie;
import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Collections;
import java.util.Map;

import static pl.pas.parcellocker.beans.CookiePackageConverter.jakartaToJaxRs;

public class HttpClient {

    private final String SERVICE_CONTEXT = "http://localhost:8080/parcel-locker-rest-1.0-SNAPSHOT/api";
    private final String COOKIE_NAME = "jwt";
    Client client = ClientBuilder.newClient();

    public Response get(String path) {
        return get(path, Collections.emptyMap());
    }

    public Response get(String path, Map<String, String> params) {
        Invocation.Builder invocationBuilder = getInvocationBuilder(path, params);

        Cookie cookie = getCookie();
        if (cookie != null) {
            invocationBuilder.cookie(jakartaToJaxRs(cookie));
        }

        return invocationBuilder.get();
    }

    public Response post(String path, Object body) {
        return post(path, body,Collections.emptyMap());
    }

    public Response post(String path, Object body, Map<String, String> params) {
        Invocation.Builder invocationBuilder = getInvocationBuilder(path, params);

        Cookie cookie = getCookie();
        if (cookie != null) {
            invocationBuilder.cookie(jakartaToJaxRs(cookie));
        }

        return invocationBuilder.post(Entity.json(body));
    }

    public Response put(String path, Object body) {
        return put(path, body,Collections.emptyMap());
    }

    public Response put(String path, Object body, Map<String, String> params) {
        Invocation.Builder invocationBuilder = getInvocationBuilder(path, params);

        Cookie cookie = getCookie();
        if (cookie != null) {
            invocationBuilder.cookie(jakartaToJaxRs(cookie));
        }

        return invocationBuilder.put(Entity.json(body));
    }

    public Response delete(String path) {
        Invocation.Builder invocationBuilder = getInvocationBuilder(path, Collections.emptyMap());

        Cookie cookie = getCookie();
        if (cookie != null) {
            invocationBuilder.cookie(jakartaToJaxRs(cookie));
        }

        return invocationBuilder.delete();
    }

    private Cookie getCookie() {
        Map<String, Object> cookieMap = FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap();
        return (Cookie) cookieMap.get(COOKIE_NAME);
    }

    private Invocation.Builder getInvocationBuilder(String path, Map<String, String> params) {
        WebTarget webTarget = client.target(SERVICE_CONTEXT + path);
        for (Map.Entry<String, String> param : params.entrySet()) {
            webTarget.queryParam(param.getKey(), param.getValue());
        }

        return webTarget.request(MediaType.APPLICATION_JSON);
    }
}
