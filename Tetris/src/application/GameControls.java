package application;

import java.util.LinkedList;

import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
public class GameControls {
	//public static final int MOVE = Main.MOVE;
	public static final int SIZE = InGame.SIZE;
	public static final int ROWS = InGame.ROWS;
	public static final int COLS = InGame.COLS;
	public static int WIDTH = InGame.WIDTH;
	public static int HEIGHT = InGame.HEIGHT;
	public static int[][] BOARD = InGame.BOARD;
	public static int MID = (int) COLS / 2;
	public static int delay = 0;
	public static int lastEmptyRow;
	public static int rowCount = 0;
	public static int scoreFactor;
	public static double op = 1.0;
	public static long last = 0;
	public static String colorString = "#FCF3CF";
	public static LinkedList<Integer> fullRow = new LinkedList<Integer>();
	public static Node[] toRemove;
	public static LinkedList<Integer> rmvIndex = new LinkedList<Integer>();
	public static LinkedList<Integer> full = new LinkedList<Integer>();
	public static AnimationTimer d;
	public static AnimationTimer execute;

	public static Bricks makeBrick(String source) {
		int shape = //89;
				(int) (Math.random() * 100);
		String name = "";
		Rectangle a = new Rectangle(SIZE - 1, SIZE - 1),
				b = new Rectangle(SIZE - 1, SIZE - 1),
				c = new Rectangle(SIZE - 1, SIZE - 1),
				d = new Rectangle(SIZE - 1, SIZE - 1);
		if(shape < 15) {
			a.setX(SIZE * MID);
			//a.setY(0);
			b.setX(a.getX());
			b.setY(a.getY() + SIZE);
			c.setX(a.getX());
			c.setY(a.getY() + SIZE * 2);
			d.setX(a.getX() - SIZE);
			d.setY(a.getY() + SIZE * 2);
			name = "J";
		}else if(shape < 30) {
			a.setX(SIZE * (MID - 1));
			b.setX(a.getX());
			b.setY(a.getY() + SIZE);
			c.setX(a.getX());
			c.setY(a.getY() + SIZE * 2);
			d.setX(a.getX() + SIZE);
			d.setY(a.getY() + SIZE * 2);
			name = "L";
		}else if(shape < 45) {
			a.setX(SIZE * (MID - 1));
			b.setX(a.getX());
			b.setY(a.getY() + SIZE);
			c.setX(a.getX() + SIZE);
			d.setX(a.getX() + SIZE);
			d.setY(a.getY() + SIZE);
			name = "O";
		}else if(shape < 60) {
			a.setX(SIZE * (MID + 1));
			b.setX(a.getX() - SIZE);
			c.setX(a.getX() - SIZE);
			c.setY(a.getY() + SIZE);
			d.setX(a.getX() - SIZE * 2);
			d.setY(a.getY() + SIZE);
			name = "S";
		}else if(shape < 75) {
			a.setX(SIZE * (MID - 1));
			b.setX(a.getX() + SIZE);
			c.setX(a.getX() + SIZE);
			c.setY(a.getY() + SIZE);
			d.setX(a.getX() + SIZE * 2);
			d.setY(a.getY() + SIZE);
			name = "Z";
		}else if(shape < 90) {
			a.setX(SIZE * MID);
			b.setX(a.getX());
			b.setY(a.getY() + SIZE);
			c.setX(a.getX());
			c.setY(a.getY() + SIZE * 2);
			d.setX(a.getX());
			d.setY(a.getY() + SIZE * 3);
			name = "I";
		}else {
			a.setX(SIZE * MID);
			b.setX(a.getX() - SIZE);
			b.setY(a.getY() + SIZE);
			c.setX(a.getX());
			c.setY(a.getY() + SIZE);
			d.setX(a.getX() + SIZE);
			d.setY(a.getY() + SIZE);
			name = "T";
		}
		return new Bricks(a,b,c,d,name,source);
	}

	public static void MoveLeft(Bricks br) {
		if(br.a.getX() - SIZE >= 0 && br.b.getX() - SIZE >= 0 && br.c.getX() - SIZE >= 0 && br.d.getX() - SIZE >= 0) {
			if(BOARD[(int) (br.a.getY() / SIZE)][(int) (br.a.getX() / SIZE) - 1] == 0 && BOARD[(int) (br.b.getY() / SIZE)][(int) (br.b.getX() / SIZE) - 1] == 0 
					&& BOARD[(int) (br.c.getY() / SIZE)][(int) (br.c.getX() / SIZE) - 1] == 0 && BOARD[(int) (br.d.getY() / SIZE)][(int) (br.d.getX() / SIZE) - 1] == 0
					) {
				br.a.setX(br.a.getX() - SIZE);
				br.b.setX(br.b.getX() - SIZE);
				br.c.setX(br.c.getX() - SIZE);
				br.d.setX(br.d.getX() - SIZE);
			}
		}
	}

	public static void MoveRight(Bricks br) {
		if(br.a.getX() + SIZE < WIDTH && br.b.getX() + SIZE < WIDTH && br.c.getX() + SIZE < WIDTH && br.d.getX() + SIZE < WIDTH) {
			if(BOARD[(int) (br.a.getY() / SIZE)][(int) (br.a.getX() / SIZE) + 1] == 0 && BOARD[(int) (br.b.getY() / SIZE)][(int) (br.b.getX() / SIZE) + 1] == 0 
					&& BOARD[(int) (br.c.getY() / SIZE)][(int) (br.c.getX() / SIZE) + 1] == 0 && BOARD[(int) (br.d.getY() / SIZE)][(int) (br.d.getX() / SIZE) + 1] == 0
					) {
				br.a.setX(br.a.getX() + SIZE);
				br.b.setX(br.b.getX() + SIZE);
				br.c.setX(br.c.getX() + SIZE);
				br.d.setX(br.d.getX() + SIZE);
			}
		}
	}

	public static boolean atLeastOneFull() {
		int count = 0;
		for(int rows = 0; rows < BOARD.length; rows++) {
			for(int cols = 0; cols < BOARD[0].length; cols++) {
				if(BOARD[rows][cols] == 1)count++;
			}
			if(count == COLS)return true;
			else count = 0;
		}
		return false;
	}

	public static void delay(int time) {
		d = new AnimationTimer() {
			int last = 0;
			@Override
			public void handle(long now) {
				// TODO Auto-generated method stub
				last++;
				if(last == time * 60)d.stop();
			}

		};
	}

