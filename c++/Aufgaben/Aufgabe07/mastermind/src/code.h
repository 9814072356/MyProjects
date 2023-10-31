#ifndef CODE_H
#define CODE_H

/**
 * @file code.h
 */

#include <vector>
#include <random>
#include <iostream>
#include "result.h"
/**
 * A Code represents a combination of colored pins.
 * Colors are number digits beginning with 0.
 */
class Code
{
private:
    std::vector<unsigned> pins;

public:
    Code() = default;
    /** Create a random code */
    Code(std::default_random_engine &re, unsigned colors, unsigned positions);
    /** Create a code with the given pins*/
    Code(std::vector<unsigned> p) : pins(std::move(p)) {}
    Code(const std::initializer_list<unsigned> &i) : pins(i) {}
    /** Evaluate how good the passed in code matches this*/
    Result evaluate(const Code &other) const;

    /** Generate a vector containing all possible codes with the given number of colors and positions
        e.g. for colors=2 and positions=3 the result would be {{0,0,0},{0,0,1},{0,1,0},{0,1,1},{1,0,0},{1,0,1},{1,1,0},{1,1,1}}
        The order of the result is undefined
     */
    static std::vector<Code> createAllPossibleCombinations(unsigned colors, unsigned positions);
    friend std::ostream &operator<<(std::ostream &os, const Code &code);
    friend std::istream &operator>>(std::istream &is, Code &code);
};

#endif // CODE_H
