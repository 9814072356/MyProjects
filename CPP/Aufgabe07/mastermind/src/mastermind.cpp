/**
 * @file mastermind.cpp
 */

#include "mastermind.h"
#include <algorithm>

Mastermind::Mastermind(unsigned c, unsigned p, unsigned seed) : colors(c), positions(p), re(seed), solution(re, colors, positions), possibleCodes(Code::createAllPossibleCombinations(colors, positions))
{
}

// Mastermind::evaluate soll den
// übergebenen Code mit dem gesuchten Code solution evaluieren

Result Mastermind::evaluate(const Code &code) const
{
    return this->solution.evaluate(code);
}

// Die Methode Mastermind::considerResult soll aus possibleCodes alle Codes entfernen,
// die ausgeschlossen werden können, wenn eine Evaluierung von guess mit dem gesuchten Code zu dem Ergebnis result führt.
// Implementieren Sie diese Funktionalität!
// Hinweis:
// Ein Code c kann ausgeschlossen werden, wenn das Ergebnis der Evaluierung von c
// und guess zu einem anderen Ergebnis als result führt.

void Mastermind::considerResult(const Code &guess, const Result &result)
{
    for (int i = 0; i < int(this->possibleCodes.size()); i++)
    {
        if (guess.evaluate(this->possibleCodes.at(i)) != result)
        {
            this->possibleCodes.erase(this->possibleCodes.begin() + i);
        }
    }
}

// Mastermind::guess soll aus den noch möglichen Codes
// zufällig einen auswählen und diesen zurückgeben
Code Mastermind::guess()
{
    std::uniform_int_distribution<int> dist(0, int(this->possibleCodes.size()) - 1);
    int randomIndex = dist(this->re);
    auto result = this->possibleCodes.at(randomIndex);
    this->possibleCodes.erase(this->possibleCodes.begin() + randomIndex);
    return result;
}
