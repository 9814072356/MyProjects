package application;

//import java.util.LinkedList;
//import java.util.Timer;
//import java.util.TimerTask;
//
//import javafx.animation.AnimationTimer;
//import javafx.application.Application;
//import javafx.application.Platform;
//import javafx.scene.Node;
//import javafx.scene.Scene;
//import javafx.scene.control.ProgressIndicator;
//import javafx.scene.layout.Pane;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Rectangle;
//import javafx.stage.Stage;
//
//public class test extends Application{
//
//	public Pane group = new Pane();
//	public Rectangle tiles;
////	public Timer fade;
////	public TimerTask task;
//	public AnimationTimer execute;
//	public ProgressIndicator p;
//	public double pr= 0;
//
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		launch(args);
//	}
//
//	@Override
//	public void start(Stage stage) throws Exception {
//		// TODO Auto-generated method stub
//		LinkedList<Integer> list = new LinkedList<Integer>();
//		p = new ProgressIndicator();
//		p.setProgress(pr);
//		list.add(1);
//		list.add(2);
//		list.add(3);
//		list.add(4);
//		list.add(5);
//		System.out.println(list.getFirst());
//		list.removeFirst();
//		System.out.println(list.getFirst());
//		int tilesX = 0;
//		int tilesY = 0;
//		for(int i = 0; i < 12; i++) {
//			tiles = new Rectangle(50, 50);
//			tiles.setX(tilesX);
//			tiles.setFill(Color.RED);
//			tiles.setY(tilesY);
//			tilesX += 60;
//			group.getChildren().addAll(tiles);
//		}
//		
//		group.getChildren().addAll(p);
//		Scene sc = new Scene(group, 800, 800);
//		stage.setScene(sc);
//		stage.show();
//		stage.setOnCloseRequest(e ->{
//			System.exit(0);
//		});
//		fadeEffect(group);
//		
//	}
//	public double op = 1.0;
//	public void fadeEffect(Pane group) {
//		LinkedList<Node> toRemove = new LinkedList<Node>();
//		for(Node a : group.getChildren()) {
//			toRemove.add(a);
//		}
//		
//		execute = new AnimationTimer() {
//
//			@Override
//			public void handle(long now) {
//				// TODO Auto-generated method stub
//				if(group.getChildren().size() == 0) {
//					execute.stop();
//					System.exit(0);
//				}else if(((Rectangle)group.getChildren().get(0)).getOpacity() <= 0.0) {
//					((ProgressIndicator) group.getChildren().get(group.getChildren().size() - 1)).setProgress(pr + 1 / 5);
//					Rectangle rmv = (Rectangle)toRemove.get(0);
//					group.getChildren().remove(rmv);
//					op = 1.0;
//					System.out.println(group.getChildren().size());
//					execute.stop();
//					fadeEffect(group);
//
//				}else {
//					op -= 0.01;
//					((Rectangle)group.getChildren().get(0)).setOpacity(op);
//					//				((Rectangle)group.getChildren().get(0)).setWidth(((Rectangle)group.getChildren().get(0)).getWidth() - 2.2);
//					//				((Rectangle)group.getChildren().get(0)).setHeight(((Rectangle)group.getChildren().get(0)).getHeight() - 2.2);
//					//				((Rectangle)group.getChildren().get(0)).setX(((Rectangle)group.getChildren().get(0)).getX() - 10);
//					//				((Rectangle)group.getChildren().get(0)).setY(((Rectangle)group.getChildren().get(0)).getY() + 200);							
//				}
//			}
//			
//		};
//		execute.start();
//		
////		fade = new Timer();
////		task = new TimerTask() {
////
////			@Override
////			public void run() {
////				// TODO Auto-generated method stub
////				Platform.runLater(new Runnable() {
////
////					@Override
////					public void run() {
////						// TODO Auto-generated method stub
////						if(group.getChildren().size() == 0) {
////							fade.cancel();
////							System.exit(0);
////						}else if(((Rectangle)group.getChildren().get(0)).getOpacity() <= 0.0) {
////							Rectangle rmv = (Rectangle)toRemove.get(0);
////							group.getChildren().remove(rmv);
////							op = 1.0;
////							System.out.println(group.getChildren().size());
////							fade.cancel();
////							fadeEffect(group);
////							
////						}else {
////							op -= 0.1;
////							((Rectangle)group.getChildren().get(0)).setOpacity(op);
//////							((Rectangle)group.getChildren().get(0)).setWidth(((Rectangle)group.getChildren().get(0)).getWidth() - 2.2);
//////							((Rectangle)group.getChildren().get(0)).setHeight(((Rectangle)group.getChildren().get(0)).getHeight() - 2.2);
//////							((Rectangle)group.getChildren().get(0)).setX(((Rectangle)group.getChildren().get(0)).getX() - 10);
//////							((Rectangle)group.getChildren().get(0)).setY(((Rectangle)group.getChildren().get(0)).getY() + 200);							
////						}
////					}
////					
////				});
////			}
////			
////		};
////		fade.schedule(task, 0, 5);
//		
//	}
//	
//	public Pane getGroup() {
//		return group;
//	}
//
//}
//import java.util.Random;
//import javafx.animation.AnimationTimer;
//import javafx.application.Application;
//import javafx.scene.Group;
//import javafx.scene.Node;
//import javafx.scene.Scene;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Rectangle;
//import javafx.stage.Stage;
//
//public class test extends Application {
//
//    private static final int STAR_COUNT = 20000;
//
//    private final Rectangle[] nodes = new Rectangle[STAR_COUNT];
//    private final double[] angles = new double[STAR_COUNT];
//    private final long[] start = new long[STAR_COUNT];
//
//    private final Random random = new Random();
//
//    @Override
//    public void start(final Stage primaryStage) {
//        for (int i=0; i<STAR_COUNT; i++) {
//            nodes[i] = new Rectangle(1, 1, Color.WHITE);
//            angles[i] = 2.0 * Math.PI * random.nextDouble();
//            start[i] = random.nextInt(2000000000);
//        }
//        final Scene scene = new Scene(new Group(nodes), 800, 600, Color.BLACK);
//        primaryStage.setScene(scene);
//        primaryStage.show();
//
//        new AnimationTimer() {
//            @Override
//            public void handle(long now) {
//                final double width = 0.5 * primaryStage.getWidth();
//                final double height = 0.5 * primaryStage.getHeight();
//                final double radius = Math.sqrt(2) * Math.max(width, height);
//                for (int i=0; i<STAR_COUNT; i++) {
//                    final Node node = nodes[i];
//                    final double angle = angles[i];
//                    final long t = (now - start[i]) % 2000000000;
//                    final double d = t * radius / 2000000000.0;
//                    node.setTranslateX(Math.cos(angle) * d + width);
//                    node.setTranslateY(Math.sin(angle) * d + height);
//                }
//            }
//        }.start();
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//
//}

