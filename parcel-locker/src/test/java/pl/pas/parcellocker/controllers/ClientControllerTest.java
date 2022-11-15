package pl.pas.parcellocker.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static io.restassured.RestAssured.with;

import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import pl.pas.parcellocker.controllers.dto.ClientDto;

class ClientControllerTest {

    private static final String basePath = "http://localhost:8080/parcel-locker-1.0-SNAPSHOT/api/clients/";

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

    @Test
    void Should_GetClientByPhoneNumber() {
        ClientDto client = ClientDto.builder()
            .firstName("Jan")
            .lastName("Mostowiak")
            .telNumber("123123")
            .build();
        with()
            .contentType(ContentType.JSON)
            .body(client)
            .when()
            .post(basePath);


        RestAssured
            .when().get(basePath + client.telNumber)
            .then().statusCode(200).body("firstName", equalTo("Jan"));
    }

    @Test
    void Should_GetClientsByPhoneNumberPattern() {
        ClientDto client1 = ClientDto.builder()
            .firstName("Jan")
            .lastName("Mostowiak")
            .telNumber("123123")
            .build();
        ClientDto client2 = ClientDto.builder()
            .firstName("Jan")
            .lastName("Mostowiak")
            .telNumber("123123999")
            .build();

        with()
            .contentType(ContentType.JSON)
            .body(client1)
            .when()
            .post(basePath);
        with()
            .contentType(ContentType.JSON)
            .body(client2)
            .when()
            .post(basePath);


        RestAssured.given().queryParam("telNumber", "123")
            .when().get(basePath)
            .then().statusCode(200).body(
                "[0].telNumber", equalTo("123123"),
                "[1].telNumber", equalTo("123123999")
            );
    }

    @Test
    void Should_UnregisterClient() {
        ClientDto client = ClientDto.builder()
            .firstName("Jan")
            .lastName("Mostowiak")
            .telNumber("123123")
            .build();
        with()
            .contentType(ContentType.JSON)
            .body(client)
            .when()
            .post(basePath);

        RestAssured.given()
            .with().contentType(ContentType.TEXT).body(client.telNumber)
            .when().put(basePath)
            .then().statusCode(200)
            .body("firstName", equalTo("Jan"));
    }
}
