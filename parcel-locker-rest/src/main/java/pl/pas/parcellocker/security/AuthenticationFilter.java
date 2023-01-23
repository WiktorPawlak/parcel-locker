package pl.pas.parcellocker.security;

import jakarta.annotation.security.DeclareRoles;
import jakarta.enterprise.context.RequestScoped;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static pl.pas.parcellocker.security.JwtUtils.JWT_COOKIE_NAME;
import static pl.pas.parcellocker.security.JwtUtils.parse;

@RequestScoped
@DeclareRoles({"ADMINISTRATOR", "MODERATOR", "CLIENT", "UNAUTHORIZED"})
public class AuthenticationFilter implements HttpAuthenticationMechanism {

    private static final Pattern TOKEN_PATTERN = Pattern.compile("^Bearer *([^ ]+) *$", Pattern.CASE_INSENSITIVE);
    public static final int JWT_TOKEN_PART = 1;

    @Override
    public AuthenticationStatus validateRequest(
        HttpServletRequest httpServletRequest,
        HttpServletResponse httpServletResponse,
        HttpMessageContext httpMessageContext
    ) throws AuthenticationException {

        Optional<Cookie> cookie = findJwtCookie(httpServletRequest.getCookies());

        final String authorization = cookie.isPresent() ?
            cookie.get().getValue() : httpServletRequest.getHeader("Authorization");

        Matcher matcher = TOKEN_PATTERN.matcher(Optional.ofNullable(authorization).orElse(""));

        if (!matcher.matches()) {
            return httpMessageContext.notifyContainerAboutLogin("UNAUTHORIZED", new HashSet<>(List.of("UNAUTHORIZED")));
        }

        final String token = matcher.group(JWT_TOKEN_PART);

        Optional<JwtData> optionalJwtData = parse(token);

        if (optionalJwtData.isPresent()) {
            JwtData jwtData = optionalJwtData.get();
            return httpMessageContext.notifyContainerAboutLogin(jwtData.getTelNumber(), jwtData.getRoles());
        } else {
            return httpMessageContext.responseUnauthorized();
        }
    }

    public static Optional<Cookie> findJwtCookie(Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(JWT_COOKIE_NAME)) {
                    return Optional.of(cookie);
                }
            }
        }
        return Optional.empty();
    }
}
