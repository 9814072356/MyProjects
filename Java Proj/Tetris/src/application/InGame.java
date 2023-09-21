package application;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class InGame {
	//public static final int MOVE = 32;
	public static final int SIZE = 33;
	public static final int ROWS = 24;
	public static final int COLS = 12;
	public static int WIDTH = SIZE * COLS;
	public static int HEIGHT = SIZE * ROWS;
	public static int[][] BOARD = new int[ROWS][COLS];
	public static int score = 0;
	public static int curLevel = 0;
	public static int rectCount;
	public static int hour1 = 0;
	public static int hour2 = 0;
	public static int minute1 = 0;
	public static int minute2 = 0;
	public static int second1 = 0;
	public static int second2 = 0;
	public static int milisec1 = 0;
	public static int milisec2 = 0;
	public static long tempo = 500;
	public static double tempHeight;
	private static Pane group = new Pane();
	public static Scene scene = new Scene(group, WIDTH + WIDTH / 2, HEIGHT);
	private static boolean game = true;
	public static boolean stop = true;
	public static boolean tempochange = false;
	public static boolean play = true;
	public static boolean consumeKeyEvent = false;
	public static boolean closed = true;
	public static Text SCORE;
	public static Text nextBrick;
	public static Text level;
	public static Text P = new Text("P");
	public static Text A = new Text("A");
	public static Text U = new Text("U");
	public static Text S = new Text("S");
	public static Text E = new Text("E");
	public static Text infoText = new Text("Press P to continue");
	public static Text GAME = new Text("GAME");
	public static Text OVER = new Text("OVER");
	public static Text TOMENU = new Text("MENU");
	public static Text RESTART = new Text("RESTART");
	public static Text EXIT = new Text("EXIT");
	public static Text h1 = new Text("" + hour1);
	public static Text h2 = new Text("" + hour2);
	public static Text m1 = new Text("" + minute1);
	public static Text m2 = new Text("" + minute2);
	public static Text s1 = new Text("" + second1);
	public static Text s2 = new Text("" + second2);
	public static Text ms1 = new Text("" + milisec1);
	public static Text mColon = new Text(":");
	public static Text sColon = new Text(":");
	public static Text fullStop = new Text(".");
	public static Text setting = new Text("setting");
	public static Text opt2Txt = new Text("DEFAULT");
	public static Text speed = new Text("Speed");
	public static Text opt1Txt = new Text("PEWDIEPACK");
	public static Text back = new Text("BACK");
	public static String colorString = "#FCF3CF";
	public static String locBG;
	public static String boxSBG = "sound.png";
	public static String boxMBG = "mute.png";
	public static String musicFile;
	public static String levelUpsfx;
	public static String imgsource = Start.getSource();
	public static String brickMovesfx;
	public static Rectangle box = new Rectangle((SIZE-5)*5,(SIZE-5)*5);
	public static Rectangle r = new Rectangle(WIDTH, 10);
	public static Rectangle mute = new Rectangle(30,30);
	public static Rectangle pauseShade = new Rectangle(WIDTH, HEIGHT);
	public static Rectangle toMenu = new Rectangle();
	public static Rectangle exit = new Rectangle();
	public static Rectangle restart = new Rectangle();
	public static Rectangle tiles;
	public static Rectangle settingScreen = new Rectangle(WIDTH / 2 + 20, HEIGHT);
	public static Rectangle changeTheme = new Rectangle(WIDTH / 4, WIDTH / 8);
	public static Rectangle changeMusic = new Rectangle(WIDTH / 4, WIDTH / 8);
	public static Rectangle ret = new Rectangle(WIDTH / 2,settingScreen.getHeight() / 20);
	public static Rectangle option1 = new Rectangle(settingScreen.getWidth()/3,settingScreen.getWidth()/3);
	public static Rectangle option2 = new Rectangle(settingScreen.getWidth()/3,settingScreen.getWidth()/3);
	public static Rectangle background = new Rectangle(scene.getWidth(), scene.getHeight());
	public static Rectangle stroke1 = new Rectangle(changeTheme.getWidth() + 3, changeTheme.getHeight() + 3);
	public static Rectangle stroke2 = new Rectangle(changeTheme.getWidth() + 3, changeTheme.getHeight() + 3);
	public static Rectangle artIcon = new Rectangle(changeTheme.getHeight()-15, changeTheme.getHeight()-15);
	public static Rectangle musicIcon = new Rectangle(changeTheme.getHeight()-15, changeTheme.getHeight()-15);
	public static Bricks curBrick;
	public static Bricks nxtBrick;
	public static Line line;
	public static AnimationTimer fall;
	public static AnimationTimer time;
	public static AnimationTimer slideIn;
	public static AnimationTimer slideOut;
	public static AnimationTimer toggleListView;
	public static Media bgMusic;
	public static Media levelUp;
	public static Media brickMove;
	public static MediaPlayer mediaPlayer1;
	public static MediaPlayer mediaPlayer2;
	public static MediaPlayer mediaPlayer3;
	public static ImagePattern mutebg;
	public static ImagePattern soundbg;
	public static ImagePattern pat;
	public static ImagePattern artPat;
	public static ImagePattern musicPat;
	public static Image bm;
	public static Image bs;
	public static Image BG;
	public static Image art;
	public static Image music;
	public static Group options = new Group();
	public static ListView<String> musicList = new ListView<String>();

	//	@Override
	public void start(Stage stage, Scene scene) {
		nxtBrick = GameControls.makeBrick(imgsource);
		art = new Image("/backgrounds/art.png");
		artPat = new ImagePattern(art);
		music = new Image("/backgrounds/music.png");
		musicPat = new ImagePattern(music);
		if(imgsource == "pewdiepack") {
			setMusic("bitchlasagna.mp3","Odeds.mp3","move.mp3");
		}else {
			setMusic("tetris-remake.m4a","nice.mp3","move.mp3");
		}
		
		Font GOfont = 
				Font.loadFont(getClass()
						.getResourceAsStream("/font/game_over.ttf"), 150),
				genFont = 
				Font.loadFont(getClass()
						.getResourceAsStream("/font/Aspergit Bold.otf"),40),
				genFontSmall = 
				Font.loadFont(getClass()
						.getResourceAsStream("/font/Aspergit Bold.otf"),30),
				pewdsFont = Font.loadFont(getClass()
						.getResourceAsStream("/backgrounds/pewdiepack/FEASFBI_.ttf"),40),
				pewdsFontSmall = Font.loadFont(getClass()
						.getResourceAsStream("/backgrounds/pewdiepack/FEASFBI_.ttf"),25),
				opt1Font = Font.loadFont(getClass().getResourceAsStream("/font/Aspergit Bold.otf"), 10);;


		for (int[] a : BOARD) {
			Arrays.fill(a, 0);
		}
		if(imgsource.toLowerCase() != "") {
			locBG = "/backgrounds/" + imgsource + "/bg.jpg";
			BG = new Image(locBG);
			pat = new ImagePattern(BG);
		}

		bs = new Image(boxSBG);
		soundbg = new ImagePattern(bs);
		bm = new Image(boxMBG);
		mutebg = new ImagePattern(bm);
		//		background.setFill(Color.INDIANRED);
		//		background.toBack();
		//		background.setFill(pat);
		//		background.setX(0);
		//		background.setY(0);
		for(int i = 0; i < BOARD.length; i++) {
			for(int j = 0; j < BOARD[0].length; j++) {
				tiles = new Rectangle(SIZE, SIZE);
				tiles.setFill(Color.WHITE);
				//				tiles.setArcHeight(10);
				//				tiles.setArcWidth(10);
				tiles.setOpacity(0.8);
				tiles.setX(SIZE * j);
				tiles.setY(SIZE * i);
				group.getChildren().add(tiles);
			}
		}

		mute.setX(WIDTH + WIDTH / 2 - 60);
		mute.setY(HEIGHT - 60);
		mute.setFill(soundbg);
		mute.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent t) {
				if(!play) {
					mute.setFill(soundbg);
					mediaPlayer1.play();
					mediaPlayer1.setCycleCount(MediaPlayer.INDEFINITE);	
					mediaPlayer1.play();
					play = true;
				}else {
					mute.setFill(mutebg);
					mediaPlayer1.pause();
					play = false;
				}
			}
		});


		line = new Line(WIDTH, 0, WIDTH, HEIGHT+10);
		line.setStrokeWidth(5);
		line.setStroke(Color.BLACK);
		line.setOpacity(0.8);
		SCORE = new Text("Score: " + score);
		SCORE.setStroke(Color.BLACK);
		SCORE.setFill(imgsource == "" ? Color.web("E0FFFF") : Color.WHITE);
		SCORE.setFont(imgsource == "" ? Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20) : pewdsFont);
		SCORE.setY(50);
		SCORE.setX(WIDTH + 5);
		double scH = SCORE.getBoundsInLocal().getHeight();
		//		level = new Text("Level: " + curLevel + "\nSpeed: " + tempo + "\n");
		//		level.setStroke(Color.BLACK);
		//		level.setFont(imgsource == "" ? Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20) : pewdsFont); 
		//		level.setY(SCORE.getY() + 50);
		//		level.setX(SCORE.getX());
		//		level.setFill(imgsource == "" ? Color.web("E0FFFF") : Color.WHITE);
		speed = new Text("Speed: " + Math.round((double)(1000.0 / InGame.getTempo()) * 10) / 10.0 + "lps");
		speed.setStroke(Color.BLACK);
		speed.setFill(imgsource == "" ? Color.web("E0FFFF") : Color.WHITE);
		speed.setFont(imgsource == "" ? Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20) : pewdsFont);
		speed.setY(SCORE.getY() + scH * 1.5);
		speed.setX(WIDTH + 5);
		double spH = SCORE.getBoundsInLocal().getHeight();
		h1.setX(speed.getX());
		h1.setY(speed.getY() + spH * 1.5);
		h1.setFont(imgsource == "" ? Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20) : pewdsFontSmall);
		h1.setFill(imgsource == "" ? Color.web("E0FFFF") : Color.WHITE);
		h1.setStroke(Color.BLACK);
		double h1W = h1.getBoundsInLocal().getWidth();
		double h1H = h1.getBoundsInLocal().getHeight();
		h2.setX(h1.getX() + h1W);
		h2.setY(h1.getY());
		h2.setFont(imgsource == "" ? Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20) : pewdsFontSmall);
		h2.setFill(imgsource == "" ? Color.web("E0FFFF") : Color.WHITE);
		h2.setStroke(Color.BLACK);
		double h2W = h2.getBoundsInLocal().getWidth();
		mColon.setX(h2.getX() + h2W);
		mColon.setY(h2.getY());
		mColon.setFont(imgsource == "" ? Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20) : pewdsFontSmall);
		mColon.setFill(imgsource == "" ? Color.web("E0FFFF") : Color.WHITE);
		mColon.setStroke(Color.BLACK);
		double mCW = mColon.getBoundsInLocal().getWidth();
		m1.setX(mColon.getX() + mCW);
		m1.setY(mColon.getY());
		m1.setFont(imgsource == "" ? Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20) : pewdsFontSmall);
		m1.setFill(imgsource == "" ? Color.web("E0FFFF") : Color.WHITE);
		m1.setStroke(Color.BLACK);
		double m1W = m1.getBoundsInLocal().getWidth();
		m2.setX(m1.getX() + m1W);
		m2.setY(m1.getY());
		m2.setFont(imgsource == "" ? Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20) : pewdsFontSmall);
		m2.setFill(imgsource == "" ? Color.web("E0FFFF") : Color.WHITE);
		m2.setStroke(Color.BLACK);
		double m2W = m2.getBoundsInLocal().getWidth();
		sColon.setX(m2.getX() + m2W);
		sColon.setY(m2.getY());
		sColon.setFont(imgsource == "" ? Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20) : pewdsFontSmall);
		sColon.setFill(imgsource == "" ? Color.web("E0FFFF") : Color.WHITE);
		sColon.setStroke(Color.BLACK);
		double sCW = sColon.getBoundsInLocal().getWidth();
		s1.setX(sColon.getX() + sCW);
		s1.setY(sColon.getY());
		s1.setFont(imgsource == "" ? Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20) : pewdsFontSmall);
		s1.setFill(imgsource == "" ? Color.web("E0FFFF") : Color.WHITE);
		s1.setStroke(Color.BLACK);
		double s1W = s1.getBoundsInLocal().getWidth();
		s2.setX(s1.getX() + s1W);
		s2.setY(s1.getY());
		s2.setFont(imgsource == "" ? Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20) : pewdsFontSmall);
		s2.setFill(imgsource == "" ? Color.web("E0FFFF") : Color.WHITE);
		s2.setStroke(Color.BLACK);
		double s2W = s2.getBoundsInLocal().getWidth();
		fullStop.setX(s2.getX() + s2W + 5);
		fullStop.setY(s2.getY());
		fullStop.setFont(imgsource == "" ? Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20) : pewdsFontSmall);
		fullStop.setFill(imgsource == "" ? Color.web("E0FFFF") : Color.WHITE);
		fullStop.setStroke(Color.BLACK);
		double fSW = fullStop.getBoundsInLocal().getWidth();
		ms1.setX(fullStop.getX() + fSW + 5);
		ms1.setY(fullStop.getY());
		ms1.setFont(imgsource == "" ? Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20) : pewdsFontSmall);
		ms1.setFill(imgsource == "" ? Color.web("E0FFFF") : Color.WHITE);
		ms1.setStroke(Color.BLACK);
		nextBrick = new Text("Next Brick: ");
		nextBrick.setStroke(Color.BLACK);
		nextBrick.setFont(imgsource == "" ? Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20) : pewdsFont);
		nextBrick.setY(h1.getY() + h1H * 2);
		nextBrick.setX(h1.getX());
		nextBrick.setFill(imgsource == "" ? Color.web("E0FFFF") : Color.WHITE);
		double nBH = nextBrick.getBoundsInLocal().getHeight();


		box.setY(nextBrick.getY() + nBH * 2);
		box.setX(WIDTH + 25);
		box.setFill(Color.GHOSTWHITE);
		box.setStroke(Color.GHOSTWHITE);
		box.setStrokeWidth(4);
		box.setArcHeight(15);
		box.setArcWidth(15);
		box.setOpacity(0.9);
		r.setFill(Color.DARKORANGE);
		r.setX(0);
		r.setY(HEIGHT);

		pauseShade.setOpacity(0.8);
		pauseShade.setVisible(false);
		U.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50));
		U.setFill(Color.ORANGE);
		double UWidth = U.getBoundsInLocal().getWidth();
		U.setY(pauseShade.getHeight() / 2);
		U.setX(pauseShade.getWidth() / 2 - UWidth / 2);
		A.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50));
		A.setFill(Color.GREEN);
		double AWidth = A.getBoundsInLocal().getWidth();
		A.setY(U.getY());
		A.setX(U.getX() - 2 * AWidth);
		P.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50));
		P.setFill(Color.RED);
		double PWidth = P.getBoundsInLocal().getWidth();
		P.setY(U.getY());
		P.setX(A.getX() - 2 * PWidth);
		S.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50));
		S.setFill(Color.MEDIUMVIOLETRED);
		double SWidth = S.getBoundsInLocal().getWidth();
		S.setY(U.getY());
		S.setX(U.getX() + 2 * SWidth);
		E.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50));
		E.setFill(Color.CYAN);
		double EWidth = E.getBoundsInLocal().getWidth();
		E.setY(U.getY());
		E.setX(S.getX() + 2 * EWidth);
		infoText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
		infoText.setFill(Color.ORANGERED);
		double infoWidth = infoText.getBoundsInLocal().getWidth();
		double infoHeight = infoText.getBoundsInLocal().getHeight();
		infoText.setY(U.getY() + infoHeight + 10 );
		infoText.setX(pauseShade.getWidth() / 2 - infoWidth / 2);
		
		//S E T T I N G - A R E A
		settingScreen.setX(scene.getWidth());
		settingScreen.setY(0);
		settingScreen.setFill(Color.web("696969"));
		settingScreen.setOpacity(0.8);
		settingScreen.setVisible(true);
		settingScreen.toFront();
		settingScreen.setArcHeight(20);
		settingScreen.setArcWidth(20);
		ret.setX(settingScreen.getX());
		ret.setY(settingScreen.getHeight() - ret.getHeight());
		ret.setFill(Color.web("fff5db"));
		ret.setOpacity(0.9);
		ret.setArcHeight(20);
		ret.setArcWidth(20);
		ret.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent t) {
				slideOut();
			}
		});
		back.setFont(genFontSmall);
		back.setFill(Color.CORNFLOWERBLUE);
		double bW = back.getBoundsInLocal().getWidth();
		double bH = back.getBoundsInLocal().getHeight();
		back.setX(ret.getX() + ret.getWidth() / 2 - bW / 2 + 5);
		back.setY(ret.getY() + ret.getHeight() / 2 + bH / 2 - 5);
		back.toFront();
		changeTheme.setX(WIDTH + (scene.getWidth() - WIDTH) / 2 - changeTheme.getWidth() / 2);
		changeTheme.setY(box.getY() + box.getHeight() + 50);
		changeTheme.setFill(Color.web("ffd301"));
		changeTheme.setStroke(Color.GHOSTWHITE);
		changeTheme.setStrokeWidth(3);
		changeMusic.setX(changeTheme.getX());
		changeMusic.setY(changeTheme.getY() + changeTheme.getHeight() + 15);
		changeMusic.setFill(Color.web("ffd301"));
		changeMusic.setStroke(Color.GHOSTWHITE);
		changeMusic.setStrokeWidth(3);
		stroke1.setFill(Color.TRANSPARENT);
		stroke1.setStroke(Color.BLACK);
		stroke1.setStrokeWidth(4);
		stroke1.setArcHeight(15);
		stroke1.setArcWidth(15);
		stroke1.setX(changeTheme.getX() - 1.5);
		stroke1.setY(changeTheme.getY() - 1.5);
		stroke2.setFill(Color.TRANSPARENT);
		stroke2.setStroke(Color.BLACK);
		stroke2.setStrokeWidth(4);
		stroke2.setArcHeight(15);
		stroke2.setArcWidth(15);
		stroke2.setX(changeMusic.getX() - 1.5);
		stroke2.setY(changeMusic.getY() - 1.5);
		changeTheme.setArcHeight(15);
		changeTheme.setArcWidth(15);
		changeTheme.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent t) {
				slideIn();
			}
		});
		changeMusic.setArcHeight(15);
		changeMusic.setArcWidth(15);
		changeMusic.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent t) {
				toggle();
			}
		});
		musicList.setPrefSize(WIDTH / 2 - line.getStrokeWidth() - 4, 0);
		musicList.setLayoutX(WIDTH + WIDTH / 4 - musicList.getPrefWidth() / 2);
		musicList.setLayoutY(changeMusic.getY() + changeMusic.getHeight() + 5);
		musicList.setVisible(false);
		musicList.getItems().addAll(makeMusicList());
		musicList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String prevChoice, String currentChoice) {
				// TODO Auto-generated method stub
				mediaPlayer1.stop();
				musicFile = currentChoice.toString();
				bgMusic = new Media(new File(musicFile).toURI().toString());
				mediaPlayer1 = new MediaPlayer(bgMusic);
				mediaPlayer1.setVolume(0.2);
				if(play)mediaPlayer1.play();
			}
			
		});
		artIcon.setFill(artPat);
		artIcon.setX(changeTheme.getX() + changeTheme.getWidth()/2 - artIcon.getWidth()/2);
		artIcon.setY(changeTheme.getY() + changeTheme.getHeight()/2 - artIcon.getHeight()/2);
		artIcon.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent t) {
				slideIn();
			}
		});
		musicIcon.setFill(musicPat);
		musicIcon.setX(changeMusic.getX() + changeMusic.getWidth()/2 - musicIcon.getWidth()/2);
		musicIcon.setY(changeMusic.getY() + changeMusic.getHeight()/2 - musicIcon.getHeight()/2);
		musicIcon.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent t) {
				toggle();
			}
		});
		option1.setStroke(Color.BLACK);
		option1.setFill(Color.RED);
		option1.setStrokeWidth(3);
		option1.setArcHeight(5);
		option1.setArcWidth(5);
		option1.setX(settingScreen.getX() + 5);
		option1.setY(settingScreen.getY() + 5);
		option1.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				imgsource = "pewdiepack";
				String tmp = "/backgrounds/pewdiepack/bg.jpg";
				Image temp = new Image(tmp);
				ImagePattern p = new ImagePattern(temp);
				background.setFill(p);
				setFont(pewdsFont,pewdsFontSmall,Color.WHITE,Color.BLACK);
				mediaPlayer1.stop();
				setMusic("bitchlasagna.mp3","odeds.mp3","move.mp3");
