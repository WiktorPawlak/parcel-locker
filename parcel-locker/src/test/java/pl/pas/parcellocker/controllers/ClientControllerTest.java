package pl.pas.parcellocker.controllers;


import static org.hamcrest.CoreMatchers.equalTo;
import static io.restassured.RestAssured.when;

import org.junit.jupiter.api.Test;

import pl.pas.parcellocker.controllers.dto.ClientDto;

class ClientControllerTest {

    private static final String basePath = "http://localhost:8080/parcel-locker-1.0-SNAPSHOT";

    @Test
    void Should_CreateClient() {
        ClientDto client = ClientDto.builder()
            .firstName("Jan")
            .lastName("Mostowiak")
            .telNumber("666666666")
            .build();

        when()
            .post("/clients", client).
            then().statusCode(201)
            .body("clientDto.firstName", equalTo("Jan"));
    }
}
