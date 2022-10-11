//
// Created by student on 09.06.2021.
//

#include "managers/DeliveryManager.h"
#include "model/Delivery.h"
#include "model/Locker.h"
#include "model/Client.h"
#include  <boost/uuid/random_generator.hpp>
#include "exceptions/DeliveryManagerException.h"

DeliveryManager::DeliveryManager() {
    currentDeliveries = std::make_shared<DeliveryRepository>();
    archivedDeliveries = std::make_shared<DeliveryRepository>();
}

DeliveryPtr DeliveryManager::makeDelivery(const float &basePrice, const float &width, const float &height, const float &length,
                              const float &weight,
                              bool isFragile, const ClientPtr &shipper, const ClientPtr &receiver,
                              const LockerPtr &locker) {
    boost::uuids::random_generator gen;
    boost::uuids::uuid id = gen();
    DeliveryPtr delivery = std::make_shared<Delivery>(basePrice, id, width, height, length, weight, isFragile, shipper, receiver, locker);
    currentDeliveries->add(delivery);
    return delivery;
}

DeliveryPtr DeliveryManager::makeDelivery(const float &basePrice, bool priority, const ClientPtr &shipper,
                                          const ClientPtr &receiver, const LockerPtr &locker) {
    boost::uuids::random_generator gen;
    boost::uuids::uuid id = gen();
    DeliveryPtr delivery = std::make_shared<Delivery>(basePrice, id, priority, shipper, receiver, locker);
    currentDeliveries->add(delivery);
    return delivery;
}

void DeliveryManager::putInLocker(const DeliveryPtr &delivery, const std::string &accessCode) {
    delivery->getLocker()->putIn(delivery->getId(), delivery->getReceiver()->getTelNumber(), accessCode);
    delivery->setStatus(Ready_for_pickup);
    ///wkładamy przesyłkę           szukamy pustego                         podajemy numer odbiorcy          kod odbioru
    ///do ustalonego lockera
}

bool
DeliveryManager::takeOutDelivery(const LockerPtr &locker, const ClientPtr &receiver, const std::string &accessCode) {
    boost::uuids::uuid deliveryId = locker->takeOut(receiver->getTelNumber(), accessCode);
    DeliveryPtr delivery = currentDeliveries->findById(deliveryId);
    if(!delivery)
        return false;
    currentDeliveries->remove(delivery);
    archivedDeliveries->add(delivery);
    delivery->setStatus(Received);
    return true;
}

std::vector<DeliveryPtr> DeliveryManager::getAllClientDeliveries(const ClientPtr &client) {
    try {
        if (!client)
            throw DeliveryManagerException("client is a nullptr!");
    }
    catch (DeliveryManagerException &e) {
        std::cerr << e.what() << std::endl;
        throw;
    }
    return currentDeliveries->findBy([&client](const DeliveryPtr& delivery){ return delivery->getReceiver()->getTelNumber() == client->getTelNumber(); });
}

float DeliveryManager::checkClientShipmentBalance(const ClientPtr &client) {
    float balance = 0;
    try {
        if(!client)
            throw DeliveryManagerException("client is a nullptr!");
        for (const auto &delivery : currentDeliveries->findAll()) {
            if (delivery->getShipper() == client)
                balance += delivery->getCost();
        }
    }

    catch (DeliveryManagerException &e) {
        std::cerr << e.what() << std::endl;
        throw;
    }

    return balance;
}

const DeliveryRepositoryPtr &DeliveryManager::getArchivedDeliveries() const {
    return archivedDeliveries;
}

std::vector<DeliveryPtr> DeliveryManager::getAllReceivedClientDeliveries(const ClientPtr &client) {
    try {
        if (!client)
            throw DeliveryManagerException("client is a nullptr!");
    }
    catch (DeliveryManagerException &e) {
        std::cerr << e.what() << std::endl;
        throw;
    }
    return archivedDeliveries->findBy([&client](const DeliveryPtr& delivery){ return delivery->getReceiver()->getTelNumber() == client->getTelNumber(); });

}
