//
// Created by student on 10.06.2021.
//

#include "repositories/ClientRepository.h"
#include "model/Client.h"

ClientPtr ClientRepository::findByTelNumber(const std::string& telNumber) const {
    for (auto client : objects)
    {
        if(client != nullptr && client->getTelNumber() == telNumber)
        {
            return client;
        }
    }
    return nullptr;
}
