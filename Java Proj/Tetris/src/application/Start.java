package application;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Start extends Application{
	public static double WIDTH = InGame.getSceneWidth();
	public static double HEIGHT = InGame.getSceneHeight();
	public static Pane group = new Pane();
	public Text T1 = new Text("T");
	public Text E = new Text("E");
	public Text T2 = new Text("T");
	public Text R = new Text("R");
	public Text I = new Text("I");
	public Text S = new Text("S");
	public Text rage = new Text("R A G E");
	public Text start = new Text("S T A R T");
	public Rectangle rim = new Rectangle(WIDTH, HEIGHT);
	public Rectangle startGame = new Rectangle();
	public Text START = new Text("START");
	public Rectangle setting = new Rectangle();
	public Text SETTING = new Text("SETTING");
	public static Scene scene = new Scene(group, WIDTH, HEIGHT);
	public Rectangle settingScreen = new Rectangle(WIDTH, HEIGHT);
	public static Rectangle option1 = new Rectangle(WIDTH/10,WIDTH/10);
	public Text opt1Txt = new Text("PEWDSPACK");
	public Rectangle option2 = new Rectangle(WIDTH/10,WIDTH/10);
	public Text opt2Txt = new Text("DEFAULT");
	public static String source = "";
	public static boolean toMenuIsPressed = false;
	public static Group optionsGroup = new Group();
	public static Rectangle options = new Rectangle((option1.getWidth() + option1.getStrokeWidth() * 2) * 6,option1.getHeight() + 20);
	public static String bgSource;
	public static Image BG;
	public static ImagePattern pat;
	public ScrollBar sb = new ScrollBar();
	public Rectangle lRec = new Rectangle((scene.getWidth() - options.getWidth()) / 2 - 6,options.getHeight());
	public Rectangle rRec = new Rectangle(lRec.getWidth(),lRec.getHeight());
	public Rectangle background = new Rectangle(scene.getWidth(), scene.getHeight());

	public static void returnDetection(boolean b) {
		toMenuIsPressed = b;
	}

	public static String getSource() {
		return source;
	}

	public static Scene getScene() {
		return scene;
	}

	public Pane getGroup() {
		return group;
	}

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		Font tetrisFont = 
				Font.loadFont(getClass()
						.getResourceAsStream("/font/title.ttf"), 40),
				rageFont = Font.loadFont(getClass()
						.getResourceAsStream("/font/rage.ttf"), 65),
				buttonFont = Font.loadFont(getClass().getResourceAsStream("/font/Aspergit Bold.otf"), 30),
				opt1Font = Font.loadFont(getClass().getResourceAsStream("/font/Aspergit Bold.otf"), 10);
		bgSource = "/backgrounds/setting.jpg";
		BG = new Image(bgSource);
		pat = new ImagePattern(BG);

		T2.setFont(tetrisFont);
		T2.setFill(Color.ORANGE);
		double T2Width = T2.getBoundsInLocal().getWidth();
		double T2Height = T2.getBoundsInLocal().getHeight();
		T2.setY(HEIGHT / 2 - 200);
		T2.setX(WIDTH / 2 - T2Width);
		E.setFont(tetrisFont);
		E.setFill(Color.GREEN);
		double EWidth = E.getBoundsInLocal().getWidth();
		E.setY(T2.getY());
		E.setX(T2.getX() - 2 * EWidth);
		T1.setFont(tetrisFont);
		T1.setFill(Color.RED);
		double T1Width = T1.getBoundsInLocal().getWidth();
		T1.setY(E.getY());
		T1.setX(E.getX() - 2 * T1Width);
		R.setFont(tetrisFont);
		R.setFill(Color.MEDIUMVIOLETRED);
		double RWidth = R.getBoundsInLocal().getWidth();
		R.setY(T2.getY());
		R.setX(WIDTH / 2 + RWidth);
		I.setFont(tetrisFont);
		I.setFill(Color.CYAN);
		double IWidth = I.getBoundsInLocal().getWidth();
		I.setY(R.getY());
		I.setX(R.getX() + 2 * IWidth);
		S.setFont(tetrisFont);
		S.setFill(Color.web("#FADA5E"));
		double SWidth = S.getBoundsInLocal().getWidth();
		S.setY(I.getY());
		S.setX(I.getX() + 2 * SWidth);
		rage.setFill(Color.RED);;
		rage.setFont(rageFont);
		double rageWidth = rage.getBoundsInLocal().getWidth();
		rage.setY(T2.getY() - T2Height);
		rage.setX(scene.getWidth() / 2 - rageWidth / 2);
		rim.setFill(Color.TRANSPARENT);
		rim.setStroke(Color.POWDERBLUE);
		rim.setStrokeWidth(6);
		rim.setX(0);
		rim.setY(0);

		background.setFill(Color.FLORALWHITE);
		
		group.getChildren().addAll(background,T1,E,T2,R,I,S, rage,rim,startGame,START,setting,SETTING, settingScreen, options, optionsGroup, sb, lRec, rRec);

		settingScreen.setFill(pat);
		settingScreen.setVisible(false);

		option1.setStroke(Color.BLACK);
		option1.setFill(Color.RED);
		option1.setStrokeWidth(3);
		option1.setArcHeight(15);
		option1.setArcWidth(15);
		option1.setVisible(false);
		option1.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				source = "pewdiepack";
				settingScreen.setVisible(false);
				options.setVisible(false);
				sb.setVisible(false);
				lRec.setVisible(false);
				rRec.setVisible(false);
				for(int i = 0; i < optionsGroup.getChildren().size(); i++)optionsGroup.getChildren().get(i).setVisible(false);
			}

		});

		opt1Txt.setVisible(false);
		opt1Txt.setFont(opt1Font);
		opt1Txt.setFill(Color.WHITE);
		
		option2.setStroke(Color.web("AB8024"));
		option2.setFill(Color.web("FFD700"));
		option2.setStrokeWidth(option1.getStrokeWidth());
		option2.setArcHeight(15);
		option2.setArcWidth(15);
		option2.setVisible(false);
		option2.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				source = "";
				settingScreen.setVisible(false);
				options.setVisible(false);
				sb.setVisible(false);
				lRec.setVisible(false);
				rRec.setVisible(false);
				for(int i = 0; i < optionsGroup.getChildren().size(); i++)optionsGroup.getChildren().get(i).setVisible(false);
			}

		});

		opt2Txt.setVisible(false);
		opt2Txt.setFont(opt1Font);
		opt2Txt.setFill(Color.WHITE);
		
		optionsGroup.getChildren().addAll(option1,option2,opt1Txt,opt2Txt);
		options.setFill(Color.BLACK);
		options.setOpacity(0.3);
		options.setArcHeight(option1.getArcHeight());
		options.setArcWidth(option1.getArcWidth());
		options.setY(settingScreen.getHeight() - options.getHeight() - 70);
		options.setX(settingScreen.getWidth() / 2 - options.getWidth() / 2);
		options.setVisible(false);
		option1.setX(options.getX() + 5);
		option1.setY(options.getY() + 5);
		double op1H = opt1Txt.getBoundsInLocal().getHeight();
		opt1Txt.setX(option1.getX());
		opt1Txt.setY(option1.getY() + option1.getHeight() + option1.getStrokeWidth() + op1H);
		option2.setX(option1.getX() + option1.getWidth() + option1.getStrokeWidth() + 5);
		option2.setY(option1.getY());
		double op2W = opt2Txt.getBoundsInLocal().getWidth();
		double op2H = opt2Txt.getBoundsInLocal().getHeight();
		opt2Txt.setX(option2.getX() + option2.getWidth() / 2 - op2W / 2);
		opt2Txt.setY(option2.getY() + option2.getHeight() + option2.getStrokeWidth() + op2H);
		sb.setLayoutX(options.getX());
		sb.setLayoutY(options.getY() + options.getHeight());
        sb.setMin(0);
        sb.setOrientation(Orientation.HORIZONTAL);
        sb.setPrefWidth(options.getWidth());
        sb.setMax(optionsGroup.getChildren().size() < 6 ? 0 : (option1.getWidth() + option1.getStrokeWidth() * 2) * optionsGroup.getChildren().size());
        sb.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
