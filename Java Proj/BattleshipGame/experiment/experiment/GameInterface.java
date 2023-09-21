package experiment;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameInterface {
    final int size = 10;
    private Group group = new Group();
    private Scene scene;
    final int boardWidth = 300;
    int cellSpan = 30;
    final int WinHeight = 500;
    final int WinWidth = 800;
    int xPos;
    int yPos;
    Board ally;
    Board enemy;

    public GameInterface() {
        this.xPos = (400 - this.cellSpan * 10) / 2;
        this.yPos = (400 - this.cellSpan * 10) / 2;
        this.ally = new Board(10);
        this.enemy = new Board(10);
    }

    public void GameInit(Stage stage) throws Exception {
        this.ally.drawBoard(this.group, this.xPos, this.yPos, 10, this.cellSpan);
        this.enemy.drawBoard(this.group, 450, (400 - this.cellSpan * 10) / 2, 10, this.cellSpan);
        this.ally.drawUI(this.group, this.xPos, this.yPos, this.cellSpan, 10, "Ally Board");
        this.enemy.drawUI(this.group, 450, (400 - this.cellSpan * 10) / 2, this.cellSpan, 10, "Enemy Board");
        this.scene = new Scene(this.group, 800.0D, 500.0D);
        this.ally.hover(this.group, this.xPos, this.yPos, 10, this.cellSpan);
        stage.setScene(this.scene);
        stage.setResizable(false);
        stage.show();
    }

    public Scene getScene() {
        return this.scene;
    }

    public int getWinWidth() {
        return 500;
    }

    public int getWinHeight() {
        return 800;
    }

    public Group getGroup() {
        return this.group;
    }
}

