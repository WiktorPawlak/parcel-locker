//
// Created by student on 10.06.2021.
//

#include <boost/test/unit_test.hpp>
#include "typedefs.h"
#include "model/Client.h"
#include "repositories/Repository.h"

namespace btt = boost::test_tools;

using namespace std;

struct RepositoryFixture
{
    Repository<Client> clientRepository;
    ClientPtr c;
    ClientPtr c1;

    RepositoryFixture()
    {
        c = std::make_shared<Client>("Maciej", "Nowak", "606123654");
        c1 = std::make_shared<Client>("Maciej", "Kowal", "606123654");
    }
};

BOOST_AUTO_TEST_SUITE(ClientTest)

    BOOST_FIXTURE_TEST_CASE(AddAndGetConformance, RepositoryFixture)
    {
        clientRepository.add(c);
        BOOST_TEST(clientRepository.get(0) == c);
    }

    BOOST_FIXTURE_TEST_CASE(RemoveConformance, RepositoryFixture)
    {
        clientRepository.add(c);
        clientRepository.remove(c);
        BOOST_TEST(clientRepository.size() == 0);
    }

    BOOST_FIXTURE_TEST_CASE(ReportConformance, RepositoryFixture)
    {
        clientRepository.add(c);
        BOOST_TEST(clientRepository.report() == "Maciej Nowak phone: 606123654 Actual");
    }

    BOOST_FIXTURE_TEST_CASE(SizeConformance, RepositoryFixture)
    {
        clientRepository.add(c);
        clientRepository.add(c);
        clientRepository.add(c);
        BOOST_TEST(clientRepository.size() == 3);
    }

    BOOST_FIXTURE_TEST_CASE(findByConformance, RepositoryFixture)
    {
        clientRepository.add(c);
        clientRepository.add(c1);
        clientRepository.add(c);
        BOOST_TEST(clientRepository.findBy([](const ClientPtr& client){ return client->getLastName() == "Kowal"; }).at(0) == c1);
    }

    BOOST_FIXTURE_TEST_CASE(findAllConformance, RepositoryFixture)
    {
        clientRepository.add(c);
        clientRepository.add(c1);
        clientRepository.add(c);
        BOOST_TEST(clientRepository.findAll().size() == 3);
    }

BOOST_AUTO_TEST_SUITE_END()
