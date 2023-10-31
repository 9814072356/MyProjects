/**
 * @file code.cpp
 */

#include "code.h"
#include <deque>
#include <cassert>
#include <string>
#include <stdexcept>
#include <bits/stdc++.h>
#include <algorithm>
#include <string>

std::ostream &operator<<(std::ostream &os, const Code &code)
{
    for (auto iter = code.pins.begin(); iter != code.pins.end(); ++iter)
        os << *iter;
    return os;
}
std::istream &operator>>(std::istream &is, Code &code)
{
    std::string s;
    std::cin >> s;
    for (size_t i = 0; i < s.size(); ++i)
    {
        if (!(s[i] >= '0' && s[i] <= '9'))
            throw std::runtime_error("input must only contain digits");
        code.pins.push_back(static_cast<unsigned>(s[i] - '0'));
    }
    return is;
}

Code::Code(std::default_random_engine &re, unsigned colors, unsigned positions)
{
    this->pins.reserve(positions);
    std::uniform_int_distribution<unsigned> dist(0, colors - 1);
    for (size_t i = 0; i < positions; ++i)
        this->pins.push_back(dist(re));
}
Result Code::evaluate(const Code &other) const
{
    // TODO: Insert code here!
    int blackNumber = 0;
    int whiteNumber = 0;
    if (other.pins == this->pins)
    {
        return Result(int(other.pins.size()), 0);
    }
    else
    {
        for (int i = 0; i < int(other.pins.size()); i++)
        {
            if (other.pins.at(i) == this->pins.at(i))
            {
                blackNumber++;
            }
            else
            {
                if (std::count(this->pins.begin(), this->pins.end(), other.pins.at(i)) == std::count(other.pins.begin(), other.pins.end(), other.pins.at(i)))
                {
                    whiteNumber++;
                }
                else if ((std::count(other.pins.begin(), other.pins.end(), other.pins.at(i)) == 3) && other.pins.at(i) != other.pins.at(i + 1))
                {
                    whiteNumber += 2;
                }
            }
        }
    }
    return Result(blackNumber, whiteNumber);
}

void print(const std::vector<int> &v)
{
    for (int e : v)
    {
        std::cout << " " << e;
    }
    std::cout << std::endl;
}

unsigned int concat(int i, int j, int k, int l)
{
    std::string resultString;
    if (l != 0)
    {
        std::string sI = std::to_string(i);
        std::string sJ = std::to_string(j);
        std::string sK = std::to_string(k);
        std::string sL = std::to_string(l);
        resultString = sI + sJ + sK + sL;
    }
    else
    {
        std::string sI = std::to_string(i);
        std::string sJ = std::to_string(j);
        std::string sK = std::to_string(k);
        resultString = sI + sJ + sK;
    }

    unsigned int result = stoi(resultString);
    return result;
}

// Implementieren Sie die Methode Code::createAllPossibleCombinations!
// Diese Funktion soll einen vector mit allen möglichen Codes der entsprechenden Länge und Anzahl Faben zurückgeben.
// amountPossibleColors = 2; codeLength = 3
//[1,1,1],[1,1,2],[1,2,1],[1,2,2],[2,1,1],[2,1,2],[2,2,1],[2,2,2]
std::vector<Code> Code::createAllPossibleCombinations(unsigned amountPossibleColors, unsigned codeLength)
{
    // TODO: Insert code here!
    // std::cout << codeLength << std::endl;

    std::vector<Code> result;
    int l = 1;
    for (int i = 1; i <= int(amountPossibleColors); i++)
    {
        for (int j = 1; j <= int(amountPossibleColors); j++)
        {
            for (int k = 1; k <= int(amountPossibleColors); k++)
            {
                unsigned int currentInt = concat(i, j, k, 0);
                if (codeLength == 4)
                {
                    for (l = 1; l <= int(amountPossibleColors); l++)
                    {
                        currentInt = concat(i, j, k, l);
                        std::vector<unsigned> currentVector{currentInt};
                        Code currentCode(currentVector);
                        result.emplace_back(currentCode);
                    }
                }
                else
                {
                    std::vector<unsigned> currentVector{currentInt};
                    Code currentCode(currentVector);
                    result.emplace_back(currentCode);
                }
            }
        }
    }
    return result;
}
