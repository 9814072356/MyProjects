/**
 * @file parser.cpp
 */
#include "parser.h"
#include <sstream>
#include <bits/stdc++.h>

std::vector<std::string> split(std::istream &is, char delim)
{
    std::vector<std::string> result;

    // TODO: Implement here
    std::string token;
    // std::cout << "splittedStrings: ";
    while (std::getline(is, token, delim))
    {
        if (token != " ")
        {
            // std::cout << token;
            result.push_back(token);
        }
    }
    return result;
}

std::vector<IndexedString> parse(std::istream &is)
{
    // std::vector<std::tuple<size_t /*index*/, std::string /*content*/>> strings;
    std::vector<IndexedString> strings;

    // TODO: Implement here
    std::vector<std::string> splittedStrings = split(is, ';');

    for (unsigned int i = 0; i < splittedStrings.size(); i++)
    {
        if (!(splittedStrings.at(i).empty()) && std::isdigit(splittedStrings.at(i)[0]))
        {
            strings.emplace_back(std::stoi(splittedStrings.at(i)), splittedStrings.at(i + 1));
            // std::cout << "Element: " << std::get<0>(strings.at(i)) << ". " << std::get<1>(strings.at(i)) << "\n";
        }
    }
    std::sort(strings.begin(), strings.end());
    return strings;
}

void writeSentence(std::ostream &os, const std::vector<IndexedString> &strings)
{

    // TODO: Implement here
    for (unsigned int i = 0; i < strings.size(); i++)
    {
        // std::cout << "Element: " << std::get<0>(strings.at(i)) << ". " << std::get<1>(strings.at(i)) << "\n";
        os << std::get<1>(strings.at(i));
    }
}
