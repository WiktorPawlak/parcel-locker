//
// Created by student on 06.06.2021.
//

#ifndef INTRODUCTIONPROJECT_LOCKEREXCEPTION_H
#define INTRODUCTIONPROJECT_LOCKEREXCEPTION_H

#include <iostream>
#include <exception>

class LockerException: public std::logic_error {
public:
    explicit LockerException(const std::string &arg);
};


#endif //INTRODUCTIONPROJECT_LOCKEREXCEPTION_H
