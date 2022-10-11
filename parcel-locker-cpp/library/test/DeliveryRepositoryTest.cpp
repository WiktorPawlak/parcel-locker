//
// Created by student on 10.06.2021.
//

#include <boost/test/unit_test.hpp>
#include "typedefs.h"
#include "model/Client.h"
#include "model/Locker.h"
#include "model/Delivery.h"
#include "repositories/DeliveryRepository.h"
#include  <boost/uuid/random_generator.hpp>

namespace btt = boost::test_tools;

using namespace std;

struct DeliveryRepositoryFixture
{
    DeliveryRepository deliveryRepository;
    ClientPtr c;
    ClientPtr c1;
    DeliveryPtr d1;
    DeliveryPtr d2;
    LockerPtr l1;
    boost::uuids::uuid id;
    boost::uuids::uuid id1;

    DeliveryRepositoryFixture()
    {
        boost::uuids::random_generator gen;
        id = gen();
        id1 = gen();
        l1 = std::make_shared<Locker>(10);
        c = std::make_shared<Client>("Maciej", "Nowak", "606123654");
        c1 = std::make_shared<Client>("Maciej", "Kowal", "606123655");
        d1 = std::make_shared<Delivery>(10, id, true, c, c1, l1);
        d2 = std::make_shared<Delivery>(10, id1, true, c1, c, l1);
    }
};

BOOST_AUTO_TEST_SUITE(DeliveryRepositoryTest)

    BOOST_FIXTURE_TEST_CASE(FindByIdConformance, DeliveryRepositoryFixture)
    {
        deliveryRepository.add(d1);
        deliveryRepository.add(d2);
        BOOST_TEST(deliveryRepository.findById(id) == d1);
    }

BOOST_AUTO_TEST_SUITE_END()