#include <iostream>
#include <string>
using namespace std;

int main()
{
    string name;

    cout << "What is your given name? ";
    cin >> name;

    for (int count = 0; count <= 10; ++count)
        cout << "Hello " << name + "\n";
    return 0;
}
