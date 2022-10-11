//
// Created by student on 06.06.2021.
//

#include <boost/test/unit_test.hpp>
#include "typedefs.h"
#include "model/Parcel.h"
#include "exceptions/ParcelException.h"

namespace btt = boost::test_tools;

struct ParcelFixture
{
    PackagePtr p;

    ParcelFixture()
    {
        p = std::make_shared<Parcel>(10, 1, 2, 3, 4, true);
    }
};

BOOST_AUTO_TEST_SUITE(ParcelTest)

    BOOST_FIXTURE_TEST_CASE(ConstructorAndGettersConformance, ParcelFixture)
    {
        BOOST_TEST(p->getCost() == 10);
        BOOST_TEST(p->getInfo() == "Parcel 1x2x3 4kg cost: 10 basePrice: 10");
    }

    BOOST_AUTO_TEST_CASE(constructorExceptionCondition) {
        BOOST_REQUIRE_THROW(PackagePtr p = std::make_shared<Parcel>(10, 0, 2, 3, 4, true), ParcelException);
        BOOST_REQUIRE_THROW(PackagePtr p = std::make_shared<Parcel>(10, 1, 0, 3, 4, true), ParcelException);
        BOOST_REQUIRE_THROW(PackagePtr p = std::make_shared<Parcel>(10, 1, 2, 0, 4, true), ParcelException);
        BOOST_REQUIRE_THROW(PackagePtr p = std::make_shared<Parcel>(10 ,1, 2, 3, 0, true), ParcelException);
        BOOST_REQUIRE_THROW(PackagePtr p = std::make_shared<Parcel>(10, 50, 2, 3, 4, true), ParcelException);
        BOOST_REQUIRE_THROW(PackagePtr p = std::make_shared<Parcel>(10, 1, 50, 3, 4, true), ParcelException);
        BOOST_REQUIRE_THROW(PackagePtr p = std::make_shared<Parcel>(10, 1, 2, 50, 4, true), ParcelException);
        BOOST_REQUIRE_THROW(PackagePtr p = std::make_shared<Parcel>(10, 1, 2, 3, 50, true), ParcelException);
    }

BOOST_AUTO_TEST_SUITE_END()