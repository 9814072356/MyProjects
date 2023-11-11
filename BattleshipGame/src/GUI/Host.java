package GUI;

// GUIImports
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

// ClientImports
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;

public class Host {
	// Client Environment
	private static Socket client;
	private static PrintWriter writer;
	private static BufferedReader reader;
	private static String output;
	// --------------------

	public static final int BOARD_DIM = 10;
	private Board allieBoard = new Board(BOARD_DIM);
	private Board enemyBoard = new Board(BOARD_DIM);
	private BoardUI allieBoardUI;
	private BoardUI enemyBoardUI;
	//    private GUI.ShipUI shipUI = new GUI.ShipUI();
	private Group root = new Group();
	private static Pane leftShipContainer = new Pane();
	private static GridPane rightShipContainer = new GridPane();
	public LinkedList<ShipUI> shipUIList = new LinkedList<>();
	private static TextArea chatBox = new TextArea();
	private TextField input = new TextField();
	private Group buttons = new Group();
	private Button nextBTN = new Button("EXIT -->");
	private Group LnNA = new Group();
	private Group LnNB = new Group();
	private static Text HostName = new Text("Name here");
	private static Text GuestName = new Text("Name here");
	private Text greet = new Text("THE WAR BEGINS");
	private Text greet2 = new Text("GOOD LUCK");
	private Text message = new Text("Take Down the Enemy Fleet"+"\n"+"    Before They Take YOURS");
	Button btn1 = new Button("Thanks");
	Button btn2 = new Button("Good game");
	Button btn3 = new Button("Good luck");
	Button btn4 = new Button("Well played");
	private String shipsString = "";
	private Group newShipGroup = new Group();
	private int counter = 0;
	private int shipBlocksOnBoardA = 0;
	private int shipBlocksOnBoardE = 0;
	public int sunkShipParts = 0;
	int opponentTurn; 

	public Host() {		
		root.getChildren().addAll(leftShipContainer, rightShipContainer,chatBox,input,nextBTN,HostName,GuestName, newShipGroup);
	}

