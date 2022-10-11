//
// Created by student on 26.05.2021.
//

#include "model/List.h"


List::List(const float &basePrice, bool priority) : Package(basePrice), priority(priority) {}

const float List::getCost() const {
    if(priority)
        return (this->getBasePrice()/2)+3;
    else
        return this->getBasePrice()/2;
}

std::string List::getInfo() const {
    std::string ifPrio = priority ? "Priority" : "Registered";
    return ifPrio + (std::string)" letter" + (std::string)" cost: " + std::to_string((int)this->getCost()) + " " + Package::getInfo();
}
