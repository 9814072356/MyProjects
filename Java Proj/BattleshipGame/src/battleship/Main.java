package battleship;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        GameUI gameUI = new GameUI();
        gameUI.init();
        Scene scene = new Scene(gameUI.getRoot());
        primaryStage.setTitle("Battleship Game");
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
