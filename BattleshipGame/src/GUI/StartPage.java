package GUI;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
public class StartPage {
	public static Group group = new Group();
	public Text head = new Text("Battleship Game");
	public Text or = new Text("or");
	public Button host = new Button("HOST");
	public Button guest = new Button("GUEST");

	public StartPage() {
		group.getChildren().addAll(head,guest,host,or);
	}

	public void set(Scene scene, Stage stage) {
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
		head.setFill(Color.web("#FFA62F"));
		Font font = Font.font("Candara Light", 50);
		head.setFont(font);
		final double headWidth = head.getLayoutBounds().getWidth();
		head.setTranslateX((scene.getWidth() - headWidth)/2);
		head.setTranslateY(140);

		host.setStyle("-fx-background-color: #38ACEC;"+
				"-fx-border-width: 2;" +
				"-fx-border-radius: 5;" +
				"-fx-text-fill: #FBFCFC;");
		host.setFont(Font.font("Bell MT", 25));
		host.setPrefSize(120,30);
		host.setTranslateX(head.getTranslateX());
		host.setTranslateY(head.getTranslateY()+20);

		guest.setStyle("-fx-background-color: #F62217;"+
				"-fx-border-width: 2;" +
				"-fx-border-radius: 5;" +
				"-fx-text-fill: #FBFCFC;");
		guest.setFont(Font.font("Bell MT", 25));
		guest.setPrefSize(120,30);
		guest.setTranslateX(head.getTranslateX() + headWidth - guest.getPrefWidth());
		guest.setTranslateY(head.getTranslateY()+20);

		or.setFont(Font.font("Leelawadee UI Semilight", 25));
		double orWidth = or.getLayoutBounds().getWidth();
		double orHeight = or.getLayoutBounds().getWidth();
		or.setFill(Color.web("#17c7d7"));
		or.setTranslateX((scene.getWidth() - orWidth)/2);
		or.setTranslateY(host.getTranslateY() + (host.getPrefHeight()+orHeight)/2);

		host.setOnAction(e -> {
			login_Host host = new login_Host();
			host.set(stage);
			Scene scene2 = new Scene(login_Host.getLayout(), 580, 350);
			scene2.setFill(cycleGrad2);
			stage.setScene(scene2);
		});
		guest.setOnAction(e -> {
			login_Guest guest = new login_Guest();
			guest.set(stage);
			Scene scene3 = new Scene(login_Guest.getLayout(), 580, 350);
			scene3.setFill(cycleGrad2);
			stage.setScene(scene3);
		});
	}

	public Button getHostBTN() {
		return host;
	}

	public Button getGuestBTN() {
		return guest;
	}

	public Group getGroup() {
		return group;
	}

}
