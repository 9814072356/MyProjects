#include "runlengthencoding.h"
#include <algorithm>
#include <iterator>
#include <bitset>

// bool myfunction(ARGB32 i)
// {
//     return false;
// }

// bool operator>(const ARGB32 v1, const ARGB32 v2)
// {
//     return int(v1.getValue()) > int(v2.getValue());
// }

namespace RunLengthEncoding
{
    // Implementieren Sie in imageCompression/src/runlengthencoding.cpp die Funktion encode!
    // Diese erhält als Parameter ein unkomprimiertes Bild vom Typ TGAImage,
    // das mittels der beschriebenen Lauflängenkodierung komprimiert werden soll.
    // Dabei soll das Ergebnis unmittelbar über den vorhandenen std::ostream in eine Datei geschrieben werden.

    void encode(const TGAImage &inputImage, std::ostream &os)
    {
        TGAImageHeader header = inputImage.getHeader();
        header.setCompressed();
        os << header;

        // TODO: Implement here
        // a.)
        // Iterieren Sie mit einem Iterator über die Pixel (TGAImage::getData) des Eingabebilds.
        // Verwenden Sie std::adjacent_find zur Suche nach dem Beginn der nächsten Wiederholung von Farbwerten sowie, um das Ende der Wiederholung zu finden.
        // Die Rechereche nach der Funktionsweise der Standard-Algorithmen auf cppreference.com ist Bestandteil der Aufgabe.

        // for (auto i = inputImage.getData().begin(); i != inputImage.getData().end(); i++)
        // {
        // auto repeatStart = std::adjacent_find(inputImage.getData().begin(), inputImage.getData().end());
        // auto it = std::find_if(inputImage.getData().begin(), inputImage.getData().end(), myfunction);

        // if (*repeatStart == *it)
        // {
        //     // std::cout << int(repeatStart->getAlpha()) << std::endl;
        // }

        // auto result = std::find(inputImage.getData().begin(), inputImage.getData().end(), repeatStart);
        // }
    }

    // Implementieren Sie in imageCompression/src/runlengthencoding.cpp die Funktion decode!
    // Sie soll ein lauflängenkodiertes Bild dekomprimieren und das Ergebnis als TGAImage zurückgeben.
    // Das komprimierte Bild kann mit Hilfe des übergebenen std::istream gelesen werden.

    TGAImage decode(std::istream &is)
    {
        TGAImage image;
        is >> image.getHeader();
        image.getHeader().setUncompressed();

        // TODO: Implement here

        //a.)
        // Verarbeiten Sie den Stream des Eingabebilds, bis dieser keine Zeichen mehr enthält.
        // Das erste nach dem Dateikopf gelesene Byte (std::istream::get) ist das erste Steuerbyte (char) der Lauflängenkodierung.
        // Ermitteln Sie anhand des jeweils aktuellen Steuerbytes, ob dieses eine Wiederholung beschreibt sowie den dazugehörigen Zähler.
        // Hinweis:
        // • Die Methode get des std::istream hat einen Rückgabewert vom Typ int.
        //   Lassen Sie sich davon nicht verunsichern.
        //   Die Methode extrahiert trotzdem nur ein Byte aus dem Stream.
        //   Um Fehler zu vermeiden, können Sie gelesene Steuerbytes ebenfalls in einer int-Variable (oder unsigned char bzw. uint8_t) zwischenspeichern,
        //   damit die Werte im Bereich von 0 bis 255 liegen.
        //   Im Fall vom Typ char wäre es der nicht unbedingt günstige Bereich von −128 bis 127

        // std::cout << test << std::endl;

        int test = is.get();
        std::bitset<16> currentBitSet(int(is.get()));
        std::cout << "unmodified: " << int(is.get()) << " ; " << test << "\t-> " << currentBitSet << " -> "
                  << stoi(currentBitSet.to_string(), NULL, 2) << std::endl;
        if (is.get() != -1)
        {
            int repLimit = 127;
            std::bitset<32> currentPixelValue(is.get());

            for (int i = 0; i < repLimit; i++)
            {
                ARGB32 tempARGB();
            }

            // std::cout << "first seven bits:"
            //           << " -> " << currentPixelValue << " -> "
            //           << stoi(currentPixelValue.to_string(), NULL, 2) << std::endl;
        }
        else
        {
            // std::cout << "first seven bits: " << abs(is.get()) << "\t-> " << currentCounter << " -> "
            //           << stoi(currentCounter.to_string(), NULL, 2) << std::endl;
        }
        return image;
    }

    void encode(const std::string &inputFilename, const std::string &outputFilename)
    {
        std::ifstream is(inputFilename, std::ios::in | std::ios::binary);
        TGAImage inputImage;
        is >> inputImage;
        is.close();
        encode(inputImage, outputFilename);
    }

    void encode(const TGAImage &inputImage, const std::string &outputFilename)
    {
        std::ofstream os(outputFilename, std::ios::out | std::ios::binary);
        encode(inputImage, os);
        os.close();
    }

    void decode(const std::string &inputFilename, const std::string &outputFilename)
    {
        std::ofstream os(outputFilename, std::ios::out | std::ios::binary);
        TGAImage inputImage = decode(inputFilename);
        os << inputImage;
        os.close();
    }

    TGAImage decode(const std::string &inputFilename)
    {
        std::ifstream is(inputFilename, std::ios::in | std::ios::binary);
        TGAImage inputImage = decode(is);
        is.close();
        return inputImage;
    }
} // namespace RunLengthEncoding
