package application;


import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class card {
	String cardValue;
	String cardPattern;
	String cardName;
	ImagePattern cardFace;
	Rectangle actualCard;
	boolean faceUp;
	boolean isRotating;
	boolean goRight;
	
	public card(String val, String pat, Rectangle card, ImagePattern img, boolean faceUp, boolean isRotating,boolean dir) {
		this.cardValue = val;
		this.cardPattern = pat;
		this.cardName = val + pat;
		this.cardFace = img;
		this.actualCard = card;
		this.actualCard.setFill(img);
		this.faceUp = faceUp;
		this.isRotating = isRotating;
		this.goRight = dir;
	}
	
	public ImagePattern getCardFace() {
		return cardFace;
	}
	
	public boolean isGoingRight() {
		return goRight;
	}
	
	public boolean getState() {
		return isRotating;
	}
	
	public String getCardValue() {
		return cardValue;
	}
	
	public String getCardPattern() {
		return cardPattern;
	}
	
	public String getCardName() {
		return cardName;
	}
	
	public Rectangle getActualCard() {
		return actualCard;
	}
	
	public boolean isFacingUp() {
		return faceUp;
	}
	
	public void flip(boolean flipToFaceUp) {
		faceUp = flipToFaceUp;
		
	}
	
	public void setState(boolean state) {
		isRotating = state;
	}
	
	public boolean switchDir(boolean dir) {
		return goRight = dir;
	}
}
