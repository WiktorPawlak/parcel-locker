package pl.pas.parcellocker.security;

import jakarta.annotation.security.DeclareRoles;
import jakarta.enterprise.context.RequestScoped;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RequestScoped
@DeclareRoles({"Administrator", "Moderator", "Client"})
public class AuthenticationFilter implements HttpAuthenticationMechanism {
    @Override
    public AuthenticationStatus validateRequest(
        HttpServletRequest httpServletRequest,
        HttpServletResponse httpServletResponse,
        HttpMessageContext httpMessageContext
    ) throws AuthenticationException {

        return null;
    }
}
