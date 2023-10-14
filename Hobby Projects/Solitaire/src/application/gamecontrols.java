package application;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class gamecontrols {
	public static final double WIDTH = ingame.getWIDTH();
	public static final double HEIGHT = ingame.getHEIGHT();
	public static int maxDrawnCards = 24;
	public static int countCardsDrawn;
	public static int drawnCardsIndex;
	public static double offset = 110;
	public static Rectangle drawnCardsPack = ingame.getDrawnCardsPack();
	public static Group cardsGroup = new Group();
	public static LinkedList<Rectangle> tempList = new LinkedList<Rectangle>();
	public static String[] cardPatterns = {"C","D","H","S"};
	//	public static String[] cardValues = {"2","3","4","5","6","7","8","9","10","J","Q","K","A"};
	public static String[] cardValues = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
	public static LinkedList<card> cardsList = new LinkedList<card>();
	public static LinkedList<Rectangle> cardClicked = new LinkedList<Rectangle>();
	public static LinkedList<Rectangle> validCards = new LinkedList<Rectangle>();
	public static LinkedList<lastMove> listOfMoves = new LinkedList<lastMove>();
	public static boolean isLegal = false;
	public static Image cardSource;
	public static ImagePattern cardImages;
	public static AnimationTimer slide;
	public static AnimationTimer[] moveList = new AnimationTimer[13];
	public static AnimationTimer deal;
	public static AnimationTimer endGameAni;
	public static boolean endGame = true;
	public static Media click = new Media(gamecontrols.class.getResource("/application/clicksound.mp3").toExternalForm());
	public static MediaPlayer clickPlayer = new MediaPlayer(click);
	public static Media cardFlip = new Media(gamecontrols.class.getResource("/application/flipsound.mp3").toExternalForm());
	public static MediaPlayer cardFlipPlayer = new MediaPlayer(cardFlip);
	public static Media cardMix = new Media(gamecontrols.class.getResource("/application/mixsound.mp3").toExternalForm());
	public static MediaPlayer cardMixPlayer = new MediaPlayer(cardMix);
	public static Media victorySong = new Media(gamecontrols.class.getResource("/application/triumphanthymn.mp3").toExternalForm());
	public static MediaPlayer victorySongPlayer = new MediaPlayer(victorySong);

	public static void initiate() {
		countCardsDrawn = 0;
		drawnCardsIndex = maxDrawnCards - 1;
		for(int i = 0; i < 13; i++) {
			AnimationTimer move = null;
			moveList[i] = move;
		}
		ingame.getOpacityLevel().setValue(1);
		//				int i = 1;
		for(String s : cardValues) {
			for(String ss : cardPatterns) {
				String src = "/application/designers cards/" + s + ss + ".jpg";
				cardSource = new Image(src);
				cardImages = new ImagePattern(cardSource);			
				Rectangle a = new Rectangle(ingame.getDrawnCardsPack().getWidth(),ingame.getDrawnCardsPack().getHeight());
				a.setStroke(/* Color.DODGERBLUE */ingame.getTempBack().getStroke());
				a.setStrokeWidth(ingame.getDrawnCardsPack().getStrokeWidth());
				a.setArcWidth(15);
				a.setArcHeight(15);	
				//								addClick(a);
				//								switch(i) {
				//								case 1:
				//									a.setX(ingame.getBundle1().getX());
				//									a.setY(ingame.getBundle1().getY());
				//									break;
				//								case 2:
				//									a.setX(ingame.getBundle2().getX());
				//									a.setY(ingame.getBundle2().getY());
				//									break;
				//								case 3:
				//									a.setX(ingame.getBundle3().getX());
				//									a.setY(ingame.getBundle3().getY());
				//									break;
				//								case 4:
				//									a.setX(ingame.getBundle4().getX());
				//									a.setY(ingame.getBundle4().getY());
				//									break;
				//								}
				//								if(i != 4)i++;
				//								else i = 1;
				card c = new card(s,ss,a,cardImages,true,false,true);
				cardsList.add(c);
				cardsGroup.getChildren().add(a);
			}
		}
		shuffle(cardsGroup,cardsList);
		for(int i = 0; i < 24; i++) {
			FileInputStream input = null;
			if(ingame.getcbPath() != null) {
				try {
					input = new FileInputStream(ingame.getcbPath());
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					//					e.printStackTrace();
					System.out.println(e);
				}
			}
			cardsList.get(i).flip(false);
			((Rectangle) cardsGroup.getChildren().get(i)).setFill(input == null ? new ImagePattern(new Image("/application/cards/crown1.jpg",drawnCardsPack.getWidth() * 2,drawnCardsPack.getHeight() * 2,false,false)) : new ImagePattern(new Image(input,drawnCardsPack.getWidth() * 2,drawnCardsPack.getHeight() * 2,false,false)));
			tempList.add((Rectangle)cardsGroup.getChildren().get(i));
			addClick(((Rectangle) cardsGroup.getChildren().get(i)));
			((Rectangle)cardsGroup.getChildren().get(i)).setX(ingame.getDrawnCardsPack().getX());
			((Rectangle)cardsGroup.getChildren().get(i)).setY(ingame.getDrawnCardsPack().getY());
			//			fadeIn((Rectangle)cardsGroup.getChildren().get(i));
		}
		int index = 24;
		int amount = 1;
		while(index < cardsList.size()) {
			double X = 0 , Y = 0;
			switch(amount) {
			case 1:
				X = ingame.getRow1().getX();
				Y = ingame.getRow1().getY();
				ingame.getRow1().setVisible(false);
				break;
			case 2:
				X = ingame.getRow2().getX();
				Y = ingame.getRow2().getY();
				ingame.getRow2().setVisible(false);
				break;
			case 3:
				X = ingame.getRow3().getX();
				Y = ingame.getRow3().getY();
				ingame.getRow3().setVisible(false);
				break;
			case 4:
				X = ingame.getRow4().getX();
				Y = ingame.getRow4().getY();
				ingame.getRow4().setVisible(false);
				break;
			case 5:
				X = ingame.getRow5().getX();
				Y = ingame.getRow5().getY();
				ingame.getRow5().setVisible(false);
				break;
			case 6:
				X = ingame.getRow6().getX();
				Y = ingame.getRow6().getY();
				ingame.getRow6().setVisible(false);
				break;
			case 7:
				X = ingame.getRow7().getX();
				Y = ingame.getRow7().getY();
				ingame.getRow7().setVisible(false);
				break;
			}
			for(int i = 1; i <= amount; i++) {
				FileInputStream input = null;
				if(ingame.getcbPath() != null) {
					try {
						input = new FileInputStream(ingame.getcbPath());
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						System.out.println(e);
					}
				}
				if(i != 1)Y += 20;
				((Rectangle) cardsGroup.getChildren().get(index)).setX(X);
				((Rectangle) cardsGroup.getChildren().get(index)).setY(Y);
				if(i != amount) {
					((Rectangle) cardsGroup.getChildren().get(index))
					.setFill(input == null ? new ImagePattern(new Image("/application/cards/crown1.jpg",drawnCardsPack.getWidth() * 2,drawnCardsPack.getHeight() * 2,false,false)) 

							: new ImagePattern(new Image(input,drawnCardsPack.getWidth() *
									2,drawnCardsPack.getHeight() * 2,false,false))
							);
					cardsList.get(index).flip(false);
				}
				addClick(((Rectangle) cardsGroup.getChildren().get(index)));
				index++;
			}
			amount++;
		}
		if(!ingame.getPane().getChildren().contains(cardsGroup))ingame.getPane().getChildren().addAll(cardsGroup);

		ingame.getTime().start();
	}

	public static void flip(Rectangle front, Rectangle back) {
		ScaleTransition stHideFront = new ScaleTransition(Duration.millis(500), front);
		stHideFront.setFromX(1);
		stHideFront.setToX(0);

		ScaleTransition stShowBack = new ScaleTransition(Duration.millis(500), back);
		stShowBack.setFromX(0);
		stShowBack.setToX(1);

		stHideFront.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				//		    	ingame.getEndGameScreen().setVisible(true);
				stShowBack.play();
			}
		});


		stHideFront.play();
	}

	public static void fadeIn(Rectangle card) {
		deal = new AnimationTimer() {
			@Override
			public void handle(long now) {
				// TODO Auto-generated method stub
				if(card.getOpacity() >= 1) {
					card.setOpacity(1);
					deal.stop();
				}
				else{
					card.setOpacity(card.getOpacity() + 0.02);
				}
			}

		};
		deal.start();
	}

	public static void endGame() {
		for(Node n : cardsGroup.getChildren()) {
			endGame = endGame && ((Rectangle)n).getX() >= ingame.getBundle1().getX() 
					&& ((Rectangle)n).getX() <= ingame.getBundle4().getX()  
					&& ((Rectangle)n).getY() < ingame.getSeparator().getY();
		}
	}

	public static void shuffle(Group cGroup, LinkedList<card> cList) {
		int indexA = 0;
		int indexB = 0;
		int count = 0;
		while(count <= ingame.getDifficulty()) {
			indexA = (int)(Math.random() * 52);
			indexB = (int)(Math.random() * 52);
			while(indexB == indexA) {
				indexB = (int)(Math.random() * 52);
			}
			Node tmpNA = cGroup.getChildren().get(indexA);
			Node tmpNB = cGroup.getChildren().get(indexB);
			card tmpCA = cList.get(indexA);
			card tmpCB = cList.get(indexB);
			cGroup.getChildren().remove(indexB > indexA ? indexB : indexA);
			cGroup.getChildren().add(indexB > indexA ? indexA : indexB,indexB > indexA ? tmpNB : tmpNA);
			cGroup.getChildren().remove(indexB > indexA ? indexA + 1 : indexB + 1);
			cGroup.getChildren().add(indexB > indexA ? indexB : indexA,indexB > indexA ? tmpNA : tmpNB);
			cList.remove(indexB > indexA ? indexB : indexA);
			cList.add(indexB > indexA ? indexA : indexB,indexB > indexA ? tmpCB : tmpCA);
			cList.remove(indexB > indexA ? indexA + 1 : indexB + 1);
			cList.add(indexB > indexA ? indexB : indexA,indexB > indexA ? tmpCA : tmpCB);
			count++;
		}
	}

	public static void drawCards(){
		if(countCardsDrawn < tempList.size()) {				
			cardFlipPlayer.stop();
			cardFlipPlayer.play();
			countCardsDrawn++;
			int tempIndex = cardsGroup.getChildren().indexOf(tempList.get(drawnCardsIndex));
			if(((Rectangle)cardsGroup.getChildren().get(tempIndex)).getX() == ingame.getDrawnCardsPack().getX() && ((Rectangle)cardsGroup.getChildren().get(tempIndex)).getY() == ingame.getDrawnCardsPack().getY()) {
				if(countCardsDrawn == tempList.size()) {
					ingame.getDrawnCardsPack().setStrokeWidth(ingame.getBundle1().getStrokeWidth());
					ingame.getDrawnCardsPack().setFill(Color.TRANSPARENT);
				}
				//			slide((Rectangle)drawnCardsGroup.getChildren().get(drawnCardsIndex),1,drawnCards.getX() - offset,"left");
				cardsList.get(tempIndex).flip(true);
				slideCard(((Rectangle)cardsGroup.getChildren().get(tempIndex)),drawnCardsPack.getX() - offset);
				//				((Rectangle)cardsGroup.getChildren().get(tempIndex)).setX(drawnCardsPack.getX() - offset);
				if(countCardsDrawn != 1) {
					int prevIndex = cardsGroup.getChildren().indexOf(tempList.get(drawnCardsIndex + 1));
					if(((Rectangle)cardsGroup.getChildren().get(prevIndex)).getX() == ingame.getDrawnCardsPack().getX() - offset) {
						((Rectangle)cardsGroup.getChildren().get(prevIndex)).setVisible(false);
					}
				}
			}
			drawnCardsIndex--;
		}else {				
			cardMixPlayer.setVolume(0.3);
			cardMixPlayer.stop();
			cardMixPlayer.play();
			FileInputStream input = null;
			tempList.clear();
			int i = 0;
			while(((Rectangle)cardsGroup.getChildren().get(i)).getY() == ingame.getDrawnCardsPack().getY()) {
				if(ingame.getcbPath() != null) {
					try {
						input = new FileInputStream(ingame.getcbPath());
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						System.out.println(e);
					}
				}
				((Rectangle)cardsGroup.getChildren().get(i)).setVisible(true);
				cardsList.get(i).flip(false);
				((Rectangle) cardsGroup.getChildren().get(i))
				.setFill(input == null ? new ImagePattern(new Image("/application/cards/crown1.jpg",
						drawnCardsPack.getWidth() * 2, drawnCardsPack.getHeight() * 2, false,
						false)) : new ImagePattern(new Image(input,
								drawnCardsPack.getWidth() * 2, drawnCardsPack.getHeight() * 2, false,
								false)));
				((Rectangle)cardsGroup.getChildren().get(i)).setX(ingame.getDrawnCardsPack().getX());
				tempList.add((Rectangle)cardsGroup.getChildren().get(i));
				i++;
			}
			if (tempList.size() > 0) {
				if(ingame.getcbPath() != "") {
					try {
						input = new FileInputStream(ingame.getcbPath());
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						System.out.println(e);
					}
				}
				ingame.getDrawnCardsPack().setFill(input == null ? new ImagePattern(new Image("/cards/crown1.jpg",drawnCardsPack.getWidth() * 2,drawnCardsPack.getHeight() * 2,false,false)) : new ImagePattern(new Image(input)));
			}
			countCardsDrawn = 0;
			drawnCardsIndex = tempList.size() - 1;
		}
	}

	public static void moveCards(int i,Rectangle here, double finalX, double finalY, Rectangle there) {
		double originalStreck = Math.sqrt(Math.pow(finalX - here.getX(),2) + Math.pow(finalY - here.getY(),2));
		double startingX = here.getX();
		double startingY = here.getY();
		double diffX = here.getX() - finalX;
		double diffY = here.getY() - finalY;
		moveList[i] = new AnimationTimer(){

			@Override
			public void handle(long now) {
				// TODO Auto-generated method stub
				double currentStreck = Math.sqrt(Math.pow(here.getX() - startingX,2) + Math.pow(here.getY() - startingY,2));
				if(currentStreck >= originalStreck - 10) {
					ingame.setConsumeKey(false);
					here.setX(finalX);
					here.setY(finalY);
					moveList[i].stop();
					//					if(!cardsList.get(cardsGroup.getChildren().indexOf(here)).getState())createRotator(here);
					there.setDisable(false);
					detectEmptyColumns();
					detectEmptyBundles();
					endGame();
					if(endGame) {
						victorySongPlayer.stop();
						victorySongPlayer.play();
						initiateEndgameState();
					}else {
						endGame = true;
					}
					//					drawnCardsPack.setDisable(false);
				}else {
					//					drawnCardsPack.setDisable(true);
					ingame.setConsumeKey(true);
					here.setX(here.getX() - diffX / 20);
					here.setY(here.getY() - diffY / 20);
				}
			}		
		};
		moveList[i].start();
	}

	public static void slideCard(Rectangle here, double finalDes) {
		double disPerTick = (here.getX() - finalDes) * 10 / 100;
		double actualRatio = disPerTick / ((here.getX() - finalDes)/2);
		double shrinkRatio = here.getWidth() * actualRatio;
		slide = new AnimationTimer(){
			long last = 0;
			@Override
			public void handle(long now) {
				// TODO Auto-generated method stub
				if(now - last >= 16_000_000) {
					if(here.getX() <= finalDes) {
						ingame.setConsumeKey(false);
						here.setX(finalDes);
						int first = 0;
						while(((Rectangle) cardsGroup.getChildren().get(first)).getY() ==  drawnCardsPack.getY()) {
							((Rectangle) cardsGroup.getChildren().get(first)).setDisable(false);
							first++;
						}
						drawnCardsPack.setDisable(false);
						slide.stop();
					}
					else {
						ingame.setConsumeKey(true);
						drawnCardsPack.setDisable(true);
						int first = 0;
						while(((Rectangle) cardsGroup.getChildren().get(first)).getY() ==  drawnCardsPack.getY()) {
							((Rectangle) cardsGroup.getChildren().get(first)).setDisable(true);
							first++;
						}
						here.setX(here.getX() - disPerTick);
						if(here.getX() >= drawnCardsPack.getX() - offset / 2)here.setWidth(here.getWidth() - shrinkRatio);
						else {
							here.setFill(new ImagePattern(new Image("/application/designers cards/" + cardsList.get(cardsGroup.getChildren().indexOf(here)).getCardName() + ".jpg")));
							here.setWidth(here.getWidth() + shrinkRatio);
						}
					}
					last = now;
				}
			}

		};
		slide.start();
	}

	public static void detectEmptyColumns() {
		int colNo = 1;
		int cardsAmount = 0;
		while(colNo <= 7) {
			for(Node n : cardsGroup.getChildren()) {
				switch(colNo) {
				case 1:
					if(((Rectangle)n).getX() == ingame.getRow1().getX())cardsAmount++;
					break;
				case 2:
					if(((Rectangle)n).getX() == ingame.getRow2().getX())cardsAmount++;
					break;
				case 3:
					if(((Rectangle)n).getX() == ingame.getRow3().getX())cardsAmount++;
					break;
				case 4:
					if(((Rectangle)n).getX() == ingame.getRow4().getX())cardsAmount++;
					break;
				case 5:
					if(((Rectangle)n).getX() == ingame.getRow5().getX())cardsAmount++;
					break;
				case 6:
					if(((Rectangle)n).getX() == ingame.getRow6().getX())cardsAmount++;
					break;
				case 7:
					if(((Rectangle)n).getX() == ingame.getRow7().getX())cardsAmount++;
					break;
				}				
			}
			switch(colNo) {
			case 1:
				ingame.getRow1().setVisible(cardsAmount > 0 ? false : true);
				break;
			case 2:
				ingame.getRow2().setVisible(cardsAmount > 0 ? false : true);
				break;
			case 3:
				ingame.getRow3().setVisible(cardsAmount > 0 ? false : true);
				break;
			case 4:
				ingame.getRow4().setVisible(cardsAmount > 0 ? false : true);
				break;
			case 5:
				ingame.getRow5().setVisible(cardsAmount > 0 ? false : true);
				break;
			case 6:
				ingame.getRow6().setVisible(cardsAmount > 0 ? false : true);
				break;
			case 7:
				ingame.getRow7().setVisible(cardsAmount > 0 ? false : true);
				break;
			}	
			colNo++;
			cardsAmount = 0;
		}
	}

	public static void detectEmptyBundles() {
		int bNo = 1;
		int cardsAmount = 0;
		while(bNo <= 4) {
			for(Node n : cardsGroup.getChildren()) {
				switch(bNo) {
				case 1:
					if(((Rectangle)n).getX() == ingame.getBundle1().getX())cardsAmount++;
					break;
				case 2:
					if(((Rectangle)n).getX() == ingame.getBundle2().getX())cardsAmount++;
					break;
				case 3:
					if(((Rectangle)n).getX() == ingame.getBundle3().getX())cardsAmount++;
					break;
				case 4:
					if(((Rectangle)n).getX() == ingame.getBundle4().getX())cardsAmount++;
					break;		
				}
			}
			switch(bNo) {
			case 1:
				ingame.getBundle1().setVisible(cardsAmount != 0 ? false : true);
				break;
			case 2:
				ingame.getBundle2().setVisible(cardsAmount != 0 ? false : true);
				break;
			case 3:
				ingame.getBundle3().setVisible(cardsAmount != 0 ? false : true);
				break;
			case 4:
				ingame.getBundle4().setVisible(cardsAmount != 0 ? false : true);
				break;
			}
			bNo++;
			cardsAmount = 0;
		}	
	}

	public static void addClick(Rectangle rect) {	

		detectEmptyBundles();
		detectEmptyColumns();
		DropShadow glow = new DropShadow();
		glow.setColor(Color.WHITE);
		glow.setWidth(0);
		glow.setHeight(0);
		glow.setSpread(0.6);

		DropShadow difGlow = new DropShadow();
		difGlow.setColor(Color.RED);
		difGlow.setWidth(0);
		difGlow.setHeight(0);
		difGlow.setSpread(0.6);
		rect.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				// TODO Auto-generated method stub
				clickPlayer.stop();
				clickPlayer.play();				
			}
		});
		rect.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				// TODO Auto-generated method stub	
				lastMove newMove = new lastMove(0, 0, 0, 0, "", 0, 0);
				clickPlayer.stop();
				clickPlayer.play();
				if(cardClicked.size() == 0) {
					if(rect.getFill() != Color.TRANSPARENT  && cardsList.get(cardsGroup.getChildren().indexOf(rect)).isFacingUp()) {
						cardClicked.add(rect);
						int indexOfRect = cardsGroup.getChildren().indexOf(rect);
						boolean emptySpaces = ingame.getRow1().isVisible() 
								|| ingame.getRow2().isVisible() 
								|| ingame.getRow3().isVisible() 
								|| ingame.getRow4().isVisible() 
								|| ingame.getRow5().isVisible() 
								|| ingame.getRow6().isVisible() 
								|| ingame.getRow7().isVisible()
								|| ingame.getBundle1().isVisible() 
								|| ingame.getBundle2().isVisible() 
								|| ingame.getBundle3().isVisible() 
								|| ingame.getBundle4().isVisible();
						for(Node n : cardsGroup.getChildren()) {
							if(((Rectangle)n).getX() != ingame.getDrawnCardsPack().getX() - offset 
									&& ((Rectangle)n).getX() != ingame.getDrawnCardsPack().getX() 
									&& cardsList.get(cardsGroup.getChildren().indexOf(n)).isFacingUp() 
									&& ((Rectangle)n).getX() != rect.getX()) {
								int indexOfn = cardsGroup.getChildren().indexOf(n);
								boolean legitVal = false;
								boolean legitPat = false;
								if(		! ( ((Rectangle)n).getX() >= ingame.getBundle1().getX() 
										&& ((Rectangle)n).getX() <= ingame.getBundle4().getX() + ingame.getBundle4().getWidth()
										&& ((Rectangle)n).getY() >= ingame.getBundle1().getY()
										&& ((Rectangle)n).getY() <= ingame.getBundle1().getY() + ingame.getBundle1().getHeight())  ) {
									if(indexOfn != 51 ? ((Rectangle)cardsGroup.getChildren().get(indexOfn + 1)).getX() != ((Rectangle)n).getX() : true) {
										switch(cardsList.get(indexOfRect).getCardValue()) {
										case "A" : 
											legitVal = (cardsList.get(indexOfn).getCardValue() == "2");
											break;
										case "2" : 
											legitVal = (cardsList.get(indexOfn).getCardValue() == "3");
											break;
										case "3" : 
											legitVal = (cardsList.get(indexOfn).getCardValue() == "4");
											break;
										case "4" : 
											legitVal = (cardsList.get(indexOfn).getCardValue() == "5");
											break;
										case "5" : 
											legitVal = (cardsList.get(indexOfn).getCardValue() == "6");
											break;
										case "6" : 
											legitVal = (cardsList.get(indexOfn).getCardValue() == "7");
											break;
										case "7" : 
											legitVal = (cardsList.get(indexOfn).getCardValue() == "8");
											break;
										case "8" : 
											legitVal = (cardsList.get(indexOfn).getCardValue() == "9");
											break;
										case "9" : 
											legitVal = (cardsList.get(indexOfn).getCardValue() == "10");
											break;
										case "10" : 
											legitVal = (cardsList.get(indexOfn).getCardValue() == "J");
											break;
										case "J" : 
											legitVal = (cardsList.get(indexOfn).getCardValue() == "Q");
											break;
										case "Q" : 
											legitVal = (cardsList.get(indexOfn).getCardValue() == "K");
											break;
										}	
										switch(cardsList.get(indexOfRect).getCardPattern()) {
										case "C" : 
											legitPat = cardsList.get(indexOfn).getCardPattern() == "H" || cardsList.get(indexOfn).getCardPattern() == "D";
											break;
										case "S" : 
											legitPat = cardsList.get(indexOfn).getCardPattern() == "H" || cardsList.get(indexOfn).getCardPattern() == "D";
											break;
										case "D" : 
											legitPat = cardsList.get(indexOfn).getCardPattern() == "C" || cardsList.get(indexOfn).getCardPattern() == "S";
											break;
										case "H" : 
											legitPat = cardsList.get(indexOfn).getCardPattern() == "C" || cardsList.get(indexOfn).getCardPattern() == "S";
											break;
										}
									}
								}else {
									int indexOfClickedCard = cardsGroup.getChildren().indexOf(cardClicked.get(0));
									if(indexOfClickedCard != 51 ? ((Rectangle)cardsGroup.getChildren().get(indexOfClickedCard + 1)).getX() != cardClicked.get(0).getX() || cardClicked.get(0).getX() == drawnCardsPack.getX()-offset : true) {
										switch(cardsList.get(indexOfRect).getCardValue()) {
										case "2" : 
											legitVal = (cardsList.get(indexOfn).getCardValue() == "A");
											break;
										case "3" : 
											legitVal = (cardsList.get(indexOfn).getCardValue() == "2");
											break;
										case "4" : 
											legitVal = (cardsList.get(indexOfn).getCardValue() == "3");
											break;
										case "5" : 
											legitVal = (cardsList.get(indexOfn).getCardValue() == "4");
											break;
										case "6" : 
											legitVal = (cardsList.get(indexOfn).getCardValue() == "5");
											break;
										case "7" : 
											legitVal = (cardsList.get(indexOfn).getCardValue() == "6");
											break;
										case "8" : 
											legitVal = (cardsList.get(indexOfn).getCardValue() == "7");
											break;
										case "9" : 
											legitVal = (cardsList.get(indexOfn).getCardValue() == "8");
											break;
										case "10" : 
											legitVal = (cardsList.get(indexOfn).getCardValue() == "9");
											break;
										case "J" : 
											legitVal = (cardsList.get(indexOfn).getCardValue() == "10");
											break;
										case "Q" : 
											legitVal = (cardsList.get(indexOfn).getCardValue() == "J");
											break;
										case "K" : 
											legitVal = (cardsList.get(indexOfn).getCardValue() == "Q");
											break;
										}	
										switch(cardsList.get(indexOfRect).getCardPattern()) {
										case "C" : 
											legitPat = cardsList.get(indexOfn).getCardPattern() == "C";
											break;
										case "S" : 
											legitPat = cardsList.get(indexOfn).getCardPattern() == "S";
											break;
										case "D" : 
											legitPat = cardsList.get(indexOfn).getCardPattern() == "D";
											break;
										case "H" : 
											legitPat = cardsList.get(indexOfn).getCardPattern() == "H";
											break;
										}
									}
								}
								if(legitVal && legitPat) {
									validCards.add((Rectangle) cardsGroup.getChildren().get(indexOfn));
								}
							}
						}
						if(emptySpaces) {
							if(cardsList.get(cardsGroup.getChildren().indexOf(rect)).getCardValue() == "K") {
								if(ingame.getRow1().isVisible())validCards.add(ingame.getRow1());
								if(ingame.getRow2().isVisible())validCards.add(ingame.getRow2());
								if(ingame.getRow3().isVisible())validCards.add(ingame.getRow3());
								if(ingame.getRow4().isVisible())validCards.add(ingame.getRow4());
								if(ingame.getRow5().isVisible())validCards.add(ingame.getRow5());
								if(ingame.getRow6().isVisible())validCards.add(ingame.getRow6());
								if(ingame.getRow7().isVisible())validCards.add(ingame.getRow7());
							}
							if(cardsList.get(cardsGroup.getChildren().indexOf(rect)).getCardValue() == "A") {
								if(ingame.getBundle1().isVisible())validCards.add(ingame.getBundle1());
								if(ingame.getBundle2().isVisible())validCards.add(ingame.getBundle2());
								if(ingame.getBundle3().isVisible())validCards.add(ingame.getBundle3());
								if(ingame.getBundle4().isVisible())validCards.add(ingame.getBundle4());			
							}
						}		
						glow.setWidth(30);
						glow.setHeight(30);
						difGlow.setWidth(20);
						difGlow.setHeight(20);
						for(Rectangle r : validCards) {
							r.setEffect(difGlow);
						}
						rect.setWidth(rect.getWidth() + 3);
						rect.setHeight(rect.getHeight() + 3);
						rect.setX(rect.getX() - 1);
						rect.setY(rect.getY() - 1);
						rect.setEffect(glow);
					}
				}else {
					int beginPack = cardsGroup.getChildren().indexOf(cardClicked.get(0));
					int endPack = beginPack + (cardClicked.get(0).getY() < ingame.getSeparator().getY() ? 1 : 0);
					cardClicked.get(0).setX(cardClicked.get(0).getX() + 1);
					for(int i = beginPack; i <= 51 && ((Rectangle)cardsGroup.getChildren().get(i)).getY() > ingame.getSeparator().getY() ? ((Rectangle)cardsGroup.getChildren().get(i)).getX() == cardClicked.get(0).getX() : false; i++) {
						endPack++;
					}
					cardClicked.get(0).setX(cardClicked.get(0).getX() - 1);
					if(validCards.contains(rect)) {
						if(cardClicked.get(0).getY() > ingame.getSeparator().getY()) {
							newMove.setSection(3);
						}else {
							if(cardClicked.get(0).getX() + 1 == drawnCardsPack.getX() - offset) {
								newMove.setSection(2);
							}else {
								newMove.setSection(1);
							}
						}
						newMove.setLastX(cardClicked.get(0).getX() + 1);
						newMove.setLastY(cardClicked.get(0).getY() + 1);
						newMove.setSize(endPack-beginPack);
						newMove.setLastIndex(cardsGroup.getChildren().indexOf(cardClicked.get(0)));
						if(cardsGroup.getChildren().indexOf(cardClicked.get(0)) > 0){
							if(cardsList.get(cardsGroup.getChildren().indexOf(cardClicked.get(0)) - 1).isFacingUp()) {
								newMove.setFace("up");
							}else {
								newMove.setFace("down");
							}
						}else {
							newMove.setFace("down");
						}

						if(cardClicked.get(0).getX()+1 != ingame.getDrawnCardsPack().getX()-offset && cardsGroup.getChildren().indexOf(cardClicked.get(0)) != 0) {
							int cardBeforeClickedCard = cardsGroup.getChildren().indexOf(cardClicked.get(0)) - 1;
							cardsList.get(cardBeforeClickedCard).flip(true);
							if(((Rectangle)cardsGroup.getChildren().get(cardBeforeClickedCard)).getY() > ingame.getSeparator().getY())((Rectangle)cardsGroup.getChildren().get(cardBeforeClickedCard)).setFill(new ImagePattern(new Image("/application/designers cards/" + cardsList.get(cardBeforeClickedCard).getCardName() + ".jpg")));
						}
						LinkedList<Rectangle> tempRecList = new LinkedList<Rectangle>();
						LinkedList<card> tempCardList = new LinkedList<card>();
						int newIndex = 1;
						int tmpIndex = 0;
						for(int i = beginPack; i < endPack; i++) {
							Rectangle r = (Rectangle) cardsGroup.getChildren().get(i);
							card c = cardsList.get(i);
							tempRecList.add(r);
							tempCardList.add(c);
						}
						for(int i = beginPack; i < endPack; i++) {
							cardsGroup.getChildren().remove(beginPack);
							cardsList.remove(beginPack);
						}
						int factor = 1;
						int moveIndex = 0;
						for(int i = beginPack; i < endPack; i++) {
							int tI = 0;
							if(rect.getFill() == Color.TRANSPARENT) {
								cardsGroup.getChildren().add(tempRecList.get(tmpIndex));
								cardsList.add(tempCardList.get(tmpIndex));
								tI = cardsList.indexOf(tempCardList.get(tmpIndex));
							}else {
								tI = cardsGroup.getChildren().indexOf(rect)+newIndex;
								cardsGroup.getChildren().add(tI, tempRecList.get(tmpIndex));
								cardsList.add(tI, tempCardList.get(tmpIndex));
							}
							if(rect.getFill() == Color.TRANSPARENT 
									&& rect.getY() > ingame.getSeparator().getY() 
									&& i == beginPack) {
								factor = 0;		
							}							
							rect.setDisable(true);
							moveCards(moveIndex,(Rectangle)cardsGroup.getChildren().get(tI),rect.getX(),rect.getY() + (rect.getY() < ingame.getSeparator().getY() ? 0 : 30 * factor),rect);
							if(i == beginPack) {
								((Rectangle)cardsGroup.getChildren().get(tI)).setWidth(((Rectangle)cardsGroup.getChildren().get(tI)).getWidth() - 3);
								((Rectangle)cardsGroup.getChildren().get(tI)).setHeight(((Rectangle)cardsGroup.getChildren().get(tI)).getHeight() - 3);
							}
							glow.setWidth(0);
							glow.setHeight(0);
							difGlow.setWidth(0);
							difGlow.setHeight(0);
							((Rectangle)cardsGroup.getChildren().get(tI)).setEffect(glow);
							factor++;
							newIndex++;
							tmpIndex++;
							moveIndex++;
						}
						newMove.setCurIndex(cardsGroup.getChildren().indexOf(tempRecList.get(0)));
						//						detectEmptyColumns();
						//						detectEmptyBundles();
						listOfMoves.add(newMove);
						for(Rectangle r : validCards) {
							r.setEffect(difGlow);
						}
						cardClicked.clear();
						validCards.clear();
					}else{
						glow.setWidth(0);
						glow.setHeight(0);
						difGlow.setWidth(0);
						difGlow.setHeight(0);
						for(Rectangle r : validCards) {
							r.setEffect(difGlow);
						}
						rect.setWidth(rect.getWidth() - 3);
						rect.setHeight(rect.getHeight() - 3);
						rect.setX(rect.getX() + 1);
						rect.setY(rect.getY() + 1);
						rect.setEffect(glow);
						cardClicked.clear();
						validCards.clear();
					}
					//					for(lastMove l : listOfMoves) {
					//						System.out.print("["+l.getLastIndex()+","+l.getCurIndex()+","+l.getSize()+","+l.getSection()+","+l.getFace()+","+l.getLastX()+","+l.getLastY()+"]");
					//						System.out.print(",");
					//						if(l == listOfMoves.getLast()) {
					//							System.out.println();
					//						}
					//					}
				}
			}
		});
	}
	public static void createRotator(Node card, int cycleCount, Point3D direction, double duration) {
		//		int Rand = (int) (Math.random() * 100);
		RotateTransition rotator = new RotateTransition(Duration.millis(duration), card);
		rotator.setAxis(/* Rand < 33 ? Rotate.X_AXIS : (Rand < 66 ? Rotate.Y_AXIS : Rotate.Z_AXIS) */direction);
		rotator.setFromAngle(0);
		rotator.setToAngle(360);
		rotator.setInterpolator(Interpolator.LINEAR);
		rotator.setCycleCount(cycleCount);
		cardsList.get(cardsGroup.getChildren().indexOf(card)).setState(true);
		rotator.setOnFinished(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				cardsList.get(cardsGroup.getChildren().indexOf(card)).setState(false);
			}

		});
		rotator.play();
	}

	static int i = 51;
	static int prev = 1;
	static double m = 30;
	public static void initiateEndgameState() {
		double r = 300;
		final double centerX = ingame.getWIDTH() / 2;
		final double centerY = ingame.getHEIGHT() / 2;
		LinkedList<Rectangle> toMove = new LinkedList<Rectangle>();
		toMove.add((Rectangle)cardsGroup.getChildren().get(i));
		ingame.getRestart().setVisible(false);
		ingame.getSetting().setVisible(false);
		ingame.getRestartText().setVisible(false);
		ingame.getSettingText().setVisible(false);
		ingame.getRestart().setDisable(true);
		ingame.getSetting().setDisable(true);
		ingame.getRestartText().setDisable(true);
		ingame.getSettingText().setDisable(true);
		ingame.getTime().stop();
		endGameAni = new AnimationTimer() {
			long last = 0;
			@Override
			public void handle(long now) {
				// TODO Auto-generated method stub
				if(now - last >= 16_000_000) {
					if(((Rectangle)cardsGroup.getChildren().get(0)).getWidth() >= 10) {
						for(Rectangle rec : toMove) {
							if(rec.getX() < centerX - 3 && rec.getY() < centerY - r - 3) {
								double diffX = rec.getX() - centerX;
								double diffY = rec.getY() - (centerY - r);
								rec.setX(rec.getX() - diffX / 2);
								rec.setY(rec.getY() - diffY / 2);
							}else if(rec.getX() < centerX && rec.getY() < centerY - r){
								rec.setX(centerX);
								rec.setY(centerY - r);
							}
							if(rec.getY() >= centerY - r) {
								if(cardsList.get(cardsGroup.getChildren().indexOf(rec)).isGoingRight()) {
									if(rec.getX() >= centerX + r) {
										cardsList.get(cardsGroup.getChildren().indexOf(rec)).switchDir(false);
										rec.setX(centerX + r);
									}else {
										rec.setX(rec.getX() + m);
									}
									rec.setY(centerY - Math.sqrt(Math.pow(r, 2) - Math.pow(rec.getX() - centerX, 2)));
								}else {
									if(rec.getX() <= centerX - r) {
										cardsList.get(cardsGroup.getChildren().indexOf(rec)).switchDir(true);
										rec.setX(centerX - r);
									}else {
										rec.setX(rec.getX() - m);
									}					
									rec.setY(centerY + Math.sqrt(Math.pow(r, 2) - Math.pow(rec.getX() - centerX, 2)));				
								}
								rec.setWidth(rec.getWidth() - drawnCardsPack.getWidth() / (r*4 / m));
								rec.setHeight(rec.getHeight() - drawnCardsPack.getHeight() / (r*4 / m));
								rec.setOpacity(rec.getOpacity() - (m/(r*4)));
							}
						}
						if(toMove.getLast().getY() >= (HEIGHT / 2 - r - ingame.getBundle4().getY()) / 2) {
							if(i - prev >= 0) {
								toMove.add((Rectangle)cardsGroup.getChildren().get(i - prev));
								prev++;
							}
						}
					}else {
						ingame.getEndGamePane().setVisible(true);
						prev = 1;
						toMove.clear();
						endGameAni.stop();
					}
					last = now;
				}
			}

		};
		endGameAni.start();
	}

	public static void undo() {
		if(listOfMoves.size() > 0) {
			lastMove m = listOfMoves.getLast();
			if(m.getSection() == 3) {
				LinkedList<Rectangle> tempRecList = new LinkedList<Rectangle>();
				LinkedList<card> tempCardList = new LinkedList<card>();
				int tmpIndex = 0;
				for(int i = m.getCurIndex(); i < m.getCurIndex() + m.getSize(); i++) {
					Rectangle r = (Rectangle) cardsGroup.getChildren().get(i);
					card c = cardsList.get(i);
					tempRecList.add(r);
					tempCardList.add(c);
				}
				for(int i = m.getCurIndex(); i < m.getCurIndex() + m.getSize(); i++) {
					cardsGroup.getChildren().remove(m.getCurIndex());
					cardsList.remove(m.getCurIndex());
				}
				int factor = 0;
				for(int i = m.getLastIndex(); i < m.getLastIndex() + m.getSize(); i++) {
					cardsGroup.getChildren().add(i, tempRecList.get(tmpIndex));
					cardsList.add(i, tempCardList.get(tmpIndex));
					tmpIndex++;
				}
				for(int i = m.getLastIndex(); i < m.getLastIndex() + m.getSize(); i++) {
					((Rectangle)cardsGroup.getChildren().get(i)).setX(m.getLastX());
					((Rectangle)cardsGroup.getChildren().get(i)).setY(m.getLastY() + 30 * factor);
					factor++;
				}
				if(m.getLastIndex() > 0 && ((Rectangle)cardsGroup.getChildren().get(m.getLastIndex() - 1)).getY() > ingame.getSeparator().getY()) {
					if(m.getFace() == "down") {
						FileInputStream input = null;
						if(ingame.getcbPath() != null) {
							try {
								input = new FileInputStream(ingame.getcbPath());
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								System.out.println(e);
							}
						}
						((Rectangle) cardsGroup.getChildren().get(m.getLastIndex() - 1))
						.setFill(input == null ? new ImagePattern(new Image("/application/cards/crown1.jpg",drawnCardsPack.getWidth() * 2,drawnCardsPack.getHeight() * 2,false,false)) 
								: new ImagePattern(new Image(input)) );
					}else {
						((Rectangle) cardsGroup.getChildren().get(m.getLastIndex() - 1)).setFill(new ImagePattern(new Image("/application/designers cards/" + cardsList.get(m.getLastIndex() - 1).getCardName() + ".jpg")));
					}
				}
			}else if(m.getSection() == 2) {
				Rectangle rec = (Rectangle) cardsGroup.getChildren().get(m.getCurIndex());
				card c = cardsList.get(m.getCurIndex());

				cardsGroup.getChildren().remove(m.getCurIndex());
				cardsList.remove(m.getCurIndex());

				cardsGroup.getChildren().add(m.getLastIndex(),rec);
				cardsList.add(m.getLastIndex(),c);

				((Rectangle)cardsGroup.getChildren().get(m.getLastIndex())).setX(m.getLastX());
				((Rectangle)cardsGroup.getChildren().get(m.getLastIndex())).setY(m.getLastY());

				tempList.clear();

				for(int i = 0; ((Rectangle)cardsGroup.getChildren().get(i)).getY() == drawnCardsPack.getY(); i++) {
					tempList.add((Rectangle) cardsGroup.getChildren().get(i));
				}

				drawnCardsIndex = m.getLastIndex() - 1;
				countCardsDrawn = tempList.size() - drawnCardsIndex;

				for(int i = m.getLastIndex()-1; i >= 0; i--) {
					((Rectangle)cardsGroup.getChildren().get(i)).setX(drawnCardsPack.getX());
					FileInputStream input = null;
					if(ingame.getcbPath() != null) {
						try {
							input = new FileInputStream(ingame.getcbPath());
						} catch (FileNotFoundException e) {
							// TODO Auto-generated catch block
							System.out.println(e);
						}
					}
					((Rectangle)cardsGroup.getChildren().get(i)).setVisible(true);
					cardsList.get(i).flip(false);
					((Rectangle) cardsGroup.getChildren().get(i))
					.setFill( input == null ? new ImagePattern(new Image("/application/cards/crown1.jpg",
							drawnCardsPack.getWidth() * 2, drawnCardsPack.getHeight() * 2, false,
							false)) : new ImagePattern(new Image(input)) );
				}

				for(int i = m.getLastIndex()+1; i < tempList.size(); i++) {
					((Rectangle)cardsGroup.getChildren().get(i)).setVisible(false);
					cardsList.get(i).flip(true);

				}
			}else {
				Rectangle rec = (Rectangle) cardsGroup.getChildren().get(m.getCurIndex());
				card c = cardsList.get(m.getCurIndex());

				cardsGroup.getChildren().remove(m.getCurIndex());
				cardsList.remove(m.getCurIndex());

				cardsGroup.getChildren().add(m.getLastIndex(),rec);
				cardsList.add(m.getLastIndex(),c);

				((Rectangle)cardsGroup.getChildren().get(m.getLastIndex())).setX(m.getLastX());
				((Rectangle)cardsGroup.getChildren().get(m.getLastIndex())).setY(m.getLastY());
			}
			detectEmptyBundles();
			detectEmptyColumns();
			listOfMoves.removeLast();
		}
	}

	public static LinkedList<lastMove> getListOfMoves(){
		return listOfMoves;
	}

	public static MediaPlayer getVictorySong() {
		return victorySongPlayer;
	}

	public static boolean endPhase() {
		return endGame;
	}

	public static LinkedList<Rectangle> getTempList(){
		return tempList;
	}

	public static LinkedList<card> getCardsList(){
		return cardsList;
	}	

	public static Group getCardsGroup(){
		return cardsGroup;
	}

	public static LinkedList<Rectangle> getValidCards() {
		return validCards;
	}

	public static LinkedList<Rectangle> getChosenCard(){
		return cardClicked;
	}

	public static double getOffset() {
		return offset;
	}
}

