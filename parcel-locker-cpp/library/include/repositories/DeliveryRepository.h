//
// Created by student on 10.06.2021.
//

#ifndef INTRODUCTIONPROJECT_DELIVERYREPOSITORY_H
#define INTRODUCTIONPROJECT_DELIVERYREPOSITORY_H

#include  <boost/uuid/random_generator.hpp>
#include <iostream>
#include "repositories/Repository.h"
#include "typedefs.h"

class DeliveryRepository: public Repository<Delivery> {
public:
    DeliveryPtr findById(const boost::uuids::uuid& id) const;
};


#endif //INTRODUCTIONPROJECT_DELIVERYREPOSITORY_H
