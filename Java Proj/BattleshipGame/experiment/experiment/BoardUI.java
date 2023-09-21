package experiment;

import battleship.Board;
import battleship.Tag;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class BoardUI {
    private static final int BLOCK_WIDTH = 25;
    private static final int BLOCK_HEIGHT = 25;

    public static GridPane drawBoard(Board board) {
        GridPane boardGrid = new GridPane();
        Rectangle block = null;
        int dim = 10;
        //Draw alphabet cells.
        for (int i = 0; i < dim; i++) {
            char c = (char)('A' + i);
            Text text = new Text(Character.toString(c));
            text.setFill(Color.BLACK);
            text.setFont(Font.font("Arial", 15));
            text.setWrappingWidth(BLOCK_WIDTH);
            text.setTextAlignment(TextAlignment.CENTER);
            boardGrid.add(text, i + 1, 0);
        }
        //Draw number cells.
        for (int i = 0; i < dim; i++) {
            String numberStr = i + "";
            Text text = new Text(numberStr);
            text.setFill(Color.BLACK);
            text.setFont(Font.font("Arial", 15));
            text.setWrappingWidth(BLOCK_WIDTH);
            text.setTextAlignment(TextAlignment.RIGHT);
            boardGrid.add(text, 0, i + 1);
        }
        //Draw board cells.
        for(int y = 0; y < dim; ++y) {
            for(int x = 0; x < dim; ++x) {
                if (board.getCell(x, y) == Tag.EMPTY) {
                    block = new Rectangle(0, 0, BLOCK_WIDTH, BLOCK_HEIGHT);
                    block.setFill(Color.LIGHTGREY);
                    block.setStroke(Color.BLACK);
                    block.setStrokeWidth(1.0D);
                }
                boardGrid.add(block, x + 1, y + 1);
            }
        }
        return boardGrid;
    }
}
