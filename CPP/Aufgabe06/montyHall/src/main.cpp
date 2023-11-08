/**
 * @file main.cpp
 */

#include <iostream>
#include <random>
#include <cassert>
#include <string>
#include <stdexcept>
#include "show.h"

/**
 * Runs one Monty Hall game as an experiment.
 * @return True if remaining at first decision results in a success
 */
bool montyHallExperiment()
{
    static std::random_device rd;
    static std::default_random_engine re(rd());

    // TODO: Insert code here
    std::uniform_int_distribution<int> randomDist(1, 3);
    Show expermientalShow;
    return expermientalShow.finalResult(randomDist(re));
}

// ##### b.)
// Warum sind std::random_device rd und std::default_random_engine re static?
// --> static sorgt dafür, dass rd und re klassenunabhängig abrufbar und nicht veränderbar sind.

/**
 * Tries the Monty Hall experiment n times.
 * @return Ratio for success if remaining at first decision
 */
double montyHallExploration(int n)
{
    // TODO: Insert code here
    double result, winCounter;
    for (int i = 0; i <= n; i++)
    {
        if (montyHallExperiment())
        {
            winCounter++;
        }
    }
    result = winCounter / n;
    return result;
}

/**
 * Main function.
 */
int main(int argv, char **argc)
{
    long long n;
    std::vector<std::string> arguments(argc, argc + argv);

    if (arguments.size() == 1)
    // No number was given by commandline
    {
        int firstGuess;
        std::cout << "Your first guess: ";
        std::cin >> firstGuess;
        if (firstGuess < 1 || firstGuess > 3)
            throw std::out_of_range("Door number out of range.");
        Show show;
        int goatDoor = show.showGoatDoor(firstGuess);
        std::cout << "There is a goat behind door number " << goatDoor << "." << std::endl;
        std::cout << "What is your final decision? ";
        int finalGuess;
        std::cin >> finalGuess;
        if (finalGuess < 1 || finalGuess > 3)
            throw std::out_of_range("Door number out of range.");
        if (show.finalResult(finalGuess))
            std::cout << "Congratulation! You get the car!" << std::endl;
        else
            std::cout << "You lost! There is a goat." << std::endl;
    }
    else
    // Automatic exploration
    {
        n = std::stol(arguments[1]);
        std::cout << "Running " << n << " experiments." << std::endl;

        auto r = montyHallExploration(int(n));

        std::cout << "Ratio for success remaining at first decision: "
                  << r << std::endl;
        std::cout << "Ratio for success changing first decision:     "
                  << 1.0 - r << std::endl;
    }
    return 0;
}
