//
// Created by student on 10.06.2021.
//

#include <boost/test/unit_test.hpp>
#include "typedefs.h"
#include "model/Client.h"
#include "managers/ClientManager.h"

namespace btt = boost::test_tools;

struct ClientManagerFixture{
    ClientManagerPtr clientManager;

    ClientManagerFixture()
    {
        clientManager = std::make_shared<ClientManager>();
    }
};

BOOST_AUTO_TEST_SUITE(ClientManagerTest)

    BOOST_FIXTURE_TEST_CASE(RegisteringClientCorformance, ClientManagerFixture)
    {
        clientManager->registerClient("Bartosh","Byniowski","123456789");
        BOOST_TEST(clientManager->getClient("123456789")->getTelNumber() == "123456789");
    }

    BOOST_FIXTURE_TEST_CASE(UnRegisteringClientCorformance, ClientManagerFixture)
    {
        clientManager->registerClient("Bartosh","Byniowski","123456789");
        BOOST_TEST(clientManager->getClient("123456789")->isArchived() == false);
        clientManager->unregisterClient(clientManager->getClient("123456789"));
        BOOST_TEST(clientManager->getClient("123456789")->isArchived() == true);
    }


    BOOST_FIXTURE_TEST_CASE(getClientConformance, ClientManagerFixture)
    {
        clientManager->registerClient("Bartosh","Byniowski","123456789");
        BOOST_TEST(clientManager->getClient("123456789")->getFirstName() == "Bartosh");
        BOOST_TEST(clientManager->getClient("987654321") == nullptr);
    }

    BOOST_FIXTURE_TEST_CASE(exceptionConformance, ClientManagerFixture)
    {
        clientManager->registerClient("Bartosh","Byniowski","123456789");
        BOOST_REQUIRE_THROW(clientManager->getClient(nullptr), std::logic_error);
        BOOST_REQUIRE_THROW(clientManager->unregisterClient(nullptr), std::logic_error);
    }



BOOST_AUTO_TEST_SUITE_END()