	public static void MoveDown(Bricks br, Scene scene) {
		if(br.a.getY() + SIZE < HEIGHT && br.b.getY() + SIZE < HEIGHT && br.c.getY() + SIZE < HEIGHT && br.d.getY() + SIZE < HEIGHT) {
			if(BOARD[(int) (br.a.getY() / SIZE) + 1][(int) (br.a.getX() / SIZE)] == 0 && BOARD[(int) (br.b.getY() / SIZE) + 1][(int) (br.b.getX() / SIZE)] == 0 
					&& BOARD[(int) (br.c.getY() / SIZE) + 1][(int) (br.c.getX() / SIZE)] == 0 && BOARD[(int) (br.d.getY() / SIZE) + 1][(int) (br.d.getX() / SIZE)] == 0) {
				br.a.setY(br.a.getY() + SIZE);
				br.b.setY(br.b.getY() + SIZE);
				br.c.setY(br.c.getY() + SIZE);
				br.d.setY(br.d.getY() + SIZE);
			}else if( BOARD[(int)br.a.getY() / SIZE + 1][(int)br.a.getX() / SIZE] == 1 
					|| BOARD[(int)br.b.getY() / SIZE + 1][(int)br.b.getX() / SIZE] == 1
					|| BOARD[(int)br.c.getY() / SIZE + 1][(int)br.c.getX() / SIZE] == 1 
					|| BOARD[(int)br.d.getY() / SIZE + 1][(int)br.d.getX() / SIZE] == 1) {
				//				if(delay < 1) {
				//					delay++;
				//				}else {
				InGame.brickMovesfx().stop();
				InGame.brickMovesfx().play();
				BOARD[(int)(br.a.getY() / SIZE)][(int)(br.a.getX() / SIZE)] = 1;
				BOARD[(int)(br.b.getY() / SIZE)][(int)(br.b.getX() / SIZE)] = 1;
				BOARD[(int)(br.c.getY() / SIZE)][(int)(br.c.getX() / SIZE)] = 1;
				BOARD[(int)(br.d.getY() / SIZE)][(int)(br.d.getX() / SIZE)] = 1;

				//				InGame.brickMovesfx().play();
				//				InGame.brickMovesfx().stop();

				//				for(int rows = 0; rows < BOARD.length; rows++) {
				//					for(int cols = 0; cols < BOARD[0].length; cols++) {
				//						System.out.print(BOARD[rows][cols] + " ");
				//					}
				//					System.out.println("");
				//				}
				//				System.out.println("\n");

				InGame.curBrick = InGame.getNextBrick();
				InGame.curBrick = ScaleAndShift(InGame.curBrick.a,InGame.curBrick.b,InGame.curBrick.c,InGame.curBrick.d,InGame.curBrick.getName(),SIZE,SIZE * MID,0);
				InGame.getGroup().getChildren().removeAll(InGame.getNextBrick().a, InGame.getNextBrick().b, InGame.getNextBrick().c, InGame.getNextBrick().d);

				InGame.nxtBrick = makeBrick(InGame.getImgSource());
				InGame.nxtBrick = ScaleAndShift(InGame.nxtBrick.a, InGame.nxtBrick.b, InGame.nxtBrick.c, InGame.nxtBrick.d, InGame.nxtBrick.getName(), SIZE - 5, (int)InGame.box.getX() + (SIZE - 5) * 2, (int)InGame.box.getY() + (SIZE - 5));
				InGame.getGroup().getChildren().addAll(InGame.curBrick.a, InGame.curBrick.b, InGame.curBrick.c, InGame.curBrick.d, InGame.nxtBrick.a, InGame.nxtBrick.b, InGame.nxtBrick.c, InGame.nxtBrick.d);
				//					delay = 0;
				//				}
			}
		}else {
			InGame.brickMovesfx().stop();
			InGame.brickMovesfx().play();
			BOARD[(int)(br.a.getY() / SIZE)][(int)(br.a.getX() / SIZE)] = 1;
			BOARD[(int)(br.b.getY() / SIZE)][(int)(br.b.getX() / SIZE)] = 1;
			BOARD[(int)(br.c.getY() / SIZE)][(int)(br.c.getX() / SIZE)] = 1;
			BOARD[(int)(br.d.getY() / SIZE)][(int)(br.d.getX() / SIZE)] = 1;

			//			InGame.brickMovesfx().play();
			//			InGame.brickMovesfx().stop();
			//				for(int rows = 0; rows < BOARD.length; rows++) {
			//					for(int cols = 0; cols < BOARD[0].length; cols++) {
			//						System.out.print(BOARD[rows][cols] + " ");
			//					}
			//					System.out.println("");
			//				}
			//				System.out.println("\n");

			InGame.curBrick = InGame.getNextBrick();
			InGame.curBrick = ScaleAndShift(InGame.curBrick.a,InGame.curBrick.b,InGame.curBrick.c,InGame.curBrick.d,InGame.curBrick.getName(),SIZE,SIZE * MID,0);
			InGame.getGroup().getChildren().removeAll(InGame.getNextBrick().a, InGame.getNextBrick().b, InGame.getNextBrick().c, InGame.getNextBrick().d);

			InGame.nxtBrick = makeBrick(InGame.getImgSource());
			InGame.nxtBrick = ScaleAndShift(InGame.nxtBrick.a, InGame.nxtBrick.b, InGame.nxtBrick.c, InGame.nxtBrick.d, InGame.nxtBrick.getName(), SIZE - 5, (int)InGame.box.getX() + (SIZE - 5) * 2, (int)InGame.box.getY() + (SIZE - 5));
			InGame.getGroup().getChildren().addAll(InGame.curBrick.a, InGame.curBrick.b, InGame.curBrick.c, InGame.curBrick.d, InGame.nxtBrick.a, InGame.nxtBrick.b, InGame.nxtBrick.c, InGame.nxtBrick.d);
		}
		if(atLeastOneFull()) {
			removeRows(InGame.getGroup(), scene);
		}
		InGame.moveOnKeyPress(InGame.curBrick, scene);
	}

