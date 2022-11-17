package pl.pas.parcellocker.controllers;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.pas.parcellocker.config.JakartaContainerInitializer;
import pl.pas.parcellocker.controllers.dto.ClientDto;
import pl.pas.parcellocker.model.user.Administrator;
import pl.pas.parcellocker.model.user.Client;
import pl.pas.parcellocker.model.user.User;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

class UserControllerTest extends JakartaContainerInitializer {

    private static final String basePath = "/api/clients";

    private String adminId;

    @BeforeAll
    void init() {
        adminId = given(requestSpecification)
            .when().get(basePath + "/admin")
            .then().extract().path("id");
    }

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
            .queryParam("operatorId", adminId)
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
            .queryParam("operatorId", adminId)
            .when()
            .post(basePath)
            .then()
            .statusCode(201)
            .body("firstName", equalTo("Dariusz"));

        RestAssured.given(requestSpecification)
            .when().get(basePath + "/" + client.getTelNumber())
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
            .queryParam("operatorId", adminId)
            .when()
            .post(basePath);

        given(requestSpecification)
            .contentType(ContentType.JSON)
            .body(client2)
            .queryParam("operatorId", adminId)
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
            .queryParam("operatorId", adminId)
            .when()
            .post(basePath);

        RestAssured.given(requestSpecification)
            .with().contentType(ContentType.TEXT).body(client.getTelNumber()).queryParam("operatorId", adminId)
            .when().put(basePath)
            .then().statusCode(200)
            .body("firstName", equalTo("Jan"));
    }
}
