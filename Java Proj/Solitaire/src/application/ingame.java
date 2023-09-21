package application;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ingame extends Application{
		public static Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
	//	public static final double screenWidth = screenBounds.getWidth();
	//	public static final double screenHeight = screenBounds.getHeight();
		public static double tempHeight = screenBounds.getHeight() * 0.97;
	public static final double HEIGHT = tempHeight <= 760 ? 760 : (tempHeight >= 840 ? 840 : tempHeight);	
	public static final double WIDTH = tempHeight <= 760 ? 760 * 1.1364 : (tempHeight >= 840 ? 840 * 1.1364 : HEIGHT * 1.1364);
	public static int minute1 = 0,minute2 = 0,second1 = 0,second2 = 0;
	public static int difficulty = 10;
	public static Pane pane = new Pane();
	public static Pane settingPane = new Pane();
	public static Pane endGamePane = new Pane();
	public static Scene scene = new Scene(pane,WIDTH,HEIGHT);
	public static Rectangle rim = new Rectangle(WIDTH + 10,HEIGHT + 10);
	public static Rectangle bundle1 = new Rectangle(WIDTH * 0.109,HEIGHT * 0.1894);
	public static Rectangle bundle2 = new Rectangle(bundle1.getWidth(),bundle1.getHeight());
	public static Rectangle bundle3 = new Rectangle(bundle1.getWidth(),bundle1.getHeight());
	public static Rectangle bundle4 = new Rectangle(bundle1.getWidth(),bundle1.getHeight());
	public static Rectangle drawnCards = new Rectangle(bundle1.getWidth(),bundle1.getHeight());
	public static Rectangle row1 = new Rectangle(bundle1.getWidth(),bundle1.getHeight());
	public static Rectangle row2 = new Rectangle(bundle1.getWidth(),bundle1.getHeight());
	public static Rectangle row3 = new Rectangle(bundle1.getWidth(),bundle1.getHeight());
	public static Rectangle row4 = new Rectangle(bundle1.getWidth(),bundle1.getHeight());
	public static Rectangle row5 = new Rectangle(bundle1.getWidth(),bundle1.getHeight());
	public static Rectangle row6 = new Rectangle(bundle1.getWidth(),bundle1.getHeight());
	public static Rectangle row7 = new Rectangle(bundle1.getWidth(),bundle1.getHeight());
	public static Rectangle preview = new Rectangle(WIDTH / 2, HEIGHT / 2);
	public static Rectangle choosePicture = new Rectangle(preview.getWidth(),20,Color.ANTIQUEWHITE);
	public static Rectangle setting = new Rectangle(WIDTH / 3 - 4,HEIGHT / 30 - 4,Color.ANTIQUEWHITE);
	public static Rectangle settingScreen = new Rectangle(WIDTH,HEIGHT - setting.getHeight() - 6);
	public static Rectangle endGameScreen = new Rectangle(WIDTH / 2,HEIGHT / 3);
	public static Rectangle restart = new Rectangle(setting.getWidth(),setting.getHeight(),Color.ANTIQUEWHITE);
	public static Rectangle exit = new Rectangle(setting.getWidth(),setting.getHeight(),Color.ANTIQUEWHITE);
	public static Rectangle restart2 = new Rectangle(WIDTH / 5,HEIGHT / 20,Color.web("6897bb"));
	public static Rectangle exit2 = new Rectangle(restart2.getWidth(),restart2.getHeight(),restart2.getFill());
	public static Rectangle modes = new Rectangle(WIDTH / 4.25,HEIGHT / 2,Color.TRANSPARENT);
	public static Rectangle easy = new Rectangle(WIDTH / 6,HEIGHT / 30,Color.ANTIQUEWHITE);
	public static Rectangle medium = new Rectangle(WIDTH / 6,HEIGHT / 30,Color.ANTIQUEWHITE);
	public static Rectangle hard = new Rectangle(WIDTH / 6,HEIGHT / 30,Color.ANTIQUEWHITE);
	public static Rectangle separator = new Rectangle(WIDTH - 10, 3);
	public static Rectangle cardFill = new Rectangle(WIDTH / 4.25,HEIGHT / 2,Color.TRANSPARENT);
	public static Rectangle tempBack = new Rectangle(drawnCards.getWidth(),drawnCards.getHeight());
	public static Rectangle chooseCardBack = new Rectangle(tempBack.getWidth(),20,Color.ANTIQUEWHITE);
	public static Rectangle remove = new Rectangle(cardFill.getWidth() - 10,restart.getHeight(),restart.getFill());
	public static Text RESTART = new Text("R  E  S  T  A  R  T");
	public static Text SETTING = new Text("S  E  T  T  I  N  G");
	public static Text FOURDOTS = new Text(".  .  .  .");
	public static Text THREEDOTS = new Text(".  .  .");
	public static Text BORDERLINE = new Text("Outline color");
	public static Text OPACITYLEVEL = new Text("Card opacity");
	public static Text CLEAR = new Text("R  E  S  E  T");
	public static Text NOTE = new Text("(Hex color only)");
	public static Text EASY = new Text("E  A  S  Y");
	public static Text MEDIUM = new Text("M  E  D  I  U  M");
	public static Text HARD = new Text("H  A  R  D");
	public static Text DIFFICULTY = new Text("D  I  F  F  I  C  U  L  T  Y");
	public static Text YOUWIN = new Text("Y  O  U   W  I  N");
	public static Text EXIT = new Text("E   X   I   T");
	public static Text EXIT2 = new Text("E   X   I   T");
	public static Text RESTART2 = new Text("R  E  S  T  A  R  T");	
	public static Text m1 = new Text("" + minute1);
	public static Text m2 = new Text("" + minute2);
	public static Text s1 = new Text("" + second1);
	public static Text s2 = new Text("" + second2);
	public static Text mColon = new Text(":");
	public static Text sColon = new Text(":");
	public static Group timeGroup = new Group();
	public static TextField outlineValue = new TextField();
	public static AnimationTimer slide;
	public static AnimationTimer open;
	public static AnimationTimer time;
	public static boolean settingScreenOpen = false;
	public static boolean ctrlIsPressed = false;
	public static boolean consumeKey = false;
	public static final FileChooser fileChooser = new FileChooser();
	public static Path currentRelativePath = Paths.get("");
	public static String dirPath = currentRelativePath.toAbsolutePath().toString();
	public static String bgSource = dirPath + "/background.txt";
	public static String cdSource = dirPath + "/card.txt";
	public static String bgPath = "";
	public static String cbPath = "";
	public static Slider opacityLevel = new Slider(0, 1, 1);  
	public static Media click = new Media(ingame.class.getResource("clicksound.mp3").toExternalForm());
	public static MediaPlayer clickPlayer = new MediaPlayer(click);
	public static Media slideSound = new Media(ingame.class.getResource("slidesound.mp3").toExternalForm());
	public static MediaPlayer slideSoundPlayer = new MediaPlayer(slideSound);
	public static InputStream in1;
	public static InputStream in2;
	public static File file;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub		
		Font textFont = Font.loadFont(getClass().getResourceAsStream("/application/font/Aspergit.otf"), 20),
				textFontBig = Font.loadFont(getClass().getResourceAsStream("/application/font/NEXT ART_Regular.otf"), 30),
				endGameFont = Font.loadFont(getClass().getResourceAsStream("/application/font/NEXT ART_Light.otf"), 50),
				endGameFontSmall = Font.loadFont(getClass().getResourceAsStream("/application/font/NEXT ART_Light.otf"), 20);

		file = new File(bgSource);
		file.createNewFile();
		file = new File(cdSource);
		file.createNewFile();
		in1 = new FileInputStream(bgSource);
		BufferedReader reader1 = new BufferedReader(new InputStreamReader(in1));
		bgPath = reader1.readLine();
		in1.close();
		in2 = new FileInputStream(cdSource);
		BufferedReader reader2 = new BufferedReader(new InputStreamReader(in2));
		cbPath = reader2.readLine();

		FileInputStream input = null;
		if(bgPath != null) {
			try {
				input = new FileInputStream(bgPath);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				bgPath = "";
			}
		}
		FileInputStream input2 = null;
		if(cbPath != null) {
			try {
				input2 = new FileInputStream(cbPath);
			}catch(Exception ex) {
				cbPath = "";
			}
		}

		rim.setFill(new ImagePattern(input == null ? new Image("/application/backgrounds/black.jpg",rim.getWidth() * 2,rim.getHeight() * 2,false,false) : new Image(input,rim.getWidth() * 2,rim.getHeight() * 2,false,false)));
		rim.setStroke(Color.WHITE);
		rim.setStrokeWidth(5);
		rim.setX(WIDTH / 2 - rim.getWidth() / 2);
		rim.setY(HEIGHT / 2 - rim.getHeight() / 2);

		bundle1.setX(rim.getX() + rim.getStrokeWidth() + 5);
		bundle1.setY(rim.getY() + rim.getStrokeWidth() + 5);
		bundle1.setFill(Color.TRANSPARENT);
		bundle1.setStroke(Color.web("fffaf0"));
		bundle1.setStrokeWidth(2);
		bundle1.setArcWidth(8);
		bundle1.setArcHeight(15);
		bundle1.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(bundle1.isVisible())gamecontrols.addClick(bundle1);
			}

		});

		bundle2.setX(bundle1.getX() + bundle1.getWidth() + bundle1.getStrokeWidth() * 2 + 5);
		bundle2.setY(bundle1.getY());
		bundle2.setFill(Color.TRANSPARENT);
		bundle2.setStroke(bundle1.getStroke());
		bundle2.setStrokeWidth(bundle1.getStrokeWidth());
		bundle2.setArcWidth(bundle1.getArcWidth());
		bundle2.setArcHeight(bundle1.getArcHeight());
		bundle2.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(bundle2.isVisible())gamecontrols.addClick(bundle2);
			}

		});

		bundle3.setX(bundle2.getX() + bundle2.getWidth() + bundle2.getStrokeWidth() * 2 + 5);
		bundle3.setY(bundle2.getY());
		bundle3.setFill(Color.TRANSPARENT);
		bundle3.setStroke(bundle1.getStroke());
		bundle3.setStrokeWidth(bundle1.getStrokeWidth());
		bundle3.setArcWidth(bundle1.getArcWidth());
		bundle3.setArcHeight(bundle1.getArcHeight());
		bundle3.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(bundle3.isVisible())gamecontrols.addClick(bundle3);
			}

		});

		bundle4.setX(bundle3.getX() + bundle3.getWidth() + bundle3.getStrokeWidth() * 2 + 5);
		bundle4.setY(bundle3.getY());
		bundle4.setFill(Color.TRANSPARENT);
		bundle4.setStroke(bundle1.getStroke());
		bundle4.setStrokeWidth(bundle1.getStrokeWidth());
		bundle4.setArcWidth(bundle1.getArcWidth());
		bundle4.setArcHeight(bundle1.getArcHeight());
		bundle4.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(bundle4.isVisible())gamecontrols.addClick(bundle4);
			}

		});

		pane.getChildren().addAll(rim,bundle1,bundle2,bundle3,bundle4);

