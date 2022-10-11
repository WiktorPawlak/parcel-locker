//
// Created by student on 10.06.2021.
//

#ifndef INTRODUCTIONPROJECT_CLIENTMANAGEREXCEPTION_H
#define INTRODUCTIONPROJECT_CLIENTMANAGEREXCEPTION_H

#include <iostream>
#include <exception>

class ClientManagerException: public std::logic_error {
public:
    explicit ClientManagerException(const std::string &arg);
};


#endif //INTRODUCTIONPROJECT_CLIENTMANAGEREXCEPTION_H
