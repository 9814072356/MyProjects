package battleship;

public class ShipNode {
    private int nextDir;
    private int prevDir;
    private ShipNode next;
    private ShipNode prev;

    public ShipNode() {
        nextDir = -1;
        prevDir = -1;
    }

    public void addEdge(ShipNode node, int dir) {
        next = node;
        node.prev = this;
        nextDir = dir;
        node.prevDir = (dir + 2) % 4;
    }

    public ShipNode getNext() {
        return next;
    }

    public ShipNode getPrev() {
        return prev;
    }

    public int getNextDir() {
        return nextDir;
    }

    public int getPrevDir() {
        return prevDir;
    }
    
}
