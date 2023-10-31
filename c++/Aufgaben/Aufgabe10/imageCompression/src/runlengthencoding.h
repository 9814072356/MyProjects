#ifndef RUNLENGTHENCODING_H
#define RUNLENGTHENCODING_H

#include <string>

#include "tgaimage.h"

namespace RunLengthEncoding
{
    void encode(const std::string& inputFilename, const std::string& outputFilename);
    void encode(const TGAImage& inputImage, const std::string& outputFilename);

    void decode(const std::string& inputFilename, const std::string& outputFilename);
    TGAImage decode(const std::string& inputFilename);
}

#endif // RUNLENGTHENCODING_H

