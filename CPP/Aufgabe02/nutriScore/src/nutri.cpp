/**
 * @file nutri.cpp
 */
#include "nutri.h"
#include <iostream>
#include <tuple>

const std::tuple<std::string, char, size_t> &getUnhealthy(const std::tuple<std::string, char, size_t> &mealA,
                                                          const std::tuple<std::string, char, size_t> &mealB)
{
    // TODO: Implement here
    int scoreA = 0;
    int scoreB = 0;

    if (std::get<1>(mealA) == 'D')
    {
        scoreA = static_cast<int>(std::get<1>(mealA)) * static_cast<int>(std::get<2>(mealA));
    }
    else if (std::get<1>(mealA) == 'E')
    {
        scoreA = static_cast<int>(std::get<1>(mealA)) * static_cast<int>(std::get<2>(mealA)) * 2;
    }

    if (std::get<1>(mealB) == 'D')
    {
        scoreB = static_cast<int>(std::get<1>(mealB)) * static_cast<int>(std::get<2>(mealB));
    }
    else if (std::get<1>(mealB) == 'E')
    {
        scoreB = static_cast<int>(std::get<1>(mealB)) * static_cast<int>(std::get<2>(mealB)) * 2;
    }

    std::cout << "A: " << scoreA << " B: " << scoreB << "\n";

    if (scoreA > scoreB)
    {
        std::cout << "returning A: " << std::get<0>(mealA) << "\n";
        return mealA;
    }
    else if (scoreA < scoreB)
    {
        std::cout << "returning B: " << std::get<0>(mealB) << "\n";
        return mealB;
    }
    return mealB; // nur um die Warning zu vermeiden
}

void analyzeMeals(std::ostream &os, const std::vector<std::tuple<std::string, char, size_t>> &meals)
{
    int worstFoodHabit = 0;
    int worstFoodHabitAmount = 0;
    std::string worstFoodHabitName = "";

    // TODO: Implement here
    int amountFood = 0;
    int amountHealthyFood = 0;
    bool badFoodinVector = false;

    for (unsigned int i = 0; i < meals.size(); i++)
    {
        amountFood += static_cast<int>(std::get<2>(meals.at(i)));

        if (std::get<1>(meals.at(i)) != 'D' && std::get<1>(meals.at(i)) != 'E')
        {
            amountHealthyFood += static_cast<int>(std::get<2>(meals.at(i)));
        }

        // if (i + 1 < meals.size())
        // {
        //     if (std::get<1>(meals.at(i)) >= 'D' && std::get<1>(meals.at(i + 1)) >= 'D')
        //     {
        //         std::cout << i << ". Vergleich\n";
        //         std::cout << std::get<1>(getUnhealthy(meals.at(i), meals.at(i + 1))) << "\n"
        //                   << "-------\n";
        //     }
        // }
        if (std::get<1>(meals.at(i)) >= 'D')
        {
            int tempWorseFood = 0;
            if (std::get<1>(meals.at(i)) == 'D')
            {
                tempWorseFood = static_cast<int>(std::get<1>(meals.at(i))) * static_cast<int>(std::get<2>(meals.at(i)));
            }
            else if (std::get<1>(meals.at(i)) == 'E')
            {
                tempWorseFood = static_cast<int>(std::get<1>(meals.at(i))) * static_cast<int>(std::get<2>(meals.at(i))) * 2;
            }
            if (tempWorseFood > worstFoodHabit)
            {
                worstFoodHabit = tempWorseFood;
                worstFoodHabitAmount = static_cast<int>(std::get<2>(meals.at(i)));
                worstFoodHabitName = static_cast<std::string>(std::get<0>(meals.at(i)));
            }
            badFoodinVector = true;
        }
        if (badFoodinVector == false && i + 1 == meals.size())
        {
            std::cout << "no bad food habit found"
                      << "\n";
        }
    }
    if (badFoodinVector)
    {
        os
            << "worst food habit: " << worstFoodHabitAmount << "x " << worstFoodHabitName << "\n"
            << "eaten food amount: " << amountFood << "\n"
            << "eaten healthy food amount: " << amountHealthyFood << "\n"
            << "--------------------\n";
    }
    else
    {
        os
            << "eaten food amount: " << amountFood << "\n"
            << "eaten healthy food amount: " << amountHealthyFood << "\n"
            << "--------------------\n";
    }
}
