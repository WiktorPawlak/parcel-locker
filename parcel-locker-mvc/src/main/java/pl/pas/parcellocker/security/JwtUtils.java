package pl.pas.parcellocker.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class JwtUtils {

    public static final String JWT_COOKIE_NAME = "jwt";
    private static final String ISSUER = "parcelLocker";
    private static final Algorithm algorithm = Algorithm.HMAC256("parcelLocker123456789HeheJestSuperxDDDDDDDD");
    private static final long EXPIRATION_TIME = 3600;

    public static String createToken(String telNumber, Set<String> roles) {
        Instant now = Instant.now();
        return JWT.create()
            .withIssuedAt(now)
            .withIssuer(ISSUER)
            .withJWTId(telNumber)
            .withClaim("roles", new ArrayList<>(roles))
            .withExpiresAt(now.plusSeconds(EXPIRATION_TIME))
            .sign(algorithm);
    }

    public static Optional<JwtData> parse(String jwtText) {
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
