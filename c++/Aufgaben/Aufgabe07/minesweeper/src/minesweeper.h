/**
 * @file minesweeper.h
 */

#ifndef MINESWEEPER_H_
#define MINESWEEPER_H_

#include "square.h"

#include <algorithm>
#include <iostream>
#include <random>
#include <vector>

class Minesweeper
{
private:
    static std::mt19937 re;
    std::vector<std::vector<Square>> grid;
    size_t mineCount;
    size_t hiddenFields;
    
    void placeMines(std::vector<Point2D> indices, size_t mines);
    void incrementMineNeighbors(const Square* mine);
    void revealMinelessNeighbors(const Square* square);
    friend class Square;
    void addRevealed(const Square* square);
public:
    Minesweeper();
    Minesweeper(size_t height, size_t width, size_t mines);
    bool isFinished() const;
    bool evaluateGuess(size_t column, size_t row);
    void print(std::ostream& os, bool reveal = false) const;    
};
std::ostream& operator<<(std::ostream& os, const Minesweeper& minesweeper);
#endif /* MINESWEEPER_H_ */

