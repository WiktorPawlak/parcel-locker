package pl.pas.parcellocker.controllers;


import static org.hamcrest.CoreMatchers.equalTo;
import static io.restassured.RestAssured.with;

import org.junit.jupiter.api.Test;

import pl.pas.parcellocker.controllers.dto.ClientDto;

class ClientControllerTest {

    private static final String basePath = "http://localhost:8080/parcel-locker-1.0-SNAPSHOT/api/clients";

    @Test
    void Should_CreateClient() {
        ClientDto client = ClientDto.builder()
            .firstName("Jan")
            .lastName("Mostowiak")
            .telNumber("666666666")
            .build();

        with().body(client)
            .when().post(basePath)
            .then().statusCode(201)
            .body("clientDto.firstName", equalTo("Jan"));
    }
}