//		if(cbPath != null) {
//			try {
//				input = new FileInputStream(cbPath);
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				System.out.println(e);
//			}
//		}
		drawnCards.setX(rim.getX() + rim.getWidth() - rim.getStrokeWidth() - drawnCards.getWidth() - 5);
		drawnCards.setY(bundle3.getY());
		drawnCards.setFill(new ImagePattern(input2 == null ? new Image("/application/cards/crown1.jpg",rim.getWidth() * 2,rim.getHeight() * 2,false,false) : new Image(input2,rim.getWidth() * 2,rim.getHeight() * 2,false,false)));
		drawnCards.setStroke(bundle1.getStroke());
		drawnCards.setStrokeWidth(1);
		drawnCards.setArcWidth(bundle1.getArcWidth());
		drawnCards.setArcHeight(bundle1.getArcHeight());
		drawnCards.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(gamecontrols.getTempList().size() > 0 && (gamecontrols.getChosenCard().size() == 0 ? true : gamecontrols.getChosenCard().get(0).getX() + 1 != drawnCards.getX() - gamecontrols.getOffset())) {
					gamecontrols.drawCards();
				}
			}

		});

		separator.setX(rim.getX() + rim.getWidth() / 2 - separator.getWidth() / 2);
		separator.setY(bundle1.getY() + bundle1.getHeight() + 5);
		separator.setFill(Color.WHITE);
		separator.setStroke(Color.CORNFLOWERBLUE);
		separator.setStrokeWidth(1);
		separator.setArcWidth(10);
		separator.setArcHeight(5);

		s2.setText("" + second2);
		s1.setText("" + second1);
		m2.setText("" + minute2);
		m1.setText("" + minute1);

		m1.setFont(textFontBig);
		m1.setFill(Color.WHITE);
		double m1W = m1.getBoundsInLocal().getWidth();
		m2.setX(m1.getX() + m1W + 5);
		m2.setY(m1.getY());
		m2.setFont(m1.getFont());
		m2.setFill(m1.getFill());
		double m2W = m2.getBoundsInLocal().getWidth();
		sColon.setX(m2.getX() + m2W + 5);
		sColon.setY(m2.getY());
		sColon.setFont(m1.getFont());
		sColon.setFill(m1.getFill());
		double sCW = sColon.getBoundsInLocal().getWidth();
		s1.setX(sColon.getX() + sCW + 5);
		s1.setY(sColon.getY());
		s1.setFont(m1.getFont());
		s1.setFill(m1.getFill());
		double s1W = s1.getBoundsInLocal().getWidth();
		s2.setX(s1.getX() + s1W + 5);
		s2.setY(s1.getY());
		s2.setFont(m1.getFont());
		s2.setFill(m1.getFill());
		timeGroup.getChildren().addAll(m1,m2,sColon,s1,s2);
		timeGroup.setLayoutX(bundle4.getX() + bundle4.getWidth() + 10);
		timeGroup.setLayoutY(bundle4.getY() + bundle4.getHeight());

		row1.setX(separator.getX() + bundle1.getStrokeWidth() + 10);
		row1.setY(separator.getY() + bundle1.getStrokeWidth() * 2 + 5);
		row1.setFill(bundle1.getFill());
		row1.setStroke(bundle1.getStroke());
		row1.setStrokeWidth(bundle1.getStrokeWidth());
		row1.setArcWidth(bundle1.getArcWidth());
		row1.setArcHeight(bundle1.getArcHeight());
		row1.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(row1.isVisible())gamecontrols.addClick(row1);
			}

		});

		row2.setX(row1.getX() + row1.getWidth() + 30);
		row2.setY(row1.getY());
		row2.setFill(bundle1.getFill());
		row2.setStroke(bundle1.getStroke());
		row2.setStrokeWidth(bundle1.getStrokeWidth());
		row2.setArcWidth(bundle1.getArcWidth());
		row2.setArcHeight(bundle1.getArcHeight());
		row2.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(row2.isVisible())gamecontrols.addClick(row2);
			}

		});

		row3.setX(row2.getX() + row2.getWidth() + 30);
		row3.setY(row1.getY());
		row3.setFill(bundle1.getFill());
		row3.setStroke(bundle1.getStroke());
		row3.setStrokeWidth(bundle1.getStrokeWidth());
		row3.setArcWidth(bundle1.getArcWidth());
		row3.setArcHeight(bundle1.getArcHeight());
		row3.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(row3.isVisible())gamecontrols.addClick(row3);
			}

		});

		row4.setX(row3.getX() + row2.getWidth() + 30);
		row4.setY(row1.getY());
		row4.setFill(bundle1.getFill());
		row4.setStroke(bundle1.getStroke());
		row4.setStrokeWidth(bundle1.getStrokeWidth());
		row4.setArcWidth(bundle1.getArcWidth());
		row4.setArcHeight(bundle1.getArcHeight());
		row4.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(row4.isVisible())gamecontrols.addClick(row4);
			}

		});

		row5.setX(row4.getX() + row2.getWidth() + 30);
		row5.setY(row1.getY());
		row5.setFill(bundle1.getFill());
		row5.setStroke(bundle1.getStroke());
		row5.setStrokeWidth(bundle1.getStrokeWidth());
		row5.setArcWidth(bundle1.getArcWidth());
		row5.setArcHeight(bundle1.getArcHeight());
		row5.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(row5.isVisible())gamecontrols.addClick(row5);
			}

		});

		row6.setX(row5.getX() + row2.getWidth() + 30);
		row6.setY(row1.getY());
		row6.setFill(bundle1.getFill());
		row6.setStroke(bundle1.getStroke());
		row6.setStrokeWidth(bundle1.getStrokeWidth());
		row6.setArcWidth(bundle1.getArcWidth());
		row6.setArcHeight(bundle1.getArcHeight());
		row6.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(row6.isVisible())gamecontrols.addClick(row6);
			}

		});

		row7.setX(row6.getX() + row2.getWidth() + 30);
		row7.setY(row1.getY());
		row7.setFill(bundle1.getFill());
		row7.setStroke(bundle1.getStroke());
		row7.setStrokeWidth(bundle1.getStrokeWidth());
		row7.setArcWidth(bundle1.getArcWidth());
		row7.setArcHeight(bundle1.getArcHeight());
		row7.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(row7.isVisible())gamecontrols.addClick(row7);
			}

		});

		restart.setX(rim.getStrokeWidth() / 2 + 3);
		restart.setY(scene.getHeight()-restart.getHeight() - rim.getStrokeWidth() / 2 - 2);
		restart.setArcWidth(10);
		restart.setArcHeight(10);
		restart.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				restart.setFill(Color.web("e0dccf"));
				clickPlayer.stop();
				clickPlayer.play();
			}
		});
		restart.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				restart.setFill(Color.ANTIQUEWHITE);
				clickPlayer.stop();
				clickPlayer.play();
				restart();
				//				gamecontrols.fadeIn((Rectangle) gamecontrols.getCardsGroup().getChildren().get(51));
			}

		});

		RESTART.setFill(Color.web("bdaea8"));
		RESTART.setFont(Font.font(20));
		double reW = RESTART.getBoundsInLocal().getWidth();
		double reH = RESTART.getBoundsInLocal().getHeight();
		RESTART.setX(restart.getX() + restart.getWidth() / 2 - reW / 2);
		RESTART.setY(restart.getY() + restart.getHeight() / 2 + reH / 4);
		RESTART.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				restart.setFill(Color.web("e0dccf"));	
				clickPlayer.stop();
				clickPlayer.play();
			}
		});
		RESTART.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				restart.setFill(Color.ANTIQUEWHITE);
				clickPlayer.stop();
				clickPlayer.play();
				restart();
				//				gamecontrols.fadeIn((Rectangle) gamecontrols.getCardsGroup().getChildren().get(51));
			}

		});

		setting.setX(restart.getX() + restart.getWidth() + 1);
		setting.setY(restart.getY());
		setting.setArcWidth(restart.getArcWidth());
		setting.setArcHeight(restart.getArcHeight());
		setting.setOnMousePressed(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				setting.setFill(Color.web("e0dccf"));	
			}

		});
		setting.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				setting.setFill(Color.ANTIQUEWHITE);
				if(settingScreen.getX() == WIDTH || settingScreen.getX() == 0)openSettingScreen();		
			}

		});

		SETTING.setFill(Color.web("bdaea8"));
		SETTING.setFont(Font.font(20));
		double seW = RESTART.getBoundsInLocal().getWidth();
		double seH = RESTART.getBoundsInLocal().getHeight();
		SETTING.setX(setting.getX() + setting.getWidth() / 2 - seW / 2);
		SETTING.setY(setting.getY() + setting.getHeight() / 2 + seH / 4);
		SETTING.setOnMousePressed(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				setting.setFill(Color.web("e0dccf"));	
			}

		});
		SETTING.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				setting.setFill(Color.ANTIQUEWHITE);
				if(settingScreen.getX() == WIDTH || settingScreen.getX() == 0)openSettingScreen();		
			}

		});
		
		exit.setX(setting.getX() + setting.getWidth() + 1);
		exit.setY(setting.getY());
		exit.setArcWidth(restart.getArcWidth());
		exit.setArcHeight(restart.getArcHeight());
		exit.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				exit.setFill(Color.web("e0dccf"));	
				clickPlayer.stop();
				clickPlayer.play();
			}
		});
		exit.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				exit.setFill(Color.ANTIQUEWHITE);
				clickPlayer.stop();
				clickPlayer.play();
				System.exit(0);
			}

		});
		
		EXIT.setFill(RESTART.getFill());
		EXIT.setFont(RESTART.getFont());
		double eW = EXIT.getBoundsInLocal().getWidth();
		double eH = EXIT.getBoundsInLocal().getHeight();
		EXIT.setX(exit.getX() + exit.getWidth() / 2 - eW / 2);
		EXIT.setY(exit.getY() + exit.getHeight() / 2 + eH / 4);
		EXIT.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				exit.setFill(Color.web("e0dccf"));	
				clickPlayer.stop();
				clickPlayer.play();
			}
		});
		EXIT.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				exit.setFill(Color.ANTIQUEWHITE);
				clickPlayer.stop();
				clickPlayer.play();
				System.exit(0);
			}

		});

		settingScreen.setFill(/* Color.web("0c0f12") */Color.web("0c0f12").deriveColor(0, 1, 1, 0.8));
