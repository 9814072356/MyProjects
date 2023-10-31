/**
 * Main file. 
 */

#include <vector>
#include <iostream>
#include "hanoi.h"

/**
 * Main test function
 */
int main()
{
    std::cout << "First Example: Tower of height 3  A->B" << std::endl;
    Hanoi hanoi(3);

#if false
    std::cout << "\n\nSecond Example: Tower of height 5\n";
    Hanoi hanoi2(5);
#endif

    return 0;
}
