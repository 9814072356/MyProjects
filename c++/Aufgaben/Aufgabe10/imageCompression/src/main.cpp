#include <filesystem>
#include <string>
#include <vector>

#include "tgaimage.h"
#include "runlengthencoding.h"

void test(const std::string& name)
{
    RunLengthEncoding::encode("examples/" + name + ".tga", "results/" + name + "_enc.tga");
    RunLengthEncoding::decode("results/" + name + "_enc.tga", "results/" + name + "_enc_dec.tga");
    RunLengthEncoding::decode("examples/" + name + "_enc.tga", "results/" + name + "_dec.tga");
}

int main()
{
    std::filesystem::create_directory("results");

    test("grey");
    test("white");
    test("vga");
    test("lena");

    return 0;
}

