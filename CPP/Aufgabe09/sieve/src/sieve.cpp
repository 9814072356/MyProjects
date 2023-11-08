#include "sieve.h"
#include <algorithm>
#include <vector>
#include <iostream>

Sieve::Sieve(std::vector<std::size_t> &numbers) : numbers(numbers) {}

bool mod2(int currentNumber)
{
    bool result = false;
    int threshold = currentNumber / 2;
    int primeFlag = 0;
    for (int i = 2; i <= threshold; i++)
    {
        if (currentNumber % i == 0)
        {
            primeFlag = 1;
            break;
        }
    }
    if (primeFlag == 1)
        result = true;
    return result;
}

void Sieve::reduceToPrimes()
{
    // TODO: Implement reduceToPrimes
    sort(this->numbers.begin(), this->numbers.end());
    this->numbers.erase(std::remove_if(this->numbers.begin(), this->numbers.end(), mod2), this->numbers.end());
}

// Implementieren Sie die Methode resetToSize.
// Sie soll zunächst alle Zahlen, die größer als der übergebene Wert sind, aus dem numbers-Vektor löschen.
// Danach sollen ihm alle Nummern zwischen 2 und der übergebenen Größe hinzugefügt werden, die er nicht bereits enthält.
// Nach dem Ablauf dieser Methode sollen die Zahlen im numbers-Vektor außerdem in absteigender Reihenfolge vorliegen.
// Hinweise:
// • Der numbers-Vektor soll nicht durch einen neuen Vektor ersetzt, oder komplett geleert und dann neu befüllt werden.
// • Sortierreihenfolgen von Sortieralgorithmen der Sandardbibliothek können durch Lambdafunktionen beeinflusst werden.

void Sieve::resetToSize(std::size_t size)
{
    // TODO: Implement resetToSize
    this->numbers.erase(std::remove_if(this->numbers.begin(), this->numbers.end(), [=](size_t x) { return x > size; }), this->numbers.end());
    for (size_t i = 0; i < size; i++)
    {
        if (i + 1 < this->numbers.size())
        {
            if ((this->numbers.at(i) - this->numbers.at(i + 1)) > abs(1))
            {
                for (size_t j = this->numbers.at(i); j < this->numbers.at(i + 1); j++)
                {
                    if (j != this->numbers.at(i) && j != this->numbers.at(i + 1))
                    {
                        this->numbers.insert(this->numbers.begin() + i, j);
                        i++;
                    }
                }
            }
        }
    }
    sort(this->numbers.begin(), this->numbers.end(), std::greater<size_t>());
    for (size_t i = 0; i <= size; i++)
    {
        if (this->numbers.front() <= size && i > this->numbers.front())
        {
            this->numbers.insert(this->numbers.begin(), i);
        }
    }
    sort(this->numbers.begin(), this->numbers.end(), std::greater<size_t>());
}

// Implementieren Sie die Methode groupNumbers.
// Diese soll alle Zahlen zwischen den übergebenen Grenzen(die bereits im Vektor vorhanden sind) an den Beginn des numbers-Vektors bewegen.
// Die aufsteigende Reihenfolge soll dabei beibehalten werden.
// Hinweis:
// • Diese Methode kann mit genau einem Aufruf eines Standard-Algorithmus gelöst werden. Es bietet sich hierzu der Algorithmus std::stable_partition an.

void Sieve::groupNumbers(std::size_t lowerBound, std::size_t upperBound)
{
    // TODO: Implement groupNumbers
    std::stable_partition(this->numbers.begin(), this->numbers.end(), [=](size_t current) { return current >= lowerBound && current <= upperBound; });

    // int counter = 0;
    // for (size_t i = 0; i < this->numbers.size(); i++)
    // {
    //     if (this->numbers.at(i) >= lowerBound && this->numbers.at(i) <= upperBound)
    //     {
    //         this->numbers.insert(this->numbers.begin() + counter, this->numbers.at(i));
    //         this->numbers.erase(this->numbers.begin() + i + 1);
    //         counter++;
    //     }
    // }
}

void Sieve::printNumbers(std::ostream &os)
{
    os << "{";
    for (std::size_t i = 0; i < this->numbers.size() - 1; ++i)
    {
        os << this->numbers[i] << ", ";
    }
    os << this->numbers[this->numbers.size() - 1] << "}\n\n"
       << std::flush;
}
