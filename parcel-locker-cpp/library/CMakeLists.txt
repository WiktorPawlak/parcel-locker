cmake_minimum_required(VERSION 3.4)
project(Library)

set(SOURCE_FILES
        src/model/Client.cpp
        include/model/Client.h
        src/model/Delivery.cpp
        include/model/Delivery.h include/typedefs.h src/model/Package.cpp include/model/Package.h src/model/List.cpp include/model/List.h src/model/Parcel.cpp include/model/Parcel.h include/model/Locker.h src/model/Locker.cpp include/model/DepositBox.h src/model/DepositBox.cpp
        include/exceptions/ClientException.h include/exceptions/LockerException.h include/exceptions/ParcelException.h
        src/exceptions/ClientException.cpp src/exceptions/LockerException.cpp src/exceptions/ParcelException.cpp include/repositories/Repository.h src/managers/DeliveryManager.cpp include/managers/DeliveryManager.h include/managers/ClientManager.h src/managers/ClientManager.cpp src/repositories/ClientRepository.cpp include/repositories/ClientRepository.h src/repositories/DeliveryRepository.cpp include/repositories/DeliveryRepository.h src/exceptions/ClientManagerException.cpp include/exceptions/ClientManagerException.h src/exceptions/DeliveryManagerException.cpp include/exceptions/DeliveryManagerException.h)

#Utwórz bibliotekę typu STATIC, SHARED albo MODULE ze wskazanych źródeł
add_library(Library ${SOURCE_FILES})

target_include_directories(Library PUBLIC include)

enable_testing() # dodane wcześniej w punkcie 2
find_package (Boost 1.60.0 COMPONENTS "unit_test_framework" REQUIRED)

include_directories(
        ${CMAKE_CURRENT_SOURCE_DIR}/include
        ${Boost_INCLUDE_DIRS}
)

set(SOURCE_TEST_FILES
        test/ClientTest.cpp test/master.cpp src/model/DepositBox.cpp test/ParcelTest.cpp test/DepositBoxTest.cpp test/LockerTest.cpp test/ListTest.cpp test/DeliveryTest.cpp test/DeliveryManagerTest.cpp test/ClientManagerTest.cpp test/RepositoryTest.cpp test/ClientRepositoryTest.cpp test/DeliveryRepositoryTest.cpp) # tu w przyszłości będą dodawane pliki źródłowe testów


add_executable (LibraryTest ${SOURCE_TEST_FILES})

#dołącz bibliotekę z testowanymi klasami oraz bibliotekę boost.test do programu testów
target_link_libraries (LibraryTest
        Library
        ${Boost_UNIT_TEST_FRAMEWORK_LIBRARY}
        )

#dodaj test TestBiblioteki1. Instrukcji cmake add_test może być wiele.
#Dodatkowe parametry wpływają na szczegółowość generowanego raportu. Standardowo znajduje się on w Testing/Temporary.
add_test(LibraryTest1 LibraryTest
        --report_level=detailed
        --log_level=all
        --color_output=yes
        )

add_custom_target(check ${CMAKE_COMMAND} -E env CTEST_OUTPUT_ON_FAILURE=1 BOOST_TEST_LOG_LEVEL=all
        ${CMAKE_CTEST_COMMAND} -C $<CONFIGURATION> --verbose
        WORKING_DIRECTORY ${CMAKE_BINARY_DIR})