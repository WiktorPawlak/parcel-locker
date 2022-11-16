package pl.pas.parcellocker.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static io.restassured.RestAssured.with;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import pl.pas.parcellocker.config.RepositoryConfig;
import pl.pas.parcellocker.controllers.dto.ClientDto;
import pl.pas.parcellocker.model.user.Client;
import pl.pas.parcellocker.model.user.User;

class UserControllerTest extends RepositoryConfig {

    private static final String basePath = "http://localhost:8080/parcel-locker-1.0-SNAPSHOT/api/clients/";

    @AfterEach
    void finisher() {
        clientRepository.findAll().forEach(clientRepository::remove);
    }

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
        User user = Client.builder()
            .firstName("Jan")
            .lastName("Mostowiak")
            .telNumber("123123")
            .build();
        clientRepository.add(user);

        RestAssured
            .when().get(basePath + user.getTelNumber())
            .then().statusCode(200).body("firstName", equalTo("Jan"));
    }

    @Test
    void Should_GetClientsByPhoneNumberPattern() {
        User user1 = Client.builder()
            .firstName("Jan")
            .lastName("Mostowiak")
            .telNumber("123123")
            .build();
        User user2 = Client.builder()
            .firstName("Jan")
            .lastName("Mostowiak")
            .telNumber("123123666")
            .build();
        clientRepository.add(user1);
        clientRepository.add(user2);

        RestAssured.given().queryParam("telNumber", "123")
            .when().get(basePath)
            .then().statusCode(200).body(
                "[0].telNumber", equalTo("123123"),
                "[1].telNumber", equalTo("123123666")
            );
    }

    @Test
    void Should_UnregisterClient() {
        User user = Client.builder()
            .firstName("Jan")
            .lastName("Mostowiak")
            .telNumber("123123")
            .build();
        clientRepository.add(user);

        RestAssured.given()
            .with().contentType(ContentType.TEXT).body(user.getTelNumber())
            .when().put(basePath)
            .then().statusCode(200)
            .body("firstName", equalTo("Jan"));
    }
}
