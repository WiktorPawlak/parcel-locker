package pl.pas.parcellocker.controllers;


import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import pl.pas.parcellocker.config.RepositoryConfig;
import pl.pas.parcellocker.controllers.dto.LockerDto;
import pl.pas.parcellocker.model.locker.Locker;

import static io.restassured.RestAssured.with;
import static org.hamcrest.CoreMatchers.equalTo;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LockerControllerTest extends RepositoryConfig {

    private static final String basePath = "http://localhost:8080/parcel-locker-1.0-SNAPSHOT/api/lockers";

    @AfterEach
    void finisher() {
        lockerRepository.findAll().forEach(lockerRepository::remove);
    }

    @Test
    void Should_CreateLocker() {
        LockerDto lockerDto = LockerDto.builder()
            .identityNumber("LDZ01")
            .address("test address")
            .numberOfBoxes(10)
            .build();

        with()
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
        lockerRepository.add(locker);

        with()
            .when()
            .get(basePath + "/{identityNumber}", "LDZ01")
            .then()
            .statusCode(200)
            .body("identityNumber", equalTo("LDZ01"));
    }

    @Test
    void Should_RemoveLocker() {
        Locker locker = new Locker("LDZ01", "test address", 10);
        lockerRepository.add(locker);

        with()
            .when()
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
