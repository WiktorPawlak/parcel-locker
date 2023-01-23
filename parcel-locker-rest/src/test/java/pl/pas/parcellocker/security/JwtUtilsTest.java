package pl.pas.parcellocker.security;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import org.junit.jupiter.api.Test;

import pl.pas.parcellocker.model.user.Administrator;
import pl.pas.parcellocker.model.user.User;

class JwtUtilsTest {

    @Test
    void shouldCreateValidToken() {
        User user = new Administrator("Janusz", "Tracz", "666666666", "password");
        String jwtToken = JwtUtils.createToken(user.getTelNumber(), Set.of(user.getRole().name()));

        JwtData jwtData = JwtUtils.parse(jwtToken).get();

        assertEquals("666666666", jwtData.getTelNumber());
    }

}
