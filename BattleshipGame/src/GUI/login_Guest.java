package GUI;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class login_Guest {

	private boolean connected;

	private static Group layout;
	private static Text txt1;
	private static Text txt2;
	private static Text txt3;
	private static TextField inputName;
	private static Circle host;
	private static Circle guest;
	private static Text info;
	private static Button connect;

	public void set(Stage stage) {
		txt1 = new Text("HOST");
		txt2 = new Text("GUEST");
		txt3 = new Text("Name");
		inputName = new TextField();
		host = new Circle();
		guest = new Circle();
		info = new Text("*Input name, or it will be Player2. Boring right ? :)");
		connect = new Button("Connect");
		layout = new Group();

		txt1.setTranslateX(70);
		txt1.setTranslateY(75);
		txt1.setFont(Font.font("Copperplate Gothic Light", 30));
		txt1.setFill(Color.web("#FEFCFF"));

		txt2.setTranslateX(335);
		txt2.setTranslateY(75);
		txt2.setFont(Font.font("Copperplate Gothic Light", 30));
		txt2.setFill(Color.web("#FEFCFF"));

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
		guest.setFill(Color.LAWNGREEN);
		guest.setStroke(Color.TRANSPARENT);
		guest.setStrokeWidth(1);

		host.setRadius(10);
		host.setCenterX(txt1.getTranslateX()+txt1.getLayoutBounds().getWidth()+host.getRadius()*2);
		host.setCenterY(txt1.getTranslateY()-10);
		host.setFill(Color.WHITE);
		host.setStroke(Color.TRANSPARENT);
		host.setStrokeWidth(1);

		info.setTranslateX(inputName.getTranslateX());
		info.setTranslateY(inputName.getTranslateY()+inputName.getPrefHeight()+25);
		info.setFill(Color.web("#FDD017"));
		info.setFont(Font.font("Impact", 15));

		connect.setPrefSize(200, 40);
		connect.setTranslateX(info.getTranslateX());
		connect.setTranslateY(info.getTranslateY()+30);
		connect.setFont(Font.font("Microsoft YaHei UI Light", 20));
		connect.setTextFill(Color.web("#FFFFFF"));
		connect.setStyle("-fx-background-color: transparent;"+
				"-fx-border-width: 2;"+
				"-fx-border-radius: 5;" +
				"-fx-border-color: #0F9D58;");
			
		connect.setOnAction(e -> {
			connected =  Guest.connectToServer();
			if (connected){	
				connect.setText("Verbunden");				
				changeScreen(stage);
			}else {
				connect.setText("Keine Verbindung");
			}
		});

		layout.getChildren().addAll(txt1,txt2,txt3,inputName,guest,host,info,connect);
		

	}

	public void changeScreen(Stage stage) {
		LinearGradient cycleGrad = new LinearGradient(100, // start X
				100, // start Y
				120, // end X
				120, // end Y
				false, // proportional
				CycleMethod.REFLECT, // cycleMethod
				new Stop(0f, Color.web("#FFFFFF" )), new Stop(1.0f, Color.web("#F4FFFF")));
		Guest_preGame prepGuest = new Guest_preGame();
		prepGuest.set(stage);
		Scene scene5 = new Scene(Guest_preGame.getRoot(), 500, 485);
		scene5.setFill(cycleGrad);
		getInfo().setText("");
		Host.getGuestName().setText(getinputName().getText().isEmpty() ? "Player 2" : getinputName().getText());
		Guest.getGuestName().setText(getinputName().getText().isEmpty() ? "Player 2" : getinputName().getText());
		stage.setScene(scene5);
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
