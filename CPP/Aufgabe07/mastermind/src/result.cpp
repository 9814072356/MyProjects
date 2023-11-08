/**
 * @file result.cpp
 */

#include "result.h"

std::ostream& operator<<(std::ostream& os, const Result& r)
{
    os << r.blackPins << " black and " << r.whitePins << " white";
    return os;
}

bool operator==(const Result& a, const Result& b)
{
    return (a.blackPins == b.blackPins) && (a.whitePins == b.whitePins);
}
bool operator!=(const Result& a, const Result& b)
{
    return !(a == b);
}

