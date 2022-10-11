//
// Created by student on 08.06.2021.
//

#include <boost/test/unit_test.hpp>
#include "typedefs.h"
#include "model/Locker.h"
#include "model/DepositBox.h"
#include "exceptions/LockerException.h"

namespace btt = boost::test_tools;

using namespace std;

struct LockerFixture
{
    LockerPtr l;
    boost::uuids::uuid deliveryId;

    LockerFixture()
    {
        boost::uuids::random_generator gen;
        deliveryId = gen();
        l = std::make_shared<Locker>(10);
    }
};

BOOST_AUTO_TEST_SUITE(LockerTest)

    BOOST_FIXTURE_TEST_CASE(ConstructorConformance, LockerFixture)
    {
        BOOST_TEST(l->findAllEmpty() == 10);
    }

    BOOST_FIXTURE_TEST_CASE(PuttingInConformance, LockerFixture)
    {
        l->putIn(deliveryId,"123456789","k0z4k");
        BOOST_TEST(l->getDepositBox("0")->getTelNumber()=="123456789");
        BOOST_TEST(l->getDepositBox("0")->getAccessCode()=="k0z4k");
        BOOST_TEST(l->getDepositBox("0")->getStatus()==true);
    }

    BOOST_FIXTURE_TEST_CASE(TakingOutConformance, LockerFixture)
    {
        l->putIn(deliveryId,"123456789","k0z4k");
        BOOST_TEST(l->getDepositBox("0")->getTelNumber()=="123456789");
        BOOST_TEST(l->getDepositBox("0")->getAccessCode()=="k0z4k");
        BOOST_TEST(l->getDepositBox("0")->getStatus()==true);
        BOOST_TEST(l->findAllEmpty() == 9);
        l->takeOut("123456789", "k0z4k");
        BOOST_TEST(l->getDepositBox("0")->getTelNumber()=="");
        BOOST_TEST(l->getDepositBox("0")->getAccessCode()=="");
        BOOST_TEST(l->getDepositBox("0")->getStatus()==false);
        BOOST_TEST(l->findAllEmpty() == 10);
    }

    BOOST_FIXTURE_TEST_CASE(GetInfoConformance, LockerFixture)
    {
        BOOST_TEST(l->getInfo() == "Empty boxes: 10\n0\n1\n2\n3\n4\n5\n6\n7\n8\n9\n");
    }

    BOOST_FIXTURE_TEST_CASE(exceptionConformance, LockerFixture)
    {
        LockerPtr l1 = std::make_shared<Locker>(1);
        l1->putIn(deliveryId,"123456789","k0z4k");
        BOOST_REQUIRE_THROW(l1->putIn(deliveryId,"123", "123"), LockerException);
    }


BOOST_AUTO_TEST_SUITE_END()