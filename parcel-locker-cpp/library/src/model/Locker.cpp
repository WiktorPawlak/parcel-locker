//
// Created by student on 31.05.2021.
//

#include "model/Locker.h"
#include "model/DepositBox.h"
#include "exceptions/LockerException.h"


Locker::Locker(int boxAmount)
{
    try {
        if (!boxAmount)
            throw LockerException("Locker with 0 boxes can not be created!");
    }
    catch(LockerException &e) {
        std::cerr << e.what() << std::endl;
        throw;
    }

    for(int i = 0; i < boxAmount; i++){
        depositBoxes.push_back(std::make_shared<DepositBox>(std::to_string(i)));
    }
}

void Locker::putIn(const boost::uuids::uuid &id, const std::string& telNumber, const std::string& accessCode) {
    for(int i = 0; i < depositBoxes.size(); i++) {
        if(!depositBoxes.at(i)->getStatus()) {
            depositBoxes.at(i)->setAccessCode(accessCode);
            depositBoxes.at(i)->setStatus(true);
            depositBoxes.at(i)->setTelNumber(telNumber);
            depositBoxes.at(i)->setDeliveryId(id);
            return;
        }
    }
    try {
        throw LockerException("Not able to put package into box.");
    }
    catch (LockerException &e) {
        std::cerr << e.what() << std::endl;
        throw;
    }
}

boost::uuids::uuid Locker::takeOut(const std::string &telNumber, const std::string &code)
{
    for(int i = 0; i < depositBoxes.size(); i++) {
        if(depositBoxes.at(i)->Access(code,telNumber)) {   ///korzystamy tu z metody depositBoxa, która sama czyści
            return depositBoxes.at(i)->getDeliveryId();
        }
    }
    try {
        throw LockerException("Couldn't get package out.");
    }
    catch(LockerException &e) {
        std::cerr << e.what() << std::endl;
        throw;
    }
}

std::string Locker::getInfo()
{
    std::string lista = "Empty boxes: " + std::to_string(this->findAllEmpty()) + "\n";
    for(int i=0; i<depositBoxes.size();i++)
    {
        if(!depositBoxes.at(i)->getStatus())
        {
            lista += depositBoxes.at(i)->getId() + "\n";
        }
    }
    return lista;
}

int Locker::findAllEmpty() {
    int counter = 0;
    for(int i = 0; i < depositBoxes.size(); i++) {
        if (!depositBoxes.at(i)->getStatus()) {
            counter++;
        }
    }
    return counter;
}

DepositBoxPtr Locker::getDepositBox(std::string nrId) {
    for(int i=0; i<depositBoxes.size();i++)
    {
        if(depositBoxes.at(i)->getId() == nrId)
        {
            return depositBoxes.at(i);
        }
    }
}


