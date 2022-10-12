package pl.pas.parcellocker.managers;

import org.apache.commons.lang3.NotImplementedException;
import pl.pas.parcellocker.model.Client;
import pl.pas.parcellocker.model.Delivery;
import pl.pas.parcellocker.repositories.DeliveryRepository;

import static pl.pas.parcellocker.model.DeliveryStatus.READY_TO_PICKUP;

public class DeliveryManager {

    private DeliveryRepository deliveryRepository;

    public Long makeParcelDelivery(Delivery delivery) {
        throw new NotImplementedException("Not implemented");
    }

    public Long makeListDelivery(Delivery delivery) {
        throw new NotImplementedException("Not implemented");
    }

    public void putInLocker(Delivery delivery, String accessCode) {
        delivery.getLocker().putIn(delivery.getId(), delivery.getReceiver().getTelNumber(), accessCode);
        delivery.setStatus(READY_TO_PICKUP);
        //TODO update
    }

    public void takeOutDelivery(Delivery delivery, String accessCode) {
        throw new NotImplementedException("Not implemented");
    }

    public void getAllClientDeliveries(Client client) {
        throw new NotImplementedException("Not implemented");
    }

    public void checkClientShipmentBalance(Client client) {
        throw new NotImplementedException("Not implemented");
    }

    public void getArchivedDeliveries() {
        throw new NotImplementedException("Not implemented");
    }

    public void getAllReceivedClientDeliveries(Client client) {
        throw new NotImplementedException("Not implemented");
    }

}
