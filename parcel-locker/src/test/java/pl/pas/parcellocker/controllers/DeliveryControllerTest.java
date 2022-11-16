package pl.pas.parcellocker.controllers;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import pl.pas.parcellocker.config.JakartaContainerInitializer;
import pl.pas.parcellocker.controllers.dto.ClientDto;
import pl.pas.parcellocker.controllers.dto.DeliveryListDto;
import pl.pas.parcellocker.controllers.dto.DeliveryParcelDto;
import pl.pas.parcellocker.controllers.dto.ListDto;
import pl.pas.parcellocker.controllers.dto.LockerDto;
import pl.pas.parcellocker.controllers.dto.ParcelDto;
import pl.pas.parcellocker.model.client.Client;
import pl.pas.parcellocker.model.locker.Locker;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.pas.parcellocker.model.delivery.DeliveryStatus.READY_TO_SHIP;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DeliveryControllerTest extends JakartaContainerInitializer {

  Client receiver = new Client("Tony", "Stark", "1234567890");
  Client shipper = new Client("Steven", "Rogers", "9987654321");
  Locker locker = new Locker("PLO1", "Piotrkow", 5);

  String deliveryId;

  String baseUri = "/api/deliveries";

  @Test
  void Should_CreateListDelivery() {
    DeliveryListDto deliveryListDto =
        DeliveryListDto.builder()
            .lockerId(locker.getIdentityNumber())
            .pack(ListDto.builder().basePrice(BigDecimal.TEN).isPriority(false).build())
            .receiverTel(receiver.getTelNumber())
            .shipperTel(shipper.getTelNumber())
            .build();

    String addedEdDeliveryId =
        given(requestSpecification)
            .contentType(ContentType.JSON)
            .body(deliveryListDto)
            .when()
            .post(baseUri + "/list")
            .then()
            .statusCode(201)
            .body("id", notNullValue())
            .body("status", equalTo(READY_TO_SHIP.toString()))
            .body("pack.id", notNullValue())
            .extract()
            .path("id");

    String expectedDeliveryId =
        given(requestSpecification)
            .contentType(ContentType.JSON)
            .when()
            .get(baseUri + "/" + addedEdDeliveryId)
            .then()
            .statusCode(200)
            .extract()
            .path("id");

    assertEquals(expectedDeliveryId, addedEdDeliveryId);
  }

  @Test
  void Should_ReturnNotFoundWhenCreateIncorrectListDelivery() {
    DeliveryListDto deliveryListDto =
        DeliveryListDto.builder()
            .lockerId(locker.getIdentityNumber())
            .pack(ListDto.builder().basePrice(BigDecimal.TEN).isPriority(false).build())
            .receiverTel(receiver.getTelNumber())
            .shipperTel("0")
            .build();

    given(requestSpecification)
        .contentType(ContentType.JSON)
        .body(deliveryListDto)
        .when()
        .post(baseUri + "/list")
        .then()
        .statusCode(404);
  }

  @Test
  void Should_ReturnNotFoundWhenCreateIncorrectParcelDelivery() {
    DeliveryParcelDto deliveryParcelDto =
        DeliveryParcelDto.builder()
            .lockerId("6969")
            .pack(
                ParcelDto.builder()
                    .basePrice(BigDecimal.TEN)
                    .height(1.1)
                    .length(1.1)
                    .weight(1.1)
                    .width(1.1)
                    .isFragile(true)
                    .build())
            .receiverTel(receiver.getTelNumber())
            .shipperTel(shipper.getTelNumber())
            .build();

    given(requestSpecification)
        .contentType(ContentType.JSON)
        .body(deliveryParcelDto)
        .when()
        .post(baseUri + "/parcel")
        .then()
        .statusCode(404);
  }

    @Test
    void Should_putInAndTakeOutFromLocker() {
        String accessCode = "12345";

        given(requestSpecification)
            .contentType(ContentType.JSON)
            .when()
            .put(
                baseUri
                    + "/"
                    + deliveryId
                    + "/put-in?lockerId="
                    + locker.getIdentityNumber()
                    + "&accessCode="
                    + accessCode)
            .then()
            .statusCode(200);

        given(requestSpecification)
            .contentType(ContentType.JSON)
            .when()
            .put(
                baseUri
                    + "/"
                    + deliveryId
                    + "/take-out?telNumber="
                    + receiver.getTelNumber()
                    + "&accessCode="
                    + accessCode)
            .then()
            .statusCode(200);
    }

  @Test
  void Should_ReturnNotFoundWhenPutInDeliverIntoNotExistingLocker() {
    String accessCode = "12345";

    given(requestSpecification)
        .contentType(ContentType.JSON)
        .when()
        .put(
            baseUri
                + "/"
                + deliveryId
                + "/put-in?lockerId="
                + "6969"
                + "&accessCode="
                + accessCode)
        .then()
        .statusCode(404);
  }

    @Test
    void Should_ReturnNotFoundWhenTakeOutAndAccessCodeIsWrong() {
        String accessCode = "12345";

        given(requestSpecification)
            .contentType(ContentType.JSON)
            .when()
            .put(
                baseUri
                    + "/"
                    + deliveryId
                    + "/take-out?telNumber="
                    + receiver.getTelNumber()
                    + "&accessCode="
                    + accessCode)
            .then()
            .statusCode(409);
    }

  @Test
  void Should_CreateParcelDelivery() {
    DeliveryParcelDto deliveryParcelDto =
        DeliveryParcelDto.builder()
            .lockerId(locker.getIdentityNumber())
            .pack(
                ParcelDto.builder()
                    .basePrice(BigDecimal.TEN)
                    .height(1.1)
                    .length(1.1)
                    .weight(1.1)
                    .width(1.1)
                    .isFragile(true)
                    .build())
            .receiverTel(receiver.getTelNumber())
            .shipperTel(shipper.getTelNumber())
            .build();

    String addedEdDeliveryId =
        given(requestSpecification)
            .contentType(ContentType.JSON)
            .body(deliveryParcelDto)
            .when()
            .post(baseUri + "/parcel")
            .then()
            .statusCode(201)
            .body("id", notNullValue())
            .body("status", equalTo(READY_TO_SHIP.toString()))
            .body("pack.id", notNullValue())
            .extract()
            .path("id");

    String expectedDeliveryId =
        given(requestSpecification)
            .contentType(ContentType.JSON)
            .when()
            .get(baseUri + "/" + addedEdDeliveryId)
            .then()
            .statusCode(200)
            .extract()
            .path("id");

    assertEquals(expectedDeliveryId, addedEdDeliveryId);
  }

    @Override
    @BeforeAll
    protected void setup() {
        super.setup();
        given(requestSpecification)
            .contentType(ContentType.JSON)
            .body(LockerDto.builder()
                .identityNumber(locker.getIdentityNumber())
                .address(locker.getAddress())
                .numberOfBoxes(locker.countEmpty())
                .build())
            .when()
            .post("/api/lockers");

        given(requestSpecification)
            .contentType(ContentType.JSON)
            .body(ClientDto.builder()
                .firstName(shipper.getFirstName())
                .lastName(shipper.getLastName())
                .telNumber(shipper.getTelNumber())
                .build())
            .when()
            .post("/api/clients");

        given(requestSpecification)
            .contentType(ContentType.JSON)
            .body(ClientDto.builder()
                .firstName(receiver.getFirstName())
                .lastName(receiver.getLastName())
                .telNumber(receiver.getTelNumber())
                .build())
            .when()
            .post("/api/clients");

        DeliveryListDto deliveryListDto =
            DeliveryListDto.builder()
                .lockerId(locker.getIdentityNumber())
                .pack(ListDto.builder().basePrice(BigDecimal.TEN).isPriority(false).build())
                .receiverTel(receiver.getTelNumber())
                .shipperTel(shipper.getTelNumber())
                .build();

        deliveryId = given(requestSpecification)
            .contentType(ContentType.JSON)
            .body(deliveryListDto)
            .when()
            .post(baseUri + "/list")
            .then()
            .extract()
            .path("id");
    }
}