	public static boolean isCramped(Bricks br) {
		boolean cramped = false;		
		if(br.getName() == "I") {
			if(br.getForm() == 1) {
				if(br.a.getX() - SIZE * 2 < 0) {
					cramped =  BOARD[(int)br.a.getY() / SIZE + 1][0] == 1 
							|| BOARD[(int)br.a.getY() / SIZE + 1][1] == 1 
							|| BOARD[(int)br.a.getY() / SIZE + 1][2] == 1 
							|| BOARD[(int)br.a.getY() / SIZE + 1][3] == 1;
				}else if(br.d.getX() + SIZE >= WIDTH){
					cramped =  BOARD[(int)br.a.getY() / SIZE + 1][COLS - 4] == 1 
							|| BOARD[(int)br.a.getY() / SIZE + 1][COLS - 1] == 1 
							|| BOARD[(int)br.a.getY() / SIZE + 1][COLS - 2] == 1 
							|| BOARD[(int)br.a.getY() / SIZE + 1][COLS - 3] == 1;
				}else {
					cramped =  BOARD[(int)br.a.getY() / SIZE + 1][(int)br.a.getX() / SIZE - 2] == 1 
							|| BOARD[(int)br.a.getY() / SIZE + 1][(int)br.a.getX() / SIZE - 1] == 1 
							|| BOARD[(int)br.a.getY() / SIZE + 1][(int)br.a.getX() / SIZE] == 1 
							|| BOARD[(int)br.a.getY() / SIZE + 1][(int)br.a.getX() / SIZE + 1] == 1;
				}
			}else if(br.getForm() == 2) {
				if(br.a.getY() + SIZE * 2 >= HEIGHT) {
					cramped =  BOARD[ROWS - 1][(int)br.a.getX() / SIZE + 1] == 1 
							|| BOARD[ROWS - 2][(int)br.a.getX() / SIZE + 1] == 1 
							|| BOARD[ROWS - 3][(int)br.a.getX() / SIZE + 1] == 1 
							|| BOARD[ROWS - 4][(int)br.a.getX() / SIZE + 1] == 1;
				}else if(br.d.getY() - SIZE <= 0){
					cramped =  BOARD[0][(int)br.a.getX() / SIZE + 1] == 1 
							|| BOARD[1][(int)br.a.getX() / SIZE + 1] == 1 
							|| BOARD[2][(int)br.a.getX() / SIZE + 1] == 1 
							|| BOARD[3][(int)br.a.getX() / SIZE + 1] == 1;
				}else {
					cramped =  BOARD[(int)br.a.getY() / SIZE + 2][(int)br.a.getX() / SIZE + 1] == 1 
							|| BOARD[(int)br.a.getY() / SIZE + 1][(int)br.a.getX() / SIZE + 1] == 1 
							|| BOARD[(int)br.a.getY() / SIZE][(int)br.a.getX() / SIZE + 1] == 1 
							|| BOARD[(int)br.a.getY() / SIZE - 1][(int)br.a.getX() / SIZE + 1] == 1;
				}
			}else if(br.getForm() == 3) {
				if(br.a.getX() + SIZE * 2 >= WIDTH) {
					cramped =  BOARD[(int)br.a.getY() / SIZE - 1][COLS - 1] == 1 
							|| BOARD[(int)br.a.getY() / SIZE - 1][COLS - 2] == 1 
							|| BOARD[(int)br.a.getY() / SIZE - 1][COLS - 3] == 1 
							|| BOARD[(int)br.a.getY() / SIZE - 1][COLS - 4] == 1;
				}else if(br.d.getX() - SIZE <= 0){
					cramped =  BOARD[(int)br.a.getY() / SIZE - 1][0] == 1 
							|| BOARD[(int)br.a.getY() / SIZE - 1][1] == 1 
							|| BOARD[(int)br.a.getY() / SIZE - 1][2] == 1 
							|| BOARD[(int)br.a.getY() / SIZE - 1][3] == 1;
				}else {
					cramped =  BOARD[(int)br.a.getY() / SIZE - 1][(int)br.a.getX() / SIZE + 2] == 1 
							|| BOARD[(int)br.a.getY() / SIZE - 1][(int)br.a.getX() / SIZE + 1] == 1 
							|| BOARD[(int)br.a.getY() / SIZE - 1][(int)br.a.getX() / SIZE] == 1 
							|| BOARD[(int)br.a.getY() / SIZE - 1][(int)br.a.getX() / SIZE - 1] == 1;
				}
			}else if(br.getForm() == 4) {
				if(br.a.getY() - SIZE * 2 <= 0) {
					cramped =  BOARD[0][(int)br.a.getX() / SIZE - 1] == 1 
							|| BOARD[1][(int)br.a.getX() / SIZE - 1] == 1 
							|| BOARD[2][(int)br.a.getX() / SIZE - 1] == 1 
							|| BOARD[3][(int)br.a.getX() / SIZE - 1] == 1;
				}else if(br.d.getY() + SIZE >= HEIGHT){
					cramped =  BOARD[ROWS - 1][(int)br.a.getX() / SIZE - 1] == 1 
							|| BOARD[ROWS - 2][(int)br.a.getX() / SIZE - 1] == 1 
							|| BOARD[ROWS - 3][(int)br.a.getX() / SIZE - 1] == 1 
							|| BOARD[ROWS - 4][(int)br.a.getX() / SIZE - 1] == 1;
				}else {
					cramped =  BOARD[(int)br.a.getY() / SIZE - 2][(int)br.a.getX() / SIZE - 1] == 1 
							|| BOARD[(int)br.a.getY() / SIZE - 1][(int)br.a.getX() / SIZE - 1] == 1 
							|| BOARD[(int)br.a.getY() / SIZE][(int)br.a.getX() / SIZE - 1] == 1 
							|| BOARD[(int)br.a.getY() / SIZE + 1][(int)br.a.getX() / SIZE - 1] == 1;
				}
			}
		}else if(br.getName() == "J") {
			if(br.getForm() == 1) {
				if(br.a.getX() - SIZE * 2 <= 0) {
					cramped =  BOARD[(int)br.a.getY() / SIZE + 1][0] == 1 
							|| BOARD[(int)br.a.getY() / SIZE + 1][1] == 1 
							|| BOARD[(int)br.a.getY() / SIZE + 1][2] == 1 
							|| BOARD[(int)br.a.getY() / SIZE + 2][2] == 1;
				}else {
					cramped =  BOARD[(int)br.a.getY() / SIZE + 1][(int)br.a.getX() / SIZE - 2] == 1 
							|| BOARD[(int)br.a.getY() / SIZE + 1][(int)br.a.getX() / SIZE - 1] == 1 
							|| BOARD[(int)br.a.getY() / SIZE + 1][(int)br.a.getX() / SIZE] == 1 
							|| BOARD[(int)br.a.getY() / SIZE + 2][(int)br.a.getX() / SIZE] == 1;
				}
			}else if(br.getForm() == 2) {
				if(br.a.getY() + SIZE * 2 >= HEIGHT) {
					cramped =  BOARD[ROWS - 1][(int)br.a.getX() / SIZE + 1] == 1 
							|| BOARD[ROWS - 2][(int)br.a.getX() / SIZE + 1] == 1 
							|| BOARD[ROWS - 3][(int)br.a.getX() / SIZE + 1] == 1 
							|| BOARD[ROWS - 3][(int)br.a.getX() / SIZE + 2] == 1;
				}else {
					cramped =  BOARD[(int)br.a.getY() / SIZE + 2][(int)br.a.getX() / SIZE + 1] == 1 
							|| BOARD[(int)br.a.getY() / SIZE + 1][(int)br.a.getX() / SIZE + 1] == 1 
							|| BOARD[(int)br.a.getY() / SIZE][(int)br.a.getX() / SIZE + 1] == 1 
							|| BOARD[(int)br.a.getY() / SIZE][(int)br.a.getX() / SIZE + 2] == 1;
				}
			}else if(br.getForm() == 3) {
				if(br.a.getX() + SIZE * 2 >= WIDTH) {
					cramped =  BOARD[(int)br.a.getY() / SIZE - 1][COLS - 1] == 1 
							|| BOARD[(int)br.a.getY() / SIZE - 1][COLS - 2] == 1 
							|| BOARD[(int)br.a.getY() / SIZE- 1][COLS - 3] == 1 
							|| BOARD[(int)br.a.getY() / SIZE - 2][COLS - 3] == 1;
				}else {
					cramped =  BOARD[(int)br.a.getY() / SIZE - 1][(int)br.a.getX() / SIZE + 2] == 1 
							|| BOARD[(int)br.a.getY() / SIZE - 1][(int)br.a.getX() / SIZE + 1] == 1 
							|| BOARD[(int)br.a.getY() / SIZE - 1][(int)br.a.getX() / SIZE] == 1 
							|| BOARD[(int)br.a.getY() / SIZE - 2][(int)br.a.getX() / SIZE] == 1;
				}
			}else if(br.getForm() == 4) {
				if(br.a.getY() - SIZE * 2 <= 0) {
					cramped =  BOARD[0][(int)br.a.getX() / SIZE - 1] == 1 
							|| BOARD[1][(int)br.a.getX() / SIZE - 1] == 1 
							|| BOARD[2][(int)br.a.getX() / SIZE - 1] == 1 
							|| BOARD[3][(int)br.a.getX() / SIZE - 2] == 1;
				}else {
					cramped =  BOARD[(int)br.a.getY() / SIZE - 2][(int)br.a.getX() / SIZE - 1] == 1 
							|| BOARD[(int)br.a.getY() / SIZE - 1][(int)br.a.getX() / SIZE - 1] == 1 
							|| BOARD[(int)br.a.getY() / SIZE][(int)br.a.getX() / SIZE - 1] == 1 
							|| BOARD[(int)br.a.getY() / SIZE][(int)br.a.getX() / SIZE - 2] == 1;
				}
			}
		}else if(br.getName() == "L") {
			if(br.getForm() == 1) {
				if(br.a.getX() - SIZE <= 0) {
					cramped =  BOARD[(int)br.a.getY() / SIZE + 2][0] == 1 
							|| BOARD[(int)br.a.getY() / SIZE + 2][1] == 1 
							|| BOARD[(int)br.a.getY() / SIZE + 2][2] == 1 
							|| BOARD[(int)br.a.getY() / SIZE + 1][2] == 1;
				}else {
					cramped =  BOARD[(int)br.a.getY() / SIZE + 2][(int)br.a.getX() / SIZE - 1] == 1 
							|| BOARD[(int)br.a.getY() / SIZE + 2][(int)br.a.getX() / SIZE] == 1 
							|| BOARD[(int)br.a.getY() / SIZE + 2][(int)br.a.getX() / SIZE + 1] == 1 
							|| BOARD[(int)br.a.getY() / SIZE + 1][(int)br.a.getX() / SIZE + 1] == 1;
				}
			}else if(br.getForm() == 2) {
				if(br.a.getY() + SIZE >= HEIGHT) {
					cramped =  BOARD[ROWS - 1][(int)br.a.getX() / SIZE + 2] == 1 
							|| BOARD[ROWS - 2][(int)br.a.getX() / SIZE + 2] == 1 
							|| BOARD[ROWS - 3][(int)br.a.getX() / SIZE + 2] == 1 
							|| BOARD[ROWS - 3][(int)br.a.getX() / SIZE + 1] == 1;
				}else {
					cramped =  BOARD[(int)br.a.getY() / SIZE + 1][(int)br.a.getX() / SIZE + 2] == 1 
							|| BOARD[(int)br.a.getY() / SIZE][(int)br.a.getX() / SIZE + 2] == 1 
							|| BOARD[(int)br.a.getY() / SIZE - 1][(int)br.a.getX() / SIZE + 2] == 1 
							|| BOARD[(int)br.a.getY() / SIZE - 1][(int)br.a.getX() / SIZE + 1] == 1;
				}
			}else if(br.getForm() == 3) {
				if(br.a.getX() + SIZE >= WIDTH) {
					cramped =  BOARD[(int)br.a.getY() / SIZE - 2][COLS - 1] == 1 
							|| BOARD[(int)br.a.getY() / SIZE - 2][COLS - 2] == 1 
							|| BOARD[(int)br.a.getY() / SIZE - 2][COLS - 3] == 1 
							|| BOARD[(int)br.a.getY() / SIZE - 1][COLS - 3] == 1;
				}else {
					cramped =  BOARD[(int)br.a.getY() / SIZE - 2][(int)br.a.getX() / SIZE + 1] == 1 
							|| BOARD[(int)br.a.getY() / SIZE - 2][(int)br.a.getX() / SIZE] == 1 
							|| BOARD[(int)br.a.getY() / SIZE - 2][(int)br.a.getX() / SIZE - 1] == 1 
							|| BOARD[(int)br.a.getY() / SIZE - 1][(int)br.a.getX() / SIZE - 1] == 1;
				}
			}else if(br.getForm() == 4) {
				if(br.a.getY() - SIZE <= 0) {
					cramped =  BOARD[0][(int)br.a.getX() / SIZE - 2] == 1 
							|| BOARD[1][(int)br.a.getX() / SIZE - 2] == 1 
							|| BOARD[2][(int)br.a.getX() / SIZE - 2] == 1 
							|| BOARD[2][(int)br.a.getX() / SIZE - 1] == 1;
				}else {
					cramped =  BOARD[(int)br.a.getY() / SIZE - 1][(int)br.a.getX() / SIZE - 2] == 1 
							|| BOARD[(int)br.a.getY() / SIZE][(int)br.a.getX() / SIZE - 2] == 1 
							|| BOARD[(int)br.a.getY() / SIZE + 1][(int)br.a.getX() / SIZE - 2] == 1 
							|| BOARD[(int)br.a.getY() / SIZE + 1][(int)br.a.getX() / SIZE - 1] == 1;
				}
			}

		}else if(br.getName() == "S") {
			if(br.getForm() == 1) {
				if(br.a.getY() - SIZE <= 0) {
					cramped =  BOARD[0][(int)br.a.getX() / SIZE - 1] == 1 
							|| BOARD[1][(int)br.a.getX() / SIZE - 1] == 1 
							|| BOARD[1][(int)br.a.getX() / SIZE] == 1 
							|| BOARD[2][(int)br.a.getX() / SIZE] == 1;
				}else {
					cramped =  BOARD[(int)br.a.getY() / SIZE - 1][(int)br.a.getX() / SIZE - 1] == 1 
							|| BOARD[(int)br.a.getY() / SIZE][(int)br.a.getX() / SIZE - 1] == 1 
							|| BOARD[(int)br.a.getY() / SIZE][(int)br.a.getX() / SIZE] == 1 
							|| BOARD[(int)br.a.getY() / SIZE + 1][(int)br.a.getX() / SIZE] == 1;
				}
			}else if(br.getForm() == 2) {
				if(br.a.getX() - SIZE < 0) {
					cramped =  BOARD[(int)br.a.getY() / SIZE - 1][0] == 1 
							|| BOARD[(int)br.a.getY() / SIZE - 1][1] == 1 
							|| BOARD[(int)br.a.getY() / SIZE][1] == 1 
							|| BOARD[(int)br.a.getY() / SIZE][2] == 1;
				}else {
					cramped =  BOARD[(int)br.a.getY() / SIZE + 1][(int)br.a.getX() / SIZE - 1] == 1 
							|| BOARD[(int)br.a.getY() / SIZE + 1][(int)br.a.getX() / SIZE] == 1 
							|| BOARD[(int)br.a.getY() / SIZE][(int)br.a.getX() / SIZE] == 1 
							|| BOARD[(int)br.a.getY() / SIZE][(int)br.a.getX() / SIZE + 1] == 1;
				}
			}else if(br.getForm() == 3) {
				if(br.a.getY() + SIZE >= HEIGHT) {
					cramped =  BOARD[ROWS - 1][(int)br.a.getX() / SIZE + 1] == 1 
							|| BOARD[ROWS - 2][(int)br.a.getX() / SIZE + 1] == 1 
							|| BOARD[ROWS - 2][(int)br.a.getX() / SIZE] == 1 
							|| BOARD[ROWS - 3][(int)br.a.getX() / SIZE] == 1;
				}else {
					cramped =  BOARD[(int)br.a.getY() / SIZE + 1][(int)br.a.getX() / SIZE + 1] == 1 
							|| BOARD[(int)br.a.getY() / SIZE][(int)br.a.getX() / SIZE + 1] == 1 
							|| BOARD[(int)br.a.getY() / SIZE][(int)br.a.getX() / SIZE] == 1 
							|| BOARD[(int)br.a.getY() / SIZE - 1][(int)br.a.getX() / SIZE] == 1;
				}
			}else if(br.getForm() == 4) {
				if(br.a.getX() + SIZE >= WIDTH) {
					cramped =  BOARD[(int)br.a.getY() / SIZE - 1][COLS - 1] == 1 
							|| BOARD[(int)br.a.getY() / SIZE - 1][COLS - 2] == 1 
							|| BOARD[(int)br.a.getY() / SIZE][COLS - 2] == 1 
							|| BOARD[(int)br.a.getY() / SIZE][COLS - 3] == 1;
				}else {
					cramped =  BOARD[(int)br.a.getY() / SIZE - 1][(int)br.a.getX() / SIZE + 1] == 1 
							|| BOARD[(int)br.a.getY() / SIZE - 1][(int)br.a.getX() / SIZE] == 1 
							|| BOARD[(int)br.a.getY() / SIZE][(int)br.a.getX() / SIZE] == 1 
							|| BOARD[(int)br.a.getY() / SIZE][(int)br.a.getX() / SIZE - 1] == 1;
				}
			}

		}else if(br.getName() == "Z") {
			if(br.getForm() == 1) {
				if(br.a.getY() - SIZE <= 0) {
					cramped =  BOARD[0][(int)br.a.getX() / SIZE + 2] == 1 
							|| BOARD[1][(int)br.a.getX() / SIZE + 2] == 1 
							|| BOARD[1][(int)br.a.getX() / SIZE + 1] == 1 
							|| BOARD[2][(int)br.a.getX() / SIZE + 1] == 1;
				}else {
					cramped =  BOARD[(int)br.a.getY() / SIZE + 1][(int)br.a.getX() / SIZE + 1] == 1 
							|| BOARD[(int)br.a.getY() / SIZE][(int)br.a.getX() / SIZE + 1] == 1 
							|| BOARD[(int)br.a.getY() / SIZE][(int)br.a.getX() / SIZE + 2] == 1 
							|| BOARD[(int)br.a.getY() / SIZE - 1][(int)br.a.getX() / SIZE + 2] == 1;
				}
			}else if(br.getForm() == 2) {
				if(br.a.getX() - SIZE < 0) {
					cramped =  BOARD[(int)br.a.getY() / SIZE - 2][0] == 1 
							|| BOARD[(int)br.a.getY() / SIZE - 2][1] == 1 
							|| BOARD[(int)br.a.getY() / SIZE - 1][1] == 1 
							|| BOARD[(int)br.a.getY() / SIZE - 1][2] == 1;
				}else {
					cramped =  BOARD[(int)br.a.getY() / SIZE - 2][(int)br.a.getX() / SIZE - 1] == 1 
							|| BOARD[(int)br.a.getY() / SIZE - 2][(int)br.a.getX() / SIZE] == 1 
							|| BOARD[(int)br.a.getY() / SIZE - 1][(int)br.a.getX() / SIZE] == 1 
							|| BOARD[(int)br.a.getY() / SIZE - 1][(int)br.a.getX() / SIZE + 1] == 1;
				}
			}else if(br.getForm() == 3) {
				if(br.a.getY() + SIZE >= HEIGHT) {
					cramped =  BOARD[ROWS - 1][(int)br.a.getX() / SIZE - 2] == 1 
							|| BOARD[ROWS - 2][(int)br.a.getX() / SIZE - 2] == 1 
							|| BOARD[ROWS - 2][(int)br.a.getX() / SIZE - 1] == 1 
							|| BOARD[ROWS - 3][(int)br.a.getX() / SIZE - 1] == 1;
				}else {
					cramped =  BOARD[(int)br.a.getY() / SIZE + 1][(int)br.a.getX() / SIZE - 2] == 1 
							|| BOARD[(int)br.a.getY() / SIZE][(int)br.a.getX() / SIZE  - 2] == 1 
							|| BOARD[(int)br.a.getY() / SIZE][(int)br.a.getX() / SIZE - 1] == 1 
							|| BOARD[(int)br.a.getY() / SIZE - 1][(int)br.a.getX() / SIZE - 1] == 1;
				}
			}else if(br.getForm() == 4) {
				if(br.a.getX() + SIZE >= WIDTH) {
					cramped =  BOARD[(int)br.a.getY() / SIZE][COLS - 1] == 1 
							|| BOARD[(int)br.a.getY() / SIZE][COLS - 2] == 1 
							|| BOARD[(int)br.a.getY() / SIZE + 1][COLS - 2] == 1 
							|| BOARD[(int)br.a.getY() / SIZE + 1][COLS - 3] == 1;
				}else {
					cramped =  BOARD[(int)br.a.getY() / SIZE][(int)br.a.getX() / SIZE - 1] == 1 
							|| BOARD[(int)br.a.getY() / SIZE][(int)br.a.getX() / SIZE] == 1 
							|| BOARD[(int)br.a.getY() / SIZE + 1][(int)br.a.getX() / SIZE] == 1 
							|| BOARD[(int)br.a.getY() / SIZE + 1][(int)br.a.getX() / SIZE + 1] == 1;
				}
			}

		}else if(br.getName() == "T") {
			if(br.getForm() == 1) {
				if((int)br.a.getY() + SIZE == HEIGHT - SIZE) {
					cramped =  BOARD[ROWS - 2][(int)br.a.getX() / SIZE - 1] == 1 
							|| BOARD[ROWS - 1][(int)br.a.getX() / SIZE] == 1 
							|| BOARD[ROWS - 2][(int)br.a.getX() / SIZE] == 1 
							|| BOARD[ROWS - 3][(int)br.a.getX() / SIZE] == 1;
				}else {
					cramped =  BOARD[(int)br.a.getY() / SIZE + 1][(int)br.a.getX() / SIZE - 1] == 1 
							|| BOARD[(int)br.a.getY() / SIZE + 2][(int)br.a.getX() / SIZE] == 1 
							|| BOARD[(int)br.a.getY() / SIZE + 1][(int)br.a.getX() / SIZE] == 1 
							|| BOARD[(int)br.a.getY() / SIZE][(int)br.a.getX() / SIZE] == 1;
				}
			}else if(br.getForm() == 2) {
				if((int)br.a.getX() + SIZE == WIDTH - SIZE) {
					cramped =  BOARD[(int)br.a.getY() / SIZE + 1][COLS - 2] == 1 
							|| BOARD[(int)br.a.getY() / SIZE][COLS - 1] == 1 
							|| BOARD[(int)br.a.getY() / SIZE][COLS - 2] == 1 
							|| BOARD[(int)br.a.getY() / SIZE][COLS - 3] == 1;
				}else {
					cramped =  BOARD[(int)br.a.getY() / SIZE + 1][(int)br.a.getX() / SIZE + 1] == 1 
							|| BOARD[(int)br.a.getY() / SIZE][(int)br.a.getX() / SIZE + 2] == 1 
							|| BOARD[(int)br.a.getY() / SIZE][(int)br.a.getX() / SIZE + 1] == 1 
							|| BOARD[(int)br.a.getY() / SIZE][(int)br.a.getX() / SIZE] == 1;
				}
			}else if(br.getForm() == 3) {
				if((int)br.a.getY() - SIZE == 0) {
					cramped =  BOARD[1][(int)br.a.getX() / SIZE + 1] == 1 
							|| BOARD[0][(int)br.a.getX() / SIZE] == 1 
							|| BOARD[1][(int)br.a.getX() / SIZE] == 1 
							|| BOARD[2][(int)br.a.getX() / SIZE] == 1;
				}else {
					cramped =  BOARD[(int)br.a.getY() / SIZE][(int)br.a.getX() / SIZE + 1] == 1 
							|| BOARD[(int)br.a.getY() / SIZE - 1][(int)br.a.getX() / SIZE] == 1 
							|| BOARD[(int)br.a.getY() / SIZE][(int)br.a.getX() / SIZE] == 1 
							|| BOARD[(int)br.a.getY() / SIZE + 1][(int)br.a.getX() / SIZE] == 1;
				}
			}else if(br.getForm() == 4) {
				if((int)br.a.getX() - SIZE == 0) {
					cramped =  BOARD[(int)br.a.getY() / SIZE - 1][1] == 1 
							|| BOARD[(int)br.a.getY() / SIZE][0] == 1 
							|| BOARD[(int)br.a.getY() / SIZE][1] == 1 
							|| BOARD[(int)br.a.getY() / SIZE][2] == 1;
				}else {
					cramped =  BOARD[(int)br.a.getY() / SIZE - 1][(int)br.a.getX() / SIZE - 1] == 1 
							|| BOARD[(int)br.a.getY() / SIZE][(int)br.a.getX() / SIZE - 2] == 1 
							|| BOARD[(int)br.a.getY() / SIZE][(int)br.a.getX() / SIZE - 1] == 1 
							|| BOARD[(int)br.a.getY() / SIZE][(int)br.a.getX() / SIZE] == 1;
				}
			}

		}
		return cramped;
	}

