//
// Created by student on 31.05.2021.
//

#ifndef INTRODUCTIONPROJECT_DEPOSITBOX_H
#define INTRODUCTIONPROJECT_DEPOSITBOX_H

#include  <boost/uuid/random_generator.hpp>
#include <string>
#include "typedefs.h"


class DepositBox{
private:
    boost::uuids::uuid deliveryId;
    std::string id;
    bool status;
    std::string accessCode;
    std::string telNumber;
public:
    std::string getId();
    bool getStatus();
    bool Access(const std::string &code, const std::string &telNumber);
    explicit DepositBox(const std::string &id); ///błąd typu na uml? ///błąd typu ale w atrybucie (bo może być na przykład id = "0123")
    std::string getTelNumber();
    std::string getAccessCode();
    const boost::uuids::uuid &getDeliveryId() const;

    void setDeliveryId(const boost::uuids::uuid &deliveryId);
    void setAccessCode(const std::string &code);
    void setTelNumber(const std::string &numer);
    void setStatus(bool dostepnosc);
};

#endif //INTRODUCTIONPROJECT_DEPOSITBOX_H
