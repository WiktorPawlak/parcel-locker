package pl.pas.parcellocker.beans;

import jakarta.ws.rs.core.Cookie;

public class CookiePackageConverter {

    public static Cookie jakartaToJaxRs(jakarta.servlet.http.Cookie cookie) {
        return new Cookie(cookie.getName(), cookie.getValue());
    }
}
