//
// Created by student on 31.05.2021.
//

#include <model/DepositBox.h>


std::string DepositBox::getId() {
    return id;
}

bool DepositBox::getStatus() {
    return status;
}

bool DepositBox::Access(const std::string &code, const std::string &telNumber) {
    if(code == this->accessCode && telNumber == this->telNumber && !code.empty() && !telNumber.empty()) {
        status = false;
        this->accessCode = "";
        this->telNumber = "";
        return true;
    }
    return false;
}


DepositBox::DepositBox(const std::string &id): id(id) {
    status = false;
    telNumber = "";
    accessCode = "";
}


void DepositBox::setAccessCode(const std::string &code) {
    accessCode = code;
}

void DepositBox::setTelNumber(const std::string &numer) {
    telNumber = numer;
}

void DepositBox::setStatus(bool dostepnosc) {
    status = dostepnosc;
}

std::string DepositBox::getTelNumber() {
    return telNumber;
}

std::string DepositBox::getAccessCode() {
    return accessCode;
}

void DepositBox::setDeliveryId(const boost::uuids::uuid &deliveryId) {
    DepositBox::deliveryId = deliveryId;
}

const boost::uuids::uuid &DepositBox::getDeliveryId() const {
    return deliveryId;
}

