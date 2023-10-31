#ifndef MASTERMIND_H
#define MASTERMIND_H

/**
 * @file mastermind.h
 */

#include "result.h"
#include "code.h"
#include <vector>
#include <tuple>
#include <random>
#include <iostream>

class Mastermind
{
private:
    unsigned int               colors;
    unsigned int               positions;
    std::default_random_engine re;
    Code                       solution;
    std::vector<Code>          possibleCodes;

public:
    Mastermind(unsigned int colorCount, unsigned int positionCount, unsigned int seed = 0);
    /** Guess a solution based on previous information */
    Code guess();
    /** Evaluates black and white pins for a given code */
    Result evaluate(const Code& code) const;

    /** Erase all codes which contradict Result result for Code guess from possibleCodes*/
    void considerResult(const Code& guess, const Result& result);

    size_t getPossibleCodeCount()         const { return this->possibleCodes.size(); }

    bool   isSolved(const Result& result) const { return result == Result{positions, 0}; }
};
#endif

