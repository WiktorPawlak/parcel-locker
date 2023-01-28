package pl.pas.parcellocker.security;


import java.text.ParseException;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import pl.pas.parcellocker.exceptions.JwsVerificationException;


@ApplicationScoped
public class EntityTagSignatureProvider {

    private static final String SECRET = "parcelLocker123456789HeheJestSuperxDDDDDDDD";

    @Context
    private HttpHeaders httpHeaders;

    private JWSSigner signer;
    private JWSVerifier verifier;


    @PostConstruct
    public void init() {
        try {
            verifier = new MACVerifier(SECRET);
            signer = new MACSigner(SECRET);
        } catch (JOSEException e) {
            throw new JwsVerificationException(e.getMessage());
        }
    }

    public String sign(String dataToSign) {
        try {
            JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256), new Payload(dataToSign));
            jwsObject.sign(signer);
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new JwsVerificationException(e.getMessage());
        }
    }

    public boolean validateSignature(String signature) {
        try {
            final JWSObject jwsObject = JWSObject.parse(signature);
            return jwsObject.verify(verifier);
        } catch (ParseException | JOSEException e) {
            throw new JwsVerificationException(e.getMessage());
        }
    }

    public boolean verifyIntegrity(String dataToSign) {
        String providedSignature = httpHeaders.getHeaderString("If-Match");
        String expectedSignature = sign(dataToSign);
        return expectedSignature.equals(providedSignature);
    }
}
