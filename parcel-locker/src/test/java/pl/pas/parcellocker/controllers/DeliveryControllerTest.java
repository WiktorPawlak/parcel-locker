package pl.pas.parcellocker.controllers;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.pas.parcellocker.controllers.dto.DeliveryListDto;
import pl.pas.parcellocker.controllers.dto.DeliveryParcelDto;
import pl.pas.parcellocker.controllers.dto.ListDto;
import pl.pas.parcellocker.controllers.dto.ParcelDto;
import pl.pas.parcellocker.model.client.Client;
import pl.pas.parcellocker.model.locker.Locker;
import pl.pas.parcellocker.repositories.hibernate.ClientRepositoryHibernate;
import pl.pas.parcellocker.repositories.hibernate.DeliveryRepositoryHibernate;
import pl.pas.parcellocker.repositories.hibernate.LockerRepositoryHibernate;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;

class DeliveryControllerTest {

  private static final String DELIVERIES_PATH = "/api/deliveries";
  protected final ClientRepositoryHibernate clientRepository = new ClientRepositoryHibernate();
  protected final DeliveryRepositoryHibernate deliveryRepository =
      new DeliveryRepositoryHibernate();
  protected final LockerRepositoryHibernate lockerRepository = new LockerRepositoryHibernate();
  Client receiver = new Client("Tony", "Stark", "1234567890");
  Client shipper = new Client("Steven", "Rogers", "9987654321");
  Locker locker = new Locker("PLO1", "Piotrkow", 5);
  String baseUri = "http://localhost:8080/parcel-locker-1.0-SNAPSHOT/api/deliveries/parcel/00000000-0000-0000-0000-000000000000";

  @BeforeEach
  void init() {
    clientRepository.add(receiver);
    clientRepository.add(shipper);
    lockerRepository.add(locker);
  }

  @AfterEach
  void finisher() {
    clientRepository.remove(receiver);
    clientRepository.remove(shipper);
    lockerRepository.remove(locker);
  }

  @Test
  void Should_CreateListDelivery() {
    DeliveryParcelDto deliveryListDto =
        DeliveryParcelDto.builder()
            .lockerId(locker.getIdentityNumber())
            .pack(ParcelDto.builder().build())
            .receiverTel(receiver.getTelNumber())
            .shipperTel(shipper.getTelNumber())
            .build();

    given()
        .contentType(ContentType.JSON)
        .body(deliveryListDto)
        .when()
        .get(baseUri)
        .then()
        .statusCode(200);
  }
}
