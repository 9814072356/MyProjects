package battleship;

import javafx.geometry.Pos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;

import java.util.LinkedList;

public class GameUI {
    private static final int BOARD_DIM = 10;
    private static final int SHIP_QUANTITY = 10;
    private static final int BOARD_SPACING = 50;
    private static final int MAX_SHIP_SIZE = 6; //Length of a ship
    private Board allieBoard;
    private Board enemyBoard;
    private BoardUI allieBoardUI;
    private BoardUI enemyBoardUI;
    private GridPane root;
    private HBox boardContainer;
    static Pane leftShipContainer;
    private Pane rightShipContainer;
    private LinkedList<ShipUI> shipUIList;

	public void init() {
        allieBoard = new Board(BOARD_DIM);
        enemyBoard = new Board(BOARD_DIM);
        shipUIList = new LinkedList<>();
        root = new GridPane();
        boardContainer = new HBox();
        leftShipContainer = new Pane();
        leftShipContainer.setMaxSize(200, 300);
        leftShipContainer.setMinSize(200, 300);
        leftShipContainer.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: orange;");
//        for(int i = 0; i < 5; i++) {
//        	RowConstraints row = new RowConstraints();
//        	row.setPrefHeight(leftShipContainer.getMaxHeight()/5);
//        	leftShipContainer.getRowConstraints().add(row);
//        }
//        for(int j = 0; j < 2; j++) {
//    		ColumnConstraints col = new ColumnConstraints();
//    		col.setPrefWidth(leftShipContainer.getPrefWidth()/2-10);
//    		leftShipContainer.getColumnConstraints().add(col);
//    	}
//        leftShipContainer.setVgap(5);
        int tileX = 0;
        int tileY = 0;
        rightShipContainer = new Pane();
        rightShipContainer.setMaxSize(200, 300);
        rightShipContainer.setMinSize(200, 300);
        rightShipContainer.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: orange;");
        for (int i = 0; i < SHIP_QUANTITY; i++) {
            Ship ship = Ship.createShip(MAX_SHIP_SIZE);
            ShipUI shipUI = ShipUI.drawShip(ship, 0, 0);
            Pane tile = new Pane();
        	tile.setMaxSize(leftShipContainer.getMaxWidth()/2, leftShipContainer.getMaxHeight()/5);
        	tile.setMinSize(leftShipContainer.getMaxWidth()/2, leftShipContainer.getMaxHeight()/5);
        	tile.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: orange;");
        	if(tileX >= leftShipContainer.getMaxWidth()) {
        		tileX = 0;
        		tileY += leftShipContainer.getMaxHeight()/5;
        	}
        	tile.setTranslateX(tileX);
        	tile.setTranslateY(tileY);
        	tile.getChildren().add(shipUI);
            leftShipContainer.getChildren().add(shipUI);
        	tileX += leftShipContainer.getMaxWidth()/2;
//            Pane pane = new Pane();
//            pane.getChildren().add(shipUI);
//            shipUI.setStyle("-fx-border-width: 2;" + "-fx-border-color: orange;");
            shipUI.addDragAndDrop();
//            if(col > 1) {
//            	col = 0;
//            	row ++;
//            }
            
//            col ++;
            shipUIList.add(shipUI);
        }
        allieBoardUI = BoardUI.drawBoard(allieBoard);
        enemyBoardUI = BoardUI.drawBoard(enemyBoard);
        boardContainer.getChildren().addAll(allieBoardUI, enemyBoardUI);
        boardContainer.setSpacing(BOARD_SPACING);
        root.add(leftShipContainer, 0, 0);
        root.add(boardContainer, 1, 0);
        root.add(rightShipContainer, 2, 0);
        root.setHgap(20);
        root.setAlignment(Pos.CENTER);
        boardContainer.toBack();

    }

	public GridPane getRoot() {
		return root;
	}
}
