//
// Created by student on 10.06.2021.
//

#ifndef INTRODUCTIONPROJECT_CLIENTMANAGER_H
#define INTRODUCTIONPROJECT_CLIENTMANAGER_H

#include <iostream>
#include "repositories/Repository.h"
#include "repositories/ClientRepository.h"
#include "typedefs.h"

class ClientManager{
private:
    ClientRepositoryPtr clientRepository;
public:
    ClientManager();
    ClientPtr registerClient(const std::string &firstName, const std::string &lastName, const std::string &telNumber);
    void unregisterClient(const ClientPtr &Client);
    ClientPtr getClient(const std::string &telNumber);
};

#endif //INTRODUCTIONPROJECT_CLIENTMANAGER_H