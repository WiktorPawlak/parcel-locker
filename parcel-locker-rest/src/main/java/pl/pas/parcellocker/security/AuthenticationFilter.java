package pl.pas.parcellocker.security;

import jakarta.annotation.security.DeclareRoles;
import jakarta.enterprise.context.RequestScoped;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequestScoped
@DeclareRoles({"ADMINISTRATOR", "MODERATOR", "CLIENT", "UNAUTHORIZED"})
public class AuthenticationFilter implements HttpAuthenticationMechanism {

    private static final Pattern TOKEN_PATTERN = Pattern.compile("^Bearer *([^ ]+) *$", Pattern.CASE_INSENSITIVE);

    @Override
    public AuthenticationStatus validateRequest(
        HttpServletRequest httpServletRequest,
        HttpServletResponse httpServletResponse,
        HttpMessageContext httpMessageContext
    ) throws AuthenticationException {

        final String authorization = httpServletRequest.getHeader("Authorization");

        Matcher matcher = TOKEN_PATTERN.matcher(Optional.ofNullable(authorization).orElse(""));

        if (!matcher.matches()) {
            return httpMessageContext.notifyContainerAboutLogin("UNAUTHORIZED", new HashSet<>(List.of("UNAUTHORIZED")));
        }

        final String token = matcher.group(1);

        if (token == null) {
            return httpMessageContext.responseUnauthorized();
        }

        Optional<JwtData> optionalJwtData = JwtUtils.parse(token);

        if (optionalJwtData.isPresent()) {
            JwtData jwtData = optionalJwtData.get();
            return httpMessageContext.notifyContainerAboutLogin(jwtData.getTelNumber(), jwtData.getRoles());
        } else {
            return httpMessageContext.responseUnauthorized();
        }
    }
}
