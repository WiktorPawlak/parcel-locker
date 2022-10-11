//
// Created by student on 26.05.2021.
//

#include "model/Parcel.h"
#include "exceptions/ParcelException.h"

Parcel::Parcel(const float &basePrice, const float &width, const float &length, const float &height, const float &weight, bool fragile) : Package(
        basePrice), width(width), length(length), height(height), weight(weight), fragile(fragile) {
    try {
        if(width <= 0 || width > 40)
            throw ParcelException("invalid width value!");
        if(length <= 0 || length > 40)
            throw ParcelException("invalid length value!");
        if(height <= 0 || height > 40)
            throw ParcelException("invalid height value!");
        if(weight <= 0 || weight > 20)
            throw ParcelException("invalid weight value!");
    }
    catch(ParcelException &e) {
        std::cerr << e.what() << std::endl;
        throw;
    }
}

const float Parcel::getCost() const {
    if(width <= 20 && height <= 20 && length <= 20)
        return this->getBasePrice();
    if(width <= 30 && height <= 30 && length <= 30)
        return this->getBasePrice()*1.5f;
    if(width <= 40 && height <= 40 && length <= 40)
        return this->getBasePrice()*2.0f;
}

std::string Parcel::getInfo() const {
    return "Parcel " + std::to_string((int)width)+"x"+std::to_string((int)length)+"x"+std::to_string((int)height)+
           " "+std::to_string((int)weight)+"kg"+" cost: "+std::to_string((int)this->getCost())+ " " +Package::getInfo();
}
