package application;

import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
public class Bricks {
	Rectangle a;
	Rectangle b;
	Rectangle c;
	Rectangle d;
	String name;
	Color color;
	int form = 1;
	String image_location = "";
	Image img;
	ImagePattern pat;

	public Bricks(Rectangle a, Rectangle b, Rectangle c, Rectangle d, String name, String source) {
		this.a = a;
		this.b = b; 
		this.c = c;
		this.d = d;
		this.name = name;

		switch (name) {
		case "J":
			if(source == "")color = Color.web("5A6B34");
			else {
				image_location = "/backgrounds/" + source + "/J.png";
				img = new Image(image_location);
				pat = new ImagePattern(img);
			}
			break;
		case "L":
			if(source == "")color = Color.web("00BFFF");
			else {
				image_location = "/backgrounds/" + source + "/L.png";
				img = new Image(image_location);
				pat = new ImagePattern(img);
			}
			break;
		case "O":
			if(source == "")color = Color.web("DC143C");
			else {
				image_location = "/backgrounds/" + source + "/O.png";
				img = new Image(image_location);
				pat = new ImagePattern(img);
			}
			break;
		case "S":
			if(source == "")color = Color.web("FF8C00");
			else {
				image_location = "/backgrounds/" + source + "/S.jpg";
				img = new Image(image_location);
				pat = new ImagePattern(img);
			}
			break;
		case "T":
			if(source == "")color = Color.web("F0D64E");
			else {
				image_location = "/backgrounds/" + source + "/T.png";
				img = new Image(image_location);
				pat = new ImagePattern(img);
			}
			break;
		case "Z":
			if(source == "")color = Color.web("4B0082");
			else {
				image_location = "/backgrounds/" + source + "/Z.png";
				img = new Image(image_location);
				pat = new ImagePattern(img);
			}
			break;
		case "I":
			if(source == "")color = Color.web("32CD32");
			else {
				image_location = "/backgrounds/" + source + "/I.png";
				img = new Image(image_location);
				pat = new ImagePattern(img);
			}
			break;

		}

		this.a.setFill(source == "" ? color : pat);
		this.b.setFill(source == "" ? color : pat);
		this.c.setFill(source == "" ? color : pat);
		this.d.setFill(source == "" ? color : pat);
		this.a.setStrokeWidth(0);
		this.b.setStrokeWidth(0);
		this.c.setStrokeWidth(0);
		this.d.setStrokeWidth(0);
		this.a.setArcWidth(10);
		this.b.setArcWidth(10);
		this.c.setArcWidth(10);
		this.d.setArcWidth(10);
		this.a.setArcHeight(10);
		this.b.setArcHeight(10);
		this.c.setArcHeight(10);
		this.d.setArcHeight(10);
	}

	public String getName() {
		return name;
	}

	public int getForm() {
		return form;
	}

	public void changeForm() {
		if(form != 4) form++;
		else form = 1;
	}

	public int getBrickWidth() {
		int w = 0;
		if(this.name == "J" || this.name == "L" || this.name == "O" )w = (int) this.a.getWidth() * 2;
		else if(this.name == "T" || this.name == "Z" || this.name == "S")w = (int) this.a.getWidth() * 3;
		else w = (int) this.a.getWidth();
		return w;
	}

	public int getBrickHeight() {
		int w = 0;
		if(this.name == "J" || this.name == "L" || this.name == "I" )w = (int) this.a.getWidth() * 3;
		else if(this.name == "T" || this.name == "Z" || this.name == "S")w = (int) this.a.getWidth() * 2;
		else w = (int) this.a.getWidth();
		return w;
	}
}