	public static void moveA(Bricks br, int aX, int aY, int bAx, int bAy, String spot) {
		if(spot.toLowerCase() == "top") {
			if(aY < 0)br.a.setY(0);
			else if(BOARD[bAy][bAx] == 1)br.a.setY(aY + SIZE);
			else br.a.setY(aY);
			br.a.setX(aX);
		}else if(spot.toLowerCase() == "left") {
			if(aX < 0)br.a.setX(0);
			else if(BOARD[bAy][bAx] == 1)br.a.setX(aX + SIZE);
			else br.a.setX(aX);
			br.a.setY(aY);
		}else if(spot.toLowerCase() == "bottom") {
			if(aY >= HEIGHT)br.a.setY(HEIGHT - SIZE);
			else if(BOARD[bAy][bAx] == 1)br.a.setY(aY - SIZE);
			else br.a.setY(aY);
			br.a.setX(aX);
		}else if(spot.toLowerCase() == "right") {
			if(aX >= WIDTH )br.a.setX(WIDTH - SIZE);
			else if(BOARD[bAy][bAx] == 1)br.a.setX(aX - SIZE);
			else br.a.setX(aX);
			br.a.setY(aY);
		}
	}



	public static void moveB(Bricks br, int bX, int bY) {
		br.b.setX(bX);
		br.b.setY(bY);
	}

