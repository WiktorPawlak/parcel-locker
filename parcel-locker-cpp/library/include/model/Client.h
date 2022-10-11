//
// Created by student on 26.05.2021.
//

#ifndef INTRODUCTIONPROJECT_CLIENT_H
#define INTRODUCTIONPROJECT_CLIENT_H

#include <iostream>

class Client {
private:
    std::string firstName;
    std::string lastName;
    std::string telNumber;
    bool archive;

public:
    Client(const std::string &firstName, const std::string &lastName, const std::string &telNumber);

    const std::string &getFirstName() const;
    const std::string &getLastName() const;
    const std::string &getTelNumber() const;

    void setArchive(bool archive);

    bool isArchived() const;

    std::string getInfo() const;
};


#endif //INTRODUCTIONPROJECT_CLIENT_H
