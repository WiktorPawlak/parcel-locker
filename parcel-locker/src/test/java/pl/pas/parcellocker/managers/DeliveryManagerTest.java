package pl.pas.parcellocker.managers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import pl.pas.parcellocker.config.TestsConfig;
import pl.pas.parcellocker.exceptions.DeliveryManagerException;
import pl.pas.parcellocker.model.client.Client;
import pl.pas.parcellocker.model.delivery.Delivery;
import pl.pas.parcellocker.model.locker.Locker;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DeliveryManagerTest extends TestsConfig {

  private final DeliveryManager deliveryManager =
      new DeliveryManager(deliveryRepository, lockerRepository, clientRepository);
  private final BigDecimal basePrice = BigDecimal.TEN;
  private Client shipper1;
  private Client receiver1;
  private Locker locker;

  @BeforeAll
  void setup() {
    locker = new Locker("LDZ01", "Gawronska 12, Lodz 12-123", 20);
    shipper1 = new Client("Oscar", "Trel", "321312312");
    receiver1 = new Client("Bartosh", "Siekan", "123123123");
    clientRepository.add(shipper1);
    clientRepository.add(receiver1);
    lockerRepository.add(locker);
  }

  @AfterEach
  void eachFinisher() {
    deliveryRepository.findAll().forEach(deliveryRepository::remove);
  }

  @Test
  void Should_ThrowExceptionOnPutIn_WhenClientIsInactive() {
    Client client = new Client("Mauris", "Kakel", UUID.randomUUID().toString().substring(0, 9));
    clientRepository.add(client);

    Delivery delivery =
        deliveryManager.makeParcelDelivery(
            basePrice,
            10,
            20,
            30,
            10,
            false,
            client.getTelNumber(),
            receiver1.getTelNumber(),
            locker.getIdentityNumber());

    changeClientActiveStatus(client, false);

    Delivery refreshedDelivery = deliveryRepository.get(delivery.getId());
    assertThrows(
        DeliveryManagerException.class,
        () ->
            deliveryManager.putInLocker(
                refreshedDelivery.getId(),
                refreshedDelivery.getLocker().getIdentityNumber(),
                "123"));
  }

  @Test
  void Should_ThrowException_WhenDeliveryPutInAgain() {
    Delivery delivery =
        deliveryManager.makeParcelDelivery(
            basePrice,
            10,
            20,
            30,
            10,
            false,
            shipper1.getTelNumber(),
            receiver1.getTelNumber(),
            locker.getIdentityNumber());

    deliveryManager.putInLocker(
        delivery.getId(), delivery.getLocker().getIdentityNumber(), "54321");
    Delivery refreshedDelivery = deliveryRepository.get(delivery.getId());

    assertThrows(
        DeliveryManagerException.class,
        () ->
            deliveryManager.putInLocker(
                refreshedDelivery.getId(),
                refreshedDelivery.getLocker().getIdentityNumber(),
                "65433"));

    locker = lockerRepository.get(locker.getId());
    deliveryManager.takeOutDelivery(delivery.getId(), receiver1.getTelNumber(), "54321");
  }

  @Test
  void Should_SetAllocationTime_WhenDeliveryPutInAndTookOut() {
    Delivery delivery =
        deliveryManager.makeParcelDelivery(
            basePrice,
            10,
            20,
            30,
            10,
            false,
            shipper1.getTelNumber(),
            receiver1.getTelNumber(),
            locker.getIdentityNumber());

    assertNull(delivery.getAllocationStart());
    assertNull(delivery.getAllocationStop());

    deliveryManager.putInLocker(
        delivery.getId(), delivery.getLocker().getIdentityNumber(), "54321");
    locker = lockerRepository.get(locker.getId());
    delivery = deliveryRepository.get(delivery.getId());
    assertNotNull(delivery.getAllocationStart());

    deliveryManager.takeOutDelivery(delivery.getId(), receiver1.getTelNumber(), "54321");
    locker = lockerRepository.get(locker.getId());
    delivery = deliveryRepository.get(delivery.getId());
    assertNotNull(delivery.getAllocationStop());
  }

  @Test
  void Should_BlockDepositBox_WhenDeliveryPutInLocker() {
    Delivery delivery =
        deliveryManager.makeParcelDelivery(
            basePrice,
            10,
            20,
            30,
            10,
            false,
            shipper1.getTelNumber(),
            receiver1.getTelNumber(),
            locker.getIdentityNumber());
    int empty = locker.countEmpty();
    deliveryManager.putInLocker(
        delivery.getId(), delivery.getLocker().getIdentityNumber(), "12345");
    locker = lockerRepository.get(locker.getId());

    assertEquals(empty - 1, locker.countEmpty());

    deliveryManager.takeOutDelivery(delivery.getId(), receiver1.getTelNumber(), "12345");
    locker = lockerRepository.get(locker.getId());
  }

  @Test
  void Should_UnlockDepositBox_WhenDeliveryTookOut() {
    Delivery delivery =
        deliveryManager.makeParcelDelivery(
            basePrice,
            10,
            20,
            30,
            10,
            false,
            shipper1.getTelNumber(),
            receiver1.getTelNumber(),
            locker.getIdentityNumber());
    deliveryManager.putInLocker(
        delivery.getId(), delivery.getLocker().getIdentityNumber(), "54321");
    locker = lockerRepository.get(locker.getId());
    int empty = locker.countEmpty();
    deliveryManager.takeOutDelivery(delivery.getId(), receiver1.getTelNumber(), "54321");
    locker = lockerRepository.get(locker.getId());

    assertEquals(empty + 1, locker.countEmpty());
  }

  @Test
  void Should_ThrowException_WhenLockerIsFull() {
    Locker oneBoxLocker = new Locker("LDZ12", "Gawronska 66, Lodz 12-123", 1);
    lockerRepository.add(oneBoxLocker);

    Delivery testDelivery =
        deliveryManager.makeParcelDelivery(
            basePrice,
            10,
            20,
            30,
            10,
            false,
            shipper1.getTelNumber(),
            receiver1.getTelNumber(),
            oneBoxLocker.getIdentityNumber());
    deliveryManager.putInLocker(
        testDelivery.getId(), testDelivery.getLocker().getIdentityNumber(), "1111");
    locker = lockerRepository.get(oneBoxLocker.getId());

    assertThrows(
        DeliveryManagerException.class,
        () ->
            deliveryManager.putInLocker(
                testDelivery.getId(), testDelivery.getLocker().getIdentityNumber(), "1111"));

    deliveryManager.takeOutDelivery(testDelivery.getId(), receiver1.getTelNumber(), "1111");
    locker = lockerRepository.get(oneBoxLocker.getId());
  }

  @Test
  void Should_ReturnAllClientDeliveries() {
    Delivery delivery =
        deliveryManager.makeParcelDelivery(
            basePrice,
            10,
            20,
            30,
            10,
            false,
            shipper1.getTelNumber(),
            receiver1.getTelNumber(),
            locker.getIdentityNumber());
    Delivery delivery1 =
        deliveryManager.makeParcelDelivery(
            basePrice,
            10,
            20,
            30,
            10,
            false,
            shipper1.getTelNumber(),
            receiver1.getTelNumber(),
            locker.getIdentityNumber());

    assertEquals(delivery, deliveryManager.getAllClientDeliveries(receiver1).get(0));
    assertEquals(delivery1, deliveryManager.getAllClientDeliveries(receiver1).get(1));
  }

  @Test
  void Should_ReturnAllReceivedDeliveriesForGivenClient() {
    Delivery delivery1 =
        deliveryManager.makeParcelDelivery(
            basePrice,
            10,
            20,
            30,
            10,
            false,
            shipper1.getTelNumber(),
            receiver1.getTelNumber(),
            locker.getIdentityNumber());
    deliveryManager.putInLocker(delivery1.getId(), delivery1.getLocker().getIdentityNumber(), "2222");
    locker = lockerRepository.get(locker.getId());
    deliveryManager.takeOutDelivery(delivery1.getId(), receiver1.getTelNumber(), "2222");
    locker = lockerRepository.get(locker.getId());

    assertTrue(0 < deliveryManager.getAllReceivedClientDeliveries(receiver1.getTelNumber()).size());
  }

    @Test
    void Should_ReturnAllCurrentDeliveriesForGivenClient() {
            deliveryManager.makeParcelDelivery(
                basePrice,
                10,
                20,
                30,
                10,
                false,
                shipper1.getTelNumber(),
                receiver1.getTelNumber(),
                locker.getIdentityNumber());

        assertTrue(0 < deliveryManager.getAllCurrentClientDeliveries(receiver1.getTelNumber()).size());
    }

  @Test
  void Should_ReturnCorrectBalanceForClientShipments() {
    Delivery delivery =
        deliveryManager.makeParcelDelivery(
            basePrice,
            10,
            20,
            30,
            10,
            false,
            shipper1.getTelNumber(),
            receiver1.getTelNumber(),
            locker.getIdentityNumber());
    deliveryManager.putInLocker(delivery.getId(), delivery.getLocker().getIdentityNumber(), "5555");
    locker = lockerRepository.get(locker.getId());

    assertEquals(new BigDecimal("15.000"), deliveryManager.checkClientShipmentBalance(shipper1));

    deliveryManager.takeOutDelivery(delivery.getId(), receiver1.getTelNumber(), "5555");
    locker = lockerRepository.get(locker.getId());
  }

  @Test
  void Should_ThrowException_WhenInvalidValuesPassed() {
    assertThrows(
        DeliveryManagerException.class, () -> deliveryManager.checkClientShipmentBalance(null));
  }

    private void changeClientActiveStatus(Client client, boolean active) {
        Client refreshedClient = clientRepository.get(client.getId());
        refreshedClient.setActive(active);
        clientRepository.update(refreshedClient);
    }
}
