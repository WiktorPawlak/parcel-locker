//
// Created by student on 10.06.2021.
//

#ifndef INTRODUCTIONPROJECT_CLIENTREPOSITORY_H
#define INTRODUCTIONPROJECT_CLIENTREPOSITORY_H

#include "repositories/Repository.h"
#include "typedefs.h"

class ClientRepository: public Repository<Client> {
public:
    ClientPtr findByTelNumber(const std::string& telNumber) const;
};


#endif //INTRODUCTIONPROJECT_CLIENTREPOSITORY_H
