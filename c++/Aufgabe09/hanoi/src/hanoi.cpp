/**
 * Implementation of Tower and Hanoi
 */

#include <iostream>
#include <algorithm>
#include <stack>
#include <deque>
#include "hanoi.h"
#include <iterator>

size_t winHeight = 0;

std::string &Tower::getName()
{
    return this->name;
}

std::vector<size_t> &Tower::getTower()
{
    return this->tower;
}

Tower::Tower() {}

Tower::Tower(const std::string &name) : name(name), tower(std::vector<size_t>()) {}

Tower::Tower(const std::string &name, std::vector<size_t> &v) : name(name), tower(v) {}

Hanoi::Hanoi(size_t height)
{
    winHeight = height;
    this->towerA = Tower("TowerA");
    for (size_t i = height; i > 0; --i)
        this->towerA.getTower().push_back(i);
    this->towerB = Tower("TowerB");
    this->towerC = Tower("TowerC");
    this->solveHanoiStart(std::cout, this->towerA, this->towerB, this->towerC);
}

// Implementieren Sie die Methode moveTopPlate.
// Diese soll die oberste Platte des ersten übergebenen Turmes auf den zweiten verschieben.
// Stellen Sie sicher, dass der Zug legal ist, sprich keine größere Platte auf eine kleinere verschoben wird.
// Vor dem Verschieben soll die übergebene Funktion preMovePrint und nach dem Zug postMovePrint ausgeführt werden.
// Hinweise:
// • Die übergebenen Parameter der preMovePrint-Funktion können die Namen der
//   beiden übergebenen Türme sein (mehr dazu in späteren Teilaufgaben).

void Hanoi::moveTopPlate(Tower &origin, Tower &target,
                         const std::function<void(const std::string &, const std::string &)> &preMovePrint,
                         const std::function<void()> &postMovePrint)
{
    // TODO: Implement
    if (this->towerB.getTower().size() == winHeight || this->towerC.getTower().size() == winHeight)
    {
        std::cout << "tower is solved!" << std::endl;
        exit(0);
    }
    if (target.getTower().size() == 0 || origin.getTower().front() < target.getTower().front())
    {
        preMovePrint(origin.getName(), target.getName());
        target.getTower().insert(target.getTower().begin(), origin.getTower().at(0));
        origin.getTower().erase(origin.getTower().begin());
        postMovePrint();
    }
    else
    {
        moveTopPlate(target, origin, preMovePrint, postMovePrint);
    }
}

// Implementieren Sie die Methode solveHanoi.
// Diese wird rekursiv mit immer kleiner werdenden Turmgrößen aufgerufen
// bis das minimale Problem eines Turmes der Größe 1 einfach gelöst werden kann.
// Allerdings müssen auch der Ziel-, Aushilfs- und Ausgangsturm angepasst werden:
// • Ist die Turmgröße 1, so wird die oberste Platte vom Ausgangs- auf den Zielturm verschoben.
// • Sonst wird die Funktion rekursiv mit einer um 1 verringerten Turmgröße aufgerufen.
//   Der Ziel- und Aushilfsturm werden dabei getauscht.
// • Danach wird die Platte vom Ausgangs- zum Zielturm verschoben.
// • Zuletzt wird die Funktion noch einmal rekursiv mit einer um 1 verringerten
//   Turmgröße aufgerufen, dieses mal mit dem Aushilfs- und dem Ausgangsturm
//   vertauscht.

void Hanoi::solveHanoi(size_t towersize, Tower &source, Tower &destination, Tower &spare,
                       const std::function<void(const std::string &, const std::string &)> &preMovePrint,
                       const std::function<void()> &postMovePrint)
{
    // TODO: Implement

    if (towersize == 1)
    {
        moveTopPlate(source, destination, preMovePrint, postMovePrint);
    }
    else
    {
        solveHanoi(towersize - 1, source, spare, destination, preMovePrint, postMovePrint);
        moveTopPlate(source, destination, preMovePrint, postMovePrint);
        solveHanoi(towersize - 1, spare, destination, source, preMovePrint, postMovePrint);
    }
}

template <typename T>
std::ostream &operator<<(std::ostream &out, const std::vector<T> &v)
{
    if (!v.empty())
    {
        out << '[';
        std::copy(v.begin(), v.end(), std::ostream_iterator<T>(out, ", "));
        out << "\b\b]";
    }
    return out;
}

// Implementieren Sie die Methode solveHanoiStart.
// Diese soll zwei Lambda-Funktionen definieren, die in der Rekursion benötigt werden, und dann die Rekursion starten.
// In der Funktion preMovePrint soll auf dem übergebenen Outputstream der geplante Zug ausgegeben werden.
// Sie soll dabei ausgeben, von welchem auf welchen Turm verschoben wird.
// In der postMovePrint-Funktion sollen die Inhalte der Türme übersichtlich ausgegeben werden.
// Insgesamt ist Ihnen bei beiden Funktionen sehr viel kreativer Freiraum gelassen und Sie können, falls nötig, die Signaturen der beiden Funktionen anpassen.

void Hanoi::solveHanoiStart(std::ostream &os, Tower &source, Tower &destination, Tower &spare)
{
    // TODO: Implement
    auto preMovePrint = [&](std::string sourceString, std::string destinationString) { os << "-------------------" << std::endl
                                                                                          << "planned move: " << sourceString << " --[" << source.getTower().front() << "]--> " << destinationString << std::endl; };
    auto postMovePrint = [&]() { os << "source = " << source.getTower() << " target = " << destination.getTower() << " spare = " << spare.getTower() << std::endl; };
    solveHanoi(source.getTower().size(), source, destination, spare, preMovePrint, postMovePrint);
}