//		settingScreen.setOpacity(0.8);
		settingScreen.setArcWidth(rim.getArcWidth());
		settingScreen.setArcHeight(rim.getArcHeight());
//		settingScreen.setStroke(Color.web("ffdead"));
//		settingScreen.setStrokeWidth(rim.getStrokeWidth());

		preview.setFill(Color.TRANSPARENT);
		preview.setX(settingScreen.getWidth() / 2 - preview.getWidth() / 2);
		preview.setY(settingScreen.getY() + 10);
		preview.setStroke(Color.WHITESMOKE);
		preview.setStrokeWidth(rim.getStrokeWidth() / 2);
		preview.setArcWidth(rim.getArcWidth());
		preview.setArcHeight(rim.getArcHeight());

		modes.setX(preview.getX() + preview.getWidth() + 5);
		modes.setY(preview.getY());
		modes.setStroke(preview.getStroke());
		modes.setStrokeWidth(preview.getStrokeWidth());
		modes.setArcWidth(preview.getArcWidth());
		modes.setArcHeight(preview.getArcHeight());		

		easy.setX(modes.getX() + modes.getWidth() / 2 - easy.getWidth() / 2);
		easy.setY(modes.getY() + modes.getHeight() / 2 - easy.getHeight() - medium.getHeight() / 2 - 20);
		easy.setFill(restart.getFill());
		easy.setArcHeight(restart.getArcHeight());
		easy.setArcWidth(restart.getArcWidth());
		easy.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				easy.setFill(Color.web("e0dccf"));	
				clickPlayer.stop();
				clickPlayer.play();
			}
		});
		easy.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				easy.setFill(Color.ANTIQUEWHITE);
				clickPlayer.stop();
				clickPlayer.play();
				difficulty = 0;
				restart();
				opacityLevel.setValue(1);
				openSettingScreen();
				//				gamecontrols.fadeIn((Rectangle) gamecontrols.getCardsGroup().getChildren().get(51));
			}

		});

		medium.setX(modes.getX() + modes.getWidth() / 2 - easy.getWidth() / 2);
		medium.setY(easy.getY() + easy.getHeight() + 20);
		medium.setFill(restart.getFill());
		medium.setArcHeight(restart.getArcHeight());
		medium.setArcWidth(restart.getArcWidth());
		medium.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				medium.setFill(Color.web("e0dccf"));	
				clickPlayer.stop();
				clickPlayer.play();
			}
		});
		medium.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				medium.setFill(Color.ANTIQUEWHITE);
				clickPlayer.stop();
				clickPlayer.play();
				difficulty = 10000;
				restart();
				opacityLevel.setValue(1);
				openSettingScreen();
				//				gamecontrols.fadeIn((Rectangle) gamecontrols.getCardsGroup().getChildren().get(51));
			}

		});

		hard.setX(modes.getX() + modes.getWidth() / 2 - easy.getWidth() / 2);
		hard.setY(medium.getY() + medium.getHeight() + 20);
		hard.setFill(restart.getFill());
		hard.setArcHeight(restart.getArcHeight());
		hard.setArcWidth(restart.getArcWidth());
		hard.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				hard.setFill(Color.web("e0dccf"));
				clickPlayer.stop();
				clickPlayer.play();	
			}
		});
		hard.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				hard.setFill(Color.ANTIQUEWHITE);
				clickPlayer.stop();
				clickPlayer.play();
				difficulty = 1000000;
				restart();
				opacityLevel.setValue(1);
				openSettingScreen();
				//				gamecontrols.fadeIn((Rectangle) gamecontrols.getCardsGroup().getChildren().get(51));
			}
		});

		EASY.setFont(Font.font(20));
		EASY.setFill(RESTART.getFill());
		double eaW = EASY.getBoundsInLocal().getWidth();
		double eaH = EASY.getBoundsInLocal().getHeight();
		EASY.setX(easy.getX() + easy.getWidth() / 2 - eaW / 2);
		EASY.setY(easy.getY() + easy.getHeight() / 2 + eaH / 4);
		EASY.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				easy.setFill(Color.web("e0dccf"));
				clickPlayer.stop();
				clickPlayer.play();	
			}
		});
		EASY.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				easy.setFill(Color.ANTIQUEWHITE);
				clickPlayer.stop();
				clickPlayer.play();
				difficulty = 0;
				restart();
				opacityLevel.setValue(1);
				openSettingScreen();
				//				gamecontrols.fadeIn((Rectangle) gamecontrols.getCardsGroup().getChildren().get(51));
			}

		});

		MEDIUM.setFont(Font.font(20));
		MEDIUM.setFill(RESTART.getFill());
		double meW = MEDIUM.getBoundsInLocal().getWidth();
		double meH = MEDIUM.getBoundsInLocal().getHeight();
		MEDIUM.setX(medium.getX() + medium.getWidth() / 2 - meW / 2);
		MEDIUM.setY(medium.getY() + medium.getHeight() / 2 + meH / 4);
		MEDIUM.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				medium.setFill(Color.web("e0dccf"));	
				clickPlayer.stop();
				clickPlayer.play();
			}
		});
		MEDIUM.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				medium.setFill(Color.ANTIQUEWHITE);
				clickPlayer.stop();
				clickPlayer.play();
				difficulty = 10000;
				restart();
				opacityLevel.setValue(1);
				openSettingScreen();
				//				gamecontrols.fadeIn((Rectangle) gamecontrols.getCardsGroup().getChildren().get(51));
			}

		});

		HARD.setFont(Font.font(20));
		HARD.setFill(RESTART.getFill());
		double haW = HARD.getBoundsInLocal().getWidth();
		double haH = HARD.getBoundsInLocal().getHeight();
		HARD.setX(hard.getX() + hard.getWidth() / 2 - haW / 2);
		HARD.setY(hard.getY() + hard.getHeight() / 2 + haH / 4);
		HARD.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				hard.setFill(Color.web("e0dccf"));	
				clickPlayer.stop();
				clickPlayer.play();
			}
		});
		HARD.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				hard.setFill(Color.ANTIQUEWHITE);
				clickPlayer.stop();
				clickPlayer.play();
				difficulty = 1000000;
				restart();
				opacityLevel.setValue(1);
				openSettingScreen();
				//				gamecontrols.fadeIn((Rectangle) gamecontrols.getCardsGroup().getChildren().get(51));
			}
		});

		cardFill.setX(preview.getX() - cardFill.getWidth() - 5);
		cardFill.setY(preview.getY());
		cardFill.setStroke(preview.getStroke());
		cardFill.setStrokeWidth(preview.getStrokeWidth());
		cardFill.setArcWidth(preview.getArcWidth());
		cardFill.setArcHeight(preview.getArcHeight());

		tempBack.setFill(preview.getFill());
		tempBack.setX(preview.getX() / 2 - tempBack.getWidth() / 2);
		tempBack.setY(preview.getY() + 5);
		tempBack.setStroke(preview.getStroke());
		tempBack.setStrokeWidth(drawnCards.getStrokeWidth());
		tempBack.setArcWidth(drawnCards.getArcWidth());
		tempBack.setArcHeight(drawnCards.getArcHeight());	

		chooseCardBack.setX(tempBack.getX());
		chooseCardBack.setY(tempBack.getY() + tempBack.getHeight() + 5);
		chooseCardBack.setArcWidth(restart.getArcWidth());
		chooseCardBack.setArcHeight(restart.getArcHeight());
		chooseCardBack.setOnMousePressed(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				chooseCardBack.setFill(Color.web("e0dccf"));	
				clickPlayer.stop();
				clickPlayer.play();	
			}

		});
		chooseCardBack.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				chooseCardBack.setFill(Color.ANTIQUEWHITE);
				clickPlayer.stop();
				clickPlayer.play();
				try {
					File file = fileChooser.showOpenDialog(stage); 
					cbPath = file != null ? file.toPath().toString() : "";
					if(cbPath != "") {
						file = new File(bgSource);
						file.createNewFile();
						file = new File(cdSource);
						file.createNewFile();
						PrintWriter writer = new PrintWriter(new File(cdSource));
						writer.append(cbPath);
						writer.flush();
						drawnCards.setFill(new ImagePattern(new Image(new FileInputStream(cbPath))));
						for(int i = 0; i < gamecontrols.getCardsGroup().getChildren().size(); i++) {
							if(!gamecontrols.getCardsList().get(i).isFacingUp()) 
								((Rectangle)gamecontrols.getCardsGroup().getChildren().get(i)).setFill(new ImagePattern(new Image(new FileInputStream(cbPath),tempBack.getWidth() * 1.5,tempBack.getHeight() * 1.5,false,false)));
						}
						tempBack.setFill(new ImagePattern(new Image(new FileInputStream(cbPath))));
						THREEDOTS.setText(". . .");
						THREEDOTS.setFont(Font.font(30));
						double tdW = THREEDOTS.getBoundsInLocal().getWidth();
						THREEDOTS.setX(chooseCardBack.getX() + chooseCardBack.getWidth() / 2 - tdW / 2);
						THREEDOTS.setY(chooseCardBack.getY() + chooseCardBack.getHeight() / 2);
					}else {
						THREEDOTS.setText("NO IMAGE FOUND");
						THREEDOTS.setFont(Font.font(10));
						double tdW = THREEDOTS.getBoundsInLocal().getWidth();
						double tdH = THREEDOTS.getBoundsInLocal().getHeight();
						THREEDOTS.setX(chooseCardBack.getX() + chooseCardBack.getWidth() / 2 - tdW / 2);
						THREEDOTS.setY(chooseCardBack.getY() + chooseCardBack.getHeight() / 2 + tdH / 4);
					}
				} catch (Exception ex) {
					THREEDOTS.setText("NO IMAGE FOUND");
				}
			}
		});

		THREEDOTS.setFill(Color.web("bdaea8"));
		THREEDOTS.setFont(Font.font(30));
		double tdW = THREEDOTS.getBoundsInLocal().getWidth();
		THREEDOTS.setX(chooseCardBack.getX() + chooseCardBack.getWidth() / 2 - tdW / 2);
		THREEDOTS.setY(chooseCardBack.getY() + chooseCardBack.getHeight() / 2);
		THREEDOTS.setOnMousePressed(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				chooseCardBack.setFill(Color.web("e0dccf"));	
				clickPlayer.stop();
				clickPlayer.play();	
			}

		});
		THREEDOTS.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				chooseCardBack.setFill(Color.ANTIQUEWHITE);
				clickPlayer.stop();
				clickPlayer.play();
				try {
					file = new File(bgSource);
					file.createNewFile();
					file = new File(cdSource);
					file.createNewFile();
					File file = fileChooser.showOpenDialog(stage); 
					cbPath = file != null ? file.toPath().toString() : "";
					if(cbPath != "") {
						PrintWriter writer = new PrintWriter(new File(cdSource));
						writer.append(cbPath);
						writer.flush();
						drawnCards.setFill(new ImagePattern(new Image(new FileInputStream(cbPath))));
						for(int i = 0; i < gamecontrols.getCardsGroup().getChildren().size(); i++) {
							if(!gamecontrols.getCardsList().get(i).isFacingUp()) 
								((Rectangle)gamecontrols.getCardsGroup().getChildren().get(i)).setFill(new ImagePattern(new Image(new FileInputStream(cbPath),tempBack.getWidth() * 1.5,tempBack.getHeight() * 1.5,false,false)));
						}
						tempBack.setFill(new ImagePattern(new Image(new FileInputStream(cbPath))));
						THREEDOTS.setText(". . .");
						THREEDOTS.setFont(Font.font(30));
						double tdW = THREEDOTS.getBoundsInLocal().getWidth();
						THREEDOTS.setX(chooseCardBack.getX() + chooseCardBack.getWidth() / 2 - tdW / 2);
						THREEDOTS.setY(chooseCardBack.getY() + chooseCardBack.getHeight() / 2);
					}else {
						THREEDOTS.setText("NO IMAGE FOUND");
						THREEDOTS.setFont(Font.font(10));
						double tdW = THREEDOTS.getBoundsInLocal().getWidth();
						double tdH = THREEDOTS.getBoundsInLocal().getHeight();
						THREEDOTS.setX(chooseCardBack.getX() + chooseCardBack.getWidth() / 2 - tdW / 2);
						THREEDOTS.setY(chooseCardBack.getY() + chooseCardBack.getHeight() / 2 + tdH / 4);
					}
				} catch (Exception ex) {
					THREEDOTS.setText("NO IMAGE FOUND");
				}
			}
		});

		//		BORDERLINE.setFont(textFont);
		BORDERLINE.setFont(Font.font(20));
		BORDERLINE.setFill(Color.WHITE);
		double blW = BORDERLINE.getBoundsInLocal().getWidth();
		double blH = BORDERLINE.getBoundsInLocal().getHeight();
		BORDERLINE.setX(cardFill.getX() + 5);
		BORDERLINE.setY(chooseCardBack.getY() + chooseCardBack.getHeight() / 2 + blH * 2);	

		DIFFICULTY.setFont(BORDERLINE.getFont());
		DIFFICULTY.setFill(BORDERLINE.getFill());
		double diW = DIFFICULTY.getBoundsInLocal().getWidth();
		double diH = DIFFICULTY.getBoundsInLocal().getHeight();
		DIFFICULTY.setX(modes.getX() + modes.getWidth() / 2 - diW / 2);
		DIFFICULTY.setY(modes.getY() + diH + 5);

		outlineValue.setLayoutX(BORDERLINE.getX() + blW + 5);	
		outlineValue.setLayoutY(BORDERLINE.getY() - blH + 5);
		outlineValue.setPrefSize(preview.getX() - BORDERLINE.getX() - blW - 30 + 10, blH);
		outlineValue.setOpacity(0.9);
		outlineValue.setFont(textFont);
		outlineValue.setStyle("-fx-focus-color: transparent");;
		outlineValue.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent key) {
				// TODO Auto-generated method stub
				if(key.getCode().equals(KeyCode.ENTER)) {
					try {
						tempBack.setStroke(Color.web(outlineValue.getText()));
						drawnCards.setStroke(tempBack.getStroke());
						bundle1.setStroke(tempBack.getStroke());
						bundle2.setStroke(tempBack.getStroke());
						bundle3.setStroke(tempBack.getStroke());
						bundle4.setStroke(tempBack.getStroke());
						row1.setStroke(tempBack.getStroke());
						row2.setStroke(tempBack.getStroke());
						row3.setStroke(tempBack.getStroke());
						row4.setStroke(tempBack.getStroke());
						row5.setStroke(tempBack.getStroke());
						row6.setStroke(tempBack.getStroke());
						row7.setStroke(tempBack.getStroke());
						for(int i = 0; i < gamecontrols.getCardsGroup().getChildren().size(); i++) {
							((Rectangle)gamecontrols.getCardsGroup().getChildren().get(i)).setStroke(tempBack.getStroke());
						}
					}catch(Exception ex) {
						NOTE.setFill(Color.AQUA);
					}
				}
			}

		});

		NOTE.setFont(Font.font(15));
		NOTE.setFill(BORDERLINE.getFill());
		NOTE.setX(BORDERLINE.getX());
		NOTE.setY(BORDERLINE.getY() + 15);

		OPACITYLEVEL.setFont(BORDERLINE.getFont());
		OPACITYLEVEL.setFill(BORDERLINE.getFill());
		double opH = OPACITYLEVEL.getBoundsInLocal().getHeight();
		OPACITYLEVEL.setX(BORDERLINE.getX());
		OPACITYLEVEL.setY(BORDERLINE.getY() + blH + opH);	

		opacityLevel.setLayoutX(OPACITYLEVEL.getX());
		opacityLevel.setLayoutY(OPACITYLEVEL.getY() + 15);
		opacityLevel.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov,
					Number old_val, Number new_val) {
				tempBack.setOpacity(new_val.doubleValue());
				for(Node n : gamecontrols.getCardsGroup().getChildren()) {
					((Rectangle)n).setOpacity(new_val.doubleValue());
				}
			}
		});	

		remove.setArcHeight(restart.getArcHeight());
		remove.setArcWidth(restart.getArcWidth());
		remove.setStroke(restart.getStroke());
		remove.setStrokeWidth(restart.getStrokeWidth());
		remove.setX(cardFill.getX() + cardFill.getWidth() / 2 - remove.getWidth() / 2);
		remove.setY(cardFill.getHeight() - remove.getHeight());
		remove.setOnMousePressed(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				remove.setFill(Color.web("e0dccf"));
				clickPlayer.stop();
				clickPlayer.play();		
			}

		});
		remove.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				remove.setFill(Color.ANTIQUEWHITE);
				clickPlayer.stop();
				clickPlayer.play();
				try {
					file = new File(bgSource);
					file.createNewFile();
					file = new File(cdSource);
					file.createNewFile();
					PrintWriter writer1 = new PrintWriter(new File(cdSource));
					writer1.append("");
					writer1.flush();
					PrintWriter writer2 = new PrintWriter(new File(bgSource));
					writer2.append("");
					writer2.flush();
					for(Node n : gamecontrols.getCardsGroup().getChildren()) {
						if(!gamecontrols.getCardsList().get(gamecontrols.getCardsGroup().getChildren().indexOf(n)).isFacingUp())((Rectangle)n).setFill(new ImagePattern(new Image("/application/cards/crown1.jpg")));
					}
					cbPath = "";
					bgPath = "";
					rim.setFill(new ImagePattern(new Image("/application/backgrounds/black.jpg")));
					drawnCards.setFill(new ImagePattern(new Image("/application/cards/crown1.jpg")));
					preview.setFill(Color.TRANSPARENT);
					tempBack.setFill(Color.TRANSPARENT);
				} catch (Exception ex) {
//					System.out.println(ex);
					ex.printStackTrace();
				}
			}
		});

		CLEAR.setFont(RESTART.getFont());
		CLEAR.setFill(RESTART.getFill());
		double clW = CLEAR.getBoundsInLocal().getWidth();
		CLEAR.setX(remove.getX() + remove.getWidth() / 2 - clW / 2);
		CLEAR.setY(remove.getY() + remove.getHeight() / 2 + opH / 4);
		CLEAR.setOnMousePressed(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				remove.setFill(Color.web("e0dccf"));
				clickPlayer.stop();
				clickPlayer.play();		
			}

		});
		CLEAR.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				remove.setFill(Color.ANTIQUEWHITE);
				clickPlayer.stop();
				clickPlayer.play();
				try {
					file = new File(bgSource);
					file.createNewFile();
					file = new File(cdSource);
					file.createNewFile();
					PrintWriter writer1 = new PrintWriter(new File(cdSource));
					writer1.append("");
					writer1.flush();
					PrintWriter writer2 = new PrintWriter(new File(bgSource));
					writer2.append("");
					writer2.flush();
					for(Node n : gamecontrols.getCardsGroup().getChildren()) {
						if(!gamecontrols.getCardsList().get(gamecontrols.getCardsGroup().getChildren().indexOf(n)).isFacingUp())((Rectangle)n).setFill(new ImagePattern(new Image("/application/cards/crown1.jpg")));
					}
					cbPath = "";
					bgPath = "";
					rim.setFill(new ImagePattern(new Image("/application/backgrounds/black.jpg")));
					drawnCards.setFill(new ImagePattern(new Image("/application/cards/crown1.jpg")));
					preview.setFill(Color.TRANSPARENT);
					tempBack.setFill(Color.TRANSPARENT);
				} catch (Exception ex) {
//					System.out.println(ex);
					ex.printStackTrace();
				}
			}
		});

		choosePicture.setX(preview.getX());
		choosePicture.setY(preview.getY() + preview.getHeight() + 5);
		choosePicture.setArcWidth(restart.getArcWidth());
		choosePicture.setArcHeight(restart.getArcHeight());
		choosePicture.setOnMousePressed(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				choosePicture.setFill(Color.web("e0dccf"));		
				clickPlayer.stop();
				clickPlayer.play();
			}

		});
		choosePicture.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				choosePicture.setFill(Color.ANTIQUEWHITE);
				clickPlayer.stop();
				clickPlayer.play();
				try {
					File file = fileChooser.showOpenDialog(stage); 
					bgPath = file != null ? file.toPath().toString() : "";
					if(bgPath != "") {
						file = new File(bgSource);
						file.createNewFile();
						file = new File(cdSource);
						file.createNewFile();
						PrintWriter writer = new PrintWriter(new File(bgSource));
						writer.append(bgPath);
						writer.flush();
						rim.setFill(new ImagePattern(new Image(new FileInputStream(bgPath),rim.getWidth() * 1.5,rim.getHeight() * 1.5,false,false)));
						preview.setFill(new ImagePattern(new Image(new FileInputStream(bgPath))));
						FOURDOTS.setText(". . . .");
						FOURDOTS.setFont(Font.font(40));
						double fdW = FOURDOTS.getBoundsInLocal().getWidth();
						FOURDOTS.setX(choosePicture.getX() + choosePicture.getWidth() / 2 - fdW / 2);
						FOURDOTS.setY(choosePicture.getY() + choosePicture.getHeight() / 2);
					}else {
						FOURDOTS.setText("N O  I M A G E  F O U N D");
						FOURDOTS.setFont(Font.font(20));
						double fdW = FOURDOTS.getBoundsInLocal().getWidth();
						double fdH = FOURDOTS.getBoundsInLocal().getHeight();
						FOURDOTS.setX(choosePicture.getX() + choosePicture.getWidth() / 2 - fdW / 2);
						FOURDOTS.setY(choosePicture.getY() + choosePicture.getHeight() / 2 + fdH / 4);
					}
					//                	rim.setFill(new ImagePattern(new Image(input)));
				} catch (IOException ex) {
					FOURDOTS.setText("N O  I M A G E  F O U N D");
				}
			}
		});

		FOURDOTS.setFill(Color.web("bdaea8"));
		FOURDOTS.setFont(Font.font(40));
		double fdW = FOURDOTS.getBoundsInLocal().getWidth();
		FOURDOTS.setX(choosePicture.getX() + choosePicture.getWidth() / 2 - fdW / 2);
		FOURDOTS.setY(choosePicture.getY() + choosePicture.getHeight() / 2);
		FOURDOTS.setOnMousePressed(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				choosePicture.setFill(Color.web("e0dccf"));		
				clickPlayer.stop();
				clickPlayer.play();
			}

		});
		FOURDOTS.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				choosePicture.setFill(Color.ANTIQUEWHITE);
				clickPlayer.stop();
				clickPlayer.play();
				try {
					File file = fileChooser.showOpenDialog(stage); 
					bgPath = file != null ? file.toPath().toString() : "";
					if(bgPath != "") {
						file = new File(bgSource);
						file.createNewFile();
						file = new File(cdSource);
						file.createNewFile();
						PrintWriter writer = new PrintWriter(new File(bgSource));
						writer.append(bgPath);
						writer.flush();
						rim.setFill(new ImagePattern(new Image(new FileInputStream(bgPath),rim.getWidth() * 1.5,rim.getHeight() * 1.5,false,false)));
						preview.setFill(new ImagePattern(new Image(new FileInputStream(bgPath))));
						FOURDOTS.setText(". . . .");
						FOURDOTS.setFont(Font.font(40));
						double fdW = FOURDOTS.getBoundsInLocal().getWidth();
						FOURDOTS.setX(choosePicture.getX() + choosePicture.getWidth() / 2 - fdW / 2);
						FOURDOTS.setY(choosePicture.getY() + choosePicture.getHeight() / 2);
					}else {
						FOURDOTS.setText("N O  I M A G E  F O U N D");
						FOURDOTS.setFont(Font.font(20));
						double fdW = FOURDOTS.getBoundsInLocal().getWidth();
						double fdH = FOURDOTS.getBoundsInLocal().getHeight();
						FOURDOTS.setX(choosePicture.getX() + choosePicture.getWidth() / 2 - fdW / 2);
						FOURDOTS.setY(choosePicture.getY() + choosePicture.getHeight() / 2 + fdH / 4);
					}
					//                	rim.setFill(new ImagePattern(new Image(input)));
				} catch (IOException ex) {
					FOURDOTS.setText("N O  I M A G E  F O U N D");
				}
			}
		});

		endGameScreen.setX(WIDTH / 2 - endGameScreen.getWidth() / 2);
		endGameScreen.setY(HEIGHT / 2 - endGameScreen.getHeight() / 2);
		endGameScreen.setFill(Color.WHITE);
		endGameScreen.setOpacity(0.9);
		//		endGameScreen.setVisible(false);
		endGameScreen.setArcWidth(20);
		endGameScreen.setArcHeight(20);

		YOUWIN.setFill(Color.web("2460A7"));
		YOUWIN.setFont(endGameFont);
		double ywW = YOUWIN.getBoundsInLocal().getWidth();
		double ywH = YOUWIN.getBoundsInLocal().getHeight();
		YOUWIN.setX(endGameScreen.getX() + endGameScreen.getWidth() / 2 - ywW / 2);
		YOUWIN.setY(endGameScreen.getY() + ywH + 20);

		restart2.setX(endGameScreen.getX() + endGameScreen.getWidth() / 4 - restart2.getWidth() / 2);
		restart2.setY(endGameScreen.getY() + endGameScreen.getHeight() / 2);
		restart2.setArcWidth(10);
		restart2.setArcHeight(10);
		restart2.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				restart2.setFill(Color.web("2471A3"));
				clickPlayer.stop();
				clickPlayer.play();	
			}
		});
		restart2.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				restart2.setFill(Color.web("6897bb"));
				clickPlayer.stop();
				clickPlayer.play();
				restart();
				endGamePane.setVisible(false);
				setting.setVisible(true);
				restart.setVisible(true);
				setting.setDisable(false);
				restart.setDisable(false);
				SETTING.setVisible(true);
				RESTART.setVisible(true);
				SETTING.setDisable(false);
				RESTART.setDisable(false);
				//				gamecontrols.fadeIn((Rectangle) gamecontrols.getCardsGroup().getChildren().get(51));
			}

		});

		exit2.setX(endGameScreen.getX() + 3 * endGameScreen.getWidth() / 4 - restart2.getWidth() / 2);
		exit2.setY(restart2.getY());
		exit2.setArcWidth(10);
		exit2.setArcHeight(10);
		exit2.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				exit.setFill(Color.web("2471A3"));	
				clickPlayer.stop();
				clickPlayer.play();
			}
		});
		exit2.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				exit.setFill(Color.web("6897bb"));
				clickPlayer.stop();
				clickPlayer.play();
				System.exit(0);
			}

		});

		RESTART2.setFill(Color.web("FCF3CF"));
		RESTART2.setFont(endGameFontSmall);
		double rW = RESTART2.getBoundsInLocal().getWidth();
		double rH = RESTART2.getBoundsInLocal().getHeight();
		RESTART2.setX(restart2.getX() + restart2.getWidth() / 2 - rW / 2);
		RESTART2.setY(restart2.getY() + restart2.getHeight() / 2 + rH / 4);
		RESTART2.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				restart2.setFill(Color.web("2471A3"));
				clickPlayer.stop();
				clickPlayer.play();	
			}
		});
		RESTART2.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				restart2.setFill(Color.web("6897bb"));
				clickPlayer.stop();
				clickPlayer.play();
				restart();
				endGamePane.setVisible(false);
				setting.setVisible(true);
				restart.setVisible(true);
				setting.setDisable(false);
				restart.setDisable(false);
				SETTING.setVisible(true);
				RESTART.setVisible(true);
				SETTING.setDisable(false);
				RESTART.setDisable(false);
				gamecontrols.getVictorySong().stop();
				//				gamecontrols.fadeIn((Rectangle) gamecontrols.getCardsGroup().getChildren().get(51));
			}

		});

		EXIT2.setFill(RESTART2.getFill());
		EXIT2.setFont(RESTART2.getFont());
		double exW = EXIT2.getBoundsInLocal().getWidth();
		double exH = EXIT2.getBoundsInLocal().getHeight();
		EXIT2.setX(exit2.getX() + exit2.getWidth() / 2 - exW / 2);
		EXIT2.setY(exit2.getY() + exit2.getHeight() / 2 + exH / 4);
		EXIT2.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				exit.setFill(Color.web("2471A3"));	
				clickPlayer.stop();
				clickPlayer.play();
			}
		});
		EXIT2.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				exit.setFill(Color.web("6897bb"));
				clickPlayer.stop();
				clickPlayer.play();
				System.exit(0);
			}

		});

		endGamePane.getChildren().addAll(endGameScreen,YOUWIN,restart2,exit2,RESTART2,EXIT2);
		endGamePane.setVisible(false);

		showTimeElapsed();

		pane.getChildren().addAll(separator,restart,RESTART,exit,EXIT,setting,SETTING,row1,row2,row3,row4,row5,row6,row7);
		gamecontrols.initiate();
		settingPane.getChildren().addAll(settingScreen,cardFill,
				modes,DIFFICULTY,easy,medium,hard,EASY,MEDIUM,HARD,
				tempBack,chooseCardBack,THREEDOTS,BORDERLINE,NOTE,outlineValue,opacityLevel,OPACITYLEVEL,remove,CLEAR,
				preview,choosePicture,FOURDOTS);
		settingPane.setLayoutX(WIDTH);
		settingPane.setVisible(false);
		pane.getChildren().addAll(drawnCards,timeGroup,settingPane,endGamePane);
		checkUndoCondition(scene);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.sizeToScene();
		stage.show();
	}

	public static double getHEIGHT() {
		return HEIGHT;
	}

	public static double getWIDTH() {
		return WIDTH;
	}

	public static Rectangle getEndGameScreen() {
		return endGameScreen;
	}

	public static Rectangle getRim() {
		return rim;
	}

	public static Rectangle getBundle1() {
		return bundle1;
	} 

	public static Rectangle getBundle2() {
		return bundle2;
	}

	public static Rectangle getBundle3() {
		return bundle3;
	}

	public static Rectangle getBundle4() {
		return bundle4;
	}

	public static Rectangle getRow1() {
		return row1;
	}	

	public static Rectangle getRow2() {
		return row2;
	}	

	public static Rectangle getRow3() {
		return row3;
	}	

	public static Rectangle getRow4() {
		return row4;
	}	

	public static Rectangle getRow5() {
		return row5;
	}	

	public static Rectangle getRow6() {
		return row6;
	}	

	public static Rectangle getRow7() {
		return row7;
	}	

	public static Scene getScene() {
		return scene;
	}

	public static Rectangle getDrawnCardsPack() {
		return drawnCards;
	}

	public static Pane getPane() {
		return pane;
	}

	public static Rectangle getSeparator() {
		return separator;
	}

	public static String getcbPath() {
		return cbPath;
	}

	public static Rectangle getTempBack() {
		return tempBack;
	}

	public static int getDifficulty() {
		return difficulty;
	}

	public static Slider getOpacityLevel() {
		return opacityLevel;
	}

	public static Pane getEndGamePane() {
		return endGamePane;
	}

	public static Rectangle getRestart() {
		return restart;
	}

	public static Rectangle getSetting() {
		return setting;
	}

	public static Text getRestartText() {
		return RESTART;
	}

	public static Text getSettingText() {
		return SETTING;
	}

	public static Group getTimeGroup() {
		return timeGroup;
	}

	public static AnimationTimer getTime() {
		return time;
	}
	
	public static boolean getConsumeKey() {
		return consumeKey;
	}
	
	public static void setConsumeKey(boolean s) {
		consumeKey = s;
	}
	
	public static void checkUndoCondition(Scene scene) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
