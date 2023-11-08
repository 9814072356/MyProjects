#include "tgaimage.h"
#include <iterator>

TGAImage::TGAImage() : header(), data(0) {}

TGAImage::TGAImage(const TGAImageHeader& header) : header(header), data(0) {}

TGAImage::TGAImage(const std::string& filename) : TGAImage()
{
    std::ifstream is(filename, std::ios::in | std::ios::binary);
    is >> *this;
    is.close();
}

std::istream& operator>>(std::istream& is, TGAImage& image)
{
   is >> image.header;
   image.data.reserve(image.header.getImageSize());
   std::copy(std::istream_iterator<ARGB32>(is), std::istream_iterator<ARGB32>(), std::back_inserter(image.data));
   return is;
}

std::ostream& operator<<(std::ostream& os, const TGAImage& image)
{
    os << image.header;
    std::copy(image.data.cbegin(), image.data.cend(), std::ostream_iterator<const ARGB32&>(os));
    return os;
}

std::istream& operator>>(std::istream& is, ARGB32& argb)
{
    argb.b = static_cast<uint8_t>(is.get());
    argb.g = static_cast<uint8_t>(is.get());
    argb.r = static_cast<uint8_t>(is.get());
    argb.a = static_cast<uint8_t>(is.get());
    return is;
}

std::ostream& operator<<(std::ostream& os, const ARGB32& argb)
{
    os.put(static_cast<char>(argb.b));
    os.put(static_cast<char>(argb.g));
    os.put(static_cast<char>(argb.r));
    os.put(static_cast<char>(argb.a));
    return os;
}

