/**
 * @file mastermind-test.cpp
 */
#include "mastermind-test.h"

#include <iostream>
#include <cassert>
#include <cmath>

void testEvaluate(const Code &c1, const Code &c2, unsigned int black, unsigned int white)
{
    Result result1 = c1.evaluate(c2);
    Result expected{black, white};
    if (result1 != expected)
        std::cerr << "Evaluate " << c1 << " with " << c2 << ": Expected " << expected << " but gotten " << result1 << "\n";
    Result result2 = c2.evaluate(c1);
    if (result1 != result2)
        std::cerr << "Evaluate " << c1 << " with " << c2 << ": Expected " << result1 << " but gotten " << result2 << "\n";
}

void testMastermind()
{
    // Test evaluation
    {
        testEvaluate(Code{1, 3, 1}, Code{1, 3, 1}, 3, 0);
        testEvaluate(Code{1, 3, 1}, Code{1, 2, 0}, 1, 0);
        testEvaluate(Code{1, 3, 1}, Code{0, 2, 1}, 1, 0);
        testEvaluate(Code{1, 3, 1, 2}, Code{0, 2, 1, 2}, 2, 0);
        testEvaluate(Code{1, 3, 1, 2}, Code{0, 1, 1, 2}, 2, 1);
        testEvaluate(Code{1, 3, 1, 2}, Code{3, 1, 1, 2}, 2, 2);
        testEvaluate(Code{1, 3, 1, 2}, Code{1, 3, 2, 1}, 2, 2);
        testEvaluate(Code{1, 1, 1, 0}, Code{0, 0, 0, 1}, 0, 2);
        testEvaluate(Code{0, 1, 1, 0}, Code{2, 0, 0, 1}, 0, 3);
        testEvaluate(Code{0, 1, 2, 3}, Code{3, 2, 1, 0}, 0, 4);
    }

    // Test possible combination creation:
    {
        Mastermind game1(2, 3);
        if (game1.getPossibleCodeCount() != static_cast<unsigned>(std::pow(2, 3)))
            std::cerr << "Evaluating CodeCount: "
                      << "Expected " << static_cast<unsigned>(std::pow(2, 3)) << " but gotten " << game1.getPossibleCodeCount() << "\n";

        Mastermind game2(6, 4);
        if (game2.getPossibleCodeCount() != static_cast<unsigned>(std::pow(6, 4)))
            std::cerr << "Evaluating CodeCount: "
                      << "Expected " << static_cast<unsigned>(std::pow(6, 4)) << " but gotten " << game2.getPossibleCodeCount() << "\n";
    }
}