	public static void moveC(Bricks br, int cX, int cY) {
		br.c.setX(cX);
		br.c.setY(cY);
	}

	public static void moveD(Bricks br, int dX, int dY) {
		br.d.setX(dX);
		br.d.setY(dY);
	}

	public static void MoveTurn(Bricks br) {
		if(!isCramped(br)) {			
			switch(br.getName()) {
			case "J":
				if(br.getForm() == 1) {
					moveA(br, (int)br.a.getX() - SIZE * 2, (int)br.a.getY() + SIZE, (int)br.a.getX() / SIZE - 2, (int)br.a.getY() / SIZE + 1, "left");
					moveB(br, (int)br.a.getX() + SIZE, (int)br.a.getY());
					moveC(br, (int)br.b.getX() + SIZE, (int)br.b.getY());
					moveD(br, (int)br.c.getX(), (int)br.c.getY() + SIZE);
					br.changeForm();
				}else if(br.getForm() == 2) {
					moveA(br, (int)br.a.getX() + SIZE, (int)br.a.getY() + SIZE * 2, (int)br.a.getX() / SIZE + 1, (int)br.a.getY() / SIZE + 2, "bottom");
					moveB(br, (int)br.a.getX(), (int)br.a.getY() - SIZE);
					moveC(br, (int)br.b.getX(), (int)br.b.getY() - SIZE);
					moveD(br, (int)br.c.getX() + SIZE, (int)br.c.getY());
					br.changeForm();
				}else if(br.getForm() == 3) {
					moveA(br, (int)br.a.getX() + SIZE * 2, (int)br.a.getY() - SIZE, (int)br.a.getX() / SIZE + 2, (int)br.a.getY() / SIZE - 1, "right");
					moveB(br, (int)br.a.getX() - SIZE, (int)br.a.getY());
					moveC(br, (int)br.b.getX() - SIZE, (int)br.b.getY());
					moveD(br, (int)br.c.getX(), (int)br.c.getY() - SIZE);
					br.changeForm();
				}else if(br.getForm() == 4) {
					moveA(br, (int)br.a.getX() - SIZE, (int)br.a.getY() - SIZE * 2, (int)br.a.getX() / SIZE - 1, (int)br.a.getY() / SIZE - 2, "top");
					moveB(br, (int)br.a.getX(), (int)br.a.getY() + SIZE);
					moveC(br, (int)br.b.getX(), (int)br.b.getY() + SIZE);
					moveD(br, (int)br.c.getX() - SIZE, (int)br.c.getY());
					br.changeForm();
				}
				break;
			case "L":
				if(br.getForm() == 1) {
					moveA(br, (int)br.a.getX() - SIZE, (int)br.a.getY() + SIZE * 2, (int)br.a.getX() / SIZE - 1, (int)br.a.getY() / SIZE + 2, "left");
					moveB(br, (int)br.a.getX() + SIZE, (int)br.a.getY());
					moveC(br, (int)br.b.getX() + SIZE, (int)br.b.getY());
					moveD(br, (int)br.c.getX(), (int)br.c.getY() - SIZE);
					br.changeForm();
				}else if(br.getForm() == 2) {
					moveA(br, (int)br.a.getX() + SIZE * 2, (int)br.a.getY() + SIZE, (int)br.a.getX() / SIZE + 2, (int)br.a.getY() / SIZE + 1, "bottom");
					moveB(br, (int)br.a.getX(), (int)br.a.getY() - SIZE);
					moveC(br, (int)br.b.getX(), (int)br.b.getY() - SIZE);
					moveD(br, (int)br.c.getX() - SIZE, (int)br.c.getY());
					br.changeForm();
				}else if(br.getForm() == 3) {
					moveA(br, (int)br.a.getX() + SIZE, (int)br.a.getY() - SIZE * 2, (int)br.a.getX() / SIZE + 1, (int)br.a.getY() / SIZE - 2, "right");
					moveB(br, (int)br.a.getX() - SIZE, (int)br.a.getY());
					moveC(br, (int)br.b.getX() - SIZE, (int)br.b.getY());
					moveD(br, (int)br.c.getX(), (int)br.c.getY() + SIZE);
					br.changeForm();
				}else if(br.getForm() == 4) {
					moveA(br, (int)br.a.getX() - SIZE * 2, (int)br.a.getY() - SIZE, (int)br.a.getX() / SIZE - 2, (int)br.a.getY() / SIZE - 1, "top");
					moveB(br, (int)br.a.getX(), (int)br.a.getY() + SIZE);
					moveC(br, (int)br.b.getX(), (int)br.b.getY() + SIZE);
					moveD(br, (int)br.c.getX() + SIZE, (int)br.c.getY());
					br.changeForm();
				}
				break;
			case "S":
				if(br.getForm() == 1) { 
					moveA(br, (int)br.a.getX() - SIZE, (int)br.a.getY() - (br.a.getY() == 0 ? 0 : SIZE), (int)br.a.getX() / SIZE - 1, (int)br.a.getY() / SIZE - (br.a.getY() == 0 ? 0 : 1), "top");
					moveB(br, (int)br.a.getX(), (int)br.a.getY() + SIZE);
					moveC(br, (int)br.b.getX() + SIZE, (int)br.b.getY());
					moveD(br, (int)br.c.getX(), (int)br.c.getY() + SIZE);
					br.changeForm();
				}else if(br.getForm() == 2) { 
					moveA(br, (int)br.a.getX() - (br.a.getX() == 0 ? 0 : SIZE), (int)br.a.getY() + SIZE, (int)br.a.getX() / SIZE - (br.a.getX() == 0 ? 0 : 1), (int)br.a.getY() / SIZE + 1, "left");
					moveB(br, (int)br.a.getX() + SIZE, (int)br.a.getY());
					moveC(br, (int)br.b.getX(), (int)br.b.getY() - SIZE);
					moveD(br, (int)br.c.getX() + SIZE, (int)br.c.getY());
					br.changeForm();
				}else if(br.getForm() == 3) {  
					moveA(br, (int)br.a.getX() + SIZE, (int)br.a.getY() + (br.a.getY() == HEIGHT - SIZE ? 0 : SIZE), (int)br.a.getX() / SIZE + 1, (int)br.a.getY() / SIZE + (br.a.getY() == HEIGHT - SIZE ?  0 : 1), "bottom");
					moveB(br, (int)br.a.getX(), (int)br.a.getY() - SIZE);
					moveC(br, (int)br.b.getX() - SIZE, (int)br.b.getY());
					moveD(br, (int)br.c.getX(), (int)br.c.getY() - SIZE);
					br.changeForm();
				}else if(br.getForm() == 4) { 
					moveA(br, (int)br.a.getX() + (br.a.getX() == WIDTH - SIZE ? 0 : SIZE), (int)br.a.getY() - SIZE, (int)br.a.getX() / SIZE + (br.a.getX() == WIDTH - SIZE ? 0 : 1), (int)br.a.getY() / SIZE - 1, "right");
					moveB(br, (int)br.a.getX() - SIZE, (int)br.a.getY());
					moveC(br, (int)br.b.getX(), (int)br.b.getY() + SIZE);
					moveD(br, (int)br.c.getX() - SIZE, (int)br.c.getY());
					br.changeForm();
				}
				break;
			case "I":
				if(br.getForm() == 1) { 
					moveA(br, (int)br.a.getX() - (br.a.getX() == WIDTH - SIZE ? SIZE * 3 : SIZE * 2), (int)br.a.getY() + SIZE, (int)br.a.getX() / SIZE - (br.a.getX() == WIDTH - SIZE ? 3 : 2), (int)br.a.getY() / SIZE + 1, "left");
					moveB(br, (int)br.a.getX() + SIZE, (int)br.a.getY());
					moveC(br, (int)br.b.getX() + SIZE, (int)br.b.getY());
					moveD(br, (int)br.c.getX() + SIZE, (int)br.c.getY());
					br.changeForm();
				}else if(br.getForm() == 2) { 
					moveA(br, (int)br.a.getX() + SIZE, (int)br.a.getY() + (br.a.getY() == 0 ? SIZE * 3 : SIZE * 2), (int)br.a.getX() / SIZE + 1, (int)br.a.getY() / SIZE + (br.a.getY() == 0 ? 3 : 2), "bottom");
					moveB(br, (int)br.a.getX(), (int)br.a.getY() - SIZE);
					moveC(br, (int)br.b.getX(), (int)br.b.getY() - SIZE);
					moveD(br, (int)br.c.getX(), (int)br.c.getY() - SIZE);
					br.changeForm();
				}else if(br.getForm() == 3) { 
					moveA(br, (int)br.a.getX() + (br.a.getX() == 0 ? SIZE * 3 : SIZE * 2), (int)br.a.getY() - SIZE, (int)br.a.getX() / SIZE + (br.a.getX() == 0 ? 3 : 2), (int)br.a.getY() / SIZE - 1, "right");
					moveB(br, (int)br.a.getX() - SIZE, (int)br.a.getY());
					moveC(br, (int)br.b.getX() - SIZE, (int)br.b.getY());
					moveD(br, (int)br.c.getX() - SIZE, (int)br.c.getY());
					br.changeForm();
				}else if(br.getForm() == 4) { 
					moveA(br, (int)br.a.getX() - SIZE, (int)br.a.getY() - (br.a.getY() == HEIGHT - SIZE ? SIZE * 3 : SIZE * 2), (int)br.a.getX() / SIZE - 1, (int)br.a.getY() / SIZE - (br.a.getY() == HEIGHT - SIZE ? 3 : 2), "top");
					moveB(br, (int)br.a.getX(), (int)br.a.getY() + SIZE);
					moveC(br, (int)br.b.getX(), (int)br.b.getY() + SIZE);
					moveD(br, (int)br.c.getX(), (int)br.c.getY() + SIZE);
					br.changeForm();
				}
				break;
			case "Z":
				if(br.getForm() == 1) {
					moveA(br, (int)br.a.getX() + SIZE, (int)br.a.getY() + (br.a.getY() == 0 ? SIZE * 2 : SIZE), (int)br.a.getX() / SIZE + 1, (int)br.a.getY() / SIZE + (br.a.getY() == 0 ? 2 : 1), "bottom");
					moveB(br, (int)br.a.getX(), (int)br.a.getY() - SIZE);
					moveC(br, (int)br.b.getX() + SIZE, (int)br.b.getY());
					moveD(br, (int)br.c.getX(), (int)br.c.getY() - SIZE);
					br.changeForm();
				}else if(br.getForm() == 2) {
					moveA(br, (int)br.a.getX() + (br.a.getX() == 0 ? SIZE * 2 : SIZE), (int)br.a.getY() - SIZE, (int)br.a.getX() / SIZE + (br.a.getX() == 0 ? 2 : 1), (int)br.a.getY() / SIZE - 1, "right");
					moveB(br, (int)br.a.getX() - SIZE, (int)br.a.getY());
					moveC(br, (int)br.b.getX(), (int)br.b.getY() - SIZE);
					moveD(br, (int)br.c.getX() - SIZE, (int)br.c.getY());
					br.changeForm();
				}else if(br.getForm() == 3) {					
					moveA(br, (int)br.a.getX() - SIZE, (int)br.a.getY() - (br.a.getY() == HEIGHT - SIZE ? SIZE * 2 : SIZE), (int)br.a.getX() / SIZE - 1, (int)br.a.getY() / SIZE - (br.a.getY() == HEIGHT - SIZE ? 2 : 1), "top");
					moveB(br, (int)br.a.getX(), (int)br.a.getY() + SIZE);
					moveC(br, (int)br.b.getX() - SIZE, (int)br.b.getY());
					moveD(br, (int)br.c.getX(), (int)br.c.getY() + SIZE);
					br.changeForm();
				}else if(br.getForm() == 4) { 
					moveA(br, (int)br.a.getX() - (br.a.getX() == WIDTH - SIZE ? SIZE * 2 : SIZE), (int)br.a.getY() + SIZE, (int)br.a.getX() / SIZE - (br.a.getX() == WIDTH - SIZE ? 2 : 1), (int)br.a.getY() / SIZE + 1, "left");
					moveB(br, (int)br.a.getX() + SIZE, (int)br.a.getY());
					moveC(br, (int)br.b.getX(), (int)br.b.getY() + SIZE);
					moveD(br, (int)br.c.getX() + SIZE, (int)br.c.getY());
					br.changeForm();
				}
				break;
			case "T":
				if(br.getForm() == 1) {
					if(br.a.getY() + SIZE == HEIGHT - SIZE)moveA(br, (int)br.a.getX() - SIZE, (int)br.a.getY(), (int)br.a.getX() / SIZE - 1, (int)br.a.getY() / SIZE, "left");
					else moveA(br, (int)br.a.getX() - SIZE, (int)br.a.getY() + SIZE, (int)br.a.getX() / SIZE - 1, (int)br.a.getY() / SIZE + 1, "left");
					moveB(br, (int)br.a.getX() + SIZE, (int)br.a.getY() + SIZE);
					moveC(br, (int)br.b.getX(), (int)br.b.getY() - SIZE);
					moveD(br, (int)br.c.getX(), (int)br.c.getY() - SIZE);
					br.changeForm();
				}else if(br.getForm() == 2) {
					if(br.a.getX() + SIZE == WIDTH - SIZE)moveA(br, (int)br.a.getX(), (int)br.a.getY() + SIZE, (int)br.a.getX() / SIZE, (int)br.a.getY() / SIZE + 1, "left");
					else moveA(br, (int)br.a.getX() + SIZE, (int)br.a.getY() + SIZE, (int)br.a.getX() / SIZE + 1, (int)br.a.getY() / SIZE + 1, "bottom");
					moveB(br, (int)br.a.getX() + SIZE, (int)br.a.getY() - SIZE);
					moveC(br, (int)br.b.getX() - SIZE, (int)br.b.getY());
					moveD(br, (int)br.c.getX() - SIZE, (int)br.c.getY());
					br.changeForm();
				}else if(br.getForm() == 3) {
					if(br.a.getY() - SIZE == 0)moveA(br, (int)br.a.getX() + SIZE, (int)br.a.getY(), (int)br.a.getX() / SIZE + 1, (int)br.a.getY() / SIZE, "left");
					else moveA(br, (int)br.a.getX() + SIZE, (int)br.a.getY() - SIZE, (int)br.a.getX() / SIZE + 1, (int)br.a.getY() / SIZE - 1, "right");
					moveB(br, (int)br.a.getX() - SIZE, (int)br.a.getY() - SIZE);
					moveC(br, (int)br.b.getX(), (int)br.b.getY() + SIZE);
					moveD(br, (int)br.c.getX(), (int)br.c.getY() + SIZE);
					br.changeForm();
				}else if(br.getForm() == 4) {
					if(br.a.getX() - SIZE == 0)moveA(br, (int)br.a.getX(), (int)br.a.getY() - 1, (int)br.a.getX() / SIZE, (int)br.a.getY() / SIZE - 1, "left");
					else moveA(br, (int)br.a.getX() - SIZE, (int)br.a.getY() - SIZE, (int)br.a.getX() / SIZE - 1, (int)br.a.getY() / SIZE - 1, "top");
					moveB(br, (int)br.a.getX() - SIZE, (int)br.a.getY() + SIZE);
					moveC(br, (int)br.b.getX() + SIZE, (int)br.b.getY());
					moveD(br, (int)br.c.getX() + SIZE, (int)br.c.getY());
					br.changeForm();
				}
				break;
			}
		}
	}


