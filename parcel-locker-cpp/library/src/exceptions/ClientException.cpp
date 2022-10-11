//
// Created by student on 06.06.2021.
//

#include "../../include/exceptions/ClientException.h"

ClientException::ClientException(const std::string &arg) : logic_error(arg) {}
