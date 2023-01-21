package pl.pas.parcellocker.security;

import org.junit.jupiter.api.Test;
import pl.pas.parcellocker.model.user.Administrator;
import pl.pas.parcellocker.model.user.User;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JwtUtilsTest {

    @Test
    void shouldCreateValidToken() {
        User user = new Administrator("Janusz", "Tracz", "666666666");
        String jwtToken = JwtUtils.createToken(user.getTelNumber(), Set.of(user.getRole().name()));

        JwtData jwtData = JwtUtils.parse(jwtToken).get();

        assertEquals("666666666", jwtData.getTelNumber());
    }

}
