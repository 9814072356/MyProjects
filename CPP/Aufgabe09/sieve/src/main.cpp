/**
 * Main file. Should not be modified.
 * @file main.cpp
 */
 
#include <vector>
#include <iostream>
#include <random>
#include <algorithm>
#include "sieve.h"

int main()
{
    std::vector<std::size_t> numbers;
    for(std::size_t i = 2; i<=100; ++i)
    {
        numbers.push_back(i);
    }
    
    std::random_device rd;
    std::mt19937 g(rd());
    std::shuffle(numbers.begin(), numbers.end(), g);
    
    Sieve sieve(numbers);
    sieve.reduceToPrimes();
    std::cout << "\nPrimes:\n";
    sieve.printNumbers(std::cout);
        
    sieve.resetToSize(50);    
    std::cout << "Numbers 2-50 in reverse order:\n";
    sieve.printNumbers(std::cout);
    
    sieve.reduceToPrimes();
    sieve.groupNumbers(20, 40);
    std::cout << "Primes after Grouping:\n";    
    sieve.printNumbers(std::cout);
    
    return 0;
}


