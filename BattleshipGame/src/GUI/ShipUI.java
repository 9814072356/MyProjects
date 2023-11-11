package GUI;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ShipUI extends GridPane {
	private int size;
	private static final int BLOCK_SIZE = 10;
	private static final int BLOCK_STROKE = 1;
	private int ALTERNATIVE_SIZE = Host_preGame.getBlockSize();
	private boolean isVertical;
	int prevCol = -1;
	int prevRow = -1;
	int col;   //x-coordinate of ship on board
	int row;   //y-coordinate of ship on board
	double tempX,tempY;
	String prevDir = "unchanged";
	int mousereleaseAfterRotate = 0;

	private static Rectangle drawShipBlock(int blockSize) {
		Rectangle block = new Rectangle(0, 0, blockSize, blockSize);
		block.setFill(Color.RED);
		block.setStroke(Color.BLACK);
		block.setStrokeWidth(BLOCK_STROKE);
		return block;
	}

	public int getSize() {
		return size;
	}

	public boolean getDir() {
		return isVertical;
	}

	public int getBlockResize() {
		return ALTERNATIVE_SIZE;
	}

	public void setBlockResize(int value) {
		this.ALTERNATIVE_SIZE = value;
	}

	public static int getBlockSize() {
		return BLOCK_SIZE;
	}

	//Attach multiple square to make a ship, whether horizontal or vertical
	public void addShipBlocks(ShipUI shipUI, int size, boolean isVertical,int blockSize) {
		int coorX = 0;
		int coorY = 0;
		int deltaX = 0;
		int deltaY = 0;
		if(isVertical) {
			deltaY += 1;
		}else {
			deltaX += 1;
		}
		for(int i = 0; i < size; i ++) {
			Rectangle block = new Rectangle();
			block = drawShipBlock(blockSize);
			shipUI.add(block, coorX, coorY);
			coorX += deltaX;
			coorY += deltaY;
		}
	}

	//Create ship with variable length
	public ShipUI createShip(int shipSize, boolean isVertical,int blockSize) {
		ShipUI shipUI = new ShipUI();
		shipUI.size = shipSize;
		shipUI.isVertical = isVertical;
		addShipBlocks(shipUI, shipSize,isVertical,blockSize);
		return shipUI;
	}

	//Detect drag action on a ship
	public void addDragAndDrop(Scene scene,Group root,Board board, BoardUI boardUI, Pane container, double coorX, double coorY) {
		final Vector2dDouble vector2dDouble = new Vector2dDouble();
		setOnDragDetected(mouseEvent -> {
			startFullDrag();
		});

		//call when mouse is pressed
		setOnMousePressed(mouseEvent -> {
			setMouseTransparent(true);
			vector2dDouble.setX(mouseEvent.getSceneX());
			vector2dDouble.setY(mouseEvent.getSceneY());


			//                if(mouseEvent.isSecondaryButtonDown()) {
			
			root.setOnKeyPressed(new EventHandler<KeyEvent>()
			{
				@Override
				public void handle(KeyEvent e)
				{
					if (e.getCode().equals(KeyCode.R)){
						if(mouseEvent.isPrimaryButtonDown() && size != 1) {								
							if(getRotate() == 90.0) {
								setRotate(0);
								tempX = getTranslateX();
								tempY = getTranslateY();
								col = (int) Math.floor((tempX - boardUI.getTranslateX()) / ALTERNATIVE_SIZE);
								row = (int) Math.floor((tempY - boardUI.getTranslateY()) / ALTERNATIVE_SIZE);
								isVertical = true;
								prevDir = "horizontal";
							}else {
								setRotate(90);
								tempX = getTranslateX()-((size*ALTERNATIVE_SIZE)/2-ALTERNATIVE_SIZE/2);
								tempY = getTranslateY()+((size*ALTERNATIVE_SIZE)/2-ALTERNATIVE_SIZE/2);
								col = (int) Math.floor((tempX - boardUI.getTranslateX()) / ALTERNATIVE_SIZE);
								row = (int) Math.floor((tempY - boardUI.getTranslateY()) / ALTERNATIVE_SIZE);
								isVertical = false;
								prevDir = "vertical";
							}
							mousereleaseAfterRotate = 0;
						}
					}
				}
			});
			//                }

		});

		//call when mouse is released
		setOnMouseReleased(mouseEvent -> {
			if(!mouseEvent.isPrimaryButtonDown()) {
				if(prevDir != "unchanged") {
					mousereleaseAfterRotate++;
				}else {
					mousereleaseAfterRotate = 0;
				}
//				System.out.println(mousereleaseAfterRotate);
				if(mousereleaseAfterRotate > 1 && isVertical) {
					prevDir = "vertical";
				}else if(mousereleaseAfterRotate > 1 && !isVertical) {
					prevDir = "horizontal";
				}
				detectOnBoard(board,boardUI, prevCol, prevRow);
				setMouseTransparent(false);

			}
		});

		//call when mouse is dragged
		setOnMouseDragged(mouseEvent -> {
			double mouseX = mouseEvent.getSceneX();
			double mouseY = mouseEvent.getSceneY();
			double deltaX = mouseX - vector2dDouble.getX();
			double deltaY = mouseY - vector2dDouble.getY();

			setTranslateX(getTranslateX() + deltaX);
			setTranslateY(getTranslateY() + deltaY);

			if(!isVertical) {
				tempX = getTranslateX()-((size*ALTERNATIVE_SIZE)/2-ALTERNATIVE_SIZE/2);
				tempY = getTranslateY()+((size*ALTERNATIVE_SIZE)/2-ALTERNATIVE_SIZE/2);
			}else {
				tempX = getTranslateX();
				tempY = getTranslateY();
			}
			if(isVertical){
				if(tempX < container.getTranslateX()) {
					if(tempX <= boardUI.getTranslateX()) {
						setTranslateX(boardUI.getTranslateX());
					}
					if(tempY <= boardUI.getTranslateY()) {
						setTranslateY(boardUI.getTranslateY());
					}
					if(tempX+ALTERNATIVE_SIZE+BLOCK_STROKE > boardUI.getTranslateX()+(ALTERNATIVE_SIZE+boardUI.getBlockStroke())*board.getDim()) {
						setTranslateX(boardUI.getTranslateX()+(ALTERNATIVE_SIZE+boardUI.getBlockStroke())*board.getDim()-ALTERNATIVE_SIZE+boardUI.getBlockStroke());
					}				
					if(tempY+(ALTERNATIVE_SIZE+BLOCK_STROKE)*size > boardUI.getTranslateY()+(ALTERNATIVE_SIZE+boardUI.getBlockStroke())*board.getDim()) {
						setTranslateY(boardUI.getTranslateY()+(ALTERNATIVE_SIZE+boardUI.getBlockStroke())*board.getDim()-(ALTERNATIVE_SIZE+boardUI.getBlockStroke())*size);
					}	
				}				
			}else if(!isVertical){
				if(tempX < container.getTranslateX()) {
					if(tempX < boardUI.getTranslateX()||tempX < 0) {
						setTranslateX(boardUI.getTranslateX()+((ALTERNATIVE_SIZE+BLOCK_STROKE)*size-ALTERNATIVE_SIZE)/2);
					}
					if(tempY < boardUI.getTranslateY()||tempY < 0) {
						setTranslateY(boardUI.getTranslateY()-((ALTERNATIVE_SIZE+BLOCK_STROKE)*size-ALTERNATIVE_SIZE)/2);
					}
					if(tempX+(ALTERNATIVE_SIZE+BLOCK_STROKE)*size > boardUI.getTranslateX()+(ALTERNATIVE_SIZE+boardUI.getBlockStroke())*board.getDim()) {
						setTranslateX(boardUI.getTranslateX()+(ALTERNATIVE_SIZE+boardUI.getBlockStroke())*board.getDim()-((ALTERNATIVE_SIZE+BLOCK_STROKE)*size+ALTERNATIVE_SIZE)/2);
					}				
					if(tempY+ALTERNATIVE_SIZE+BLOCK_STROKE > boardUI.getTranslateY()+(ALTERNATIVE_SIZE+boardUI.getBlockStroke())*board.getDim()) {
						setTranslateY(boardUI.getTranslateY()+(ALTERNATIVE_SIZE+boardUI.getBlockStroke())*board.getDim()-((ALTERNATIVE_SIZE+BLOCK_STROKE)*size+(ALTERNATIVE_SIZE+BLOCK_STROKE))/2);
					}					
				}	
			}		
			//If ship leaves the container, it will be resized to match the size of board cells.
			//If not, it returns to normal size
			if(getTranslateX() < coorX) {

				//check if ship is on the board. If yes, its coordinates will be set to coordinates of board
				if (tempX > boardUI.getTranslateX()
						&& tempY > boardUI.getTranslateY()
						&& tempX < boardUI.getTranslateX()+Host_preGame.BOARD_DIM*Host_preGame.getBlockSize()
						&& tempY < boardUI.getTranslateY()+Host_preGame.BOARD_DIM*Host_preGame.getBlockSize()) {
					if(!isVertical) {
						tempX = getTranslateX()-((size*ALTERNATIVE_SIZE)/2-ALTERNATIVE_SIZE/2);
						tempY = getTranslateY()+((size*ALTERNATIVE_SIZE)/2-ALTERNATIVE_SIZE/2);
						col = (int) Math.floor((tempX - boardUI.getTranslateX()) / ALTERNATIVE_SIZE);
						row = (int) Math.floor((tempY - boardUI.getTranslateY()) / ALTERNATIVE_SIZE);
					}else {
						tempX = getTranslateX();
						tempY = getTranslateY();
						col = (int) Math.floor((tempX - boardUI.getTranslateX()) / ALTERNATIVE_SIZE);
						row = (int) Math.floor((tempY - boardUI.getTranslateY()) / ALTERNATIVE_SIZE);
					}
					//					System.out.println("Ship: column= "+col+"|row="+row+";"+"prevRow: "+prevRow+"|"+"prevCol: "+prevCol);
				}

				//Scale ship
				for(int a = 0; a < size; a++) {
					setBlockResize(25);
					((Rectangle)getChildren().get(a)).setWidth(ALTERNATIVE_SIZE);
					((Rectangle)getChildren().get(a)).setHeight(ALTERNATIVE_SIZE);
				}
			}else {
				for(int a = 0; a < size; a++) {
					((Rectangle)getChildren().get(a)).setWidth(BLOCK_SIZE);
					((Rectangle)getChildren().get(a)).setHeight(BLOCK_SIZE);

				}
			}
			vector2dDouble.setX(mouseX);
			vector2dDouble.setY(mouseY);
		});
	}

	//Check if the ships are legally placed on the board.
	//Counter represents number of correctly placed blocks of ship.
	//If counter is equal to ship size, the ship is legally placed.
	public int checkValid(BoardUI boardUI, Board board, int Row, int Col, boolean vertical, int boardSize) {
		int counter = 0;

		for(int d = 0; d < boardSize; d++) {
			if(vertical) {
				if (tempX > boardUI.getTranslateX()
						&& tempY > boardUI.getTranslateY()
						&& tempX < boardUI.getTranslateX()+Host_preGame.BOARD_DIM*ALTERNATIVE_SIZE
						&& tempY+getBlockResize()*(size-1) < boardUI.getTranslateY()+Host_preGame.BOARD_DIM*ALTERNATIVE_SIZE) {
					if(board.getCell(Row+d, Col) == BoardTag.EMPTY) {
						counter++;
					}
				}
			}else {
				if (tempX > boardUI.getTranslateX()
						&& tempY > boardUI.getTranslateY()
						&& tempX+getBlockResize()*(size-1) < boardUI.getTranslateX()+Host_preGame.BOARD_DIM*ALTERNATIVE_SIZE
						&& tempY < boardUI.getTranslateY()+Host_preGame.BOARD_DIM*ALTERNATIVE_SIZE) {
					if(board.getCell(Row, Col+d) == BoardTag.EMPTY) {
						counter++;
					}
				}
			}
		}
		return counter;
	}

	/*Detect coordinates of a ship on the board. It will take the form (col,row) in which the far left corner be (0,0).
        If the ship is vertical, the y coordinate will change along with the size of the ship, ex. ((0,0),(0,1),(0,2)),
        which represents a vertical ship of size 3 at cells (0,0),(0,1),(0,2). The same rules are applied for the horizontal
        ships but the x coordinate will change.

        The cell which contains the ship is set to BRIG. If the ship is relocated, the previous location of the ship on the board will be set to EMPTY.
	 */
	public void detectOnBoard(Board board,BoardUI boardUI, int prevcol, int prevrow) {
		if (tempX > boardUI.getTranslateX()
				&& tempY > boardUI.getTranslateY()
				&& tempX < boardUI.getTranslateX()+Host_preGame.BOARD_DIM*Host_preGame.getBlockSize()
				&& tempY < boardUI.getTranslateY()+Host_preGame.BOARD_DIM*Host_preGame.getBlockSize()) {

			//prevCol & prevRow = -1 means the ship is either not on the board or is not legally placed
			if(prevCol == -1 && prevRow == -1) {
				//Iterate through every cell
				for(int Row = 0; Row < board.getDim(); Row++) {
					for(int Col = 0; Col < board.getDim(); Col++) {
						//if the first block of ship enters a cell
						if(Row == row && Col == col) {
							//check ship position
							int counter = checkValid(boardUI,board,Row,Col,isVertical,size);
							//set all cells which contain ship's blocks to GUI.BoardTag.BRIG
							for(int d = 0; d < size; d++) {
								if(isVertical) {
									if(Host_preGame.BOARD_DIM-row >= size && counter == size) {
										board.setCell(Row+d, Col, BoardTag.BRIG);
										prevCol = col;
										prevRow = row;
									}
								}else {
									if(Host_preGame.BOARD_DIM-col >= size && counter == size) {
										board.setCell(Row, Col+d, BoardTag.BRIG);
										prevCol = col;
										prevRow = row;
									}
								}
							}
						}
					}
				}
			}
			//if ship is relocated
			else {
				//reset cells which contain previous position of ship to GUI.BoardTag.EMPTY
				for(int Row = 0; Row < board.getDim(); Row++) {
					for(int Col = 0; Col < board.getDim(); Col++) {
						if(Row == prevRow && Col == prevCol) {
							for(int d = 0; d < size; d++) {
								if(isVertical) {
									if(prevDir == "unchanged" || prevDir == "vertical") {
										board.setCell(Row+d, Col, BoardTag.EMPTY);
									}else if(prevDir == "horizontal") {
										board.setCell(Row, Col+d, BoardTag.EMPTY);
									}
								}else {
									if(prevDir == "horizontal") {
										board.setCell(Row, Col+d, BoardTag.EMPTY);
									}else if(prevDir == "vertical") {
										board.setCell(Row+d, Col, BoardTag.EMPTY);
									}
								}
							}

						}
					}
				}
				//then set cells which are ships's new position to GUI.BoardTag.Brig
				for(int Row = 0; Row < board.getDim(); Row++) {
					for(int Col = 0; Col < board.getDim(); Col++) {
						if(Row == row && Col == col) {
							int counter = checkValid(boardUI,board,Row,Col,isVertical,size);
							for(int d = 0; d < size; d++) {
								if(isVertical) {
									if(counter == size) {
										if(Host_preGame.BOARD_DIM-row >= size) {
											board.setCell(Row+d, Col, BoardTag.BRIG);
											prevCol = col;
											prevRow = row;
										}
										//in case ships's position is illegal, reset prevCol and prevRow to -1
										else {
											prevCol = -1;
											prevRow = -1;
										}
									}
								}else {
									if(counter == size) {
										if(Host_preGame.BOARD_DIM-col >= size) {
											board.setCell(Row, Col+d, BoardTag.BRIG);
											prevCol = col;
											prevRow = row;
										}else {
											prevCol = -1;
											prevRow = -1;
										}
									}

								}
							}
						}
					}
				}
			}

//			for(int Row = 0; Row < board.getDim(); Row++) {
//				for(int Col = 0; Col < board.getDim(); Col++) {
//					if(board.getCell(Row, Col) == GUI.BoardTag.BRIG) {
//						System.out.println("Ship: column= "+Col+"|row="+Row+";"+"prevRow: "+prevRow+"|"+"prevCol: "+prevCol);
//					}
//				}
//			}
		}
	}
	@Override
	public String toString(){
		String sizeString = Integer.toString(size);
		String XString = Integer.toString(col);
		String YString = Integer.toString(row);
		String dirString = isVertical ? "1" : "0" ;
		return sizeString + XString + YString + dirString;
	}
}
