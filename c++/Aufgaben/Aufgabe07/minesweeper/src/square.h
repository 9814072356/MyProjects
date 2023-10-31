/**
 * @file square.h
 */

#ifndef SQUARE_H_
#define SQUARE_H_

#include <iostream>
class Minesweeper;
enum Attributes : unsigned char
{
    Mine = 16,
    Revealed = 32
};

struct Point2D
{
    size_t x;
    size_t y;
    Point2D() = default;
    Point2D(size_t x, size_t y) : x(x), y(y) {}
    friend std::ostream &operator<<(std::ostream &os, const Point2D &point)
    {
        return os << "(" << point.x << ", " << point.y << ")";
    }
};

class Square
{
private:
    Point2D position;
    unsigned char attributes;

public:
    Square(size_t x, size_t y);

    void incrementAdjacentMineCount();

    void layMine(Minesweeper *parent);
    bool reveal(Minesweeper *parent);

    size_t getX() const;
    size_t getY() const;

    size_t getAdjacentMineCount() const;

    bool isMine() const;
    bool isRevealed() const;
    void print(std::ostream &os, bool reveal = false) const;
};
std::ostream &operator<<(std::ostream &os, const Square &square);
#endif /* SQUARE_H_ */
