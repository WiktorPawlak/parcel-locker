//
// Created by student on 10.06.2021.
//

#include "exceptions/ClientManagerException.h"

ClientManagerException::ClientManagerException(const std::string &arg) : logic_error(arg) {}
