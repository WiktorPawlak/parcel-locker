//
// Created by student on 31.05.2021.
//

#ifndef INTRODUCTIONPROJECT_LOCKER_H
#define INTRODUCTIONPROJECT_LOCKER_H

#include <string>
#include <vector>
#include <typedefs.h>
#include <algorithm>
#include <iterator>
#include "model/DepositBox.h"

class Locker{
private:
    ///int boxAmount; ///trzeba chyba dodać do uml
    ///wydaje mi się, że boxAmount może być zmienną tymczasową bo jest tylko używany w konstruktorze
    std::vector<DepositBoxPtr> depositBoxes;
public:
    explicit Locker(int boxAmount);
    void putIn(const boost::uuids::uuid &id, const std::string& telNumber, const std::string& accessCode);
    boost::uuids::uuid takeOut(const std::string &telNumber, const std::string &code);
    std::string getInfo();
    int findAllEmpty(); ///dodałem też do UML, może sie przydać
    DepositBoxPtr getDepositBox(std::string nrId);

};

#endif //INTRODUCTIONPROJECT_LOCKER_H
