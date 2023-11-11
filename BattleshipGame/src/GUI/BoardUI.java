package GUI;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


public class BoardUI extends GridPane {
	static double BLOCK_SIZE;
	private static final int BLOCK_STROKE = 1;

	public static double getBlockSize(Scene scene) {
		BLOCK_SIZE = (scene.getHeight()*0.5208)/10;
		return BLOCK_SIZE;
	}
	
	public int getBlockStroke() {
		return BLOCK_STROKE;
	}

	//draw number column and alphabet row
	public static Group drawUI(double xPos, double yPos, int boardSize, double size) {
		Group LnN = new Group();
		
		//Draw alphabet row.
		double x = xPos;
		double y = yPos;
		for (int i = 0; i < boardSize; i++) {
			char c = (char)('A' + i);
			Text text = new Text(Character.toString(c));
			text.setTranslateX(x);
			text.setTranslateY(y);
			text.setFill(Color.BLACK);
			text.setFont(Font.font("Arial", size*60/100));
			text.setWrappingWidth(size);
			text.setTextAlignment(TextAlignment.CENTER);
			LnN.getChildren().add(text);
			x += size+BLOCK_STROKE;
		}
		x = xPos;
		y = yPos;
		//Draw number column.
		for (int i = 0; i < boardSize; i++) {
			String numberStr = i + "";
			Text text = new Text(numberStr);
			text.setTranslateX(x-size);
			text.setTranslateY(y+3*size/4);
			text.setFill(Color.BLACK);
			text.setFont(Font.font("Arial", size*60/100));
			text.setWrappingWidth(size);
			text.setTextAlignment(TextAlignment.RIGHT);
			LnN.getChildren().add(text);
			y += size+BLOCK_STROKE;
		}
		return LnN;
	}

	//draw board interface
	public static BoardUI drawBoard(Board board, Color stroke_color,double size) {
		BoardUI boardUI = new BoardUI();
		//Draw board cells.
		for(int row = 0; row < board.getDim(); row++) {
			for(int col = 0; col < board.getDim(); col++) {            	
				if (board.getCell(row, col) == BoardTag.EMPTY) {    
					Rectangle block = new Rectangle(0, 0, size, size);
					block.setFill(Color.LIGHTSKYBLUE);
					block.setStroke(stroke_color);
					block.setStrokeWidth(BLOCK_STROKE);                    
					boardUI.add(block, col, row);
				}
			}
		}
		return boardUI;
	}
}
