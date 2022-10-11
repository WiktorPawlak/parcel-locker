//
// Created by student on 10.06.2021.
//

#include "exceptions/DeliveryManagerException.h"

DeliveryManagerException::DeliveryManagerException(const std::string &arg) : logic_error(arg) {}
