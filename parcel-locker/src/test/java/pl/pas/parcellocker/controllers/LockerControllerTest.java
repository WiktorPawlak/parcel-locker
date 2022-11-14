package pl.pas.parcellocker.controllers;


import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import pl.pas.parcellocker.config.JakartaContainerInitializer;
import pl.pas.parcellocker.controllers.dto.LockerDto;
import pl.pas.parcellocker.model.locker.Locker;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LockerControllerTest extends JakartaContainerInitializer {

    private static final String basePath = "/api/lockers";

    @Test
    void Should_CreateLocker() {
        LockerDto lockerDto = LockerDto.builder()
            .identityNumber("LDZ01")
            .address("test address")
            .numberOfBoxes(10)
            .build();

        given(requestSpecification)
            .contentType(ContentType.JSON)
            .body(lockerDto)
        .when()
            .post(basePath)
        .then()
            .statusCode(201)
            .body("identityNumber", equalTo("LDZ01"));
    }

    @Test
    void Should_GetLocker() {
        Locker locker = new Locker("LDZ01", "test address", 10);

        given(requestSpecification).
        when()
            .get(basePath + "/{identityNumber}", "LDZ01")
        .then()
            .statusCode(200)
            .body("identityNumber", equalTo("LDZ01"));
    }
}
