package GUI;

import java.util.LinkedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Host_preGame {
	public static final int BOARD_DIM = 10;
	public static final int BLOCK_SIZE = 25;
	public static final int SHIP_BLOCK_SIZE = 10;
	private static final int SHIP_QUANTITY_5 = 1;
	private static final int SHIP_QUANTITY_4 = 2;
	private static final int SHIP_QUANTITY_3 = 3;
	private static final int SHIP_QUANTITY_2 = 4;
	//    private static final int SHIP_QUANTITY_1 = 2;
	private BoardUI boardUI;
	private static Board board;
	private Pane shipContainer;
	private Pane info;
	private static Group root;
	private Group LnN;
	private ShipUI shipUI;
	private Group ShipGroup;
	public static LinkedList<ShipUI> shipUIList;  //ship list --> game logic, determines which ship is still on the board
	private Text rules = new Text("* SHIPS MUST BE ONE BLOCK APART\n"+"* SHIP'S TOP LEFT CORNER MUST BE\n WITHIN A CELL\n"+"* PRESS R TO ROTATE SHIP\n"
			+"* SHIPS CAN ONLY BE ROTATED WHILE\n DRAGGING\n"+"* SHIPS CANNOT BE OVERLAPPED\n");
	private Text greet = new Text("Welcome to Battleship");
	private Button message = new Button("Click THIS to Continue");
	private int count = 0;

	public void set(Stage stage) {
		shipContainer = new Pane();
		info = new Pane();
		root = new Group();
		board = new Board(BOARD_DIM);
		shipUI = new ShipUI();
		ShipGroup = new Group();
		shipUIList = new LinkedList<>();

		boardUI = BoardUI.drawBoard(board, Color.web("#EAECEE"),25);
		boardUI.setTranslateX(15);
		boardUI.setTranslateY(15);
		LnN = new Group();
		LnN = BoardUI.drawUI(boardUI.getTranslateX(),boardUI.getTranslateY(),BOARD_DIM,BLOCK_SIZE);

		info.setPrefSize(275, 200);
		info.setStyle("-fx-border-style: solid inside;" +
				"-fx-border-width: 2;" +
				"-fx-border-radius: 5;" +
				"-fx-border-color: orange;");
		info.setTranslateX(5);
		info.setTranslateY(280);
		greet.setFont(Font.font("Impact", 28));
		greet.setFill(Color.web("#D35400"));
		greet.setTranslateX(8);
		greet.setTranslateY(30);
		rules.setFont(Font.font("Bell MT", 12));
		rules.setFill(Color.web("#4682B4"));
		rules.setTranslateX(32);
		rules.setTranslateY(50);
		message.setFont(Font.font("Impact",18));
		message.setStyle("-fx-border-color: transparent;"+
				"-fx-background-color: transparent;");
		message.setTextFill(Color.web("#4682B4"));
		message.setTranslateX(info.getPrefWidth()/2-82);
		message.setTranslateY(info.getPrefHeight()-60);
		info.getChildren().addAll(rules,greet,message);

		shipContainer.setTranslateX(boardUI.getTranslateX()+250+20);
		shipContainer.setTranslateY(boardUI.getTranslateY());
		shipContainer.setPrefSize(210,465);
		shipContainer.setStyle("-fx-border-style: solid inside;" +
				"-fx-border-width: 2;" +
				"-fx-border-radius: 5;" +
				"-fx-border-color: orange;");

		//Position of ships in container
		double tileX = shipContainer.getTranslateX();
		double tileY = shipContainer.getTranslateY();
		double lastCol = tileX + shipContainer.getPrefWidth();

		//Making ships
		for (int i = 0; i < SHIP_QUANTITY_5; i++) {
			ShipUI ship = shipUI.createShip(5,true,SHIP_BLOCK_SIZE);
			if(tileX >= lastCol) {
				tileX = shipContainer.getTranslateX();
				tileY += shipContainer.getPrefHeight()/5;
			}

			ship.setTranslateX(tileX + shipContainer.getPrefWidth()/8);
			ship.setTranslateY(tileY + shipContainer.getPrefHeight()/25);


			ShipGroup.getChildren().add(ship);
			tileX += shipContainer.getPrefWidth()/2;
			ship.addDragAndDrop(stage.getScene(), getRoot(), board,boardUI,shipContainer,shipContainer.getTranslateX(),shipContainer.getTranslateY());
			shipUIList.add(ship);
		}
		for (int i = 0; i < SHIP_QUANTITY_4; i++) {
			ShipUI ship = shipUI.createShip(4,true,SHIP_BLOCK_SIZE);
			if(tileX >= lastCol) {
				tileX = shipContainer.getTranslateX();
				tileY += shipContainer.getPrefHeight()/5;
			}

			ship.setTranslateX(tileX + shipContainer.getPrefWidth()/8);
			ship.setTranslateY(tileY + shipContainer.getPrefHeight()/25);


			ShipGroup.getChildren().add(ship);
			tileX += shipContainer.getPrefWidth()/2;
			ship.addDragAndDrop(stage.getScene(), getRoot(), board,boardUI,shipContainer,shipContainer.getTranslateX(),shipContainer.getTranslateY());
			shipUIList.add(ship);
		}
		for (int i = 0; i < SHIP_QUANTITY_3; i++) {
			ShipUI ship = shipUI.createShip(3,true,SHIP_BLOCK_SIZE);
			if(tileX >= lastCol) {
				tileX = shipContainer.getTranslateX();
				tileY += shipContainer.getPrefHeight()/5;
			}

			ship.setTranslateX(tileX + shipContainer.getPrefWidth()/8);
			ship.setTranslateY(tileY + shipContainer.getPrefHeight()/25);


			ShipGroup.getChildren().add(ship);
			tileX += shipContainer.getPrefWidth()/2;
			ship.addDragAndDrop(stage.getScene(), getRoot(), board,boardUI,shipContainer,shipContainer.getTranslateX(),shipContainer.getTranslateY());
			shipUIList.add(ship);
		}
		for (int i = 0; i < SHIP_QUANTITY_2; i++) {
			ShipUI ship = shipUI.createShip(2,true,SHIP_BLOCK_SIZE);
			if(tileX >= lastCol) {
				tileX = shipContainer.getTranslateX();
				tileY += shipContainer.getPrefHeight()/5;
			}

			ship.setTranslateX(tileX + shipContainer.getPrefWidth()/8);
			ship.setTranslateY(tileY + shipContainer.getPrefHeight()/25);


			ShipGroup.getChildren().add(ship);
			tileX += shipContainer.getPrefWidth()/2;
			ship.addDragAndDrop(stage.getScene(), getRoot(), board,boardUI,shipContainer,shipContainer.getTranslateX(),shipContainer.getTranslateY());
			shipUIList.add(ship);
		}

		root.getChildren().addAll(boardUI,info,LnN,shipContainer,ShipGroup);

		changeScreen(stage);
	}

	//Change to next screen
	public void changeScreen(Stage stage) {
		message.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				int counter = 0;
				for(int row = 0; row < BOARD_DIM; row++) {
					for(int col = 0; col < BOARD_DIM; col++) {
						if(board.getCell(row, col) == BoardTag.BRIG)counter++;
					}
				}

				//Check if every ship is on the board
				if (counter == SHIP_QUANTITY_5 * 5 + SHIP_QUANTITY_4 * 4 + SHIP_QUANTITY_3 * 3 + SHIP_QUANTITY_2 * 2||true){
					//update the state of every cell of the host board in the in game window based on the state of the board
					//in pre game
					Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
					double screenWidth = primaryScreenBounds.getWidth()/2;
					double screenHeight = 3*primaryScreenBounds.getHeight()/5;
					System.out.println(""+screenWidth+" "+screenHeight);

					LinearGradient cycleGrad = new LinearGradient(100, // start X
							100, // start Y
							120, // end X
							120, // end Y
							false, // proportional
							CycleMethod.REFLECT, // cycleMethod
							new Stop(0f, Color.web("#FFFFFF" )), new Stop(1.0f, Color.web("#F4FFFF")));
					Host hostUI = new Host();
					String shipString = "";
					for (int i = 0; i < 10; i++){
						ShipUI ship = shipUIList.get(i);
						double tempX, tempY;
						if(!ship.getDir()) {
							tempX = ship.getTranslateX()-((ship.getSize()*ship.getBlockResize())/2-ship.getBlockResize()/2);
							tempY = ship.getTranslateY()+((ship.getSize()*ship.getBlockResize())/2-ship.getBlockResize()/2);
						}else {
							tempX = ship.getTranslateX();
							tempY = ship.getTranslateY();
						}
						if (tempX > boardUI.getTranslateX()
								&& tempY > boardUI.getTranslateY()
								&& tempX < boardUI.getTranslateX()+Host_preGame.BOARD_DIM*ship.getBlockResize()
								&& tempY < boardUI.getTranslateY()+Host_preGame.BOARD_DIM*ship.getBlockResize()) {
							String x = ship.toString();
							shipString += x;
						}
					}
					Scene scene6 = new Scene(hostUI.getRoot(), screenWidth, screenHeight);
					hostUI.init(scene6,stage);
					scene6.setFill(cycleGrad);
					hostUI.sendShipsToServer(shipString);
					for(int row = 0; row < BOARD_DIM; row++) {
						for(int col = 0; col < BOARD_DIM; col++) {
							if(board.getCell(row, col) == BoardTag.BRIG) {
								hostUI.getallieBoard().setCell(row, col, BoardTag.BRIG);
								count++;
								((Rectangle)hostUI.getallieBoardUI().getChildren().get(row*BOARD_DIM+col)).setFill(Color.web("#F5B041"));
							}else {
								hostUI.getallieBoard().setCell(row, col, BoardTag.EMPTY);
								((Rectangle)hostUI.getallieBoardUI().getChildren().get(row*BOARD_DIM+col)).setFill(Color.LIGHTSKYBLUE);
							}
						}
					}
					hostUI.setShipBlocksOnBoardA(count);
					stage.setScene(scene6);
					stage.setFullScreen(false);
				}else {
					message.setTextFill(Color.web("#DC143C"));
					message.setTranslateX(0);
					message.setText("All ships must be on the board"+"\n"+"(Click)");
				}
			}

		});
	}

	public static Board getBoard() {
		return board;
	}

	public static LinkedList<ShipUI> getShipList(){
		return shipUIList;
	}

	public static Group getRoot() {
		return root;
	}

	public static int getBlockSize() {
		return BLOCK_SIZE;
	}
}
