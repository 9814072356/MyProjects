package GUI;

public class Board {
    public BoardTag[][] cells; //Condition of every cell on the board(ship, wrecked, empty)
    public boolean[][] shots; // Shots taken
    private int dim;
    //Create an NxN board
    Board(int dim) {
        this.dim = dim;
        cells = new BoardTag[dim][dim];
        shots = new boolean[dim][dim];
        for(int row = 0; row < dim; row++) {
            for(int col = 0; col < dim; col++) {
                cells[row][col] = BoardTag.EMPTY;
                shots[row][col] = false;
            }
        }
    }

    public int getDim() {
        return dim;
    }
    
    public void setCell(int row, int col, BoardTag tag) {
    	cells[row][col] = tag;
    }

    public BoardTag getCell(int row, int col) {
        return cells[row][col];
    }

    public boolean getShot(int row, int col) {return shots[row][col];}
    
    public void setShot(int row, int col, boolean value) {this.shots[row][col] = value;}
}
