/**
 * @file decipherer.cpp
 */

#include "decipherer.h"
#include <string>
#include <iostream>
#include <map>
#include <tuple>
#include <utility>

std::string decipherMessage(const std::string &codedMessage, const std::map<char, char> &cipher)
{
    std::string result;

    // TODO: Implement here!
    std::cout << "\nencoded string: \n"
              << codedMessage;

    for (char const &currentChar : codedMessage)
    {
        result += currentChar + cipher.find(currentChar)->second;
    }

    std::cout << "\ndecoded string: \n"
              << result;

    return result;
}

std::string removeErrors(const std::string &messageWithErrors)
{

    // TODO: Implement here (find wrong character)!
    std::cout << "--------------------------------------------\n\n"
              << "error message\n"
              << messageWithErrors;

    std::map<char, int> charNAmount;

    // std::cout << "found chars and corresponding amount:\n";
    for (char const &currentChar : messageWithErrors)
    {
        if (charNAmount.find(currentChar) == charNAmount.end() && currentChar != '\n')
        {
            charNAmount.emplace(currentChar, 1);
        }
        else
        {
            charNAmount.find(currentChar)->second++;
        }
    }

    // for (auto elem : charNAmount)
    // {
    //     std::cout << elem.first << ": " << elem.second << "\n";
    // }

    std::string result;

    // TODO: Implement here (correct message)!
    char leastOccuringChar;
    int smallestValue = INT32_MAX;
    for (auto elem : charNAmount)
    {
        if (elem.second < smallestValue)
        {
            smallestValue = elem.second;
            leastOccuringChar = elem.first;
        }
    }
    std::cout << "corrected message\n";

    for (char const &currentChar : messageWithErrors)
    {
        if (currentChar == leastOccuringChar)
        {
            result += ' ';
        }
        else
        {
            result += currentChar;
        }
    }

    return result;
}