	public void init(Scene scene, Stage stage) {

		Thread t = new Thread(new MessagesFromServerListener());


		//chatbox --> network implementation
		chatBox.setPrefSize(scene.getWidth()*0.26041, scene.getHeight()*0.78883);
		chatBox.setTranslateX(scene.getWidth()-chatBox.getPrefWidth()-2);
		chatBox.setTranslateY(0);
		chatBox.setEditable(false);
		chatBox.setStyle("-fx-border-style: solid inside;" +
				"-fx-border-width: 2;" +
				"-fx-border-radius: 5;" +
				"-fx-text-fill: #2D6580;" +
				"-fx-background-color: transparent;" +
				"-fx-focus-color: transparent;" +
				"-fx-faint-focus-color: transparent;" +
				"-fx-border-color: #48CCCD;");
		chatBox.setFont(Font.font("Titillium Web", chatBox.getPrefWidth()*5/100));

		input.setPrefSize(chatBox.getPrefWidth(), scene.getHeight()*0.0606796);
		input.setTranslateX(chatBox.getTranslateX());
		input.setTranslateY(chatBox.getTranslateY()+chatBox.getPrefHeight()+2);
		input.setStyle("-fx-border-width: 2;" +
				"-fx-border-radius: 5;" +
				"-fx-focus-color: transparent;" +
				"-fx-faint-focus-color: transparent;" +
				"-fx-border-color: #48CCCD;");
		input.setFont(Font.font("Titillium Web", input.getPrefHeight()*40/100));
		input.addEventHandler(KeyEvent.KEY_PRESSED,new EventHandler<KeyEvent>(){

			@Override
			public void handle(KeyEvent key) {
				if(key.getCode() == KeyCode.ENTER) {
					//					String message = isHost ? "Player 1: " : "Player 2: ";
					output = (getHostName().getText() + ": "  + input.getText());
					sendMessageToServer();
					input.clear();
				}

			}

		});


		btn1.setPrefSize(input.getPrefWidth()/2, (scene.getHeight()-(input.getTranslateY()+input.getPrefHeight()))/3);
		btn1.setTranslateX(input.getTranslateX());
		btn1.setTranslateY(input.getTranslateY()+input.getPrefHeight());
		btn1.setStyle("-fx-background-color: #FEFCFF;"+
				"-fx-border-width: 2;"+
				"-fx-border-radius: 5;" +
				"-fx-border-color: #4285F4;");
		btn1.setFont(Font.font("Titillium Web", btn1.getPrefHeight()*38/100));
		btn1.setTextFill(Color.web("#4285F4"));

		btn2.setPrefSize(input.getPrefWidth()/2, btn1.getPrefHeight());
		btn2.setTranslateX(input.getTranslateX()+input.getPrefWidth()/2);
		btn2.setTranslateY(btn1.getTranslateY());
		btn2.setStyle("-fx-background-color: #FEFCFF;"+
				"-fx-border-width: 2;"+
				"-fx-border-radius: 5;" +
				"-fx-border-color: #DB4437;");
		btn2.setFont(Font.font("Titillium Web", btn1.getFont().getSize()));
		btn2.setTextFill(Color.web("#DB4437"));

		btn3.setPrefSize(input.getPrefWidth()/2, btn1.getPrefHeight());
		btn3.setTranslateX(btn1.getTranslateX());
		btn3.setTranslateY(btn1.getTranslateY()+btn1.getPrefHeight());
		btn3.setStyle("-fx-background-color: #FEFCFF;"+
				"-fx-border-width: 2;"+
				"-fx-border-radius: 5;" +
				"-fx-border-color: #F4B400;");
		btn3.setFont(Font.font("Titillium Web", btn1.getFont().getSize()));
		btn3.setTextFill(Color.web("#F4B400"));

		btn4.setPrefSize(input.getPrefWidth()/2, btn1.getPrefHeight());
		btn4.setTranslateX(btn2.getTranslateX());
		btn4.setTranslateY(btn2.getTranslateY()+btn2.getPrefHeight());
		btn4.setStyle("-fx-background-color: #FEFCFF;"+
				"-fx-border-width: 2;"+
				"-fx-border-radius: 5;" +
				"-fx-border-color: #0F9D58;");
		btn4.setFont(Font.font("Titillium Web", btn1.getFont().getSize()));
		btn4.setTextFill(Color.web("#0F9D58"));

		nextBTN.setPrefSize(btn1.getPrefWidth()*2, btn1.getPrefHeight());
		nextBTN.setTranslateX(btn1.getTranslateX());
		nextBTN.setTranslateY(btn3.getTranslateY()+btn3.getPrefHeight());
		nextBTN.setStyle("-fx-background-color: #43BFC7;" + "-fx-border-radius: 5;");
		nextBTN.setFont(Font.font("Titillium Web", btn1.getFont().getSize()));
		nextBTN.setTextFill(Color.WHITE);
		nextBTN.setOnAction(e -> System.exit(0));

		buttons.getChildren().addAll(btn1,btn2,btn3,btn4);
		for(int x = 0; x < 4; x++) {
			((Button)buttons.getChildren().get(x)).setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent a) {
					String text = ((Button)a.getSource()).getText();
					output = (getHostName().getText() + ": "  + text);
					sendMessageToServer();
					input.clear();
				}

			});
		}


		//Boards --> game logic implementation
		//Creating board on the left
		allieBoardUI = BoardUI.drawBoard(allieBoard, Color.web("#EAECEE"),BoardUI.getBlockSize(scene));
		allieBoardUI.setTranslateX((chatBox.getTranslateX()/2 - BoardUI.getBlockSize(scene)*BOARD_DIM)/2 );
		allieBoardUI.setTranslateY(scene.getHeight()*0.07767/* 64 */);
		LnNA = BoardUI.drawUI(allieBoardUI.getTranslateX(),allieBoardUI.getTranslateY(),BOARD_DIM,BoardUI.getBlockSize(scene));

		//Creating board on the right
		enemyBoardUI = BoardUI.drawBoard(enemyBoard, Color.web("#EAECEE"),BoardUI.getBlockSize(scene));
		//		/* String temp = */enemyBoardUI.checkShot(BOARD_DIM, enemyBoard);
		//System.out.println(temp);
		//sendCoordinatesToServer(temp);
		enemyBoardUI.setTranslateX(chatBox.getTranslateX()/2 + (chatBox.getTranslateX()/2 - BoardUI.getBlockSize(scene)*BOARD_DIM)/2);
		enemyBoardUI.setTranslateY(allieBoardUI.getTranslateY());
		LnNB = BoardUI.drawUI(enemyBoardUI.getTranslateX(),enemyBoardUI.getTranslateY(),BOARD_DIM,BoardUI.getBlockSize(scene));
		for(int row = 0; row < BOARD_DIM; row++) {
			for(int col = 0; col < BOARD_DIM; col++) {
				Rectangle block = (Rectangle)enemyBoardUI.getChildren().get(row*BOARD_DIM+col);
				int ROW = row;
				int COL = col;
				block.setDisable(true);
				
				block.setOnMouseClicked(mouseEvent -> {
					if(enemyBoard.getCell(ROW, COL) == BoardTag.BRIG)counter++;
					output = ""+ROW+COL;
					sendCoordinatesToServer("guest");
					
				});
			}
		}

		//UI stuffs, not important
		HostName.setFont(Font.font("Copperplate Gothic Light", scene.getHeight()*0.0485));
		HostName.setFill(Color.web("#D35400"));
		HostName.setTranslateX(allieBoardUI.getTranslateX());
		HostName.setTranslateY(scene.getHeight()*0.048543);

		GuestName.setFont(Font.font("Copperplate Gothic Light", HostName.getFont().getSize()));
		GuestName.setFill(Color.web("#D35400"));
		GuestName.setTranslateX(enemyBoardUI.getTranslateX());
		GuestName.setTranslateY(scene.getHeight()*0.048543);

		//UI stuffs again
		leftShipContainer.setPrefSize(chatBox.getTranslateX()/2-3, scene.getHeight()-(allieBoardUI.getTranslateY()+BoardUI.getBlockSize(scene)*BOARD_DIM+15));
		leftShipContainer.setStyle("-fx-border-style: solid inside;" +
				"-fx-border-width: 2;" +
				"-fx-border-radius: 10;" +
				"-fx-border-color: #FBB917;");
		leftShipContainer.setTranslateX(0);
		leftShipContainer.setTranslateY(allieBoardUI.getTranslateY()+BoardUI.getBlockSize(scene)*BOARD_DIM+15);
		greet.setFont(Font.font("Impact", leftShipContainer.getPrefHeight()/6));
		final double greetWidth = greet.getLayoutBounds().getWidth();
		greet.setFill(Color.web("#b80f0a"));
		greet.setTranslateX(leftShipContainer.getTranslateX()+(leftShipContainer.getPrefWidth()-greetWidth)/2);
		greet.setTranslateY(greet.getFont().getSize()+2);
		greet2.setFont(Font.font("Titillium Web", greet.getFont().getSize()*80/100));
		final double greet2Width = greet2.getLayoutBounds().getWidth();
		greet2.setFill(Color.web("#b80f0a"));
		greet2.setTranslateX(leftShipContainer.getTranslateX()+(leftShipContainer.getPrefWidth()-greet2Width)/2);
		greet2.setTranslateY(leftShipContainer.getPrefHeight()-5);
		message.setFont(Font.font("Bell MT",greet.getFont().getSize()*60/100));
		final double messageWidth = message.getLayoutBounds().getWidth();
		message.setFill(Color.web("#4682B4"));
		message.setTranslateX(leftShipContainer.getTranslateX()+(leftShipContainer.getPrefWidth()-messageWidth)/2);
		message.setTranslateY(leftShipContainer.getPrefHeight()/2-7);
		leftShipContainer.getChildren().addAll(greet, greet2, message);



		rightShipContainer.setPrefSize(leftShipContainer.getPrefWidth(), leftShipContainer.getPrefHeight());
		rightShipContainer.setStyle("-fx-border-style: solid inside;" +
				"-fx-border-width: 2;" +
				"-fx-border-radius: 10;" +
				"-fx-border-color: #FBB917;");
		rightShipContainer.setTranslateX(leftShipContainer.getTranslateX()+leftShipContainer.getPrefWidth()+2);
		rightShipContainer.setTranslateY(leftShipContainer.getTranslateY());
		System.out.println(""+rightShipContainer.getPrefHeight()+"|"+rightShipContainer.getPrefWidth());
		int x = 25;
		int y = 13;
		for(int i = 0; i < y; i++) {
			for(int j = 0; j < x; j++) {
				Rectangle shipBlock = new Rectangle(rightShipContainer.getPrefWidth()*0.03571,rightShipContainer.getPrefWidth()*0.03571);
				shipBlock.setFill(Color.TRANSPARENT);
				rightShipContainer.add(shipBlock, j, i);
			}
		}
		// send PlayerName
		sendPlayerNameToServer();
		t.start();
		root.getChildren().addAll(allieBoardUI, enemyBoardUI,buttons,LnNA,LnNB);
	}

	public static Text getHostName() {
		return HostName;
	}

	public static Text getGuestName() {
		return GuestName;
	}

	public static Pane getLeftContainer() {
		return leftShipContainer;
	}

	public static Pane getRightContainer() {
		return rightShipContainer;
	}

	public BoardUI getallieBoardUI(){
		return allieBoardUI;
	}

	public BoardUI getenemyBoardUI(){
		return enemyBoardUI;
	}

	public Board getallieBoard() {
		return allieBoard;
	}

	public Board getenemyBoard() {
		return enemyBoard;
	}

	public Button getNextBTN() {
		return nextBTN;
	}

	public Group getRoot() {
		return root;
	}

	public static void appendMessageToChatBox(String message)
	{
		chatBox.appendText(message);
	}

	public String getInput()
	{
		return input.getText();
	}

	public String getShipsString() {
		return shipsString;
	}

	public void updateShipString(String s) {
		this.shipsString = s;
	}
	
	public void setShipBlocksOnBoardA(int i) {
		shipBlocksOnBoardA = i;
	}

	public static boolean connectToServer() {
		try {

			client = new Socket("localhost", 9999);
			System.out.println("client accepted");
			reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
			writer = new PrintWriter(client.getOutputStream());
			appendTextMessages("Netzwerkverbindung hergestellt");
			return true;
		} catch (Exception e) {
			appendTextMessages("Netzwerkverbindung konnte nicht hergestellt werden");
			return false;
		}
	}

	public static void appendTextMessages(String message) {
		appendMessageToChatBox(message + "\n");
	}

	public void sendMessageToServer() {
		String EncodedOutput;
//		System.out.println("ToServer: " + output);
		EncodedOutput = ("message" + output);
		writer.println(EncodedOutput);
		writer.flush();
	}


	public class MessagesFromServerListener implements Runnable {

		@Override
		public void run() {
			String ServerInput;
			String DecodedInput;
			try {
				while((reader) != null) {
					ServerInput = reader.readLine();
//					System.out.println("Guest_INPUT" + ServerInput);
					DecodedInput = ServerInput;
					// Case If INPUT is a message

					if(DecodedInput.contains("message")) {
						if(DecodedInput.substring(9).contains("host") || DecodedInput.substring(9).contains("guest")) {
							int row = Integer.parseInt(ServerInput.substring(7,8));
							int col = Integer.parseInt(ServerInput.substring(8,9));
							if(DecodedInput.contains("guest")) {
								if(enemyBoard.getCell(row, col) == BoardTag.BRIG) {
									((Rectangle)enemyBoardUI.getChildren().get(row*BOARD_DIM+col)).setFill(Color.web("#C11B17"));
									enemyBoard.setCell(row, col, BoardTag.WRECKED);
									if(counter == shipBlocksOnBoardE) {
										chatBox.setStyle("-fx-text-fill: #DC381F");
										appendTextMessages("\n< GAME ENDS >\n"+ "CONGRATULATIONS YOU WIN!!!");
										for(int y = 0; y < BOARD_DIM; y++) {
											for(int x = 0; x < BOARD_DIM; x++) {
												Rectangle block = (Rectangle)enemyBoardUI.getChildren().get(y*BOARD_DIM+x);
												block.setDisable(true);
											}
										}

									}else {
										for(int x = 0; x < BOARD_DIM; x++) {
											for(int y = 0; y < BOARD_DIM; y++) {
												Rectangle block = (Rectangle)enemyBoardUI.getChildren().get(y*BOARD_DIM+x);
												block.setDisable(false);
											}
										}
										appendTextMessages(getHostName().getText()+"'s turn");
									}
								}else if(enemyBoard.getCell(row, col) == BoardTag.EMPTY){
									((Rectangle)enemyBoardUI.getChildren().get(row*BOARD_DIM+col)).setFill(Color.web("#2B65EC"));
									for(int x = 0; x < BOARD_DIM; x++) {
										for(int y = 0; y < BOARD_DIM; y++) {
											Rectangle block = (Rectangle)enemyBoardUI.getChildren().get(y*BOARD_DIM+x);
											block.setDisable(true);
										}
									}
									appendTextMessages(getGuestName().getText()+"'s turn");
								}
							}else if(DecodedInput.contains("host")){
								char c = (char) ('A'+col);
								if(allieBoard.getCell(row, col) == BoardTag.BRIG) {
									((Rectangle)allieBoardUI.getChildren().get(row*BOARD_DIM+col)).setFill(Color.web("#C11B17"));
									appendTextMessages(getGuestName().getText() + " hit your ship at " + c + row);
									allieBoard.setCell(row, col, BoardTag.WRECKED);
									sunkShipParts++;
									if(sunkShipParts == shipBlocksOnBoardA) {
										chatBox.setStyle("-fx-text-fill: #DC381F");
										appendTextMessages("\n< GAME ENDS >\n"+ getGuestName().getText() + " WINS\n"+"BETTER LUCK NEXT TIME ^.^");
										for(int y = 0; y < BOARD_DIM; y++) {
											for(int x = 0; x < BOARD_DIM; x++) {
												Rectangle block = (Rectangle)enemyBoardUI.getChildren().get(y*BOARD_DIM+x);
												block.setDisable(true);
											}
										}

									}else {
										for(int x = 0; x < BOARD_DIM; x++) {
											for(int y = 0; y < BOARD_DIM; y++) {
												Rectangle block = (Rectangle)enemyBoardUI.getChildren().get(y*BOARD_DIM+x);
												block.setDisable(true);
											}
										}
										appendTextMessages(getGuestName().getText()+"'s turn");
									}
								}else if(allieBoard.getCell(row, col) == BoardTag.EMPTY){
									((Rectangle)allieBoardUI.getChildren().get(row*BOARD_DIM+col)).setFill(Color.web("#2B65EC"));
									appendTextMessages(getGuestName().getText() + " missed your ship at " + c + row);
									for(int x = 0; x < BOARD_DIM; x++) {
										for(int y = 0; y < BOARD_DIM; y++) {
											Rectangle block = (Rectangle)enemyBoardUI.getChildren().get(y*BOARD_DIM+x);
											block.setDisable(false);
										}
									}
									appendTextMessages(getHostName().getText()+"'s turn");
								}
							}
						}else {
							appendTextMessages(ServerInput.substring(7));
						}
						// Case if INPUT is a gamelogic info
					}else if(DecodedInput.contains("gamelog")){
						if(DecodedInput.contains("gname")) {
							getGuestName().setText(ServerInput.substring(13));
							opponentTurn = Integer.parseInt(DecodedInput.substring(12,13));
							if(opponentTurn == 1) {
								for(int row = 0; row < BOARD_DIM; row++) {
									for(int col = 0; col < BOARD_DIM; col++) {
										Rectangle block = (Rectangle)enemyBoardUI.getChildren().get(row*BOARD_DIM+col);
										block.setDisable(true);
									}
								}
								appendTextMessages(getGuestName().getText() + "'s turn");
							}else {
								for(int row = 0; row < BOARD_DIM; row++) {
									for(int col = 0; col < BOARD_DIM; col++) {
										Rectangle block = (Rectangle)enemyBoardUI.getChildren().get(row*BOARD_DIM+col);
										block.setDisable(false);
									}
								}
								appendTextMessages(getHostName().getText() + "'s turn");
							}
						}else if(DecodedInput.substring(7).contains("gship")) {
							char[] ships = DecodedInput.substring(12).toCharArray();
							updateShipString(DecodedInput.substring(12));
							int col = 1;
							int row = 1;
							for(int i = 0; i < ships.length; i += 4) {
//								System.out.println(ships[i]+ships[i+1]+ships[i+2]+ships[i+3]);
								int length = ships[i]-'0';
								int startX = ships[i+1]-'0';
								int startY = ships[i+2]-'0';
								boolean vertical = ships[i+3]-'0' == 1 ? true : false;								
								if(vertical) {
									for(int j = 0; j < length; j++) {
										enemyBoard.setCell(startY+j, startX, BoardTag.BRIG);
										shipBlocksOnBoardE++;
										((Rectangle)rightShipContainer.getChildren().get((row+j)*25+col)).setFill(Color.RED);
										((Rectangle)rightShipContainer.getChildren().get((row+j)*25+col)).setStroke(Color.WHITE);
										((Rectangle)rightShipContainer.getChildren().get((row+j)*25+col)).setStrokeWidth(1); 
									}
								}else {
									for(int j = 0; j < length; j++) {
										enemyBoard.setCell(startY, startX+j, BoardTag.BRIG);
										shipBlocksOnBoardE++;
										col++;
										((Rectangle)rightShipContainer.getChildren().get((row)*25+col)).setFill(Color.RED);
										((Rectangle)rightShipContainer.getChildren().get((row)*25+col)).setStroke(Color.WHITE);
										((Rectangle)rightShipContainer.getChildren().get((row)*25+col)).setStrokeWidth(1); 
									}
								}
								col += 2;
								if(25-col < 5) {
									row = 8;
									col = 1;
								}
							}
						}
					}
				}
			} catch (IOException e) {
				appendTextMessages("Nachricht konnte nicht empfangen werden!");
				e.printStackTrace();
			}
		}
	}

	public static void sendPlayerNameToServer() {
		String hostname = getHostName().getText();
//		System.out.println("GuestName:" + hostname);
		String EncodedName;
		System.out.println("ToServer: " + hostname);
		EncodedName = ("gameloghname" + hostname);
		writer.println(EncodedName);
		writer.flush();
	}

	public static void sendCoordinatesToServer(String SendTo) {
		// Format yx
		String encodedcoordinates;
//		System.out.println("ToServer: " + output + SendTo);
		encodedcoordinates = ("message" + output + SendTo);
		writer.println(encodedcoordinates);
		writer.flush();
	}

	public void sendShipsToServer(String temp) {
		String ships = temp;
		String encodedShips;
//		System.out.println("ToServer: " + ships);
		encodedShips = ("gameloghship" + ships);
		writer.println(encodedShips);
		writer.flush();
	}
}
