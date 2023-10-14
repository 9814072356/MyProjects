import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class Init extends Application {
	static Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
	public final static double WIDTH = 500;
	public final static double HEIGHT = primaryScreenBounds.getHeight() - 25;
	public static int red;
	public static int green;
	public static int blue;
	public static Pane pane = new Pane();
	public static Pane deco = new Pane();
	public static Scene scene = new Scene(pane,WIDTH,HEIGHT);
	public Text INTRO1 = new Text("ULTRA EYE TEST");
	public Text START = new Text("u ded");
	public Text QUIT = new Text("GIVE UP ?");
	public static Rectangle background = new Rectangle(WIDTH,HEIGHT,Color.web("111e6c"));
	public Rectangle start = new Rectangle(WIDTH / 3,HEIGHT / 16, Color.BURLYWOOD);
	public Rectangle quit = new Rectangle(WIDTH / 3,HEIGHT / 16, Color.BURLYWOOD);
	public Rectangle sound = new Rectangle(30,30);
	public Rectangle frame = new Rectangle();
	public static AnimationTimer flick;
	public static AnimationTimer bling;
	public static AnimationTimer up;
	public static AnimationTimer down;
	public static AnimationTimer decor;
	public static LinkedList<Rectangle> blingList = new LinkedList<Rectangle>();
	public static boolean movingUp = true;
	public static boolean movingDown = true;
	public static MediaPlayer clickPlayer;{
		Media click = new Media(getClass().getResource("clicksound.mp3").toExternalForm());
		clickPlayer = new MediaPlayer(click);
	}
	public static MediaPlayer popPlayer;{
		Media pop = new Media(getClass().getResource("pop.mp3").toExternalForm());
		popPlayer = new MediaPlayer(pop);
	}
	public static GamePhase g = new GamePhase();
	public static Image sImg = new Image("sound.png");
	public static ImagePattern sPat = new ImagePattern(sImg);
	public static Image mImg = new Image("mute.png");
	public static ImagePattern mPat = new ImagePattern(mImg);
	public static File file;
	public static Path currentRelativePath = Paths.get("");
	public static String dirPath = currentRelativePath.toAbsolutePath().toString();
	public static String scoreSource = dirPath + "/best.txt";

	@Override
	public void start(Stage stage) throws Exception{
		Font startFont = Font.loadFont(getClass().getResourceAsStream("/font/AgusSans-Regular.otf"), 30),
				startFontBig = Font.loadFont(getClass().getResourceAsStream("/font/AgusSans-Regular.otf"), 35),
				introFontSmall = Font.loadFont(getClass().getResourceAsStream("/font/Aspergit Bold.otf"), 40),
				introFontBig = Font.loadFont(getClass().getResourceAsStream("/font/Aspergit Bold.otf"), 45);
		popPlayer.setVolume(0.5);

		file = new File(scoreSource);
		file.createNewFile();

		DropShadow difGlow = new DropShadow();
		difGlow.setColor(Color.WHITE);
		difGlow.setWidth(10);
		difGlow.setHeight(10);
		difGlow.setSpread(0.4);
		DropShadow difGlow2 = new DropShadow();
		difGlow2.setColor(Color.DEEPPINK);
		difGlow2.setWidth(25);
		difGlow2.setHeight(25);
		difGlow2.setSpread(0.6);
		
		sound.setX(5);
		sound.setY(HEIGHT - sound.getHeight() - 5);
		sound.setFill(g.getMelodyPlayer().getStatus() == MediaPlayer.Status.PLAYING ? sPat : mPat);
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
					g.getMelodyPlayer().pause();
				}else {
					on = true;
					sound.setFill(sPat);
					g.getMelodyPlayer().play();
				}
			}

		});

		pane.getChildren().addAll(background, sound, frame);

		red = (int) (136 + Math.random() * ((255 - 136) + 1) - 50);
		green = (int) (111 + Math.random() * ((238 - 111) + 1));
		blue = (int) (92 + Math.random() * ((180 - 92) + 1));

		background.setStroke(Color.web("ffd700"));
		background.setStrokeWidth(4);
		background.setArcWidth(30);
		background.setArcHeight(30);   

		INTRO1.setFont(introFontSmall);
		double introWidth = INTRO1.getBoundsInLocal().getWidth();
		double introHeight = INTRO1.getBoundsInLocal().getHeight();
		INTRO1.setFill(Color.WHITE);
		INTRO1.setX(WIDTH / 2 - introWidth / 2 + 2);
		INTRO1.setY(HEIGHT / 4 - introHeight / 4);
		INTRO1.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				double startwidth = INTRO1.getBoundsInLocal().getWidth();
				double startheight = INTRO1.getBoundsInLocal().getHeight();
				INTRO1.setFont(introFontBig);
				double STARTWIDTH = INTRO1.getBoundsInLocal().getWidth();
				double STARTHEIGHT = INTRO1.getBoundsInLocal().getHeight();
				INTRO1.setX(INTRO1.getX() - (STARTWIDTH - startwidth) / 2);
				INTRO1.setY(INTRO1.getY() + (STARTHEIGHT - startheight) / 4);
				popPlayer.stop();
				popPlayer.play();
			}

		});
		INTRO1.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				double startwidth = INTRO1.getBoundsInLocal().getWidth();
				double startheight = INTRO1.getBoundsInLocal().getHeight();
				INTRO1.setFont(introFontSmall);
				double STARTWIDTH = INTRO1.getBoundsInLocal().getWidth();
				double STARTHEIGHT = INTRO1.getBoundsInLocal().getHeight();
				INTRO1.setX(INTRO1.getX() + (startwidth - STARTWIDTH) / 2);
				INTRO1.setY(INTRO1.getY() - (startheight - STARTHEIGHT) / 4);
			}

		});
		
		frame.setWidth(introWidth + 60);
		frame.setHeight(HEIGHT - 100);
		frame.setX(WIDTH / 2 - frame.getWidth() / 2);
		frame.setY(HEIGHT / 2 - frame.getHeight() / 2);
		frame.setFill(Color.TRANSPARENT);
		frame.setStroke(Color.CYAN);
		frame.setStrokeWidth(3);
		frame.setEffect(difGlow2);

		pane.getChildren().addAll(INTRO1,deco,start,START,quit,QUIT);

		deco.setPrefWidth(100);
		deco.setPrefHeight(deco.getPrefWidth());
		deco.setLayoutX(WIDTH / 2 - deco.getPrefWidth() / 2);
		deco.setLayoutY(INTRO1.getY() + introHeight + 50);
		deco.setEffect(difGlow);

		start.setStroke(Color.web("0e4d92"));
		start.setStrokeWidth(5);
		start.setArcWidth(50);
		start.setArcHeight(50);
		start.setX(WIDTH / 2 - start.getWidth() / 2);
		start.setY(deco.getLayoutY() + deco.getPrefHeight() + start.getHeight() + 50);
		start.setFill(Color.TRANSPARENT);
		start.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				start.setStroke(Color.WHITE);
				START.setStroke(Color.WHITE);
				//				bling.stop();
				flick.stop();
				clickPlayer.stop();
				clickPlayer.play();
			}

		});
		//		g.start(stage);
		start.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				start.setStroke(Color.web("0e4d92"));
				START.setStroke(Color.web("0e4d92"));
				Scene s = g.getScene();
				g.setRatio(2);
				g.setClickedTimes(0);
				g.setOffset(70);
				g.setClicks("0");
				g.getPane().getChildren().clear();
				g.getTimeGroup().getChildren().clear();
				g.getPlatform().getChildren().clear();
				g.setTime(null);
				g.setMinute(0,0);
				g.setSecond(2,0);
				g.setS("" + g.getSecond2(),"" + g.getSecond1());
				g.setM("" + g.getMinute2(),"" + g.getMinute1());
				g.start(stage);
				g.getEndGamePane().setVisible(false);
				g.getPlatform().setDisable(false);
				stage.sizeToScene();
				stage.setResizable(false);
				stage.setScene(s);
				g.showTimeElapsed();
				g.getTime().start();
			}

		});
		start.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				start.setWidth(start.getWidth() + 4);
				start.setHeight(start.getHeight() + 4);
				start.setX(start.getX() - 2);
				start.setY(start.getY() - 2);
				double startwidth = START.getBoundsInLocal().getWidth();
				double startheight = START.getBoundsInLocal().getHeight();
				START.setFont(startFontBig);
				double STARTWIDTH = START.getBoundsInLocal().getWidth();
				double STARTHEIGHT = START.getBoundsInLocal().getHeight();
				START.setX(START.getX() - (STARTWIDTH - startwidth) / 2);
				START.setY(START.getY() + (STARTHEIGHT - startheight) / 4);
				popPlayer.stop();
				popPlayer.play();
			}

		});
		start.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				start.setWidth(start.getWidth() - 4);
				start.setHeight(start.getHeight() - 4);
				start.setX(start.getX() + 2);
				start.setY(start.getY() + 2);
				double startwidth = START.getBoundsInLocal().getWidth();
				double startheight = START.getBoundsInLocal().getHeight();
				START.setFont(startFont);
				double STARTWIDTH = START.getBoundsInLocal().getWidth();
				double STARTHEIGHT = START.getBoundsInLocal().getHeight();
				START.setX(START.getX() + (startwidth - STARTWIDTH) / 2);
				START.setY(START.getY() - (startheight - STARTHEIGHT) / 4);
			}

		});

		START.setFont(startFont);
		START.setFill(start.getStroke());
		double STARTWIDTH = START.getBoundsInLocal().getWidth();
		double STARTHEIGHT = START.getBoundsInLocal().getHeight();
		START.setX(start.getX() + start.getWidth() / 2 - STARTWIDTH / 2);
		START.setY(start.getY() + start.getHeight() / 2 + STARTHEIGHT / 4);
		START.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				start.setStroke(Color.WHITE);
				START.setStroke(Color.WHITE);
				//				bling.stop();
				flick.stop();
				clickPlayer.stop();
				clickPlayer.play();
			}

		});
		START.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				start.setStroke(Color.web("0e4d92"));
				START.setStroke(Color.web("0e4d92"));
				Scene s = g.getScene();
				g.setRatio(2);
				g.setClickedTimes(0);
				g.setOffset(70);
				g.setClicks("0");
				g.getPane().getChildren().clear();
				g.getTimeGroup().getChildren().clear();
				g.getPlatform().getChildren().clear();
				g.setTime(null);
				g.setMinute(0,0);
				g.setSecond(2,0);
				g.setS("" + g.getSecond2(),"" + g.getSecond1());
				g.setM("" + g.getMinute2(),"" + g.getMinute1());
				g.start(stage);
				g.getEndGamePane().setVisible(false);
				g.getPlatform().setDisable(false);
				stage.sizeToScene();
				stage.setResizable(false);
				stage.setScene(s);
				g.showTimeElapsed();
				g.getTime().start();
			}
		});
		START.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				start.setWidth(start.getWidth() + 4);
				start.setHeight(start.getHeight() + 4);
				start.setX(start.getX() - 2);
				start.setY(start.getY() - 2);
				double startwidth = START.getBoundsInLocal().getWidth();
				double startheight = START.getBoundsInLocal().getHeight();
				START.setFont(startFontBig);
				double STARTWIDTH = START.getBoundsInLocal().getWidth();
				double STARTHEIGHT = START.getBoundsInLocal().getHeight();
				START.setX(START.getX() - (STARTWIDTH - startwidth) / 2);
				START.setY(START.getY() + (STARTHEIGHT - startheight) / 4);
				popPlayer.stop();
				popPlayer.play();
			}

		});
		START.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				start.setWidth(start.getWidth() - 4);
				start.setHeight(start.getHeight() - 4);
				start.setX(start.getX() + 2);
				start.setY(start.getY() + 2);
				double startwidth = START.getBoundsInLocal().getWidth();
				double startheight = START.getBoundsInLocal().getHeight();
				START.setFont(startFont);
				double STARTWIDTH = START.getBoundsInLocal().getWidth();
				double STARTHEIGHT = START.getBoundsInLocal().getHeight();
				START.setX(START.getX() + (startwidth - STARTWIDTH) / 2);
				START.setY(START.getY() - (startheight - STARTHEIGHT) / 4);
			}

		});

		quit.setStroke(Color.web("0e4d92"));
		quit.setStrokeWidth(5);
		quit.setArcWidth(50);
		quit.setArcHeight(50);
		quit.setX(WIDTH / 2 - quit.getWidth() / 2);
		quit.setY(start.getY() + start.getHeight() + 40);
		quit.setFill(Color.TRANSPARENT);
		quit.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				quit.setStroke(Color.WHITE);
				QUIT.setStroke(Color.WHITE);
				//				bling.stop();
				flick.stop();
			}

		});
		quit.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				System.exit(0);
			}

		});
		quit.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				quit.setWidth(quit.getWidth() + 4);
				quit.setHeight(quit.getHeight() + 4);
				quit.setX(quit.getX() - 2);
				quit.setY(quit.getY() - 2);
				double startwidth = QUIT.getBoundsInLocal().getWidth();
				double startheight = QUIT.getBoundsInLocal().getHeight();
				QUIT.setFont(startFontBig);
				double STARTWIDTH = QUIT.getBoundsInLocal().getWidth();
				double STARTHEIGHT = QUIT.getBoundsInLocal().getHeight();
				QUIT.setX(QUIT.getX() - (STARTWIDTH - startwidth) / 2);
				QUIT.setY(QUIT.getY() + (STARTHEIGHT - startheight) / 4);
				popPlayer.stop();
				popPlayer.play();
			}

		});
		quit.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				quit.setWidth(quit.getWidth() - 4);
				quit.setHeight(quit.getHeight() - 4);
				quit.setX(quit.getX() + 2);
				quit.setY(quit.getY() + 2);
				double startwidth = QUIT.getBoundsInLocal().getWidth();
				double startheight = QUIT.getBoundsInLocal().getHeight();
				QUIT.setFont(startFont);
				double STARTWIDTH = QUIT.getBoundsInLocal().getWidth();
				double STARTHEIGHT = QUIT.getBoundsInLocal().getHeight();
				QUIT.setX(QUIT.getX() + (startwidth - STARTWIDTH) / 2);
				QUIT.setY(QUIT.getY() - (startheight - STARTHEIGHT) / 4);
			}

		});

		QUIT.setFont(startFont);
		QUIT.setFill(quit.getStroke());
		double QUITWIDTH = QUIT.getBoundsInLocal().getWidth();
		double QUITHEIGHT = QUIT.getBoundsInLocal().getHeight();
		QUIT.setX(quit.getX() + quit.getWidth() / 2 - QUITWIDTH / 2);
		QUIT.setY(quit.getY() + quit.getHeight() / 2 + QUITHEIGHT / 4);
		QUIT.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				quit.setStroke(Color.WHITE);
				QUIT.setStroke(Color.WHITE);
				//				bling.stop();
				flick.stop();
			}

		});
		QUIT.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				System.exit(0);
			}

		});
		QUIT.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				quit.setWidth(quit.getWidth() + 4);
				quit.setHeight(quit.getHeight() + 4);
				quit.setX(quit.getX() - 2);
				quit.setY(quit.getY() - 2);
				double startwidth = QUIT.getBoundsInLocal().getWidth();
				double startheight = QUIT.getBoundsInLocal().getHeight();
				QUIT.setFont(startFontBig);
				double STARTWIDTH = QUIT.getBoundsInLocal().getWidth();
				double STARTHEIGHT = QUIT.getBoundsInLocal().getHeight();
				QUIT.setX(QUIT.getX() - (STARTWIDTH - startwidth) / 2);
				QUIT.setY(QUIT.getY() + (STARTHEIGHT - startheight) / 4);
				popPlayer.stop();
				popPlayer.play();
			}

		});
		QUIT.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				quit.setWidth(quit.getWidth() - 4);
				quit.setHeight(quit.getHeight() - 4);
				quit.setX(quit.getX() + 2);
				quit.setY(quit.getY() + 2);
				double startwidth = QUIT.getBoundsInLocal().getWidth();
				double startheight = QUIT.getBoundsInLocal().getHeight();
				QUIT.setFont(startFont);
				double STARTWIDTH = QUIT.getBoundsInLocal().getWidth();
				double STARTHEIGHT = QUIT.getBoundsInLocal().getHeight();
				QUIT.setX(QUIT.getX() + (startwidth - STARTWIDTH) / 2);
				QUIT.setY(QUIT.getY() - (startheight - STARTHEIGHT) / 4);
			}

		});

		for(int row = 0; row < 2; row++) {
			for(int col = 0; col < 2; col++) {
				Rectangle a = new Rectangle(deco.getPrefWidth() / 2 - 4,deco.getPrefWidth() / 2 - 4);
				a.setArcHeight(a.getWidth() * 0.2);
				a.setArcWidth(a.getHeight() * 0.2);
				a.setFill(Color.rgb(red, green, blue));
				a.setX(col * deco.getPrefWidth() / 2 + (deco.getPrefWidth() / 2) / 2 - a.getWidth() / 2);
				a.setY(row * deco.getPrefHeight() / 2 + (deco.getPrefHeight() / 2) / 2 - a.getHeight() / 2);
				a.setOnMouseEntered(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent arg0) {
						// TODO Auto-generated method stub
						a.setWidth(a.getWidth() + 4);
						a.setHeight(a.getHeight() + 4);
						a.setX(a.getX() - 2);
						a.setY(a.getY() - 2);
						popPlayer.stop();
						popPlayer.play();
					}

				});
				a.setOnMouseExited(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent arg0) {
						// TODO Auto-generated method stub
						a.setWidth(a.getWidth() - 4);
						a.setHeight(a.getHeight() - 4);
						a.setX(a.getX() + 2);
						a.setY(a.getY() + 2);
					}

				});
				deco.getChildren().add(a);
			}
		}	

		red += 50;
		((Rectangle)deco.getChildren().get(1)).setFill(Color.rgb(red, green, blue));

		flicker(QUIT,quit,START,start,INTRO1);

		scene.setFill(background.getStroke());
		stage.setScene(scene);
		stage.sizeToScene();
		stage.setResizable(false);
		stage.show();
	}

	public static AnimationTimer getFlick() {
		return flick;
	}

	public static void flicker(Text QUIT, Rectangle quit, Text START, Rectangle start, Text intro) {
		DropShadow difGlow = new DropShadow();
		difGlow.setColor(Color.DEEPPINK);
		difGlow.setWidth(25);
		difGlow.setHeight(25);
		difGlow.setSpread(0.6);
		flick = new AnimationTimer() {

			long last = 0;
			int times = 1;
			@Override
			public void handle(long now) {
				// TODO Auto-generated method stub

				if(now - last >= 500_000_000) {
					if(START.getStroke() != Color.WHITE) {
						START.setStroke(Color.WHITE);
						start.setStroke(Color.WHITE);
						QUIT.setStroke(Color.WHITE);
						quit.setStroke(Color.WHITE);
					}else {
						START.setStroke(Color.web("0e4d92"));
						start.setStroke(Color.web("0e4d92"));	
						QUIT.setStroke(Color.web("0e4d92"));
						quit.setStroke(Color.web("0e4d92"));				
					}
					START.setEffect(difGlow);
					start.setEffect(difGlow);
					QUIT.setEffect(difGlow);
					quit.setEffect(difGlow);
					intro.setEffect(difGlow);
					switch(times) {
					case 1:
						intro.setFill(Color.RED);
						times++;
						break;
					case 2:
						intro.setFill(Color.ORANGE);
						times++;
						break;
					case 3:
						intro.setFill(Color.YELLOW);
						times++;
						break;
					case 4:
						intro.setFill(Color.LIMEGREEN);
						times++;
						break;
					case 5:
						intro.setFill(Color.LIGHTSKYBLUE);
						times++;
						break;
					case 6:
						intro.setFill(Color.MEDIUMPURPLE);
						times++;
						break;
					case 7:
						intro.setFill(Color.CORAL);
						times++;
						break;
					case 8:
						intro.setFill(Color.CYAN);
						times++;
						break;
					case 9:
						intro.setFill(Color.PINK);
						times++;
						break;
					case 10:
						intro.setFill(Color.WHITE);
						times = 1;
						break;
					}

					last = now;
				}
			}

		};

		flick.start();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public static String getScoreSource() {
		return scoreSource;
	}

	public static double getWIDTH() {
		return WIDTH;
	}

	public static double getHEIGHT() {
		return HEIGHT;
	}

	public static Scene getScene() {
		return scene;
	}

	public static GamePhase getGamePhase() {
		return g;
	}

	public static void bling() {
		bling=new AnimationTimer() {
			long blingbling = 0;
			int i = 0;
			boolean posChanged = true;
			@Override
			public void handle(long blong) {
				// TODO Auto-generated method stub
				if(blong - blingbling >= 16000000) {
					if(posChanged) {
						for(Rectangle bling : blingList) {
							double randX = Math.random() * WIDTH;
							double randY = Math.random() * HEIGHT;
							bling.setX(randX);
							bling.setY(randY);
							bling.setOpacity(1);
						}
						posChanged = false;
					}else {
						if(blingList.getLast().getOpacity() < 0) {
							for(Rectangle r : blingList) {
								r.setWidth(0);
								r.setHeight(20);
							}
							posChanged = true;
						}else {
							for(Rectangle r : blingList) {
								r.setWidth(r.getWidth() + 10);
								r.setX(r.getX() - 5);
								r.setOpacity(blingList.get(i).getOpacity() - 0.01);
							}
						}						
					}
					blingbling = blong;
				}
			}

		};
	}	
}
