#include <iostream>
#include <string>
#include <fstream>
#include <vector>

void compareFilesLineByLine(const std::vector<std::string> &, const std::vector<std::string> &);

int main(int argc, char **argv)
{
    std::vector<std::string> arguments(argv, argv + argc);

    // Add code here
    std::string line;
    std::vector<std::string> firstFileVector(0);
    std::vector<std::string> secondFileVector(0);

    std::ifstream firstFile(argv[1]);
    while (getline(firstFile, line))
    {
        firstFileVector.push_back(line);
    }
    firstFile.close();

    std::ifstream secondFile(argv[2]);
    while (getline(secondFile, line))
    {
        secondFileVector.push_back(line);
    }
    secondFile.close();
    compareFilesLineByLine(firstFileVector, secondFileVector);
    return 0;
}

void compareFilesLineByLine(const std::vector<std::string> &file1, const std::vector<std::string> &file2)
{
    // Add code here

    if (file1.size() < file2.size())
    {
        for (long unsigned int i = 0; i < file2.size(); i++)
        {
            if (i < file1.size())
            {
                if (file1.at(i).length() > file2.at(i).length() || file1.at(i).compare(file2.at(i)) != 0)
                {
                    std::cout << "<<<" << i + 1 << "<<< " << file1.at(i) << std::endl;
                    std::cout << ">>>" << i + 1 << ">>> " << file2.at(i) << std::endl;
                }
                else if (file1.at(i).length() < file2.at(i).length() || file1.at(i).compare(file2.at(i)) != 0)
                {
                    std::cout << ">>>" << i + 1 << ">>> " << file1.at(i) << std::endl;
                    std::cout << "<<<" << i + 1 << "<<< " << file2.at(i) << std::endl;
                }
            }
            else
            {
                std::cout << "-------" << std::endl;
                std::cout << ">>>" << i + 1 << ">>> " << file2.at(i) << std::endl;
            }
        }
    }
    else
    {
        for (long unsigned int i = 0; i < file1.size(); i++)
        {
            if (i < file2.size())
            {
                if (file1.at(i).length() > file2.at(i).length() || file1.at(i).compare(file2.at(i)) != 0)
                {
                    std::cout << "<<<" << i + 1 << "<<< " << file1.at(i) << std::endl;
                    std::cout << ">>>" << i + 1 << ">>> " << file2.at(i) << std::endl;
                }
                else if (file1.at(i).length() < file2.at(i).length() || file1.at(i).compare(file2.at(i)) != 0)
                {
                    std::cout << ">>>" << i + 1 << ">>> " << file1.at(i) << std::endl;
                    std::cout << "<<<" << i + 1 << "<<< " << file2.at(i) << std::endl;
                }
            }
            else
            {
                std::cout << "-------" << std::endl;
                std::cout << ">>>" << i + 1 << ">>> " << file1.at(i) << std::endl;
            }
        }
    }
}