	public static boolean fullRow(int[][] board, int rowIndex) {
		int count = 0;
		while(count < COLS) {
			if(board[rowIndex][count] == 0)break;
			else count++;
		}
		return count == COLS;
	}

	public static boolean emptyRow(int[][] board, int rowIndex) {
		int count = 0;
		while(count < COLS) {
			if(board[rowIndex][count] == 1)break;
			else count++;
		}
		return count == COLS;
	}

	//	public static void rePosBlocks(Pane pane) {
	//		int emptyCells = 0;
	//		for(int colsIndex = 0; colsIndex < COLS; colsIndex++) {
	//			for(int rowsIndex = ROWS - 1; rowsIndex >= 0; rowsIndex--) {
	//				//if(emptyRow(BOARD,rowsIndex))emptyLines++;
	//				if(BOARD[rowsIndex][colsIndex] == 0)emptyCells++;
	//				else {
	//					for(int i = 0; i < pane.getChildren().size(); i++) {
	//						if(pane.getChildren().get(i) instanceof Rectangle 
	//								&& (int)((Rectangle) pane.getChildren().get(i)).getY() == rowsIndex * SIZE
	//								&& (int)((Rectangle) pane.getChildren().get(i)).getX() == colsIndex * SIZE
	//								&& ((Rectangle) pane.getChildren().get(i)).getX() < WIDTH 
	//								&& ((Rectangle) pane.getChildren().get(i)).getStrokeWidth() == 0) {
	//							int cellX = (int) (((Rectangle) pane.getChildren().get(i)).getX() / SIZE);
	//							BOARD[rowsIndex][cellX] = 0;
	//							BOARD[rowsIndex + emptyCells][cellX] = 1;
	//							((Rectangle) pane.getChildren().get(i)).setY(rowsIndex * SIZE + SIZE * emptyCells);
	//						}
	//					}
	//				}
	//			}
	//			emptyCells = 0;
	//		}
	//	}

