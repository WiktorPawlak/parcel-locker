//
// Created by student on 10.06.2021.
//

#include "repositories/DeliveryRepository.h"
#include "model/Delivery.h"
#include "model/Client.h"

DeliveryPtr DeliveryRepository::findById(const boost::uuids::uuid &id) const {
    for(auto delivery : objects)
    {
        if(delivery != nullptr && delivery->getId() == id)
        {
            return delivery;
        }
    }
    return nullptr;
}
