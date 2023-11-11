package GUI;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class login_Host {
    private static Group layout;
    private Text txt1;
    private Text txt2;
    private Text txt3;
    private static TextField inputName;
    private Circle host;
    private Circle guest;
    private Text info;

    public void set(Stage stage) {


        txt1 = new Text("HOST");
        txt2 = new Text("GUEST");
        txt3 = new Text("Name");
        inputName = new TextField();
        host = new Circle();
        guest = new Circle();
        info = new Text("*Input name, or it will be Player1. Boring right ? :)");
        layout = new Group();

        txt1.setTranslateX(70);
        txt1.setTranslateY(75);
        txt1.setFont(Font.font("Copperplate Gothic Light", 30));
        txt1.setFill(Color.web("#D1D0CE"));

        txt2.setTranslateX(335);
        txt2.setTranslateY(75);
        txt2.setFont(Font.font("Copperplate Gothic Light", 30));
        txt2.setFill(Color.web("#D1D0CE"));

        txt3.setTranslateX(100);
        txt3.setTranslateY(150);
        txt3.setFont(Font.font("Microsoft YaHei UI Light", 25));
        txt3.setFill(Color.web("#FEFCFF"));

        inputName.setPrefSize(321, 25);
        inputName.setFont(Font.font("Copperplate Gothic Light", 15));
        inputName.setTranslateX(txt3.getTranslateX()+80);
        inputName.setTranslateY(txt3.getTranslateY()-20);
        inputName.setStyle("-fx-focus-color: transparent;"+
                "-fx-faint-focus-color: transparent");


        guest.setRadius(10);
        guest.setCenterX(txt2.getTranslateX()+txt2.getLayoutBounds().getWidth()+guest.getRadius()*2);
        guest.setCenterY(txt2.getTranslateY()-10);
        guest.setFill(Color.WHITE);
        guest.setStroke(Color.TRANSPARENT);
        guest.setStrokeWidth(1);

        host.setRadius(10);
        host.setCenterX(txt1.getTranslateX()+txt1.getLayoutBounds().getWidth()+host.getRadius()*2);
        host.setCenterY(txt1.getTranslateY()-10);
        host.setFill(Color.LAWNGREEN);
        host.setStroke(Color.TRANSPARENT);
        host.setStrokeWidth(1);

        info.setTranslateX(inputName.getTranslateX());
        info.setTranslateY(inputName.getTranslateY()+inputName.getPrefHeight()+25);
        info.setFill(Color.web("#FDD017"));
        info.setFont(Font.font("Impact", 15));

        layout.getChildren().addAll(txt1,txt2,txt3,inputName,guest,host,info);

        Thread t = new Thread(new Netzwerk.Server());
        t.start();
        Host.connectToServer();
        changeScreen(stage);
    }

    public void changeScreen(Stage stage) {
        getLayout().setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent e)
            {
                if (e.getCode().equals(KeyCode.ENTER)){
                    LinearGradient cycleGrad = new LinearGradient(100, // start X
                            100, // start Y
                            120, // end X
                            120, // end Y
                            false, // proportional
                            CycleMethod.REFLECT, // cycleMethod
                            new Stop(0f, Color.web("#FFFFFF" )), new Stop(1.0f, Color.web("#F4FFFF")));
                    Host_preGame prepHost = new Host_preGame();
                    prepHost.set(stage);
                    Scene scene4 = new Scene(Host_preGame.getRoot(), 500, 485);
                    scene4.setFill(cycleGrad);
                    getInfo().setText("");
                    Host.getHostName().setText(getinputName().getText().isEmpty() ? "Player 1" : getinputName().getText());
                    Guest.getHostName().setText(getinputName().getText().isEmpty() ? "Player 1" : getinputName().getText());
                    stage.setScene(scene4);
                }
            }
        });
    }

    public Text getInfo() {
        return info;
    }

    public static TextField getinputName() {
        return inputName;
    }

    public static Group getLayout() {
        return layout;
    }
}
