//
// Created by student on 10.06.2021.
//

#include <boost/test/unit_test.hpp>
#include  <boost/uuid/random_generator.hpp>
#include "typedefs.h"
#include "model/Delivery.h"
#include "model/Client.h"
#include "model/Locker.h"
#include "managers/DeliveryManager.h"

namespace btt = boost::test_tools;

struct DeliveryManagerFixture
{
    DeliveryManagerPtr deliveryManager;
    ClientPtr shipper1;
    ClientPtr receiver1;
    LockerPtr locker;
    DeliveryPtr delivery;
    float basePrice = 10;
    boost::uuids::uuid id;

    DeliveryManagerFixture()
    {
        boost::uuids::random_generator gen;
        id = gen();
        locker = std::make_shared<Locker>(20);
        shipper1 =  std::make_shared<Client>("Oscar", "Trel", "321312312");
        receiver1 = std::make_shared<Client>("Bartosh", "Siekan", "123123123");
        deliveryManager = std::make_shared<DeliveryManager>();
        delivery = deliveryManager->makeDelivery(10, 10, 20, 30, 10, false, shipper1, receiver1, locker);
    }
};

BOOST_AUTO_TEST_SUITE(DeliveryManagerTest)

    BOOST_FIXTURE_TEST_CASE(putInLockerConformance, DeliveryManagerFixture)
    {
        int empty = locker->findAllEmpty();
        deliveryManager->putInLocker(delivery, "1234");
        BOOST_TEST(locker->findAllEmpty() == empty-1);
    }

    BOOST_FIXTURE_TEST_CASE(takeOutDeliveryConformance, DeliveryManagerFixture)
    {
        deliveryManager->putInLocker(delivery, "1234");
        int empty = locker->findAllEmpty();
        deliveryManager->takeOutDelivery(locker, receiver1, "1234");
        BOOST_TEST(locker->findAllEmpty() == empty+1);
        BOOST_TEST(deliveryManager->getArchivedDeliveries()->size() == 1);
    }

    BOOST_FIXTURE_TEST_CASE(getAllClientDeliveriesConformance, DeliveryManagerFixture)
    {
        DeliveryPtr delivery1 = deliveryManager->makeDelivery(10, 10, 20, 30, 10, false, shipper1, receiver1, locker);
        BOOST_TEST(deliveryManager->getAllClientDeliveries(receiver1)[0] == delivery);
        BOOST_TEST(deliveryManager->getAllClientDeliveries(receiver1)[1] == delivery1);
    }

    BOOST_FIXTURE_TEST_CASE(getAllReceivedClientDeliveriesConformance, DeliveryManagerFixture)
    {
        DeliveryPtr delivery1 = deliveryManager->makeDelivery(10, 10, 20, 30, 10, false, shipper1, receiver1, locker);
        deliveryManager->putInLocker(delivery1 ,"123");
        deliveryManager->takeOutDelivery(locker, receiver1,"123");
        BOOST_TEST(deliveryManager->getAllReceivedClientDeliveries(receiver1)[0] == delivery1);
    }

    BOOST_FIXTURE_TEST_CASE(checkClientShipmentBalanceConformance, DeliveryManagerFixture)
    {
        BOOST_TEST(deliveryManager->checkClientShipmentBalance(shipper1) == basePrice*1.5f);
    }

    BOOST_FIXTURE_TEST_CASE(exceptionConformance, DeliveryManagerFixture)
    {
        BOOST_REQUIRE_THROW(deliveryManager->checkClientShipmentBalance(nullptr), std::logic_error);
        BOOST_REQUIRE_THROW(deliveryManager->getAllClientDeliveries(nullptr), std::logic_error);
        BOOST_REQUIRE_THROW(deliveryManager->getAllReceivedClientDeliveries(nullptr), std::logic_error);
    }

BOOST_AUTO_TEST_SUITE_END()