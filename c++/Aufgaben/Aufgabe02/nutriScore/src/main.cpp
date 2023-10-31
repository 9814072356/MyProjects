/**
 * Main file. Should not be modified.
 * @file main.cpp
 */

#include "nutri.h"
#include <vector>
#include <string>
#include <tuple>

/**
 * Main function. Creates multiple Arrays of meals and calls the analyseMeals function for each
 */
int main()
{
    //Besser direkt initialisieren?
    std::vector<std::tuple<std::string, char, size_t>> meals;
    meals.emplace_back("Vollkorn Fischstaebchen", 'A', 5);
    meals.emplace_back("Broccoli-Nudelauflauf", 'B', 6);
    meals.emplace_back("Kartoffelsalat", 'C', 5);
    meals.emplace_back("Schwaebische Festtagssuppe", 'D', 5);
    meals.emplace_back("Nougatcreme", 'E', 4);
    meals.emplace_back("Milchschnitte", 'E', 3);
    analyzeMeals(std::cout, meals);
    meals.emplace_back("Backfisch-Stäbchen", 'C', 15);
    meals.emplace_back("Schoko-Sahne-Pudding", 'D', 9);
    meals.emplace_back("Rahm-Spinat", 'A', 42);
    analyzeMeals(std::cout, meals);
    std::vector<std::tuple<std::string, char, size_t>> moreMeals;
    moreMeals.emplace_back("Broccoli-Nudelauflauf", 'B', 6);
    moreMeals.emplace_back("Rahm-Spinat", 'A', 42);
    analyzeMeals(std::cout, moreMeals);
    return 0;
}
