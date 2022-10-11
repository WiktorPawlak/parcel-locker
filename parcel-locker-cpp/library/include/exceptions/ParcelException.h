//
// Created by student on 06.06.2021.
//

#ifndef INTRODUCTIONPROJECT_PARCELEXCEPTION_H
#define INTRODUCTIONPROJECT_PARCELEXCEPTION_H

#include <iostream>
#include <exception>

class ParcelException: public std::logic_error {
public:
    explicit ParcelException(const std::string &arg);
};


#endif //INTRODUCTIONPROJECT_PARCELEXCEPTION_H