//				if(consumeKey) {
//					event.consume();
//					return;
//				}
				switch (event.getCode()) {
				case CONTROL:
					ctrlIsPressed = true;
					break;
				case Z:
					if(ctrlIsPressed) {
						gamecontrols.undo();
					}
					break;
				default:
					break;
				}
			}
			
		});
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				// TODO Auto-generated method stub
				switch (event.getCode()) {
				case CONTROL:
					ctrlIsPressed = false;
					break;
				default:
					break;
				}
			}
			
		});
	}

	public static void showTimeElapsed() {
		time = new AnimationTimer(){
			long last = 0;
			@Override
			public void handle(long now) {
				// TODO Auto-generated method stub
				if(now - last >= 1000_000_000) {
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
									time.stop();
								}
							}
						}
					}
					last = now;
				}
			}
		};
	}

	public static void slide(Rectangle rec, double offset, double des, String dir) {
		slide = new AnimationTimer() {

			@Override
			public void handle(long arg0) {
				// TODO Auto-generated method stub
				if(dir == "left") {
					if(rec.getX() <= des)slide.stop();
					else rec.setX(rec.getX() - offset);
				}else if(dir == "right") {
					if(rec.getX() >= des)slide.stop();
					else rec.setX(rec.getX() + offset);
				}
			}

		};
		slide.start();
	}

	public static void restart() {
		second2 = second1 = minute2 = minute1 = 0;
		s2.setText("" + second2);
		s1.setText("" + second1);
		m2.setText("" + minute2);
		m1.setText("" + minute1);
		gamecontrols.getCardsGroup().getChildren().clear();
		gamecontrols.getCardsList().clear();
		gamecontrols.getValidCards().clear();
		gamecontrols.getTempList().clear();
		gamecontrols.getListOfMoves().clear();
		gamecontrols.initiate();		
	}

	public static void openSettingScreen() {
		slideSoundPlayer.stop();
		slideSoundPlayer.play();
		settingPane.setVisible(true);
		open = new AnimationTimer() {
			@Override
			public void handle(long now) {
				if(!settingScreenOpen) {
					if(settingPane.getLayoutX() <= WIDTH / 40) {
						setting.setDisable(false);
						SETTING.setDisable(false);
						settingPane.setDisable(false);
						settingPane.setLayoutX(0);
						settingScreenOpen = true;
						open.stop();
					}else {
						setting.setDisable(true);
						SETTING.setDisable(true);
						settingPane.setDisable(true);
						settingPane.setLayoutX(settingPane.getLayoutX() - WIDTH / 40);
					}
				}else {
					if(settingPane.getLayoutX() >= WIDTH) {
						setting.setDisable(false);
						SETTING.setDisable(false);
						settingPane.setDisable(false);
						settingPane.setLayoutX(WIDTH);
						settingScreenOpen = false;
						open.stop();
						settingPane.setVisible(false);
					}else {
						setting.setDisable(true);
						SETTING.setDisable(true);
						settingPane.setDisable(true);
						settingPane.setLayoutX(settingPane.getLayoutX() + WIDTH / 40);
					}	
				}
			}
		};
		open.start();
	}
}