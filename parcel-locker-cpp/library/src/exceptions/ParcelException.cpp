//
// Created by student on 06.06.2021.
//

#include "../../include/exceptions/ParcelException.h"

ParcelException::ParcelException(const std::string &arg) : logic_error(arg) {}
