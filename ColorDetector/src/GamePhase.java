import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GamePhase{
	public static Pane platform = new Pane();
	public static Pane pane = new Pane();
	public static Pane endGamePane = new Pane();
	public final static double WIDTH = Init.getWIDTH();
	public final static double HEIGHT = Init.getHEIGHT();
	public static int minute1 = 0,minute2 = 0,second1 = 2,second2 = 0;
	public static int ratio = 2;
	public static int maxLevel = 50;
	public static boolean ratioChanged = false;
	public static int clickedTimes = 0;
	public static int rand;		
	public static int red;	
	public static int green;	
	public static int blue;	
	public static int offset = 70;
	public static Text m1 = new Text("" + minute1);
	public static Text m2 = new Text("" + minute2);
	public static Text s1 = new Text("" + second1);
	public static Text s2 = new Text("" + second2);
	public static Text mColon = new Text(":");
	public static Text sColon = new Text(":");
	public static Text level = new Text("Level : ");
	public static Text clicks = new Text("0");
	public static Text your = new Text("Y O U R");
	public static Text score1 = new Text("S C O R E");
	public static Text sc = new Text("0");
	public static Text best = new Text("B E S T");
	public static Text score2 = new Text("S C O R E");
	public static Text be = new Text("0");
	public static Text GO = new Text("G A M E  O V E R");
	public static Text SKIP = new Text("S K I P");
	public static Group timeGroup = new Group();
	public static Scene scene = new Scene(pane,WIDTH,HEIGHT);
	public static Rectangle background = new Rectangle(WIDTH,HEIGHT);
	public static Rectangle endGameBG = new Rectangle(WIDTH, HEIGHT);
	public static Rectangle restart = new Rectangle(endGameBG.getWidth() / 6,endGameBG.getWidth() / 6);
	public static Rectangle exit = new Rectangle(restart.getWidth(),restart.getHeight());
	public static Rectangle sound = new Rectangle(30,30);
	public static Rectangle leftDoor = new Rectangle(WIDTH / 2, HEIGHT);
	public static Rectangle rightDoor = new Rectangle(WIDTH / 2, HEIGHT);
	public static Rectangle skip = new Rectangle(100,30);
	public static Image rImg = new Image("restart.png");
	public static ImagePattern rPat = new ImagePattern(rImg);
	public static Image eImg = new Image("exit.png");
	public static ImagePattern ePat = new ImagePattern(eImg);
	public static Image sImg = new Image("sound.png");
	public static ImagePattern sPat = new ImagePattern(sImg);
	public static Image mImg = new Image("mute.png");
	public static ImagePattern mPat = new ImagePattern(mImg);
	public static LinkedList<Rectangle> recList = new LinkedList<Rectangle>();	
	public static AnimationTimer time;
	public static MediaPlayer bruhPlayer;{
		Media bruh = new Media(getClass().getResource("bruhsound.mp3").toExternalForm());
		bruhPlayer = new MediaPlayer(bruh);
	}
	public static MediaPlayer melodyPlayer;{
		Media melody = new Media(getClass().getResource("cma.mp3").toExternalForm());
		melodyPlayer = new MediaPlayer(melody);
	}
	public static MediaPlayer popPlayer;{
		Media pop = new Media(getClass().getResource("pop.mp3").toExternalForm());
		popPlayer = new MediaPlayer(pop);
	}
	MediaPlayer clapPlayer;
	public static MediaView clapView;{
		Media clap = new Media(getClass().getResource("clap.mp4").toExternalForm());
		clapPlayer = new MediaPlayer(clap);
		clapView = new MediaView(clapPlayer); 
	}
	public static String bestScore = "";
	public static PrintWriter writer;
	public static Font textFontBig,textFontSmall,textFontMedium;
	public static DropShadow shadow1 = new DropShadow(),
			shadow2 = new DropShadow();

	public void start(Stage stage){
		// TODO Auto-generated method stub				
		textFontBig = Font.loadFont(getClass().getResourceAsStream("/font/Aspergit Bold.otf"), 40);
		textFontSmall = Font.loadFont(getClass().getResourceAsStream("/font/Aspergit Bold.otf"), 15);
		textFontMedium = Font.loadFont(getClass().getResourceAsStream("/font/Aspergit.otf"), 25);

		background.setFill(Color.web("002366"));
//		background.setArcWidth(20);
//		background.setArcHeight(20);
//		background.setStroke(Color.web("d4af37"));
//		background.setStrokeWidth(2);

		shadow1.setOffsetX(3);
		shadow1.setOffsetY(3);
		shadow1.setWidth(5);
		shadow1.setHeight(5);
		shadow1.setSpread(0.4);
		shadow1.setColor(Color.HOTPINK);
		shadow2.setOffsetX(10);
		shadow2.setOffsetY(20);
		shadow2.setWidth(5);
		shadow2.setHeight(5);
		shadow2.setSpread(0.1);
		shadow2.setColor(Color.GREY);

		scene.setFill(background.getStroke());

		s2.setText("" + second2);
		s1.setText("" + second1);
		m2.setText("" + minute2);
		m1.setText("" + minute1);

		m1.setFont(textFontBig);
		m1.setFill(Color.WHITE);
		double m1W = m1.getBoundsInLocal().getWidth();
		double m1H = m1.getBoundsInLocal().getHeight();
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
		timeGroup.setLayoutX(background.getWidth() - 120);
		timeGroup.setLayoutY(background.getHeight() / 8 - m1H / 2);
		timeGroup.setEffect(shadow1);

		level.setFill(Color.WHITE);
		level.setFont(m1.getFont());
		double lW = level.getBoundsInLocal().getWidth();
		level.setX(10);
		level.setY(timeGroup.getLayoutY());
		level.setEffect(shadow1);

		clicks.setFill(Color.WHITE);
		clicks.setFont(m1.getFont());
		clicks.setX(level.getX() + lW);
		clicks.setY(level.getY());
		clicks.setEffect(shadow1);

		platform.setPrefWidth(WIDTH);
		platform.setPrefHeight(platform.getPrefWidth());
		platform.setLayoutX(WIDTH / 2 - platform.getPrefWidth() / 2);
		platform.setLayoutY(HEIGHT / 2 - platform.getPrefHeight() / 4);
		
		skip.setX(platform.getLayoutX() + platform.getPrefWidth() - skip.getWidth() - 10);
		skip.setY(platform.getLayoutY() + platform.getPrefHeight() - skip.getHeight() - 10);
		skip.setFill(Color.GREY);
		skip.setOpacity(0.5);
		skip.setArcWidth(10);
		skip.setArcHeight(10);
		skip.setVisible(false);
		skip.setOnMouseReleased(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(clapPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
					clapPlayer.stop();
					((Rectangle)platform.getChildren().get(rand)).setStrokeWidth(0);
					endGamePane.setVisible(true);
					sc.setText("" + clickedTimes);	

					sc.setFill(Color.DODGERBLUE);
					sc.setFont(textFontBig);
					double scW = sc.getBoundsInLocal().getWidth();
					double scH = sc.getBoundsInLocal().getHeight();
					sc.setX(endGameBG.getWidth() / 2 - scW / 2);
					sc.setY(endGameBG.getHeight() - 100);
					sc.setEffect(shadow2);

					your.setFont(textFontSmall);
					your.setFill(sc.getFill());
					double reW = your.getBoundsInLocal().getWidth();
					your.setX(sc.getX() - reW - 10);
					your.setY(sc.getY());
					your.setEffect(shadow2);

					score1.setFont(your.getFont());
					score1.setFill(your.getFill());
					score1.setX(sc.getX() + scW + 10);
					score1.setY(your.getY());
					score1.setEffect(shadow2);

					be.setFill(sc.getFill());
					be.setFont(sc.getFont());
					double beW = be.getBoundsInLocal().getWidth();
					be.setX(endGameBG.getWidth() / 2 - beW / 2);
					be.setY(sc.getY() + scH + 20);
					be.setEffect(shadow2);

					best.setFont(textFontSmall);
					best.setFill(sc.getFill());
					double bW = best.getBoundsInLocal().getWidth();
					best.setX(be.getX() - bW - 10);
					best.setY(be.getY());
					best.setEffect(shadow2);

					score2.setFont(your.getFont());
					score2.setFill(your.getFill());
					score2.setX(be.getX() + beW + 10);
					score2.setY(best.getY());
					score2.setEffect(shadow2);
					clapView.setVisible(false);		
					melodyPlayer.play();		
				}
			}
			
		});
		
		SKIP.setFill(Color.WHITE);
		SKIP.setFont(textFontMedium);
		double skW = SKIP.getBoundsInLocal().getWidth();
		double skH = SKIP.getBoundsInLocal().getHeight();
		SKIP.setX(skip.getX() + skip.getWidth() / 2 - skW / 2);
		SKIP.setY(skip.getY() + skip.getHeight() / 2 + skH / 3);
		SKIP.setVisible(false);
		SKIP.setOnMouseReleased(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(clapPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
					clapPlayer.stop();
					((Rectangle)platform.getChildren().get(rand)).setStrokeWidth(0);
					endGamePane.setVisible(true);
					sc.setText("" + clickedTimes);	

					sc.setFill(Color.DODGERBLUE);
					sc.setFont(textFontBig);
					double scW = sc.getBoundsInLocal().getWidth();
					double scH = sc.getBoundsInLocal().getHeight();
					sc.setX(endGameBG.getWidth() / 2 - scW / 2);
					sc.setY(endGameBG.getHeight() - 100);
					sc.setEffect(shadow2);

					your.setFont(textFontSmall);
					your.setFill(sc.getFill());
					double reW = your.getBoundsInLocal().getWidth();
					your.setX(sc.getX() - reW - 10);
					your.setY(sc.getY());
					your.setEffect(shadow2);

					score1.setFont(your.getFont());
					score1.setFill(your.getFill());
					score1.setX(sc.getX() + scW + 10);
					score1.setY(your.getY());
					score1.setEffect(shadow2);

					be.setFill(sc.getFill());
					be.setFont(sc.getFont());
					double beW = be.getBoundsInLocal().getWidth();
					be.setX(endGameBG.getWidth() / 2 - beW / 2);
					be.setY(sc.getY() + scH + 20);
					be.setEffect(shadow2);

					best.setFont(textFontSmall);
					best.setFill(sc.getFill());
					double bW = best.getBoundsInLocal().getWidth();
					best.setX(be.getX() - bW - 10);
					best.setY(be.getY());
					best.setEffect(shadow2);

					score2.setFont(your.getFont());
					score2.setFill(your.getFill());
					score2.setX(be.getX() + beW + 10);
					score2.setY(best.getY());
					score2.setEffect(shadow2);
					clapView.setVisible(false);		
					melodyPlayer.play();		
				}
			}
			
		});

		SwitchButton sw = new SwitchButton();
		sw.setLayoutX(WIDTH - sw.getMinWidth() - 30);
		sw.setLayoutY(platform.getLayoutY() - sw.getMinHeight() - 30);

		sound.setX(level.getX());
		sound.setY(sw.getLayoutY());
		sound.setFill(melodyPlayer.getStatus() == MediaPlayer.Status.PLAYING ? sPat : mPat);
		sound.setOnMouseReleased(new EventHandler<MouseEvent>() {
			boolean on = true;
			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				popPlayer.setVolume(0.4);
				popPlayer.stop();
				popPlayer.play();
				if(on) {
					on = false;
					sound.setFill(mPat);
					melodyPlayer.pause();
				}else {
					on = true;
					sound.setFill(sPat);
					melodyPlayer.play();
				}
			}

		});

		red = (int) (136 + Math.random() * ((255 - 136) + 1));
		green = (int) (111 + Math.random() * ((238 - 111) + 1));
		blue = (int) (92 + Math.random() * ((180 - 92) + 1) - offset);
		while(Color.rgb(red, green, blue) == background.getFill()) {
			red = (int) (Math.random() * 255);
			green = (int) (Math.random() * (255 - offset));
			blue = (int) (Math.random() * 255);
		}
		for(int row = 0; row < ratio; row++) {
			for(int col = 0; col < ratio; col++) {
				Rectangle a = new Rectangle(platform.getPrefWidth() / ratio - 4,platform.getPrefWidth() / ratio - 4);
				a.setArcHeight(a.getWidth() * 0.15);
				a.setArcWidth(a.getHeight() * 0.15);
				a.setFill(Color.rgb(red, green, blue));
				a.setX(col * platform.getPrefWidth() / ratio + (platform.getPrefWidth() / ratio) / 2 - a.getWidth() / 2);
				a.setY(row * platform.getPrefHeight() / ratio + (platform.getPrefHeight() / ratio) / 2 - a.getHeight() / 2);
				addClick(a);
				platform.getChildren().add(a);
			}
		}		

		rand = (int) (Math.random() * platform.getChildren().size());
		blue += offset;
		((Rectangle)platform.getChildren().get(rand)).setFill(Color.rgb(red, green, blue));

		clapView.setVisible(false);
		clapView.fitWidthProperty().bind(platform.widthProperty());
		clapView.fitHeightProperty().bind(platform.heightProperty());
		clapView.setPreserveRatio(false);
		clapView.setLayoutY(platform.getLayoutY());

		if(endGamePane.getChildren().size() == 0) {
			endGameBG.setFill(Color.web("fff8e7"));
			endGameBG.setOpacity(1);
//			endGameBG.setArcWidth(30);
//			endGameBG.setArcHeight(30);

			GO.setFont(textFontBig);
			GO.setFill(Color.ORANGE);
			double gW = GO.getBoundsInLocal().getWidth();
			double gH = GO.getBoundsInLocal().getHeight();
			GO.setX(endGameBG.getWidth() / 2 - gW / 2);
			GO.setY(endGameBG.getHeight() / 4 + gH / 2);
			GO.setEffect(shadow2);

			restart.setFill(rPat);
			restart.setX(endGameBG.getWidth() / 2 - restart.getWidth() / 2);
			restart.setY(GO.getY() + gH + 30);
			restart.setEffect(shadow2);
			restart.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {
					// TODO Auto-generated method stub
					ratio = 2;
					clickedTimes = 0;
					offset = 70;
					clicks.setText("0");
					pane.getChildren().clear();
					timeGroup.getChildren().clear();
					platform.getChildren().clear();
					time = null;
					minute1 = 0;minute2 = 0;second1 = 2;second2 = 0;
					s2.setText("" + second2);
					s1.setText("" + second1);
					m2.setText("" + minute2);
					m1.setText("" + minute1);
					start(stage);
					showTimeElapsed();
					time.start();
					endGamePane.setVisible(false);
					platform.setDisable(false);
				}

			});
			restart.setOnMouseEntered(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {
					// TODO Auto-generated method stub	
					restart.setWidth(restart.getWidth() + 4);
					restart.setHeight(restart.getHeight() + 4);
					restart.setX(restart.getX() - 2);
					restart.setY(restart.getY() - 2);
					popPlayer.stop();
					popPlayer.play();
				}

			});
			restart.setOnMouseExited(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {
					// TODO Auto-generated method stub
					restart.setWidth(restart.getWidth() - 4);
					restart.setHeight(restart.getHeight() - 4);
					restart.setX(restart.getX() + 2);
					restart.setY(restart.getY() + 2);
				}

			});

			exit.setFill(ePat);
			exit.setX(restart.getX());
			exit.setY(restart.getY() + restart.getHeight() + 80);
			exit.setEffect(shadow2);
			exit.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {
					// TODO Auto-generated method stub
					Scene sc = Init.getScene();
					stage.setScene(sc);
					Init.getFlick().start();
				}

			});
			exit.setOnMouseEntered(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {
					// TODO Auto-generated method stub
					exit.setWidth(exit.getWidth() + 8);
					exit.setHeight(exit.getHeight() + 8);
					exit.setX(exit.getX() - 4);
					exit.setY(exit.getY() - 4);
					popPlayer.stop();
					popPlayer.play();
				}

			});
			exit.setOnMouseExited(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {
					// TODO Auto-generated method stub
					exit.setWidth(exit.getWidth() - 8);
					exit.setHeight(exit.getHeight() - 8);
					exit.setX(exit.getX() + 4);
					exit.setY(exit.getY() + 4);
				}

			});

			endGamePane.getChildren().addAll(endGameBG, restart, exit, GO, your, sc, score1, best, be, score2);
			endGamePane.setLayoutX(WIDTH / 2 - endGameBG.getWidth() / 2);
			endGamePane.setLayoutY(HEIGHT / 2 - endGameBG.getHeight() / 2);
			endGamePane.setVisible(false);
		}

		pane.getChildren().addAll(background, sw, sound, platform, timeGroup, level, clicks, clapView, skip, SKIP);
		if(!pane.getChildren().contains(endGamePane))pane.getChildren().add(endGamePane);
	}

	public Group getTimeGroup() {
		return timeGroup;
	}

	public static Text getClicks() {
		return clicks;
	}

	public static Text getLevel() {
		return level;
	}

	public Scene getScene() {
		return scene;
	}

	public AnimationTimer getTime() {
		return time;
	}

	public void setTime(AnimationTimer t) {
		time = t;
	}

	public static Rectangle getBackground() {
		return background;
	}

	public static void addClick(Rectangle r) {
		r.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				if(clickedTimes % 10 == 4) {
					if(ratio < maxLevel) {
						ratio++;
						ratioChanged = true;
					}
				}
				if(platform.getChildren().indexOf(r) == rand) {
					if(ratioChanged) {
						if(offset - 15 > 0)offset -= 15;
						else offset = 5;
						red = (int) (136 + Math.random() * ((255 - 136) + 1));
						green = (int) (111 + Math.random() * ((238 - 111) + 1));
						blue = (int) (92 + Math.random() * ((180 - 92) + 1) - offset);
						platform.getChildren().clear();
						while(Color.rgb(red, green, blue) == background.getFill()) {
							red = (int) (136 + Math.random() * ((255 - 136) + 1));
							green = (int) (111 + Math.random() * ((238 - 111) + 1));
							blue = (int) (92 + Math.random() * ((180 - 92) + 1) - offset);
						}
						for(int row = 0; row < ratio; row++) {
							for(int col = 0; col < ratio; col++) {
								Rectangle a = new Rectangle(platform.getPrefWidth() / ratio - 4,platform.getPrefWidth() / ratio - 4);
								a.setArcHeight(a.getWidth() * 0.15);
								a.setArcWidth(a.getHeight() * 0.15);
								a.setFill(Color.rgb(red, green, blue));
								a.setX(col * platform.getPrefWidth() / ratio + (platform.getPrefWidth() / ratio) / 2 - a.getWidth() / 2);
								a.setY(row * platform.getPrefHeight() / ratio + (platform.getPrefHeight() / ratio) / 2 - a.getHeight() / 2);
								addClick(a);
								platform.getChildren().add(a);
							}
						}	
						rand = (int) (Math.random() * platform.getChildren().size());
						blue += offset;
						((Rectangle)platform.getChildren().get(rand)).setFill(Color.rgb(red, green, blue));
						ratioChanged = false;
					}else {
						red = (int) (136 + Math.random() * ((255 - 136) + 1));
						green = (int) (111 + Math.random() * ((238 - 111) + 1));
						blue = (int) (92 + Math.random() * ((180 - 92) + 1) - offset);
						while(Color.rgb(red, green, blue) == background.getFill()) {
							red = (int) (136 + Math.random() * ((255 - 136) + 1));
							green = (int) (111 + Math.random() * ((238 - 111) + 1));
							blue = (int) (92 + Math.random() * ((180 - 92) + 1) - offset);
						}
						for(Node n : platform.getChildren()) {
							((Rectangle)n).setFill(Color.rgb(red, green, blue));
						}
						rand = (int) (Math.random() * platform.getChildren().size());
						blue += offset;
						((Rectangle)platform.getChildren().get(rand)).setFill(Color.rgb(red, green, blue));
					}
					clickedTimes++;		
					clicks.setText("" + clickedTimes);
					if(second2 < 9) {
						second2++;
						s2.setText("" + second2);
					}else {
						if(second1 < 5) {
							second2 = 0;
							s2.setText("" + second2);
							second1++;
							s1.setText("" + second1);
						}else {
							if(minute2 < 9) {
								second2 = 0;
								s2.setText("" + second2);
								second1 = 0;
								s1.setText("" + second1);
								minute2++;
								m2.setText("" + minute2);
							}else {
								if(minute1 < 5) {
									second2 = 0;
									s2.setText("" + second2);
									second1 = 0;
									s1.setText("" + second1);
									minute2 = 0;
									m2.setText("" + minute2);
									minute1++;
									m1.setText("" + minute1);
								}
							}
						}
					}
				} 
				else { 
					bruhPlayer.stop(); 
					bruhPlayer.play(); 
					if(melodyPlayer.getStatus() == MediaPlayer.Status.PLAYING){ 
						melodyPlayer.pause();
						bruhPlayer.setOnEndOfMedia(() -> {
							melodyPlayer.play(); 
						}); 
					} 
				}
			}

		});
	}

	public void showTimeElapsed() {
		time = new AnimationTimer(){
			long last = 0;
			int delay = 0;
			@Override
			public void handle(long now) {
				// TODO Auto-generated method stub
				if(now - last >= 1000_000_000) {
					if(second2 == 0 && second1 == 0 && minute2 == 0 && minute1 == 0) {
						if(delay == 1){
							clapView.setVisible(true);
							skip.setVisible(true);
							SKIP.setVisible(true);
							clapPlayer.play();
							clapPlayer.setOnEndOfMedia(() -> {
								clapPlayer.stop();
								((Rectangle)platform.getChildren().get(rand)).setStrokeWidth(0);
								endGamePane.setVisible(true);
								sc.setText("" + clickedTimes);	

								sc.setFill(Color.DODGERBLUE);
								sc.setFont(textFontBig);
								double scW = sc.getBoundsInLocal().getWidth();
								double scH = sc.getBoundsInLocal().getHeight();
								sc.setX(endGameBG.getWidth() / 2 - scW / 2);
								sc.setY(endGameBG.getHeight() - 100);
								sc.setEffect(shadow2);

								your.setFont(textFontSmall);
								your.setFill(sc.getFill());
								double reW = your.getBoundsInLocal().getWidth();
								your.setX(sc.getX() - reW - 10);
								your.setY(sc.getY());
								your.setEffect(shadow2);

								score1.setFont(your.getFont());
								score1.setFill(your.getFill());
								score1.setX(sc.getX() + scW + 10);
								score1.setY(your.getY());
								score1.setEffect(shadow2);

								be.setFill(sc.getFill());
								be.setFont(sc.getFont());
								double beW = be.getBoundsInLocal().getWidth();
								be.setX(endGameBG.getWidth() / 2 - beW / 2);
								be.setY(sc.getY() + scH + 20);
								be.setEffect(shadow2);

								best.setFont(textFontSmall);
								best.setFill(sc.getFill());
								double bW = best.getBoundsInLocal().getWidth();
								best.setX(be.getX() - bW - 10);
								best.setY(be.getY());
								best.setEffect(shadow2);

								score2.setFont(your.getFont());
								score2.setFill(your.getFill());
								score2.setX(be.getX() + beW + 10);
								score2.setY(best.getY());
								score2.setEffect(shadow2);
								clapView.setVisible(false);		
								melodyPlayer.play();						
							});
							time.stop();
						}else {
							((Rectangle)platform.getChildren().get(rand)).setStroke(Color.DEEPSKYBLUE);
							((Rectangle)platform.getChildren().get(rand)).setStrokeWidth(5);
							for(Node r : platform.getChildren()) {
								((Rectangle)r).setDisable(true);
							}

							try {
								File prob = new File(Init.getScoreSource());
								Scanner reader1 = new Scanner(prob);
								while (reader1.hasNextLine()) {
									bestScore = reader1.nextLine();
								}
								reader1.close();
							}catch(Exception ex) {
								System.out.println(ex);
							}
							if(bestScore == "" || clickedTimes > Integer.parseInt(bestScore)) {
								try {
									writer = new PrintWriter(new File(Init.getScoreSource()));
									writer.append("" + clickedTimes);
									writer.flush();
								} catch (FileNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								be.setText("" + clickedTimes);
							}else {				
								be.setText(bestScore);
							}
							melodyPlayer.pause();
							delay++;
						}
					}else {
						if(second2 != 0) {
							second2--;
							s2.setText("" + second2);
						}else {
							if(second1 != 0) {
								second2 = 9;
								s2.setText("" + second2);
								second1--;
								s1.setText("" + second1);
							}else {
								if(minute2 != 0) {
									second2 = 9;
									s2.setText("" + second2);
									second1 = 5;
									s1.setText("" + second1);
									minute2--;
									m2.setText("" + minute2);
								}else {
									if(minute1 != 0) {
										second2 = 9;
										s2.setText("" + second2);
										second1 = 5;
										s1.setText("" + second1);
										minute2 = 9;
										m2.setText("" + minute2);
										minute1--;
										m1.setText("" + minute1);
									}else {
										m1.setText("" + minute1);
									}
								}
							}
						}
					}
					last = now;
				}
			}
		};
	}

	public Pane getPlatform() {
		// TODO Auto-generated method stub
		return platform;
	}

	public Pane getEndGamePane() {
		// TODO Auto-generated method stub
		return endGamePane;
	}

	public void setSecond(int sec1, int sec2) {
		// TODO Auto-generated method stub
		second1 = sec1;
		second2 = sec2;
	}

	public int getSecond2() {
		// TODO Auto-generated method stub
		return second2;
	}

	public int getSecond1() {
		// TODO Auto-generated method stub
		return second1;
	}

	public int getMinute2() {
		// TODO Auto-generated method stub
		return minute2;
	}

	public int getMinute1() {
		// TODO Auto-generated method stub
		return minute1;
	}

	public void setS(String se2, String se1) {
		// TODO Auto-generated method stub
		s2.setText(se2);
		s1.setText(se1);
	}

	public void setM(String mi2, String mi1) {
		// TODO Auto-generated method stub
		m2.setText(mi2);
		m1.setText(mi1);		
	}

	public void setMinute(int min2, int min1) {
		// TODO Auto-generated method stub
		minute1 = min1;
		minute2 = min2;
	}

	public Pane getPane() {
		// TODO Auto-generated method stub
		return pane;
	}

	public int getClickedTimes() {
		// TODO Auto-generated method stub
		return clickedTimes;
	}

	public void setClicks(String cl) {
		// TODO Auto-generated method stub
		clicks.setText(cl);
	}

	public void setOffset(int i) {
		// TODO Auto-generated method stub
		offset = i;
	}

	public void setClickedTimes(int i) {
		// TODO Auto-generated method stub
		clickedTimes = i;
	}

	public void setRatio(int i) {
		// TODO Auto-generated method stub
		ratio = i;
	}

	public MediaPlayer getMelodyPlayer() {
		return melodyPlayer;
	}
}
