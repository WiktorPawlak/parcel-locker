package pl.pas.parcellocker.security;

import java.util.HashMap;
import java.util.Map;

import jakarta.faces.context.FacesContext;
import jakarta.ws.rs.core.Cookie;

public class CookieHandler {
    public static void save(Cookie cookie) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("maxAge", 31536000);
        properties.put("path", "/");
        FacesContext.getCurrentInstance().getExternalContext().addResponseCookie(cookie.getName(), cookie.getValue(), properties);
    }

    public static void remove(Cookie cookie) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("maxAge", 0);
        properties.put("path", "/");
        if (cookie != null) {
            FacesContext.getCurrentInstance().getExternalContext().addResponseCookie(cookie.getName(), cookie.getValue(), properties);
        }
    }
}
