/**
 * @file mapcreator.cpp
 */

#include "mapcreator.h"
#include <fstream>
#include <utility>
#include <string>
#include <vector>
#include <iostream>
#include <map>
#include <random>
#include <tuple>

std::random_device rd;
std::mt19937 re(rd());

std::tuple<std::string, std::map<char, char>> createCipherMap(std::istream &is)
{
    std::map<char, char> cipher;
    std::string str;
    char c;
    while (true)
    {
        c = static_cast<char>(is.get());
        if (is.eof())
        {
            break;
        }
        std::uniform_int_distribution<int> dist(0, 2);
        char chardistance = static_cast<char>(dist(re));
        if (cipher.find(static_cast<char>(c - chardistance)) == cipher.end())
            cipher.emplace(c - chardistance, chardistance);

        // str += static_cast<char>(c);
        str += static_cast<char>(c - chardistance);
    }
    return std::make_tuple(str, cipher);
}
