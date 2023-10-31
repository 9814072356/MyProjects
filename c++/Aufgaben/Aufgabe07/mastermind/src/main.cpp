/**
 * @file main.cpp
 */

#include <iostream>
#include <string>
#include <memory>
#include "player.h"
#include "mastermind-test.h"

/** Plays Mastermind interactively with given parameters */
void playMastermind(unsigned int colors, unsigned positions, Player &guesser, Player &evaluator)
{
    std::random_device rd;
    Mastermind game(colors, positions, rd());
    Code guess;
    Result result;
    unsigned int guesses = 0;
    do
    {
        std::cout << "Number of possible codes: " << game.getPossibleCodeCount() << std::endl;

        if (!game.getPossibleCodeCount())
        {
            std::cout << "Contradiction!" << std::endl;
            return;
        }
        guess = guesser.guess(game);
        std::cout << "Guess:  " << guess << std::endl;
        ++guesses;
        result = evaluator.evaluate(game, guess);
        std::cout << "Result: " << result << std::endl;
        game.considerResult(guess, result);
    } while (!game.isSolved(result));
    std::cout << "Completed after " << guesses << " guesses!" << std::endl;
}

/** Reads in a bool as "y" and "n" from cin*/
bool getBoolInput()
{
    std::string answer;
    std::cin >> answer;
    if (answer == "y")
        return true;
    else if (answer != "n")
        std::cerr << "Unknown input, only \"y\" and \"n\" accepted. Assuming \"n\"" << std::endl;
    return false;
}

/** Plays Mastermind interactively. Asks for game parameters */
void playMastermind()
{
    unsigned colors;
    unsigned positions;
    std::cout << "Number of colors: ";
    std::cin >> colors;
    std::cout << "Number of positions: ";
    std::cin >> positions;

    std::unique_ptr<Player> guesser;
    std::unique_ptr<Player> evaluator;
    std::cout << "Computer guesses (y/n): ";
    if (getBoolInput())
        guesser.reset(new AutomatedPlayer());
    else
        guesser.reset(new ManualPlayer(std::cin, std::cout));
    std::cout << "Computer evaluates (y/n): ";
    if (getBoolInput())
        evaluator.reset(new AutomatedPlayer());
    else
        evaluator.reset(new ManualPlayer(std::cin, std::cout));

    playMastermind(colors, positions, *guesser, *evaluator);
}

int main()
{
    testMastermind();
    playMastermind();
}
