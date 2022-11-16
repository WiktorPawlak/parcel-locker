package pl.pas.parcellocker.controllers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static io.restassured.RestAssured.with;

import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import pl.pas.parcellocker.config.JakartaContainerInitializer;
import pl.pas.parcellocker.controllers.dto.ClientDto;
import pl.pas.parcellocker.model.client.Client;

class ClientControllerTest extends JakartaContainerInitializer {

    private static final String basePath = "/api/clients/";

    @Test
    void Should_CreateClient() {
        ClientDto client = ClientDto.builder()
            .firstName("Jan")
            .lastName("Mostowiak")
            .telNumber("123123")
            .build();

        RestAssured.given(requestSpecification)
            .contentType(ContentType.JSON)
            .body(client)
            .when()
            .post(basePath)
            .then()
            .statusCode(201)
            .body("firstName", equalTo("Jan"));
    }

    @Test
    void Should_GetClientByPhoneNumber() {
        Client client = Client.builder()
            .firstName("Dariusz")
            .lastName("Szpak")
            .telNumber("321321")
            .build();

        given(requestSpecification)
            .contentType(ContentType.JSON)
            .body(client)
            .when()
            .post(basePath)
            .then()
            .statusCode(201)
            .body("firstName", equalTo("Dariusz"));

        RestAssured.given(requestSpecification)
            .when().get(basePath + client.getTelNumber())
            .then().statusCode(200).body("firstName", equalTo("Dariusz"));
    }

    @Test
    void Should_GetClientsByPhoneNumberPattern() {
        Client client1 = Client.builder()
            .firstName("Jan")
            .lastName("Mostowiak")
            .telNumber("111222333")
            .build();
        Client client2 = Client.builder()
            .firstName("Dariusz")
            .lastName("Szpak")
            .telNumber("111333222")
            .build();

        given(requestSpecification)
            .contentType(ContentType.JSON)
            .body(client1)
            .when()
            .post(basePath);

        given(requestSpecification)
            .contentType(ContentType.JSON)
            .body(client2)
            .when()
            .post(basePath);

        RestAssured.given(requestSpecification).queryParam("telNumber", "111")
            .when().get(basePath)
            .then().statusCode(200).body(
                "[0].telNumber", equalTo("111222333"),
                "[1].telNumber", equalTo("111333222")
            );
    }

    @Test
    void Should_UnregisterClient() {
        Client client = Client.builder()
            .firstName("Jan")
            .lastName("Mostowiak")
            .telNumber("333333")
            .build();

        given(requestSpecification)
            .contentType(ContentType.JSON)
            .body(client)
            .when()
            .post(basePath);

        RestAssured.given(requestSpecification)
            .with().contentType(ContentType.TEXT).body(client.getTelNumber())
            .when().put(basePath)
            .then().statusCode(200)
            .body("firstName", equalTo("Jan"));
    }
}
