package battleship;

import java.util.Random;

import battleship.Ship;
import battleship.ShipNode;

public class Ship {
    private ShipNode[] nodes;

    public Ship(int size) {
        nodes = new ShipNode[size];
    }
	
    public static Ship createShip(int max) {
    	int curX = 0;
    	int curY = 0;
        Random r = new Random();
        int size = r.nextInt(max) + 1;
        Ship ship = new Ship(size);
        ShipNode[] nodes = ship.nodes;
        int index = 0;
        ShipNode node = new ShipNode();
        nodes[index] = node;
        index++;
        while (index < size) {
            ShipNode nextNode = new ShipNode();
            int dir = r.nextInt(4);
            switch (dir) {
	            case 0:
	                curY--;
	                break;
	            case 1:
	                curX++;
	                break;
	            case 2:
	                curY++;
	                break;
	            case 3:
	                curX--;
	                break;
            }
            while (node.getPrevDir() == dir || curX < 0 || curY < 0) {
            	if(curX < 0)curX = 0;
            	else if(curY < 0)curY = 0;
                dir = r.nextInt(4);
                switch (dir) {
	                case 0:
	                    curY--;
	                    break;
	                case 1:
	                    curX++;
	                    break;
	                case 2:
	                    curY++;
	                    break;
	                case 3:
	                    curX--;
	                    break;
                }
            }
            curX = 0;
            curY = 0;
            node.addEdge(nextNode, dir);
            node = nextNode;
            nodes[index] = node;
            index++;
        }
        return ship;
    }

    public ShipNode getFirstNode() {
        return nodes[0];
    }

    public void print() {
        for (ShipNode node : nodes) {
            System.out.println("Prev: " + node.getPrevDir() + " next: " + node.getNextDir());
        }
    }
}
