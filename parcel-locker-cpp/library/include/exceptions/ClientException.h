//
// Created by student on 06.06.2021.
//

#ifndef INTRODUCTIONPROJECT_CLIENTEXCEPTION_H
#define INTRODUCTIONPROJECT_CLIENTEXCEPTION_H

#include <iostream>
#include <exception>

class ClientException : public std::logic_error {
public:
    explicit ClientException(const std::string &arg);
};


#endif //INTRODUCTIONPROJECT_CLIENTEXCEPTION_H
