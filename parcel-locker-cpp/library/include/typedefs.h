//
// Created by student on 26.05.2021.
//

#ifndef INTRODUCTIONPROJECT_TYPEDEFS_H
#define INTRODUCTIONPROJECT_TYPEDEFS_H

#include <memory>
#include <functional>

class Client;
class Package;
class Delivery;
class DepositBox;
class Locker;
class DeliveryManager;
class ClientManager;
class ClientRepository;
class DeliveryRepository;

typedef std::shared_ptr<Client> ClientPtr;
typedef std::shared_ptr<Package> PackagePtr;
typedef std::shared_ptr<Delivery> DeliveryPtr;
typedef std::shared_ptr<DepositBox> DepositBoxPtr;
typedef std::shared_ptr<Locker> LockerPtr;
typedef std::shared_ptr<DeliveryManager> DeliveryManagerPtr;
typedef std::shared_ptr<ClientManager> ClientManagerPtr;
typedef std::shared_ptr<ClientRepository> ClientRepositoryPtr;
typedef std::shared_ptr<DeliveryRepository> DeliveryRepositoryPtr;

#endif //INTRODUCTIONPROJECT_TYPEDEFS_H
