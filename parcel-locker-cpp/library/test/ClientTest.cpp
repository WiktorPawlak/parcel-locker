//
// Created by student on 26.05.2021.
//

#include <boost/test/unit_test.hpp>
#include "typedefs.h"
#include "model/Client.h"
#include "exceptions/ClientException.h"

namespace btt = boost::test_tools;

using namespace std;

struct ClientFixture
{
    ClientPtr c;

    ClientFixture()
    {
        c = std::make_shared<Client>("Maciej", "Nowak", "606123654");
    }
};

BOOST_AUTO_TEST_SUITE(ClientTest)

    BOOST_FIXTURE_TEST_CASE(sthConformance, ClientFixture)
    {
        BOOST_TEST(c->getFirstName() == "Maciej");
        BOOST_TEST(c->getLastName() == "Nowak");
        BOOST_TEST(c->getTelNumber() == "606123654");
    }

    BOOST_FIXTURE_TEST_CASE(shtConformance, ClientFixture)
    {
        c->setArchive(true);
        BOOST_TEST(c->isArchived() == true);
    }

    BOOST_AUTO_TEST_CASE(sthdCondition) {
        BOOST_REQUIRE_THROW(ClientPtr client = std::make_shared<Client>("", "a", "12346"), ClientException);
        BOOST_REQUIRE_THROW(ClientPtr client = std::make_shared<Client>("a", "", "123"), ClientException);
        BOOST_REQUIRE_THROW(ClientPtr client = std::make_shared<Client>("a", "a", ""), ClientException);

    }

BOOST_AUTO_TEST_SUITE_END()
