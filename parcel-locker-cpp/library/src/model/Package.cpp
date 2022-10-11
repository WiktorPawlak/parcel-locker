//
// Created by student on 26.05.2021.
//

#include "model/Package.h"

Package::~Package() {
}

std::string Package::getInfo() const {
    return "basePrice: " + std::to_string((int)this->basePrice);
}

Package::Package(const float &basePrice) : basePrice(basePrice) {}

float Package::getBasePrice() const {
    return basePrice;
}

