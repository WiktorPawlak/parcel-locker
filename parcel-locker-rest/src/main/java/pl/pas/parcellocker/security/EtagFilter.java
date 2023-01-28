package pl.pas.parcellocker.security;

import jakarta.inject.Inject;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;


@Provider
@EtagValidatorFilterBinding
public class EtagFilter implements ContainerRequestFilter {

    @Inject
    private EntityTagSignatureProvider entityTagSignatureProvider;

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String signature = requestContext.getHeaderString("If-Match");
        if (signature == null || signature.isEmpty() || !entityTagSignatureProvider.validateSignature(signature)) {
            requestContext.abortWith(Response.status(Response.Status.PRECONDITION_FAILED).build());
        }
    }
}
