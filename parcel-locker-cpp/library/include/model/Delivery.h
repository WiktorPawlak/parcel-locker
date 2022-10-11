//
// Created by student on 26.05.2021.
//

#ifndef INTRODUCTIONPROJECT_DELIVERY_H
#define INTRODUCTIONPROJECT_DELIVERY_H

#include <iostream>
#include "typedefs.h"
#include  <boost/uuid/random_generator.hpp>

enum Status {
    Ready_to_ship,
    Ready_for_pickup,
    Received
};

class Delivery {
private:
    boost::uuids::uuid id;
    ClientPtr shipper;
    ClientPtr receiver;
    Status status;
    PackagePtr package;
    LockerPtr locker;

public:
    Delivery(const float &basePrice, const boost::uuids::uuid &id, const float &width, const float &height,
             const float &length, const float &weight, const bool &isFragile, const ClientPtr &shipper,
             const ClientPtr &receiver, const LockerPtr &locker);
    Delivery(const float &basePrice, const boost::uuids::uuid &id, const bool &priority, const ClientPtr &shipper,
             const ClientPtr &receiver, const LockerPtr &locker);
    const float getCost() const;
    std::string getInfo() const;

    void setStatus(Status status);

    const boost::uuids::uuid & getId() const;
    const ClientPtr &getShipper() const;
    const ClientPtr &getReceiver() const;
    Status getStatus() const;
    const PackagePtr &getAPackage() const;
    const LockerPtr &getLocker() const;
};


#endif //INTRODUCTIONPROJECT_DELIVERY_H
