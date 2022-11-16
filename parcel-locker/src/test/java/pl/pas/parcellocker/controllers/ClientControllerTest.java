package pl.pas.parcellocker.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static io.restassured.RestAssured.with;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import pl.pas.parcellocker.config.RepositoryConfig;
import pl.pas.parcellocker.controllers.dto.ClientDto;
import pl.pas.parcellocker.model.client.Client;

class ClientControllerTest extends RepositoryConfig {

    private static final String basePath = "http://localhost:8080/api/clients/";

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
        Client client = Client.builder()
            .firstName("Jan")
            .lastName("Mostowiak")
            .telNumber("123123")
            .build();
        clientRepository.add(client);

        RestAssured
            .when().get(basePath + client.getTelNumber())
            .then().statusCode(200).body("firstName", equalTo("Jan"));
    }

    @Test
    void Should_GetClientsByPhoneNumberPattern() {
        Client client1 = Client.builder()
            .firstName("Jan")
            .lastName("Mostowiak")
            .telNumber("123123")
            .build();
        Client client2 = Client.builder()
            .firstName("Jan")
            .lastName("Mostowiak")
            .telNumber("123123666")
            .build();
        clientRepository.add(client1);
        clientRepository.add(client2);

        RestAssured.given().queryParam("telNumber", "123")
            .when().get(basePath)
            .then().statusCode(200).body(
                "[0].telNumber", equalTo("123123"),
                "[1].telNumber", equalTo("123123666")
            );
    }

    @Test
    void Should_UnregisterClient() {
        Client client = Client.builder()
            .firstName("Jan")
            .lastName("Mostowiak")
            .telNumber("123123")
            .build();
        clientRepository.add(client);

        RestAssured.given()
            .with().contentType(ContentType.TEXT).body(client.getTelNumber())
            .when().put(basePath)
            .then().statusCode(200)
            .body("firstName", equalTo("Jan"));
    }
}