//import javafx.application.Application;
//import javafx.beans.value.ChangeListener;
//import javafx.beans.value.ObservableValue;
//import javafx.geometry.Insets;
//import javafx.geometry.Orientation;
//import javafx.scene.Group;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.ListView;
//import javafx.scene.control.ScrollBar;
//import javafx.scene.effect.DropShadow;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.Background;
//import javafx.scene.layout.BackgroundFill;
//import javafx.scene.layout.CornerRadii;
//import javafx.scene.layout.VBox;
//import javafx.scene.paint.Color;
//import javafx.stage.Stage;
//
//public class test extends Application {
// 
//    final ScrollBar sc = new ScrollBar();
//    final Image[] images = new Image[5];
//    final ImageView[] pics = new ImageView[5];
//    final VBox vb = new VBox();
//    DropShadow shadow = new DropShadow();
//    ListView ls = new ListView();
// 
//    @Override
//    public void start(Stage stage) {
//        Group root = new Group();
//        Scene scene = new Scene(root, 300, 300);
//        scene.setFill(Color.BLACK);
//        stage.setScene(scene);
//        stage.setTitle("Scrollbar");
//        root.getChildren().addAll(vb, sc, ls);
//        scene.setFill(Color.WHITE);
// 
//        shadow.setColor(Color.YELLOW);
//        shadow.setOffsetX(2);
//        shadow.setOffsetY(2);
// 
//        vb.setLayoutX(5);
//        vb.setSpacing(10);
//        vb.setBackground(new Background(new BackgroundFill(Color.WHITE,CornerRadii.EMPTY,Insets.EMPTY)));
// 
//        sc.setLayoutX(scene.getWidth()-sc.getWidth());
//        sc.setMin(0);
//        sc.setOrientation(Orientation.VERTICAL);
//        sc.setPrefHeight(180);
//        sc.setMax(1000);
//        
//        Button btn = new Button();
//        ls.setLayoutX(10);
//        ls.setLayoutY(10);
//        ls.setPrefSize(100, 100);
//        ls.getItems().add(btn);
// 
//        for (int i = 0; i < 5; i++) {
//            final Image image = images[i] =
//                new Image(getClass().getResourceAsStream(
//                    "fw" +(i+1)+ ".png")
//                );
//            final ImageView pic = pics[i] =
//                new ImageView(images[i]);
//            pic.setEffect(shadow);
//            vb.getChildren().add(pics[i]);
//        }
// 
//        sc.valueProperty().addListener(new ChangeListener<Number>() {
//            public void changed(ObservableValue<? extends Number> ov,
//                Number old_val, Number new_val) {
//                    vb.setLayoutY(-new_val.doubleValue());
//                    ((Button)ls.getItems().get(0)).setLayoutY(-new_val.doubleValue());
//            }
//        });
// 
//        stage.show();
//    }
// 
//    public static void main(String[] args) {
//        launch(args);
//    }
//}
//import java.util.ArrayList;
//import javafx.application.Application;
//import javafx.beans.value.ChangeListener;
//import javafx.beans.value.ObservableValue;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.ListView;
//import javafx.scene.control.SelectionMode;
//import javafx.scene.control.TextArea;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
// 
//public class test extends Application
//{
//    // Declaring the TextArea for Logging
//    TextArea logging;
//     
//    public static void main(String[] args) 
//    {
//        Application.launch(args);
//    }
//     
//    @Override
//    public void start(Stage stage) 
//    {
//        // Create the TextArea
//        logging = new TextArea();
//        logging.setMaxWidth(350);
//        logging.setMaxHeight(350);
//         
//        // Create the Label
//        Label monthsLbl = new Label("Select Month: ");
//        ListView l = new ListView();
//        l.getItems().addAll(createMonthList());
//        l.setPrefSize(100, 100);
//         
//        // Create the ListView
//        final ListView<String> months = new ListView<>();
//        // Add the items to the List 
//        months.getItems().addAll(createMonthList());
//        // Set the size of the ListView
//        months.setPrefSize(120, 120);
//        // Enable multiple selection
//        months.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
//         
//        // Update the message Label when the selected item changes
//        months.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>()
//        {
//            public void changed(ObservableValue<? extends String> ov,
//                    final String oldvalue, final String newvalue) 
//            {
//                monthChanged(ov, oldvalue, newvalue);
//        }});
// 
//        // Create the HBox for the Months
//        HBox monthsSelection = new HBox();
//        // Set Spacing to 10 pixels
//        monthsSelection.setSpacing(10);
//        // Add the Label and the List to the HBox
//        monthsSelection.getChildren().addAll(monthsLbl, months);
//         
//        // Create some buttons to assist in selection
//        Button selectAllBtn = new Button("Select All");
//        Button clearAllBtn = new Button("Clear All");
//        Button selectFirstBtn = new Button("Select First");
//        Button selectLastBtn = new Button("Select Last");
//        Button selectNextBtn = new Button("Select Next");
//        Button selectPreviousBtn = new Button("Select Previous");
// 
//        // Let all buttons expand as needed
//        selectAllBtn.setMaxWidth(Double.MAX_VALUE);
//        clearAllBtn.setMaxWidth(Double.MAX_VALUE);
//        selectFirstBtn.setMaxWidth(Double.MAX_VALUE);
//        selectLastBtn.setMaxWidth(Double.MAX_VALUE);
//        selectNextBtn.setMaxWidth(Double.MAX_VALUE);
//        selectPreviousBtn.setMaxWidth(Double.MAX_VALUE);
//         
//        // Update the TextArea when all items will be selected
//        selectAllBtn.setOnAction(new EventHandler<ActionEvent>() 
//        {
//            @Override public void handle(ActionEvent e) 
//            {
//                months.getSelectionModel().selectAll();
//            }
//        }); 
//                 
//        // Update the TextArea when the selection will be deleted
//        clearAllBtn.setOnAction(new EventHandler<ActionEvent>() 
//        {
//            @Override public void handle(ActionEvent e) 
//            {
//                months.getSelectionModel().clearSelection();
//            }
//        }); 
//         
//        // Update the TextArea when the first item will be selected
//        selectFirstBtn.setOnAction(new EventHandler<ActionEvent>() 
//        {
//            @Override public void handle(ActionEvent e) 
//            {
//                months.getSelectionModel().selectFirst();
//            }
//        }); 
//         
//        // Update the TextArea when the last item will be selected
//        selectLastBtn.setOnAction(new EventHandler<ActionEvent>() 
//        {
//            @Override public void handle(ActionEvent e) 
//            {
//                months.getSelectionModel().selectLast();
//            }
//        }); 
//         
//        // Update the TextArea when the next item will be selected
//        selectNextBtn.setOnAction(new EventHandler<ActionEvent>() 
//        {
//            @Override public void handle(ActionEvent e) 
//            {
//                months.getSelectionModel().selectNext();
//            }
//        }); 
//                 
//        // Update the TextArea when the previous item will be selected
//        selectPreviousBtn.setOnAction(new EventHandler<ActionEvent>() 
//        {
//            @Override public void handle(ActionEvent e) 
//            {
//                months.getSelectionModel().selectPrevious();
//            }
//        }); 
//         
//        // Create the VBox for the Buttons 
//        VBox buttons = new VBox();
//        // Add the Buttons to the VBox
//        buttons.getChildren().addAll(selectFirstBtn,selectLastBtn,selectNextBtn,
//                selectPreviousBtn,selectAllBtn,clearAllBtn);
//         
//        // Create the Selection HBox
//        HBox selection = new HBox();
//        // Set Spacing to 10 pixels
//        selection.setSpacing(10);
//        // Add the List and the Buttons to the HBox
//        selection.getChildren().addAll(monthsSelection,buttons);
//         
//        // Create the GridPane
//        GridPane pane = new GridPane();
//        // Set the horizontal and vertical gaps between children
//        pane.setHgap(10);       
//        pane.setVgap(5);        
//        // Add the HBox to the GridPane at position 0
//        pane.addColumn(0, selection);
//        // Add the Buttons to the GridPane at position 1
//        pane.addColumn(1, buttons);
//         
//        // Create the VBox
//        VBox root = new VBox();
//        // Set Spacing to 10 pixels
//        root.setSpacing(10);
//        // Add the GridPane and the TextArea to the VBox
//        root.getChildren().addAll(pane,logging,l);
//         
//        // Set the Style-properties of the VBox
//        root.setStyle("-fx-padding: 10;" +
//                "-fx-border-style: solid inside;" +
//                "-fx-border-width: 2;" +
//                "-fx-border-insets: 5;" +
//                "-fx-border-radius: 5;" +
//                "-fx-border-color: blue;");
// 
//        // Create the Scene
//        Scene scene = new Scene(root);
//        // Add the Scene to the Stage
//        stage.setScene(scene);
//        // Set the Title
//        stage.setTitle("A ListView Example with a Selection Model");
//        // Display the Stage
//        stage.show();       
//    }
// 
//    // Helper-Method to create an ArrayList of Persons
//    private ArrayList<String> createMonthList()
//    {
//        ArrayList<String> months = new ArrayList<String>();
//         
//        months.add("January");
//        months.add("February");
//        months.add("March");
//        months.add("April");
//        months.add("May");
//        months.add("June");
//        months.add("July");
//        months.add("August");
//        months.add("September");
//        months.add("October");
//        months.add("November");
//        months.add("December");
//         
//        return months;
//    }
// 
//    // Method to display the Data, which has been changed
//    public void monthChanged(ObservableValue<? extends String> observable,String oldValue,String newValue) 
//    {
//        String oldText = oldValue == null ? "null" : oldValue.toString();
//        String newText = newValue == null ? "null" : newValue.toString();
//         
//        logging.appendText("Itemchanged: old = " + oldText + ", new = " + newText + "\n");
//    }
//     
//}

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

