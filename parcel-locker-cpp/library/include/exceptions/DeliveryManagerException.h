//
// Created by student on 10.06.2021.
//

#ifndef INTRODUCTIONPROJECT_DELIVERYMANAGEREXCEPTION_H
#define INTRODUCTIONPROJECT_DELIVERYMANAGEREXCEPTION_H

#include <iostream>
#include <exception>

class DeliveryManagerException: public std::logic_error {
public:
    explicit DeliveryManagerException(const std::string& arg);

};


#endif //INTRODUCTIONPROJECT_DELIVERYMANAGEREXCEPTION_H
