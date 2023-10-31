#ifndef TGAIMAGE_H
#define TGAIMAGE_H

#include <cstdint>
#include <fstream>
#include <vector>

#include "tgaimageheader.h"

struct ARGB32;

class TGAImage
{
private:
    TGAImageHeader header;
    std::vector<ARGB32> data;

public:
    TGAImage();
    TGAImage(const TGAImageHeader& header);
    TGAImage(const std::string& filename);

    const std::vector<ARGB32>&    getData()   const { return data; }
          std::vector<ARGB32>&    getData()         { return data; }

    const TGAImageHeader& getHeader() const { return header; }
          TGAImageHeader& getHeader()       { return header; }

    void setData(const std::vector<ARGB32>& data)  { this->data = data; }
    void setHeader(const TGAImageHeader& header) { this->header = header; }

    friend std::istream& operator>>(std::istream& is, TGAImage& i);
    friend std::ostream& operator<<(std::ostream& os, const TGAImage& i);
};

struct ARGB32
{
private:
    uint8_t b;  // Blue
    uint8_t g;  // Green
    uint8_t r;  // Red
    uint8_t a;  // Alpha

public:
    ARGB32() = default;
    ARGB32(uint8_t a, uint8_t r, uint8_t g, uint8_t b) : b(b), g(g), r(r), a(a) {}

    uint8_t getAlpha() const { return a; }
    uint8_t getRed() const { return r; }
    uint8_t getGreen() const { return g; }
    uint8_t getBlue() const { return b; }

    uint32_t getValue() const { return static_cast<uint32_t>(a << 24 | r << 16 | g << 8 | b); }

    void setAlpha(uint8_t a) { this->a = a; }
    void setRed(uint8_t r) { this->r = r; }
    void setGreen(uint8_t g) { this->g = g; }
    void setBlue(uint8_t b) { this->b = b; }

    bool operator==(const ARGB32& other) const { return this->getValue() == other.getValue(); }
    bool operator!=(const ARGB32& other) const { return !(*this == other); }

    friend std::istream& operator>>(std::istream& is, ARGB32& argb);
    friend std::ostream& operator<<(std::ostream& os, const ARGB32& argb);
};

#endif // TGAIMAGE_H