	public static void rePosBlocks(Pane pane) {
		int emptyRows = 0;
		//		for(int colIndex = 0; colIndex < COLS; colIndex++) {
		for(int rowIndex = lastEmptyRow; rowIndex >= 0; rowIndex-- ) {
			if(emptyRow(BOARD,rowIndex))emptyRows++;
			else {
				for(Node n : pane.getChildren()) {
					if(n instanceof Rectangle) {
						if(((Rectangle) n).getY() == rowIndex * SIZE && ((Rectangle) n).getX() < WIDTH
								&& ((Rectangle) n).getStrokeWidth() == 0) {
							int cellX = (int) (((Rectangle) n).getX() / SIZE);
							BOARD[rowIndex][cellX] = 0;
							BOARD[rowIndex + emptyRows][cellX] = 1;
							((Rectangle) n).setY(rowIndex * SIZE + SIZE * emptyRows);
						}
					}
				}
			}
		}
		//		}
	}

	public static void fadeEffect(Pane pane, Scene scene) {	
		LinkedList<Node> remove = new LinkedList<Node>();
		int col = 0;
		scoreFactor = full.size() + (full.size() > 1 ? full.size() : 0);
		for(int r = 0; r < full.size(); r++) {
			while(col < COLS) {
				for(int i = 0; i < pane.getChildren().size(); i++) {
					Node a = pane.getChildren().get(i);
					if(a instanceof Rectangle && ((Rectangle) a).getStrokeWidth() == 0 && ((Rectangle)a).getX() == col * SIZE && ((Rectangle)a).getY() == full.get(r) * SIZE) {
						remove.add(a);
						col++;
					}
				}
			}
			col = 0;
		}
		execute = new AnimationTimer() {

			@Override
			public void handle(long now) {
				// TODO Auto-generated method stub
				if(now - last >= 10_000_000) {
					if(remove.size() == 0) {
						InGame.setScore(InGame.getScore() + scoreFactor * COLS);
						InGame.setScoreText("Score: " + InGame.getScore());
						if(rowCount >= 10 && InGame.getTempo() > 100) {
//							InGame.getLvlSound().setVolume(1);
//							InGame.getLvlSound().stop();
//							InGame.getLvlSound().play();
//							InGame.getLvlSound().stop();
							rowCount -= 10;
							InGame.setTempo(InGame.getTempo() - 30);
							InGame.getSpeedText().setText("Speed: " + Math.round((double)(1000.0 / InGame.getTempo()) * 10) / 10.0 + "lps");
							//							InGame.setLevelText("Level: " + InGame.getLevel() + "\nSpeed: " + InGame.getTempo());
							InGame.setTempoChange();
						}
						InGame.isConsumed(false);
						execute.stop();
						last = 0;
						rePosBlocks(pane);

						full.clear();
						int trace = 0;	
						for(int rows = 0; rows < BOARD.length; rows++) {
							for(int cols = 0; cols < BOARD[0].length; cols++) {
								if(BOARD[rows][cols] == 1) {
									trace++;
								}
							}
							if(trace == COLS) {
								full.add(rows);
							}
							trace = 0;
						}
						if(full.size() > 0)fadeEffect(pane,scene);
						else InGame.getTimer().start();
					}
					else {
						int pos = pane.getChildren().indexOf(remove.getFirst());
						if(((Rectangle)pane.getChildren().get(pos)).getOpacity() < 0.1){
							Rectangle rmv = (Rectangle)remove.getFirst();
							BOARD[(int)(rmv.getY() / SIZE)][(int) (rmv.getX() / SIZE)] = 0;
							pane.getChildren().remove(rmv);
							//						op = 1.0;
							remove.removeFirst();
						}else {
							//						op -= 0.5;
							((Rectangle)pane.getChildren().get(pos)).setOpacity(0);
						}
					}
					last = now;
				}
			}

		};
		execute.start();
	}