/** Example of playing all audio files in a given directory. */
public class test extends Application {
	final Label currentlyPlaying = new Label();
	final ProgressBar progress = new ProgressBar();
	private ChangeListener<Duration> progressChangeListener;

	public static void main(String[] args) throws Exception { launch(args); }

	public void start(final Stage stage) throws Exception {
		stage.setTitle("Simple Audio Player");

		final StackPane layout = new StackPane();

		// determine the source directory for the playlist (either the first parameter to the program or a 
		final List<String> params = getParameters().getRaw();
		final File dir = (params.size() > 0)
				? new File(params.get(0))
						: new File("C:\\Users\\kyanh\\Desktop\\Java Assignments\\Tetris");
				if (!dir.exists() && dir.isDirectory()) {
					System.out.println("Cannot find audio source directory: " + dir);
				}

				// create some media players.
				final List<MediaPlayer> players = new ArrayList<MediaPlayer>();
				for (String file : dir.list(new FilenameFilter() {
					@Override public boolean accept(File dir, String name) {
						return name.endsWith(".mp3");
					}
				})) players.add(createPlayer("file:///" + (dir + "\\" + file).replace("\\", "/").replaceAll(" ", "%20")));
				if (players.isEmpty()) {
					System.out.println("No audio found in " + dir);
					return;
				}

				// create a view to show the mediaplayers.
				final MediaView mediaView = new MediaView(players.get(0));
				final Button skip = new Button("Skip");
				final Button play = new Button("Pause");

				// play each audio file in turn.
				for (int i = 0; i < players.size(); i++) {
					final MediaPlayer player     = players.get(i);
					final MediaPlayer nextPlayer = players.get((i + 1) % players.size());
					player.setOnEndOfMedia(new Runnable() {
						@Override public void run() {
							player.currentTimeProperty().removeListener(progressChangeListener);
							mediaView.setMediaPlayer(nextPlayer);
							nextPlayer.play();
						}
					});
				}

				// allow the user to skip a track.
				skip.setOnAction(new EventHandler<ActionEvent>() {
					@Override public void handle(ActionEvent actionEvent) {
						final MediaPlayer curPlayer = mediaView.getMediaPlayer();
						MediaPlayer nextPlayer = players.get((players.indexOf(curPlayer) + 1) % players.size());
						mediaView.setMediaPlayer(nextPlayer);
						curPlayer.currentTimeProperty().removeListener(progressChangeListener);
						curPlayer.stop();
						nextPlayer.play();
					}
				});

				// allow the user to play or pause a track.
				play.setOnAction(new EventHandler<ActionEvent>() {
					@Override public void handle(ActionEvent actionEvent) {
						if ("Pause".equals(play.getText())) {
							mediaView.getMediaPlayer().pause();
							play.setText("Play");
						} else {
							mediaView.getMediaPlayer().play();
							play.setText("Pause");
						}
					}
				});

				// display the name of the currently playing track.
				mediaView.mediaPlayerProperty().addListener(new ChangeListener<MediaPlayer>() {
					@Override public void changed(ObservableValue<? extends MediaPlayer> observableValue, MediaPlayer oldPlayer, MediaPlayer newPlayer) {
						setCurrentlyPlaying(newPlayer);
					}
				});

				// start playing the first track.
				mediaView.setMediaPlayer(players.get(0));
				mediaView.getMediaPlayer().play();
				setCurrentlyPlaying(mediaView.getMediaPlayer());

				// silly invisible button used as a template to get the actual preferred size of the Pause button.
				Button invisiblePause = new Button("Pause");
				invisiblePause.setVisible(false);
				play.prefHeightProperty().bind(invisiblePause.heightProperty());
				play.prefWidthProperty().bind(invisiblePause.widthProperty());

				// layout the scene.
				layout.setStyle("-fx-background-color: cornsilk; -fx-font-size: 20; -fx-padding: 20; -fx-alignment: center;");
				layout.getChildren().addAll(
						invisiblePause,
						VBoxBuilder.create().spacing(10).children(
								currentlyPlaying,
								HBoxBuilder.create().spacing(10).alignment(Pos.CENTER).children(skip, play, progress, mediaView).build()
								).build()
						);
				progress.setMaxWidth(Double.MAX_VALUE);
				HBox.setHgrow(progress, Priority.ALWAYS);
				Scene scene = new Scene(layout, 600, 120);
				stage.setScene(scene);
				stage.show();
	}

	/** sets the currently playing label to the label of the new media player and updates the progress monitor. */
	private void setCurrentlyPlaying(final MediaPlayer newPlayer) {
		progress.setProgress(0);
		progressChangeListener = new ChangeListener<Duration>() {
			@Override public void changed(ObservableValue<? extends Duration> observableValue, Duration oldValue, Duration newValue) {
				progress.setProgress(1.0 * newPlayer.getCurrentTime().toMillis() / newPlayer.getTotalDuration().toMillis());
			}
		};
		newPlayer.currentTimeProperty().addListener(progressChangeListener);

		String source = newPlayer.getMedia().getSource();
		source = source.substring(0, source.length() - ".mp3".length());
		source = source.substring(source.lastIndexOf("/") + 1).replaceAll("%20", " ");
		currentlyPlaying.setText("Now Playing: " + source);
	}

	/** @return a MediaPlayer for the given source which will report any errors it encounters */
	private MediaPlayer createPlayer(String aMediaSrc) {
		final MediaPlayer player = new MediaPlayer(new Media(aMediaSrc));
		player.setOnError(new Runnable() {
			@Override public void run() {
				System.out.println("Media error occurred: " + player.getError());
			}
		});
		return player;
	}
}