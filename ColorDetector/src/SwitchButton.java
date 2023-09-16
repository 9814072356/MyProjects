import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class SwitchButton extends StackPane {
    private final Rectangle back = new Rectangle(50, 10, Color.RED);
    private final Button button = new Button();
    private String buttonStyleOff = "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 0.2, 0.0, 0.0, 2); -fx-background-color: WHITE;";
    private String buttonStyleOn = "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 0.2, 0.0, 0.0, 2); -fx-background-color: #00893d;";
    private boolean state;
	public MediaPlayer clickPlayer;{
		 Media click = new Media(getClass().getResource("clicksound.mp3").toExternalForm());
		clickPlayer = new MediaPlayer(click);
	}

    private void init() {
        getChildren().addAll(back, button);
        setMinSize(30, 15);
        back.maxWidth(50);
        back.minWidth(50);
        back.maxHeight(20);
        back.minHeight(20);
        back.setArcHeight(back.getHeight());
        back.setArcWidth(back.getHeight());
        back.setFill(Color.valueOf("#ced5da"));
        Double r = 2.0;
        button.setShape(new Circle(r));
        setAlignment(button, Pos.CENTER_LEFT);
        button.setMaxSize(20, 20);
        button.setMinSize(20, 20);
        button.setStyle(buttonStyleOff);
    }

    public SwitchButton() {
        init();
        EventHandler<Event> click = new EventHandler<Event>() {
            @Override
            public void handle(Event e) {
            	clickPlayer.stop();
            	clickPlayer.play();
            	GamePhase g = Init.getGamePhase();
                if (state) {
                	GamePhase.getBackground().setFill(Color.web("002366"));
                	GamePhase.getLevel().setFill(Color.WHITE);
                	GamePhase.getClicks().setFill(Color.WHITE);
                	for(Node t : g.getTimeGroup().getChildren()) {
                		((Text)t).setFill(Color.WHITE);
                	}
                    button.setStyle(buttonStyleOff);
                    back.setFill(Color.valueOf("#ced5da"));
                    setAlignment(button, Pos.CENTER_LEFT);
                    state = false;
                } else {
                	GamePhase.getBackground().setFill(Color.web("fffddb"));
                	GamePhase.getLevel().setFill(Color.web("002366"));
                	GamePhase.getClicks().setFill(Color.web("002366"));
                	for(Node t : g.getTimeGroup().getChildren()) {
                		((Text)t).setFill(Color.web("002366"));
                	}
                    button.setStyle(buttonStyleOn);
                    back.setFill(Color.valueOf("#80C49E"));
                    setAlignment(button, Pos.CENTER_RIGHT);
                    state = true;
                }
            }
        };

        button.setFocusTraversable(false);
        setOnMouseClicked(click);
        button.setOnMouseClicked(click);
    }
}