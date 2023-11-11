import GUI.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
//		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
//		double screenWidth = primaryScreenBounds.getWidth();
//		double screenHeight = primaryScreenBounds.getHeight();
////		System.out.println("Screen width: " + screenWidth + "| Screen height: " + screenHeight);
//
//		LinearGradient cycleGrad = new LinearGradient(100, // start X
//				100, // start Y
//				120, // end X
//				120, // end Y
//				false, // proportional
//				CycleMethod.REFLECT, // cycleMethod
//				new Stop(0f, Color.web("#FFFFFF" )), new Stop(1.0f, Color.web("#F4FFFF")));
		LinearGradient cycleGrad2 = new LinearGradient(
				0,
				0,
				580,
				350,
				false,
				CycleMethod.REFLECT,
				new Stop(0f,Color.web("#565656")),new Stop (0.20f,Color.web("#444444")),
				new Stop(0.40,Color.web("#333333")),new Stop(0.60f,Color.web("#444444")),
				new Stop(0.80f,Color.web("#555555")),new Stop(1.0f,Color.web("#565656")));

		StartPage sP = new StartPage();
		Scene scene1 = new Scene(sP.getGroup(), 600, 400);
		sP.set(scene1,primaryStage);
		scene1.setFill(cycleGrad2);

		primaryStage.setTitle("Battleship Game");
		primaryStage.setScene(scene1);
		primaryStage.sizeToScene();
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
