//
// Created by student on 26.05.2021.
//

#include "model/Delivery.h"
#include "model/Parcel.h"
#include "model/List.h"
#include "model/Client.h"
#include "model/Locker.h"

Delivery::Delivery(const float &basePrice, const boost::uuids::uuid &id, const float &width, const float &height,
                   const float &length, const float &weight, const bool &isFragile, const ClientPtr &shipper,
                   const ClientPtr &receiver, const LockerPtr &locker)
        : id(id), shipper(shipper), receiver(receiver), locker(locker) {
    package = std::make_shared<Parcel>(basePrice, width, height, length, weight, isFragile);
    status = Ready_to_ship;
}


Delivery::Delivery(const float &basePrice, const boost::uuids::uuid &id, const bool &priority, const ClientPtr &shipper,
                   const ClientPtr &receiver, const LockerPtr &locker) : id(id), shipper(shipper), receiver(receiver), locker(locker) {
    package = std::make_shared<List>(basePrice, priority);
    status = Ready_to_ship;
}

const float Delivery::getCost() const {
    return package->getCost();
}

const boost::uuids::uuid & Delivery::getId() const {
    return id;
}

const ClientPtr &Delivery::getShipper() const {
    return shipper;
}

const ClientPtr &Delivery::getReceiver() const {
    return receiver;
}

Status Delivery::getStatus() const {
    return status;
}

const PackagePtr &Delivery::getAPackage() const {
    return package;
}

void Delivery::setStatus(Status status) {
    Delivery::status = status;
}

std::string Delivery::getInfo() const {
    std::string stat;
    if(status == Ready_to_ship)
        stat = "Ready to ship";
    else if(status == Ready_for_pickup)
        stat = "Ready for pickup";
    else
        stat = "Received";
    return stat + "\nshipper: " + shipper->getInfo() + "\nreceiver: " + receiver->getInfo();
}

const LockerPtr &Delivery::getLocker() const {
    return locker;
}

