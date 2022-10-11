//
// Created by student on 08.06.2021.
//

#include <boost/test/unit_test.hpp>
#include  <boost/uuid/random_generator.hpp>
#include "typedefs.h"
#include "model/Delivery.h"
#include "model/Client.h"
#include "model/Parcel.h"
#include "model/List.h"
#include "model/Locker.h"

namespace btt = boost::test_tools;

struct DeliveryFixture
{
    DeliveryPtr delivery1;
    DeliveryPtr delivery2;
    ClientPtr shipper1;
    ClientPtr receiver1;
    PackagePtr box;
    PackagePtr list;
    LockerPtr locker;
    float basePrice = 10;
    boost::uuids::uuid id;

    DeliveryFixture()
    {
        boost::uuids::random_generator gen;
        id = gen();
        locker = std::make_shared<Locker>(20);
        shipper1 =  std::make_shared<Client>("Oscar", "Trel", "321312312");
        receiver1 = std::make_shared<Client>("Bartosh", "Siekan", "123123123");
        delivery1 = std::make_shared<Delivery>(basePrice, id, 10, 20, 30, 10, true, shipper1, receiver1, locker);
        box = std::make_shared<Parcel>(basePrice, 10, 20, 30, 10, true);
        delivery2 = std::make_shared<Delivery>(basePrice, id, true, shipper1, receiver1, locker);
        list = std::make_shared<List>(basePrice, true);
    }
};

BOOST_AUTO_TEST_SUITE(DeliveryTest)

    BOOST_FIXTURE_TEST_CASE(DeliveryParcelConstructorAnGettersConformance, DeliveryFixture)
    {
        BOOST_TEST(delivery1->getId() == id);
        BOOST_TEST(delivery1->getShipper() == shipper1);
        BOOST_TEST(delivery1->getReceiver() == receiver1);
        BOOST_TEST(delivery1->getStatus() == Ready_to_ship);
        BOOST_TEST(delivery1->getAPackage()->getInfo() == box->getInfo());
        BOOST_TEST(delivery1->getLocker() == locker);
    }

    BOOST_FIXTURE_TEST_CASE(DeliveryListConstructorAndGettersConformance, DeliveryFixture)
    {
        BOOST_TEST(delivery2->getId() == id);
        BOOST_TEST(delivery2->getShipper() == shipper1);
        BOOST_TEST(delivery2->getReceiver() == receiver1);
        BOOST_TEST(delivery2->getStatus() == Ready_to_ship);
        BOOST_TEST(delivery2->getAPackage()->getInfo() == list->getInfo());
        BOOST_TEST(delivery2->getLocker() == locker);
    }

    BOOST_FIXTURE_TEST_CASE(DeliverySetterConformance, DeliveryFixture)
    {
        BOOST_TEST(delivery1->getStatus() == Ready_to_ship);
        delivery1->setStatus(Ready_for_pickup);
        BOOST_TEST(delivery1->getStatus() == Ready_for_pickup);
        delivery1->setStatus(Received);
        BOOST_TEST(delivery1->getStatus() == Received);
    }

BOOST_AUTO_TEST_SUITE_END()