//				settingScreen.setVisible(false);
//				for(int i = 0; i < options.getChildren().size(); i++)options.getChildren().get(i).setVisible(false);
			}

		});

		double op1H = opt1Txt.getBoundsInLocal().getHeight();
		opt1Txt.setX(option1.getX());
		opt1Txt.setY(option1.getY() + option1.getHeight() + option1.getStrokeWidth() + op1H);
		opt1Txt.setFont(opt1Font);
		opt1Txt.setFill(Color.WHITE);
		
		option2.setStroke(Color.web("AB8024"));
		option2.setFill(Color.web("FFD700"));
		option2.setStrokeWidth(option1.getStrokeWidth());
		option2.setArcHeight(option1.getArcHeight());
		option2.setArcWidth(option1.getArcWidth());
		option2.setX(option1.getX() + option1.getWidth() + option1.getStrokeWidth() + 5);
		option2.setY(option1.getY());
		option2.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				imgsource = "";
				background.setFill(Color.web("ffe19c"));
				setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20),Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20),Color.web("E0FFFF"),Color.BLACK);
				mediaPlayer1.stop();
				setMusic("jojo.mp3","nice.mp3","move.mp3");
			}

		});

		double op2H = opt2Txt.getBoundsInLocal().getHeight();
		opt2Txt.setX(option2.getX());
		opt2Txt.setY(option2.getY() + option2.getHeight() + option2.getStrokeWidth() + op2H);
		opt2Txt.setFont(opt1Font);
		opt2Txt.setFill(Color.WHITE);
		
		options.getChildren().addAll(option1,option2,opt1Txt,opt2Txt);

		GAME.setFont(GOfont);
		GAME.setFill(Color.WHITE);
		double GAMEw = GAME.getBoundsInLocal().getWidth();
		GAME.setY(pauseShade.getHeight() / 2 - 70);
		GAME.setX(pauseShade.getWidth() / 2 - GAMEw - 10);
		OVER.setFont(GOfont);
		OVER.setFill(Color.WHITE);
		OVER.setY(pauseShade.getHeight() / 2 - 70);
		OVER.setX(pauseShade.getWidth() / 2 + 10);
		GAME.setVisible(false);OVER.setVisible(false);
		P.setVisible(false);A.setVisible(false);U.setVisible(false);S.setVisible(false);E.setVisible(false);infoText.setVisible(false);

		
		TOMENU.setFont(genFont);
		TOMENU.setFill(Color.WHITE);
		double menuW = TOMENU.getBoundsInLocal().getWidth();
		double menuH = TOMENU.getBoundsInLocal().getHeight();
		RESTART.setFont(genFont);
		RESTART.setFill(Color.WHITE);
		double reW = RESTART.getBoundsInLocal().getWidth();
		double reH = RESTART.getBoundsInLocal().getHeight();
		EXIT.setFont(genFont);
		EXIT.setFill(Color.WHITE);
		double exW = EXIT.getBoundsInLocal().getWidth();
		double exH = EXIT.getBoundsInLocal().getHeight();
		toMenu.setWidth(menuW + 20);
		toMenu.setHeight(pauseShade.getHeight() / 16);
		toMenu.setX(pauseShade.getWidth() / 2 - toMenu.getWidth() / 2);
		toMenu.setY(GAME.getY() + 70);
		toMenu.setFill(Color.RED);
		TOMENU.setX(toMenu.getX() + toMenu.getWidth() / 2 - menuW / 2);
		TOMENU.setY(toMenu.getY() + toMenu.getHeight() / 2 + menuH / 2 - 5);
		restart.setWidth(reW + 20);
		restart.setHeight(pauseShade.getHeight() / 16);
		restart.setX(pauseShade.getWidth() / 2 - restart.getWidth() / 2);
		restart.setY(toMenu.getY() + 70);
		restart.setFill(Color.BLACK);
		RESTART.setX(restart.getX() + restart.getWidth() / 2 - reW / 2);
		RESTART.setY(restart.getY() + restart.getHeight() / 2 + reH / 2 - 5);
		exit.setWidth(exW + 20);
		exit.setHeight(pauseShade.getHeight() / 16);
		exit.setX(pauseShade.getWidth() / 2 - exit.getWidth() / 2);
		exit.setY(restart.getY() + 70);
		exit.setFill(Color.RED);
		EXIT.setX(exit.getX() + exit.getWidth() / 2 - exW / 2);
		EXIT.setY(exit.getY() + exit.getHeight() / 2 + exH / 2 - 5);
		exit.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent t) {
				System.exit(0);
			}
		});
		EXIT.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent t) {
				System.exit(0);
			}
		});
		restart.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent t) {
				restart();
				SCORE.setText("Score: " + score);
				speed.setText("Speed: " + Math.round((double)(1000.0 / InGame.getTempo()) * 10) / 10.0 + "lps");
			}
		});
		RESTART.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent t) {
				restart();
				SCORE.setText("Score: " + score);
				speed.setText("Speed: " + Math.round((double)(1000.0 / InGame.getTempo()) * 10) / 10.0 + "lps");
			}
		});
		toMenu.setVisible(false);TOMENU.setVisible(false);
		toMenu.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent t) {
				Start.returnDetection(true);
				stage.setResizable(false);
				stage.setScene(Start.getScene());
				stage.show();
			};
		});
		TOMENU.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent t) {
				Start.returnDetection(true);
				stage.setResizable(false);
				stage.setScene(Start.getScene());
				stage.show();
			};
		});
		restart.setVisible(false);RESTART.setVisible(false);
		exit.setVisible(false);EXIT.setVisible(false);

		group.getChildren().addAll(background, SCORE, speed, line, nextBrick, box, r, mute, h1, h2, mColon, m1, m2, sColon, s1, s2, fullStop, ms1);

		Bricks a = nxtBrick;
		group.getChildren().addAll(a.a, a.b, a.c, a.d);
		moveOnKeyPress(a, scene);
		curBrick = a;
		nxtBrick = GameControls.makeBrick(imgsource);
		nxtBrick = GameControls.ScaleAndShift(nxtBrick.a, nxtBrick.b, nxtBrick.c, nxtBrick.d, nxtBrick.name, SIZE - 5, (int)box.getX() + (SIZE - 5) * 2, (int)box.getY() + (SIZE - 5)); 
		group.getChildren().addAll(nxtBrick.a, nxtBrick.b, nxtBrick.c, nxtBrick.d, 
				pauseShade, P, A, U, S, E, infoText, GAME, OVER, toMenu, exit, restart, TOMENU, RESTART, EXIT,
				stroke1, stroke2, changeTheme, changeMusic, musicList, artIcon, musicIcon, settingScreen, ret, back, options);

		background.toBack();
		background.setFill(imgsource == "" ? Color.web("ffe19c") : pat);

		stage.setScene(scene);
		stage.setTitle("T E T R I S T E T R I S T E T R I S T E T R I S T E T R I S ");
		//stage.sizeToScene();
		stage.setResizable(false);
		stage.show();
		stage.setOnCloseRequest(e -> {
			System.exit(0);
		});
		mediaPlayer1.setVolume(0.2);
		mediaPlayer1.play();
		mediaPlayer1.setCycleCount(MediaPlayer.INDEFINITE);
		mediaPlayer1.play();
		startTimer(scene);

		showTimeElapsed();
	}
	
	/*GETTER*/
	
	public static Text getSpeedText() {
		return speed;
	}
	
	public static MediaPlayer getLvlSound() {
		return mediaPlayer2;
	}

	public static double getSceneWidth() {
		return (double)WIDTH + WIDTH / 2;
	}

	public static double getSceneHeight() {
		return (double)HEIGHT;
	}

	public static Bricks getNextBrick(){
		return nxtBrick;
	}

	public static Bricks getCurrentBrick(){
		return curBrick;
	}

	public static Pane getGroup() {
		return group;
	}

	public static int getLevel() {
		return curLevel;
	}

	public static void levelUp() {
		curLevel++;
	}

	public static int getScore() {
		return score;
	}

	public static Text getScoreText() {
		return SCORE;
	}

	public static long getTempo() {
		return tempo;
	}

	public static Text getLevelText() {
		return level;
	}

	public static String getColorString() {
		return colorString;
	}

	public static String getLocBG() {
		return locBG;
	}

	public static boolean getTempoChange() {
		return tempochange;
	}

	public static Scene getScene() {
		return scene;
	}
	
	public static MediaPlayer brickMovesfx() {
		return mediaPlayer3;
	}

	public static String getImgSource() {
		return imgsource;
	}
	
	/*GETTER*/

	/*SETTER*/
	
	public static void setTempoChange() {
		tempochange = !tempochange;
	}

	public static void setTempo(long tmp) {
		tempo = tmp;
	}

	public static void setScoreText(String text) {
		SCORE.setText(text);
	}

	public static void setScore(int newScore) {
		score = newScore;
	}

	public static void setLevel(int lv) {
		curLevel = lv;
	}

	public static void setImgSource(String s) {
		imgsource = s;
	}
	
	public static void setFont(Font font, Font timerFont, Color color, Color strokeColor) {
		SCORE.setFont(font);
		speed.setFont(font);
		nextBrick.setFont(font);
		h1.setFont(timerFont);
		h2.setFont(timerFont);
		m1.setFont(timerFont);
		m2.setFont(timerFont);
		s1.setFont(timerFont);
		s2.setFont(timerFont);
		ms1.setFont(timerFont);
		SCORE.setFill(color);
		speed.setFill(color);
		nextBrick.setFill(color);
		h1.setFill(color);
		h2.setFill(color);
		m1.setFill(color);
		m2.setFill(color);
		s1.setFill(color);
		s2.setFill(color);
		ms1.setFill(color);
		SCORE.setStroke(strokeColor);
		speed.setStroke(strokeColor);
		nextBrick.setStroke(strokeColor);
		h1.setStroke(strokeColor);
		h2.setStroke(strokeColor);
		m1.setStroke(strokeColor);
		m2.setStroke(strokeColor);
		s1.setStroke(strokeColor);
		s2.setStroke(strokeColor);
		ms1.setStroke(strokeColor);
		fullStop.setFont(timerFont);
		fullStop.setFill(color);
		fullStop.setStroke(strokeColor);
		sColon.setFont(timerFont);
		sColon.setFill(color);
		sColon.setStroke(strokeColor);
		mColon.setFont(timerFont);
		mColon.setFill(color);
		mColon.setStroke(strokeColor);
	}
	
	public static void setMusic(String musicsource, String lvl, String move) {
		musicFile = musicsource;
		levelUpsfx = lvl;
		brickMovesfx = move;
		bgMusic = new Media(new File(musicFile).toURI().toString());
		levelUp = new Media(new File(levelUpsfx).toURI().toString());
		brickMove = new Media(new File(brickMovesfx).toURI().toString());
		mediaPlayer1 = new MediaPlayer(bgMusic);
		mediaPlayer2 = new MediaPlayer(levelUp);
		mediaPlayer3 = new MediaPlayer(brickMove);
		//mediaPlayer1.setVolume(0.9);
		mediaPlayer1.play();
		mediaPlayer1.setCycleCount(MediaPlayer.INDEFINITE);
		mediaPlayer1.play();
		
	}
	
	/*SETTER*/

	/*OTHERS*/
	
	public static AnimationTimer getTimer() {
		return fall;
	}
	
	public static ArrayList<String> makeMusicList(){
		ArrayList<String> musicList = new ArrayList<String>();
		
		musicList.add("bitchlasagna.mp3");
		musicList.add("jojo.mp3");
		
		return musicList;
	}

	public static void isConsumed(boolean b) {
		consumeKeyEvent = b;
	}
	
	public static void slideIn() {
		slideIn = new AnimationTimer() {
			long last = 0;
			@Override
			public void handle(long now) {
				// TODO Auto-generated method stub
				if(now - last >= 16_000_000) {
					stop = true;
					consumeKeyEvent = true;
					settingScreen.setX(settingScreen.getX() - 3);
					ret.setX(ret.getX() - 3);
					option1.setX(option1.getX() - 3);
					opt1Txt.setX(opt1Txt.getX() - 3);
					option2.setX(option2.getX() - 3);
					opt2Txt.setX(opt2Txt.getX() - 3);
					back.setX(back.getX() - 3);
					if(settingScreen.getX() <= WIDTH + line.getStrokeWidth() - 3) {
						slideIn.stop();
						time.stop();
						fall.stop();
					}
				}
			}

		};
		slideIn.start();
	}
	
	public static void slideOut() {
		slideOut = new AnimationTimer() {
			long last = 0;
			@Override
			public void handle(long now) {
				// TODO Auto-generated method stub
				if(now - last >= 20_000_000) {
					stop = false;
					consumeKeyEvent = false;
					pause(scene);
					settingScreen.setX(settingScreen.getX() + 10);
					ret.setX(settingScreen.getX());
					option1.setX(option1.getX() + 10);
					opt1Txt.setX(opt1Txt.getX() + 10);
					option2.setX(option2.getX() + 10);
					opt2Txt.setX(opt2Txt.getX() + 10);
					back.setX(back.getX() + 10);
					if(settingScreen.getX() >= scene.getWidth() + 3) {
						slideOut.stop();
						time.start();
						fall.start();
					}
				}
			}

		};
		slideOut.start();
	}
	
	public static void toggle() {
		tempHeight = musicList.getPrefHeight();
		toggleListView = new AnimationTimer() {

			@Override
			public void handle(long arg0) {
				// TODO Auto-generated method stub
				if(!closed) {
					tempHeight -= 10.0;
					if(tempHeight <= 0)tempHeight = 0;
					musicList.setPrefHeight(tempHeight);
					if(musicList.getPrefHeight() <= 0) {
						closed = true;
						musicList.setVisible(false);
						time.start();
						pause(scene);
						toggleListView.stop();
					}
				}else {
					musicList.setVisible(true);
					tempHeight += 10.0;
					if(tempHeight >= HEIGHT / 4)tempHeight = HEIGHT / 4;
					musicList.setPrefHeight(tempHeight);
					musicList.setPrefHeight(musicList.getPrefHeight() + 10);
					if(musicList.getPrefHeight() >= HEIGHT / 4) {
						closed = false;
						musicList.setVisible(true);
						time.stop();
						pause(scene);
						toggleListView.stop();
					}
					
				}
			}
			
		};
		
		toggleListView.start();
	}

	public static void showTimeElapsed() {
		milisec1 = second2 = second1 = minute2 = minute1 = hour2 = hour1 = 0;
		ms1.setText("" + milisec1);
		s2.setText("" + second2);
		s1.setText("" + second1);
		m2.setText("" + minute2);
		m1.setText("" + minute1);
		h2.setText("" + hour2);
		h1.setText("" + hour1);
		time = new AnimationTimer(){

			@Override
			public void handle(long now) {
				// TODO Auto-generated method stub

				if(milisec1 != 60) {
					milisec1++;
					ms1.setText("" + milisec1);
				}else {
					milisec1 = 0;
					ms1.setText("" + milisec1);
					if(second2 != 9) {
						second2++;
						s2.setText("" + second2);
					}else {
						second2 = 0;
						s2.setText("" + second2);
						if(second1 != 5) {
							second1++;
							s1.setText("" + second1);
						}else {
							second1 = 0;
							s1.setText("" + second1);
							if(minute2 != 9) {
								minute2++;
								m2.setText("" + minute2);
							}else {
								minute2 = 0;
								m2.setText("" + minute2);
								if(minute1 != 5) {
									minute1++;
									m1.setText("" + minute1);
								}else {
									minute1 = 0;
									m1.setText("" + minute1);
									if(hour2 != 9) {
										hour2++;
										h2.setText("" + hour2);
									}else {
										hour2 = 0;
										h2.setText("" + hour2);
										if(hour1 != 9) {
											hour1++;
											h1.setText("" + hour1);
										}else {
											time.stop();
										}
									}
								}
							}
						}
					}
				}
			}
		};
		time.start();
	}

	public static void restart() {
		consumeKeyEvent = false;
		tempo = 500;
		score = 0;		
		LinkedList<Node> toRemove = new LinkedList<Node>();
		for(Node a : group.getChildren()) {
			if(a instanceof Rectangle && ((Rectangle) a).getStrokeWidth() == 0) {
				toRemove.add(a);
			}
		}
		for(Node a : toRemove) {
			Rectangle rmv = (Rectangle) a;
			if(rmv.getStrokeWidth() == 0) {
				group.getChildren().remove(rmv);
			}
		}
		for (int[] a : BOARD) {
			Arrays.fill(a, 0);
		}
		mediaPlayer1.setVolume(0.2);
		mediaPlayer1.play();
		mediaPlayer1.setCycleCount(MediaPlayer.INDEFINITE);
		mediaPlayer1.play();
		score = 0;
		curLevel = 0;
		nxtBrick = GameControls.makeBrick(imgsource);
		Bricks a = nxtBrick;
		group.getChildren().addAll(a.a, a.b, a.c, a.d);
		moveOnKeyPress(a,scene);
		curBrick = a;
		nxtBrick = GameControls.makeBrick(imgsource);
		nxtBrick = GameControls.ScaleAndShift(nxtBrick.a, nxtBrick.b, nxtBrick.c, nxtBrick.d, nxtBrick.name, SIZE - 5, (int)box.getX() + (SIZE - 5) * 2, (int)box.getY() + (SIZE - 5)); 
		group.getChildren().addAll(nxtBrick.a, nxtBrick.b, nxtBrick.c, nxtBrick.d);
		toMenu.setVisible(false);TOMENU.setVisible(false);
		restart.setVisible(false);RESTART.setVisible(false);
		exit.setVisible(false);EXIT.setVisible(false);
		GAME.setVisible(false);OVER.setVisible(false);
		pauseShade.setVisible(false);
		game = true;
		score = 0;
		showTimeElapsed();
		slideOut();
		fall.start();
	}
	
	public static void moveOnKeyPress(Bricks br, Scene scene) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(consumeKeyEvent) {
					event.consume();
					return;
				}

				switch (event.getCode()) {
				case RIGHT:
					if(stop)GameControls.MoveRight(br);
					break;
				case DOWN:
					if(stop)GameControls.MoveDown(br, scene);
					break;
				case LEFT:
					if(stop)GameControls.MoveLeft(br);
					break;
				case UP:
					if(stop)GameControls.MoveTurn(br);
					break;
				case P:
					pause(scene);
					break;
				default:
					break;
				}
			}
		});
	}

	public static void pause(Scene scene) {
		if(stop) {
			pauseShade.toFront();
			P.toFront();
			A.toFront();
			U.toFront();
			S.toFront();
			E.toFront();
			infoText.toFront();
			stop = !stop;
			fall.stop();
			mediaPlayer1.pause();
			mute.setFill(mutebg);
			pauseShade.setFill(Color.WHITE);
			pauseShade.setVisible(true);
			P.setVisible(true);A.setVisible(true);U.setVisible(true);S.setVisible(true);E.setVisible(true);infoText.setVisible(true);
			play = false;
			time.stop();
			//System.out.println(stop);
		}else {
			stop = !stop;
			mediaPlayer1.play();
			mute.setFill(soundbg);
			pauseShade.setVisible(false);
			P.setVisible(false);A.setVisible(false);U.setVisible(false);S.setVisible(false);E.setVisible(false);infoText.setVisible(false);
			play = true;
			time.start();
			fall.start();
		}
	}

	public static void startTimer(Scene scene) {
		fall = new AnimationTimer() {
			long last = 0;
			@Override
			public void handle(long now) {
				// TODO Auto-generated method stub
				if(now - last >= tempo * 1000000) {
					if(tempochange) {
						mediaPlayer2.setVolume(1);
						mediaPlayer2.stop();
						mediaPlayer2.play();
						setTempoChange();
					}
					int count = 0;
					for(int rows = 0; rows < 4; rows++) {
						for(int cols = 0; cols < BOARD[0].length; cols++) {
							if(BOARD[rows][cols] == 0)count++;
						}
					}
					if((curBrick.a.getY() == 0 || curBrick.b.getY() == 0 || curBrick.c.getY() == 0 || curBrick.d.getY() == 0) && count < 24 || BOARD[0][(int)COLS/2] == 1) {
						game = false;
						isConsumed(true);
						fall.stop();
						pauseShade.setVisible(true);
						toMenu.setVisible(true);TOMENU.setVisible(true);
						restart.setVisible(true);RESTART.setVisible(true);
						exit.setVisible(true);EXIT.setVisible(true);
						pauseShade.setFill(Color.BLACK);
						GAME.setVisible(true);OVER.setVisible(true);
						pauseShade.toFront();
						GAME.toFront();
						OVER.toFront();
						toMenu.toFront();
						exit.toFront();
						restart.toFront();
						TOMENU.toFront();
						RESTART.toFront();
						EXIT.toFront();
						mediaPlayer1.stop();
						time.stop();
					}
					if(game) {
						GameControls.MoveDown(curBrick, scene);
					}
					last = now;
				}
			}

		};
		fall.start();
	}
	
	/*OTHERS*/
}