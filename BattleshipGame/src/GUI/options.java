package GUI;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
public class options {
	static int turn;
	public static int set() {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setMinWidth(250);
		
		Button option1 = new Button("Go first");
		Button option2 = new Button("Go second");
		
		option1.setOnAction(e->{
			turn = 1;
			window.close();
		});
		option2.setOnAction(e->{
			turn = 0;
			window.close();
		});
		HBox root = new HBox(15);
		root.getChildren().addAll(option1,option2);
		root.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(root);
		window.setScene(scene);
		window.showAndWait();
		
		return turn;
	}
}
