#include <fstream>
#include <iostream>
#include <streambuf>
#include <string>

int main() {
    std::ifstream t("./input/day-10.txt");
    std::string str((std::istreambuf_iterator<char>(t)),
                    std::istreambuf_iterator<char>());
    std::cout << str;
    return 0;
}
