package pl.pas.parcellocker.controllers;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import pl.pas.parcellocker.config.JakartaContainerInitializer;
import pl.pas.parcellocker.controllers.dto.LockerDto;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

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
        LockerDto lockerDto = LockerDto.builder()
            .identityNumber("LDZ01")
            .address("test address")
            .numberOfBoxes(10)
            .build();

        given(requestSpecification)
            .contentType(ContentType.JSON)
            .body(lockerDto)
            .when()
            .post(basePath);

        given(requestSpecification)
            .when()
            .get(basePath + "/{identityNumber}", "LDZ01")
            .then()
            .statusCode(200)
            .body("identityNumber", equalTo("LDZ01"));
    }

    @Test
    void Should_RemoveLocker() {
        LockerDto lockerDto = LockerDto.builder()
            .identityNumber("LDZ01")
            .address("test address")
            .numberOfBoxes(10)
            .build();

        given(requestSpecification)
            .contentType(ContentType.JSON)
            .body(lockerDto)
            .when()
            .post(basePath);

        given(requestSpecification).
            when()
            .delete(basePath + "/{identityNumber}", "LDZ01")
            .then()
            .statusCode(204);
    }

    @Test
    void ShouldNot_CreateLocker_WhenThereIsAlreadyLockerWithThatNumber() {
        Locker locker = new Locker("LDZ01", "test address", 10);
        lockerRepository.add(locker);

        LockerDto lockerDto = LockerDto.builder()
            .identityNumber(locker.getIdentityNumber())
            .address(locker.getAddress())
            .numberOfBoxes(locker.countEmpty())
            .build();

        with()
            .contentType(ContentType.JSON)
            .body(lockerDto)
            .when()
            .post(basePath)
            .then()
            .statusCode(409);
    }

    @Test
    void ShouldNot_GetLocker_WhenThereIsNoLockerWithThatNumber() {
        with()
            .when()
            .get(basePath + "/{identityNumber}", "LDZ01")
            .then()
            .statusCode(404);
    }

    @Test
    void ShouldNot_RemoveLocker_WhenThereIsNoLockerWithThatNumber() {
        with()
            .when()
            .delete(basePath + "/{identityNumber}", "LDZ01")
            .then()
            .statusCode(404);
    }
}
