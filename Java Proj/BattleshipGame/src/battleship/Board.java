package battleship;

public class Board {
    private Tag[][] cells;
    private int dim;

    Board(int dim) {
        this.dim = dim;
        cells = new Tag[dim][dim];
        for(int x = 0; x < dim; x++) {
            for(int y = 0; y < dim; y++) {
                cells[x][y] = Tag.EMPTY;
            }
        }
    }

    public int getDim() {
        return dim;
    }

    public Tag getCell(int x, int y) {
        return cells[y][x];
    }
}
