#include <iostream>
#include "model/Client.h"
#include "typedefs.h"
#include "model/Delivery.h"
#include "managers/DeliveryManager.h"
#include "managers/ClientManager.h"
#include "model/Locker.h"

int main() {
    ClientManager clientManager;
    DeliveryManager deliveryManager;
    LockerPtr locker = std::make_shared<Locker>(10);
    clientManager.registerClient("Kamil", "Nowak", "12345656");
    clientManager.registerClient("Maciek", "Kowal", "3234566");

    DeliveryPtr delivery = deliveryManager.makeDelivery(10, true, clientManager.getClient("12345656"), clientManager.getClient("3234566"), locker);
    std::cout << deliveryManager.getAllClientDeliveries(clientManager.getClient("3234566")).at(0)->getInfo() << std::endl;

    deliveryManager.putInLocker(delivery, "123");
    std::cout << deliveryManager.getAllClientDeliveries(clientManager.getClient("3234566")).at(0)->getInfo() << std::endl;

    deliveryManager.takeOutDelivery(locker, clientManager.getClient("3234566"), "123");
    std::cout << deliveryManager.getAllReceivedClientDeliveries(clientManager.getClient("3234566")).at(0)->getInfo() << std::endl;

    return 0;
}
