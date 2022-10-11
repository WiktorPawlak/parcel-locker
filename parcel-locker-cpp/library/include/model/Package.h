//
// Created by student on 26.05.2021.
//

#ifndef INTRODUCTIONPROJECT_PACKAGE_H
#define INTRODUCTIONPROJECT_PACKAGE_H

#include <iostream>

class Package {
private:
    float basePrice;

public:
    explicit Package(const float &basePrice);
    virtual ~Package();

    virtual const float getCost() const = 0;
    virtual std::string getInfo() const;

    float getBasePrice() const;
};


#endif //INTRODUCTIONPROJECT_PACKAGE_H
