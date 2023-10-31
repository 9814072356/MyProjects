#include "labyrinth.h"
#include <iostream>
#include <vector>
#include <queue>
#include <stdexcept>

// Implementieren Sie in Labyrinth/src/labyrinth.cpp die Methode getOrigin!
// Sie soll einen Pointer auf die Ursprungskachel (Origin) des Labyrinths zurückgeben
Tile *Labyrinth::getOrigin()
{
    //Implement this function
    for (long unsigned int i = 0; i < floor.size(); i++)
    {
        for (long unsigned int j = 0; j < floor[i].size(); j++)
        {
            if (floor[i][j].isOrigin())
            {
                return &floor[i][j];
            }
        }
    }
    return NULL;
}

// Implementieren Sie in Labyrinth/src/labyrinth.cpp die Hilfsmethode emplaceNeighboor!
// Handelt es sich bei der Kachel an der übergebenen Koordinate
// nicht um eine Barriere (Barrier) oder um eine bereits besuchte Kachel (Visited),
// so soll ein Pointer auf diese Kachel in die Queue pending eingefügt werden.
// Außerdem muss in diesem Fall der Wert an der übergebenen Koordinate im Vektor
// predecessors auf die als current übergebene Kachel gesetzt werden

/** Helper function that is supposed to be used to process the neighbors of a Tile. */
void emplaceNeighbor(std::vector<std::vector<Tile>> &floor,
                     std::vector<std::vector<Tile *>> &predecessors,
                     std::queue<Tile *> &pending,
                     Tile *current, size_t x, size_t y)
{
    //Implement this function
    for (long unsigned int i = 0; i < x; i++)
    {
        for (long unsigned int j = 0; j < y; j++)
        {
            if (!floor[i][j].isBarrier() || floor[i][j].isVisited())
            {
                pending.emplace(&floor[i][j]);
                predecessors[i][j] = current;
            }
            else
            {
                predecessors[i][j] = nullptr;
            }
        }
    }
}

// Vervollständigen Sie in Labyrinth/src/labyrinth.cpp die Methode searchShortestPath!
// Diese soll auf Basis der bereits beschriebenen Breitensuche den kürzesten Pfad durch das Labyrinth finden.
// Hinweise:
// • Pfade dürfen in diesem Labyrinth nur rechtwinklig verlaufen, diagonale Pfade,
//   also solche, in denen sich zwei hintereinander überquerte Kacheln nur in einem
//   Eckpunkt berühren, sind nicht zulässig.
// • Für die erfolgreiche Implementierung des Algorithmus ist es zielführend,
//   zunächst den Ursprung des Labyrinths zu finden und dessen Nachbarn zu
//   bearbeiten. Anschließend sollten die Nachbarn der Nachbarn behandelt werden
//   und so weiter.
// • Das erste Auffinden des Zielpunktes ist bei diesem Vorgehen automatisch über
//   den kürzest möglichen Weg erreicht.
// • Zur Abarbeitung aller Kacheln in der richtigen Reihenfolge könnten sich die
//   Nutzung der Warteschlange pending und die Hilfsmethode emplaceNeighboor
//   als überaus hilfreich erweisen.
// • Die Methode .visit() der Klasse Tile kann verwendet werden, um Kacheln
//   als bereits besucht zu markieren.
// • Mit der Methode .addToPath() werden Kacheln als zum Pfad gehörig markiert

void Labyrinth::searchShortestPath()
{
    std::queue<Tile *> pending;
    std::vector<std::vector<Tile *>> predecessors(this->floor.size(), std::vector<Tile *>(this->floor[0].size(), nullptr));

    for (long unsigned int i = 0; i < floor.size(); i++)
    {
        for (long unsigned int j = 0; j < floor[i].size(); j++)
        {
            std::cout << floor[i][j].isVisited() << std::endl;
        }
    }

    if (pending.empty())
    {
        std::cout << "pending is empty" << std::endl;
    }

    std::cout << std::endl;
    //Implement here
}

void Labyrinth::printLabyrinth(std::ostream &os, bool visualizeVisited) const
{
    for (const std::vector<Tile> &row : this->floor)
    {
        for (const Tile &tile : row)
            tile.print(os, visualizeVisited);
        os << "\n";
    }
}
