/**
 * @file rps.cpp
 */
#include "rps.h"
#include <sstream>

Move parseInput(const std::string &input)
{
    // TODO: Implement here
    Move result = Move::Error;
    if (input.compare("rock") == 0)
    {
        result = Move::Rock;
    }
    else if (input.compare("paper") == 0)
    {
        result = Move::Paper;
    }
    else if (input.compare("scissors") == 0)
    {
        result = Move::Scissors;
    }
    return result;
}

Result rockPaperScissors(const std::string &p1, const std::string &p2)
{
    // TODO: Implement here
    Result result = Result::Invalid;
    if ((p1 == "rock" && p2 == "scissors") || (p1 == "scissors" && p2 == "paper") || (p1 == "paper" && p2 == "rock"))
    {
        result = Result::Player1Wins;
    }
    else if ((p2 == "rock" && p1 == "scissors") || (p2 == "scissors" && p1 == "paper") || (p2 == "paper" && p1 == "rock"))
    {
        result = Result::Player2Wins;
    }
    else if ((p2 == "rock" && p1 == "rock") || (p2 == "scissors" && p1 == "scissors") || (p2 == "paper" && p1 == "paper"))
    {
        result = Result::Tie;
    }

    return result;
}

int main(int argc, char **argv)
{
    std::vector<std::string> args(argv, argv + argc);
    // TODO: Add code here
    std::string player1Move = args[1];
    std::string player2Move = args[2];

    if (parseInput(player1Move) != Move::Error && parseInput(player2Move) != Move::Error)
    {
        std::cout << "Player 1: " << player1Move << ", Player2: " << player2Move;
        if (rockPaperScissors(player1Move, player2Move) == Result::Player1Wins)
        {
            std::cout << ", Winner: Player1\n";
        }
        else if (rockPaperScissors(player1Move, player2Move) == Result::Player2Wins)
        {
            std::cout << ", Winner: Player2\n";
        }
        else if (rockPaperScissors(player1Move, player2Move) == Result::Tie)
        {
            std::cout << " ==> Tie\n";
        }
        else
        {
            std::cout << " ==> Invalid\n";
        }
    }
    else
    {
        std::cout << "Invalid Input\n";
    }
    return 0;
}