//                    option1.setX(+new_val.doubleValue());
//                    option2.setX(+new_val.doubleValue());
//                    opt1Txt.setX(+new_val.doubleValue());
//                    opt2Txt.setX(+new_val.doubleValue());
            	optionsGroup.setLayoutX(-new_val.doubleValue());
            }
        });
        sb.setVisible(false);
        lRec.setX(options.getX() - lRec.getWidth());
        lRec.setY(options.getY());
        rRec.setX(options.getX() + options.getWidth());
        rRec.setY(options.getY());
        lRec.setFill(Color.web("f1f1f1"));
        rRec.setFill(Color.web("f1f1f1"));
        lRec.setVisible(false);
        rRec.setVisible(false);

		startGame.setWidth(WIDTH / 5);
		startGame.setHeight(HEIGHT / 18);
		startGame.setX(WIDTH / 2 - startGame.getWidth() / 2);
		startGame.setY(T2.getY() + 100);
		startGame.setFill(Color.web("#008ecc"));
		startGame.setOpacity(0.4);
		START.setFont(buttonFont);
		START.setFill(scene.getFill());
		double sW = START.getBoundsInLocal().getWidth();
		double sH = START.getBoundsInLocal().getHeight();
		START.setX(startGame.getX() + startGame.getWidth() / 2 - sW / 2);
		START.setY(startGame.getY() + startGame.getHeight() / 2 + sH / 2 - 5);
		startGame.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(!toMenuIsPressed) {
					InGame g = new InGame();
					Scene sc = g.getScene();

					g.setImgSource(source);
					g.start(stage, sc);

					stage.setTitle("T E T R I S T E T R I S T E T R I S T E T R I S T E T R I S ");
					//stage.sizeToScene();
					stage.setResizable(false);
					//stage.setScene(sc);
					stage.setOnCloseRequest(e -> {
						System.exit(0);
					});
				}else {
					
					stage.setResizable(false);
					stage.setScene(InGame.getScene());
					stage.show();
					stage.setOnCloseRequest(e -> {
						System.exit(0);
					});
				}
			}

		});
		START.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(!toMenuIsPressed) {
					InGame g = new InGame();
					Scene sc = g.getScene();

					g.setImgSource(source);
					g.start(stage, sc);

					stage.setTitle("T E T R I S T E T R I S T E T R I S T E T R I S T E T R I S ");
					//stage.sizeToScene();
					stage.setResizable(false);
					stage.setScene(sc);
					stage.setOnCloseRequest(e -> {
						System.exit(0);
					});
				}else {
					InGame.restart();
					stage.setResizable(false);
					stage.setScene(InGame.getScene());
					stage.show();
					stage.setOnCloseRequest(e -> {
						System.exit(0);
					});
				}
			}

		});

		setting.setWidth(WIDTH / 5);
		setting.setHeight(HEIGHT / 18);
		setting.setX(WIDTH / 2 - setting.getWidth() / 2);
		setting.setY(startGame.getY() + 50);
		setting.setFill(Color.web("#d21404"));
		setting.setOpacity(0.4);
		SETTING.setFont(buttonFont);
		SETTING.setFill(scene.getFill());
		double seW = SETTING.getBoundsInLocal().getWidth();
		double seH = SETTING.getBoundsInLocal().getHeight();
		SETTING.setX(setting.getX() + setting.getWidth() / 2 - seW / 2);
		SETTING.setY(setting.getY() + setting.getHeight() / 2 + seH / 2 - 5);
		setting.setDisable(true);
		setting.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				settingScreen.setVisible(true);
				options.setVisible(true);
				sb.setVisible(true);
				lRec.setVisible(true);
				rRec.setVisible(true);
				for(int i = 0; i < optionsGroup.getChildren().size(); i++)optionsGroup.getChildren().get(i).setVisible(true);
			}

		});
		SETTING.setDisable(true);
		SETTING.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				settingScreen.setVisible(true);
				options.setVisible(true);
				sb.setVisible(true);
				lRec.setVisible(true);
				rRec.setVisible(true);
				for(int i = 0; i < optionsGroup.getChildren().size(); i++)optionsGroup.getChildren().get(i).setVisible(true);
			}

		});		
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case RIGHT:
					System.out.println("Right");
					break;
				case DOWN:
					System.out.println("Down");
					break;
				case LEFT:
					System.out.println("Left");
					break;
				case UP:
					System.out.println("Up");
					break;
				case P:
					System.out.println("Pause");
					break;
				default:
					break;
				}
			}
		});
		stage.setScene(scene);
		stage.setTitle("!!!-------------------------------WELCOME TO TETRIS-------------------------------!!!");
		stage.sizeToScene();
		stage.setResizable(false);
		stage.show();
		stage.setOnCloseRequest(e -> {
			System.exit(0);
		});
	}
	public static void main(String[] args) {
		launch(args);
	}
	
	public static void moveOnKeyPress(Scene scene) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case RIGHT:
					System.out.println("Right");
					break;
				case DOWN:
					System.out.println("Down");
					break;
				case LEFT:
					System.out.println("Left");
					break;
				case UP:
					System.out.println("Up");
					break;
				case P:
					System.out.println("Pause");
					break;
				default:
					break;
				}
			}
		});
	}
}
