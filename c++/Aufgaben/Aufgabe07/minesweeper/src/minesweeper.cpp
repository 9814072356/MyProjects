/**
 * @file minesweeper.cpp
 */

#include "minesweeper.h"
#include <iomanip>
std::mt19937 Minesweeper::re(std::random_device{}());

Minesweeper::Minesweeper() : Minesweeper(9, 9, 10)
{
}

Minesweeper::Minesweeper(size_t height, size_t width, size_t mineCount) : mineCount(0), hiddenFields(height * width)
{
    this->grid.resize(height);
    std::vector<Point2D> indices;
    indices.reserve(height * width);
    for (size_t y = 0; y < height; ++y)
        for (size_t x = 0; x < width; ++x)
        {
            this->grid[y].emplace_back(x, y);
            indices.emplace_back(x, y);
        }
    this->placeMines(std::move(indices), mineCount);
}

void Minesweeper::placeMines(std::vector<Point2D> indices, size_t mineCount)
{
    for (long unsigned i = 0; i < mineCount; i++)
    {
        std::shuffle(std::begin(indices), std::end(indices), this->re);
        for (long unsigned j = 0; j < indices.at(i).x; j++)
        {
            for (long unsigned k = 0; k < indices.at(i).y; k++)
            {
                this->grid[j][k].layMine(this);
            }
        }
    }
}

std::pair<Point2D, Point2D> getSurroundingCoordinates(size_t x, size_t y, size_t sizeX, size_t sizeY)
{
    size_t startX = x > 0 ? x - 1 : 0;
    size_t startY = y > 0 ? y - 1 : 0;
    size_t endY = y < sizeY - 1 ? y + 2 : sizeY;
    size_t endX = x < sizeX - 1 ? x + 2 : sizeX;
    return std::make_pair(Point2D{startX, startY}, Point2D{endX, endY});
}

void Minesweeper::incrementMineNeighbors(const Square *mine)
{
    long unsigned int targetX = getSurroundingCoordinates(mine->getX(), mine->getY(), 9, 9).first.x;
    long unsigned int targetY = getSurroundingCoordinates(mine->getX(), mine->getY(), 9, 9).first.y;
    for (int i = 0; i < int(this->grid.size()); i++)
    {
        for (int j = 0; j < int(this->grid[i].size()); j++)
        {
            this->grid[i][j].incrementAdjacentMineCount();

            if (this->grid[i][j].getX() == targetX && this->grid[i][j].getY() <= targetY && this->grid[i][j].getX() != mine->getX() && this->grid[i][j].getY() != mine->getY())
            {
                this->grid[i][j].incrementAdjacentMineCount();
            }
        }
    }
}

bool Minesweeper::evaluateGuess(size_t column, size_t row)
{
    Square *choice = &this->grid[row][column];
    choice->reveal(this);
    if (choice->isMine())
        return false;
    this->revealMinelessNeighbors(choice);
    return true;
}
bool Minesweeper::isFinished() const
{
    return this->hiddenFields <= this->mineCount;
}

void Minesweeper::revealMinelessNeighbors(const Square *square)
{
    long unsigned int targetX = getSurroundingCoordinates(square->getX(), square->getY(), 9, 9).first.x;
    long unsigned int targetY = getSurroundingCoordinates(square->getX(), square->getY(), 9, 9).first.y;
    for (int i = 0; i < int(this->grid.size()); i++)
    {
        for (int j = 0; j < int(this->grid[i].size()); j++)
        {
            if (this->grid[i][j].getX() == targetX && this->grid[i][j].getY() <= targetY && this->grid[i][j].isMine() && !this->grid[i][j].isRevealed())
            {
            }
            else
            {
                this->grid[i][j].reveal(this);
                if (i + 1 < int(this->grid.size()) && j + 1 < int(this->grid[i].size()))
                {
                    revealMinelessNeighbors(&this->grid[i + 1][j + 1]);
                }
            }
        }
    }
}
void Minesweeper::addRevealed(const Square *)
{
    --this->hiddenFields;
}
void Minesweeper::print(std::ostream &os, bool reveal) const
{
    os << "\n";
    int width = static_cast<int>(std::ceil(std::log10(this->grid[0].size())));
    os << "  ";
    for (size_t x = 0; x < this->grid[0].size(); ++x)
    {
        os << " " << std::setw(width) << (x + 1);
    }
    os << "\n\n";
    for (size_t y = 0; y < this->grid.size(); ++y)
    {
        os << static_cast<char>(y + 'A') << " ";
        for (size_t x = 0; x < this->grid[y].size(); ++x)
        {
            os << " " << std::setw(width);
            this->grid[y][x].print(os, reveal);
        }
        os << "\n";
    }
    os << this->mineCount << " bombs"
       << "\n";
    os << "\n";
}
std::ostream &operator<<(std::ostream &os, const Minesweeper &minesweeper)
{
    minesweeper.print(os, false);
    return os;
}
