package pl.pas.parcellocker;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import jakarta.faces.context.FacesContext;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

    public void destroy() {
        Map<String, Object> cookieMap = FacesContext.getCurrentInstance().getExternalContext().getRequestCookieMap();
        Cookie cookie = (jakarta.servlet.http.Cookie) cookieMap.get("jwt");
        Map<String, Object> properties = new HashMap<>();
        properties.put("maxAge", 0);
        properties.put("path", "/");
        if (cookie != null) {
            cookie.setMaxAge(0);
            FacesContext.getCurrentInstance().getExternalContext().addResponseCookie(cookie.getName(), cookie.getValue(), properties);
        }
    }
}