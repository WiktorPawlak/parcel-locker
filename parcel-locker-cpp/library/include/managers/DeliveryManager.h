//
// Created by student on 09.06.2021.
//

#ifndef INTRODUCTIONPROJECT_DELIVERYMANAGER_H
#define INTRODUCTIONPROJECT_DELIVERYMANAGER_H

#include <iostream>
#include "repositories/DeliveryRepository.h"
#include "typedefs.h"

class Delivery;

class DeliveryManager {
private:
    DeliveryRepositoryPtr currentDeliveries;
    DeliveryRepositoryPtr archivedDeliveries;

public:
    DeliveryManager();
    DeliveryPtr makeDelivery(const float &basePrice, const float &width, const float &height, const float &length,
                             const float &weight,bool isFragile, const ClientPtr &shipper, const ClientPtr &receiver,
                             const LockerPtr &locker);
    DeliveryPtr makeDelivery(const float &basePrice, bool priority, const ClientPtr &shipper, const ClientPtr &receiver,
                             const LockerPtr &locker);

    void putInLocker(const DeliveryPtr& delivery, const std::string& accessCode);
    bool takeOutDelivery(const LockerPtr &locker, const ClientPtr &receiver, const std::string &accessCode);
    std::vector<DeliveryPtr> getAllClientDeliveries(const ClientPtr& client);
    std::vector<DeliveryPtr> getAllReceivedClientDeliveries(const ClientPtr& client);
    float checkClientShipmentBalance(const ClientPtr& client);

    const DeliveryRepositoryPtr &getArchivedDeliveries() const;
};


#endif //INTRODUCTIONPROJECT_DELIVERYMANAGER_H
