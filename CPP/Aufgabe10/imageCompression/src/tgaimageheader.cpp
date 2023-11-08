#include "tgaimageheader.h"

std::istream& operator>>(std::istream& is, TGAImageHeader& header)
{
   return is.read(reinterpret_cast<char*>(&header), sizeof(TGAImageHeader));
}

std::ostream& operator<<(std::ostream& os, const TGAImageHeader& header)
{
   return os.write(reinterpret_cast<const char*>(&header), sizeof(TGAImageHeader));
}

