/**
 * @file player.cpp
 */

#include "player.h"

Code ManualPlayer::guess(Mastermind &) const
{
    Code result;
    (*this->os) << "Your guess: \n    ";
    (*this->is) >> result;
    return result;
}

Result ManualPlayer::evaluate(Mastermind &, const Code &) const
{
    unsigned int black;
    unsigned int white;
    (*this->os) << "Your evaluation: \n    Black: ";
    (*this->is) >> black;
    (*this->os) << "    White: ";
    (*this->is) >> white;
    (*this->os) << "\n";
    return {black, white};
}

// Implementieren Sie anschließend eine von Player abgeleitete Klasse AutomatedPlayer, die das Raten bzw.
// Evaluieren von den soeben implementierten Methoden der übergebenen Mastermind-Instanz durchführen lässt.
// Wenn Sie die Klasse implementiert haben, müssen Sie den typedef entfernen, der AutomatedPlayer als Synonym für ManualPlayer definiert.
// Hinweise:
// In dieser Teilaufgabe müssen auch Klassen geändert werden.
// Daher dürfen und müssen Sie auch die Header ändern.
// Sie können selbstverständlich auch eigene (Hilfs-)Funktionen definieren, wo Sie es für sinnvoll halten.

Code AutomatedPlayer::guess(Mastermind &master) const
{
    return master.guess();
}

Result AutomatedPlayer::evaluate(Mastermind &master, const Code &currentCode) const
{
    return master.evaluate(currentCode);
}