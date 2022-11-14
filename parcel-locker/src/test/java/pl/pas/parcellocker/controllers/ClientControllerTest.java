package pl.pas.parcellocker.controllers;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import pl.pas.parcellocker.controllers.dto.ClientDto;

import static io.restassured.RestAssured.with;
import static org.hamcrest.CoreMatchers.equalTo;

class ClientControllerTest {

    private static final String basePath = "/api/clients";

    @Test
    void Should_CreateClient() {
        ClientDto client = ClientDto.builder()
            .firstName("Jan")
            .lastName("Mostowiak")
            .telNumber("123123")
            .build();

        with()
            .contentType(ContentType.JSON)
            .body(client)
        .when()
            .post(basePath)
        .then()
            .statusCode(201)
            .body("firstName", equalTo("Jan"));
    }
}
