/**
 * Main file. Should not be modified.
 * @file main.cpp
 */

#include "minesweeper.h"
#include <vector>

Minesweeper chooseDifficulty(const std::string &difficulty);

/**
 * Main function.
 */
int main(int argv, char **argc)
{
    std::vector<std::string> arguments(argc, argc + argv);

    if (arguments.size() == 1)
    {
        arguments.push_back("easy");
        std::cout << "No difficulty specified - defaulting to easy difficulty!\n\n";
    }

    Minesweeper game = chooseDifficulty(arguments[1]);

    while (true)
    {
        std::cout << game;
        if (game.isFinished())
        {
            std::cout << "Congratulations! You win!\n";
            break;
        }

        std::cout << "Enter coordinates:\n";
        std::string line;
        std::getline(std::cin, line);
        size_t yInput = static_cast<size_t>(line.front() - 'A');
        size_t xInput = std::stoull(line.substr(1)) - 1;
        if (!game.evaluateGuess(xInput, yInput))
        {
            game.print(std::cout, true);
            std::cout << "You loose!\n\n";
            break;
        }
    }

    return 0;
}

Minesweeper chooseDifficulty(const std::string &difficulty)
{
    if (difficulty == "hard")
        return Minesweeper(16, 30, 99);
    else if (difficulty == "medium")
        return Minesweeper(16, 16, 40);
    else if (difficulty == "easy")
        return Minesweeper(9, 9, 10);
    return Minesweeper();
}
