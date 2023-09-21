package battleship;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.LinkedList;

public class BoardUI extends GridPane {
    private LinkedList<Rectangle> blocks;
    private static final int BLOCK_WIDTH = 25;
    private static final int BLOCK_HEIGHT = 25;
    private static final int BLOCK_STROKE = 1;

    public BoardUI() {
        blocks = new LinkedList<>();
    }

    public static int getBlockWidth() {
    	return BLOCK_WIDTH;
    }
    
    public static int getBlockHeight() {
    	return BLOCK_HEIGHT;
    }
    
    public static BoardUI drawBoard(Board board) {
        BoardUI boardUI = new BoardUI();
        Rectangle block = null;
        //Draw alphabet cells.
        for (int i = 0; i < board.getDim(); i++) {
            char c = (char)('A' + i);
            Text text = new Text(Character.toString(c));
            text.setFill(Color.BLACK);
            text.setFont(Font.font("Arial", 15));
            text.setWrappingWidth(BLOCK_WIDTH);
            text.setTextAlignment(TextAlignment.CENTER);
            boardUI.add(text, i + 1, 0);
        }
        //Draw number cells.
        for (int i = 0; i < board.getDim(); i++) {
            String numberStr = i + "";
            Text text = new Text(numberStr);
            text.setFill(Color.BLACK);
            text.setFont(Font.font("Arial", 15));
            text.setWrappingWidth(BLOCK_WIDTH);
            text.setTextAlignment(TextAlignment.RIGHT);
            boardUI.add(text, 0, i + 1);
        }
        //Draw board cells.
        for(int y = 0; y < board.getDim(); y++) {
            for(int x = 0; x < board.getDim(); x++) {
                if (board.getCell(x, y) == Tag.EMPTY) {
                    block = new Rectangle(0, 0, BLOCK_WIDTH, BLOCK_HEIGHT);
                    block.setFill(Color.DEEPSKYBLUE);
                    block.setStroke(Color.BLACK);
                    block.setStrokeWidth(BLOCK_STROKE);
                    boardUI.add(block, y + 1, x + 1);
                    boardUI.blocks.add((block));
                }
            }
        }
        return boardUI;
    }

}
