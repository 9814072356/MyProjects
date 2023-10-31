#ifndef RESULT_H
#define RESULT_H

/**
 * @file result.h
 */

#include <iostream>


/**
 * A Result represents an evaluation of the comparison of two codes.
 */
class Result
{
private:
    /** Number of positions where pins have the same color */
    unsigned blackPins;
    /** Number of pins of one code that arise in other code at different position */
    unsigned whitePins;
public:
    Result() : Result(0, 0) {}
    Result(unsigned b, unsigned w) : blackPins(b), whitePins(w) {}
    friend std::ostream& operator<<(std::ostream& os, const Result& code);
    friend bool operator==(const Result& a, const Result& b);
    friend bool operator!=(const Result& a, const Result& b);
};

#endif // RESULT_H

