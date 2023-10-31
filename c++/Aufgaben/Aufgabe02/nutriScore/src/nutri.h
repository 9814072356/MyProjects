#ifndef NUTRI_H_
#define NUTRI_H_
#include <vector>
#include <string>
#include <tuple>
#include <iostream>


/**
 * @brief getUnhealthy calculates and returns the more unhealthy of both given meals
 * @param mealA first meal to compare
 * @param mealB second meal to compare
 * @return the more unhealthy mealth of both
 */
const std::tuple<std::string, char, size_t>&  getUnhealthy(const std::tuple<std::string, char, size_t>& mealA,
                                                           const std::tuple<std::string, char, size_t>& mealB);

/**
 * @brief analyzeMeals calculates the number of the given meals which were healthy and
 * what meal was the most unhealthy
 * @param os output stream to write result on
 * @param meals array of tuple containing information of the meals
 */
void analyzeMeals(std::ostream& os, const std::vector<std::tuple<std::string, char, size_t>>& meals);


#endif /* NUTRI_H_ */

