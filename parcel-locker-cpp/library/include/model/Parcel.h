//
// Created by student on 26.05.2021.
//

#ifndef INTRODUCTIONPROJECT_PARCEL_H
#define INTRODUCTIONPROJECT_PARCEL_H

#include "Package.h"

class Parcel: public Package {
private:
    float width;
    float length;
    float height;
    float weight;
    bool fragile;

public:
    Parcel(const float &basePrice, const float &width, const float &length,
           const float &height, const float &weight, bool fragile);

    const float getCost() const override;
    std::string getInfo() const override;
};


#endif //INTRODUCTIONPROJECT_PARCEL_H
