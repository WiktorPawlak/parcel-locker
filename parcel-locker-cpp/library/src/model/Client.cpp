//
// Created by student on 26.05.2021.
//

#include "../../include/model/Client.h"
#include "exceptions/ClientException.h"

Client::Client(const std::string &firstName, const std::string &lastName, const std::string &telNumber)
:firstName(firstName), lastName(lastName), telNumber(telNumber) {
    try {
        if (firstName.empty())
            throw ClientException("Empty firstName variable!");
        if (lastName.empty())
            throw ClientException("Empty lastName variable!");
        if (telNumber.empty())
            throw ClientException("Empty telNumber variable!");
    }
    catch (ClientException &e) {
        std::cerr << e.what() << std::endl;
        throw;
    }
    archive = false;
}

const std::string &Client::getFirstName() const {
    return firstName;
}

const std::string &Client::getLastName() const {
    return lastName;
}

const std::string &Client::getTelNumber() const {
    return telNumber;
}

bool Client::isArchived() const {
    return archive;
}

std::string Client::getInfo() const {
    return firstName + " " + lastName + " phone: " + telNumber + (archive ? " Archived" : " Actual");
}

void Client::setArchive(bool archive) {
    this->archive = archive;
}
