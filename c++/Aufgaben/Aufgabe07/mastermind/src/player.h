#ifndef PLAYER_H
#define PLAYER_H

/**
 * @file player.h
 */

#include "mastermind.h"
#include <iostream>

/**
 * A Player is an entity which can guess and evaluate Codes for a given Mastermind
 */
class Player
{
public:
    virtual Code guess(Mastermind &mastermind) const = 0;
    virtual Result evaluate(Mastermind &mastermind, const Code &code) const = 0;
    virtual ~Player() = default;
};

class ManualPlayer : public Player
{
private:
    std::ostream *os;
    std::istream *is;

public:
    ManualPlayer(std::istream &in, std::ostream &out) : Player(), os(&out), is(&in) {}
    ManualPlayer() : ManualPlayer(std::cin, std::cout) {}
    Code guess(Mastermind &mastermind) const override;
    Result evaluate(Mastermind &mastermind, const Code &code) const override;
};

//TODO: Implement an actual AutomatedPlayer
class AutomatedPlayer : public Player
{

public:
    AutomatedPlayer() : Player() {}
    Code guess(Mastermind &mastermind) const override;
    Result evaluate(Mastermind &mastermind, const Code &code) const override;
};

//TODO: Remove the following using when AutomatedPlayer is implemented
// using AutomatedPlayer = ManualPlayer;
#endif // PLAYER_H
