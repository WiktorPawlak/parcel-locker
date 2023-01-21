package pl.pas.parcellocker.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import pl.pas.parcellocker.model.user.User;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class JwtUtils {

    private static final String ISSUER = "parcelLocker";
    private static final Algorithm algorithm = Algorithm.HMAC256("secretHehe");
    private static final long EXPIRATION_TIME = 3600;

    static String createToken(User user) {
        Instant now = Instant.now();
        return JWT.create()
            .withIssuedAt(now)
            .withIssuer(ISSUER)
            .withJWTId(user.getTelNumber())
            .withClaim("roles", List.of(user.getRole().name()))
            .withExpiresAt(now.plusSeconds(EXPIRATION_TIME))
            .sign(algorithm);
    }

    static Optional<JwtData> parse(String jwtText) {
        try {
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(ISSUER).build();
            final DecodedJWT jwt = verifier.verify(jwtText);
            final Claim roles = jwt.getClaim("roles");

      return Optional.of(new JwtData(jwt.getId(), new HashSet<>(roles.asList(String.class))));

        } catch (JWTVerificationException exp) {
            return Optional.empty();
        }
    }

}
