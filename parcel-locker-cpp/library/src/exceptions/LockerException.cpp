//
// Created by student on 06.06.2021.
//

#include "../../include/exceptions/LockerException.h"

LockerException::LockerException(const std::string &arg) : logic_error(arg) {}