	public static void removeRows(Pane pane, Scene scene) {
		int track = 0;	
		for(int rows = 0; rows < BOARD.length; rows++) {
			for(int cols = 0; cols < BOARD[0].length; cols++) {
				if(BOARD[rows][cols] == 1) {
					track++;
				}
			}
			if(track == COLS) {
				full.add(rows);
			}
			track = 0;
		}
		
		if(full.size() > 0) {		
			lastEmptyRow = full.getLast();
			rowCount += full.size();
			InGame.getTimer().stop();
			InGame.isConsumed(true);
			fadeEffect(pane, scene);			
		}
	}

	public static Bricks ScaleAndShift(Rectangle a, Rectangle b, Rectangle c, Rectangle d, String name, int size, int X, int Y) {
		int newSize = size - 1;
		a = new Rectangle(newSize,newSize);
		b = new Rectangle(newSize,newSize);
		c = new Rectangle(newSize,newSize);
		d = new Rectangle(newSize,newSize);
		if(name == "J") {
			a.setX(X);
			a.setY(Y);
			b.setX(a.getX());
			b.setY(a.getY() + size);
			c.setX(a.getX());
			c.setY(a.getY() + size * 2);
			d.setX(a.getX() - size);
			d.setY(a.getY() + size * 2);
		}else if(name == "L") {
			a.setX(X);
			a.setY(Y);
			b.setX(a.getX());
			b.setY(a.getY() + size);
			c.setX(a.getX());
			c.setY(a.getY() + size * 2);
			d.setX(a.getX() + size);
			d.setY(a.getY() + size * 2);
		}else if(name == "O") {
			a.setX(X - size);
			a.setY(Y);
			b.setX(a.getX());
			b.setY(a.getY() + size);
			c.setX(a.getX() + size);
			c.setY(a.getY());
			d.setX(a.getX() + size);
			d.setY(a.getY() + size);
		}else if(name == "S") {
			a.setX(X + size);
			a.setY(Y);
			b.setX(a.getX() - size);
			b.setY(a.getY());
			c.setX(a.getX() - size);
			c.setY(a.getY() + size);
			d.setX(a.getX() - size * 2);
			d.setY(a.getY() + size);
		}else if(name == "Z") {
			a.setX(X - size);
			a.setY(Y);
			b.setX(a.getX() + size);
			b.setY(a.getY());
			c.setX(a.getX() + size);
			c.setY(a.getY() + size);
			d.setX(a.getX() + size * 2);
			d.setY(a.getY() + size);
		}else if(name == "I") {
			a.setX(X);
			a.setY(Y);
			b.setX(a.getX());
			b.setY(a.getY() + size);
			c.setX(a.getX());
			c.setY(a.getY() + size * 2);
			d.setX(a.getX());
			d.setY(a.getY() + size * 3);
		}else {
			a.setX(X);
			a.setY(Y);
			b.setX(a.getX() - size);
			b.setY(a.getY() + size);
			c.setX(a.getX());
			c.setY(a.getY() + size);
			d.setX(a.getX() + size);
			d.setY(a.getY() + size);
		}
		return new Bricks(a,b,c,d,name,InGame.getImgSource());
	}
}
