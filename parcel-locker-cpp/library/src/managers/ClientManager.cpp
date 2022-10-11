//
// Created by student on 10.06.2021.
//

#include <managers/ClientManager.h>
#include "model/Client.h"
#include "repositories/ClientRepository.h"
#include "exceptions/ClientManagerException.h"

ClientManager::ClientManager() {
    clientRepository = std::make_shared<ClientRepository>();
}

ClientPtr ClientManager::getClient(const std::string &telNumber) {
    try {
        if (telNumber.empty())
            throw ClientManagerException("telNumber variable is empty!");
    }
    catch (ClientManagerException &e) {
        std::cerr << e.what() << std::endl;
        throw;
    }
    return clientRepository->findByTelNumber(telNumber);
}

ClientPtr ClientManager::registerClient(const std::string &firstName, const std::string &lastName, const std::string &telNumber) {
    for(const auto& client : clientRepository->findAll()){
        if(client->getTelNumber() == telNumber)
        {
            return client;
        }
    }
    ClientPtr newClient = std::make_shared<Client>(firstName, lastName, telNumber);
    clientRepository->add(newClient);
    return newClient;
}

void ClientManager::unregisterClient(const ClientPtr &Client) {
    try {
        if(!Client)
            throw ClientManagerException("client is a nullptr!");
        if (this->getClient(Client->getTelNumber()) != nullptr)
            Client->setArchive(true);;
    }
    catch (ClientManagerException &e) {
        std::cerr << e.what() << std::endl;
        throw;
    }
}


