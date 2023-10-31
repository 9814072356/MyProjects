#include <vector>
#include <iostream>
#include <ostream>
int main()
{
    // std::vector<int> v = {10, 9}; // Vektor mit 2 int Elementen
    // int *p1 = &v[0];              // Pointer auf den Wert des 1. Elementes
    // // std::cout << p1 << "," << p1 + 1 << ", " << --*p1 << ", " << *--p1 << std::endl;
    // *p1 = --*p1 * *(p1 + 1);                        // p1 = p1Wert * Wert(p1Pointer + 1)
    // std::cout << v[0] << ", " << v[1] << std::endl; // 81,9

    int i = 0;
    // short *p1 = &i; // Shortpointer zeigt auf int Wert
    int *p2 = 0; // Nullpointer
    // std::cout << p2 << std::endl;
    // int *p3 = i;  // Wert von i wird in p3 gespeichert
    int *p4 = &i; // Speicheradresse von i wird im Pointer p4 gespeichert
    if (*p4)
    {
        std::cout << "hlle";
    }
    else if (p4)
    {
        std::cout << "hlle22";
    }
    // int *p2 = 0;
    // int *p3 = i;
    // int *p4 = &i;
}