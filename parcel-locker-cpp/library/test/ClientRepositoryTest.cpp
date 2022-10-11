//
// Created by student on 10.06.2021.
//

#include <boost/test/unit_test.hpp>
#include "typedefs.h"
#include "model/Client.h"
#include "repositories/ClientRepository.h"

namespace btt = boost::test_tools;

using namespace std;

struct ClientRepositoryFixture
{
    ClientRepository clientRepository;
    ClientPtr c;
    ClientPtr c1;

    ClientRepositoryFixture()
    {
        c = std::make_shared<Client>("Maciej", "Nowak", "606123654");
        c1 = std::make_shared<Client>("Maciej", "Kowal", "606123655");
    }
};

BOOST_AUTO_TEST_SUITE(ClientRepositoryTest)

    BOOST_FIXTURE_TEST_CASE(FindByTelNumberConformance, ClientRepositoryFixture)
    {
        clientRepository.add(c);
        clientRepository.add(c1);
        BOOST_TEST(clientRepository.findByTelNumber("606123654") == c);
    }

BOOST_AUTO_TEST_SUITE_END()