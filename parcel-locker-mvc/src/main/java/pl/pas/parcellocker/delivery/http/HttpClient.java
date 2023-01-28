package pl.pas.parcellocker.delivery.http;

import static pl.pas.parcellocker.beans.CookiePackageConverter.jakartaToJaxRs;

import java.util.Collections;
import java.util.Map;

import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.Cookie;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class HttpClient implements AutoCloseable {

    public static final String IF_MATCH_HEADER_NAME = "If-Match";
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
        return post(path, body, Collections.emptyMap());
    }

    public Response post(String path, Object body, Map<String, String> params) {
        Invocation.Builder invocationBuilder = getInvocationBuilder(path, params);

        Cookie cookie = getCookie();
        if (cookie != null) {
            invocationBuilder.cookie(jakartaToJaxRs(cookie));
        }

        return invocationBuilder.post(Entity.json(body));
    }

    public Response putTextBody(String path, Object body) {
        return put(path, Entity.text(body), Collections.emptyMap());
    }

    public Response putJsonBody(String path, Object body) {
        return put(path, Entity.json(body), Collections.emptyMap());
    }

    public Response put(String path, Entity entity, Map<String, String> params) {
        Invocation.Builder invocationBuilder = getInvocationBuilder(path, params);

        Cookie cookie = getCookie();
        if (cookie != null) {
            invocationBuilder.cookie(jakartaToJaxRs(cookie));
        }

        return invocationBuilder.put(entity);
    }

    public Response signedPut(String path, Entity entity, String eTag) {
        Invocation.Builder invocationBuilder = getInvocationBuilder(path, Collections.emptyMap());

        Cookie cookie = getCookie();
        if (cookie != null) {
            invocationBuilder.cookie(jakartaToJaxRs(cookie));
        }

        invocationBuilder.header(IF_MATCH_HEADER_NAME, eTag);

        return invocationBuilder.put(entity);
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
            webTarget = webTarget.queryParam(param.getKey(), param.getValue());
        }

        return webTarget.request(MediaType.APPLICATION_JSON_TYPE);
    }

    @Override
    public void close() {
        this.client.close();
    }
}
