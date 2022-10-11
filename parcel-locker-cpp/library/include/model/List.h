//
// Created by student on 26.05.2021.
//

#ifndef INTRODUCTIONPROJECT_LIST_H
#define INTRODUCTIONPROJECT_LIST_H

#include "Package.h"

class List: public Package {
private:
    bool priority;

public:
    List(const float &basePrice, bool priority);

    const float getCost() const override;
    std::string getInfo() const override;
};


#endif //INTRODUCTIONPROJECT_LIST_H
