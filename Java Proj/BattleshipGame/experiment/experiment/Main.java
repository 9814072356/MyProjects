package experiment;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    final int size = 10;
    final int boardWidth = 400;
    int cellSpan = 40;
    final int verticalSpan = 500;
    final int horizontalSpan = 1000;
    int xPos = 50;
    int yPos = 50;

    public Main() {
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        GameInterface ui = new GameInterface();
        ui.GameInit(primaryStage);
    }
}

