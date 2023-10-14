package experiment;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.util.ArrayList;

public class Ship {
    ArrayList<Coord> ship;
    int compartments;

    public Ship(int length) {
        this.ship = new ArrayList(length);
    }

    public Coord getCompartment(int x) {
        return (Coord)this.ship.get(x);
    }
}

