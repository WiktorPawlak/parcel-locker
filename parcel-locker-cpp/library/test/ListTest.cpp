//
// Created by student on 08.06.2021.
//

#include <boost/test/unit_test.hpp>
#include "typedefs.h"
#include "model/List.h"

namespace btt = boost::test_tools;

struct ListFixture
{
    PackagePtr prio;
    PackagePtr nonprio;
    float basePrice = 10;

    ListFixture()
    {
        prio = std::make_shared<List>(basePrice, true);
        nonprio = std::make_shared<List>(basePrice, false);
    }
};

BOOST_AUTO_TEST_SUITE(ListTest)

    BOOST_FIXTURE_TEST_CASE(PriorityListConformance, ListFixture)
    {
        BOOST_TEST(prio->getCost() == (basePrice/2)+3);
        BOOST_TEST(prio->getInfo() == "Priority letter cost: 8 basePrice: 10");
    }

    BOOST_FIXTURE_TEST_CASE(NonPriorityListConformance, ListFixture)
    {
        BOOST_TEST(nonprio->getCost() == (basePrice/2));
        BOOST_TEST(nonprio->getInfo() == "Registered letter cost: 5 basePrice: 10");
    }

BOOST_AUTO_TEST_SUITE_END()