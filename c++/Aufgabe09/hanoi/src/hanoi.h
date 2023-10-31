/**
 * Header file for Tower and Hanoi
 */
 
#include <vector>
#include <functional>

class Tower
{
    private:
    std::string name;
    std::vector<size_t> tower;
    public:
    Tower();
    Tower(const std::string& name);
    Tower(const std::string& name, std::vector<size_t>& v);
    std::string& getName();
    std::vector<size_t>& getTower();
};
    
class Hanoi
{
    private:
    Tower towerA;
    Tower towerB;
    Tower towerC;
    public:
    Hanoi(size_t height);
    void moveTopPlate(Tower& origin, Tower& target, 
        const std::function<void (const std::string&, const std::string&)> &preMovePrint, 
        const std::function<void ()> &postMovePrint);
    void solveHanoiStart(std::ostream& os, Tower& source, Tower& destination, Tower& spare);
    void solveHanoi(size_t towersize, Tower& source, Tower& destination, Tower& spare, 
        const std::function<void (const std::string&, const std::string&)> &preMovePrint, 
        const std::function<void ()> &postMovePrint);
};

