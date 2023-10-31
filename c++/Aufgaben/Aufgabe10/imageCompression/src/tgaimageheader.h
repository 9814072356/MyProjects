#ifndef TGAIMAGEHEADER_H
#define TGAIMAGEHEADER_H

#include <cstdint>
#include <iostream>

#pragma pack(push, 1)
struct TGAImageHeader
{
private:
    uint8_t idLength;
    uint8_t colorMapType;
    uint8_t imageType;

    uint16_t colorMapFirstEntryIndex;
    uint16_t colorMapLength;
    uint8_t  colorMapEntrySize;

    uint16_t originXCoordinate;
    uint16_t originYCoordinate;
    uint16_t imageWidth;
    uint16_t imageHeight;
    uint8_t  pixelDepth;
    uint8_t  imageDescriptor;

public:
    size_t getImageWidth()  { return static_cast<size_t>(imageWidth); }
    size_t getImageHeight() { return static_cast<size_t>(imageHeight); }
    size_t getImageSize()   { return static_cast<size_t>(imageWidth * imageHeight); }

    void setCompressed()   { if(isUncompressed()) imageType = static_cast<uint8_t>(imageType + 8); }
    void setUncompressed() { if(isCompressed())   imageType = static_cast<uint8_t>(imageType - 8); }

    bool isCompressed()   { return imageType > 8 && imageType < 12; }
    bool isUncompressed() { return imageType > 0 && imageType <  4; }

    friend std::istream& operator>>(std::istream& is, TGAImageHeader& h);
    friend std::ostream& operator<<(std::ostream& os, const TGAImageHeader& h);
};
#pragma pack(pop)

#endif // TGAIMAGEHEADER_H

