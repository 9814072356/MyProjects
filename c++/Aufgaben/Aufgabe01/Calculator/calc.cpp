#include <string>
#include <iostream>
using namespace std;

int main()
{
    int number1, number2, result;
    string operation;
    cout << "Number1: " << std::endl;
    cin >> number1;
    cout << "Number2: " << std::endl;
    cin >> number2;
    cout << "possible operations: add, sub: " << std::endl;
    cout << "Operation: " << std::endl;
    cin >> operation;
    if (operation == "add" || operation == "ADD" || operation == "Add")
    {
        result = number1 + number2;
        cout << "Result: " << result << std::endl;
    }
    else if (operation == "sub" || operation == "SUB" || operation == "Sub")
    {
        result = number1 - number2;
        cout << "Result: " << result << std::endl;
    }

    return 0;
}
