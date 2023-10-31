/**
 * @file square.cpp
 */

#include "square.h"
#include "minesweeper.h"
Square::Square(size_t x, size_t y) : 
    position(x, y), attributes(0)
{

}

void Square::incrementAdjacentMineCount()
{
    this->attributes++;
}

void Square::layMine(Minesweeper* parent)
{
    this->attributes |= Attributes::Mine;
    ++parent->mineCount;
    parent->incrementMineNeighbors(this);
}

bool Square::reveal(Minesweeper* parent)
{
    if(this->isRevealed())
        return false;
    this->attributes |= Attributes::Revealed;
    parent->addRevealed(this);
    return true;
}

size_t Square::getX() const
{
    return this->position.x;
}

size_t Square::getY() const
{
    return this->position.y;
}

size_t Square::getAdjacentMineCount() const
{
    return this->attributes & 15;
}

bool Square::isMine() const
{
    return this->attributes & Attributes::Mine;
}

bool Square::isRevealed() const
{
    return this->attributes & Attributes::Revealed;
}

void Square::print(std::ostream& os, bool reveal) const
{
    if(!this->isRevealed() && !reveal)
        os << "-";
    else if(this->isMine())
        os << "!";
    else
        os << this->getAdjacentMineCount();
}
std::ostream& operator<<(std::ostream& os, const Square& square)
{
    square.print(os, false);
    return os;
}


