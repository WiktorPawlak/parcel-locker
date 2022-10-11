//
// Created by student on 08.06.2021.
//

#include <boost/test/unit_test.hpp>
#include "typedefs.h"
#include "model/DepositBox.h"

namespace btt = boost::test_tools;

using namespace std;

struct DepositBoxFixture
{
    DepositBoxPtr d;

    DepositBoxFixture()
    {
        d = std::make_shared<DepositBox>("123");
    }
};

BOOST_AUTO_TEST_SUITE(DepositBoxTest)

    BOOST_FIXTURE_TEST_CASE(ConstructorAndGetterConformance, DepositBoxFixture)
    {
        BOOST_TEST(d->getId() == "123");
    }

    BOOST_FIXTURE_TEST_CASE(SettersAndGettersConformance, DepositBoxFixture)
    {
        d->setAccessCode("2k21");
        d->setTelNumber("604502304");
        d->setStatus(false);
        BOOST_TEST(d->getAccessCode() == "2k21");
        BOOST_TEST(d->getTelNumber() == "604502304");
        BOOST_TEST(d->getStatus() == false);
    }

    BOOST_FIXTURE_TEST_CASE(AccessConformance, DepositBoxFixture)
    {
        d->setAccessCode("k0z4k");
        d->setTelNumber("123456789");
        d->Access("k0z4k","123456789");
        BOOST_TEST(d->getStatus() == false);
        BOOST_TEST(d->getAccessCode() == "");
        BOOST_TEST(d->getTelNumber() == "");
    }


BOOST_AUTO_TEST_SUITE_END()