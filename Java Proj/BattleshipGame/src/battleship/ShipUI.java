package battleship;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.LinkedList;

public class ShipUI extends GridPane {
    private LinkedList<Rectangle> blocks;
    private static final int BLOCK_WIDTH = 10;
    private static final int BLOCK_HEIGHT = 10;
    private static final int BLOCK_STROKE = 1;

    public ShipUI() {
        super();
        blocks = new LinkedList<>();
    }

    private static void drawNode(ShipUI shipUI, ShipNode node, int col, int row) {
        Rectangle block = new Rectangle();
        block.setWidth(BLOCK_WIDTH);
        block.setHeight(BLOCK_HEIGHT);
        block.setFill(Color.RED);
        block.setStroke(Color.BLACK);
        block.setStrokeWidth(BLOCK_STROKE);
        shipUI.add(block,col,row);
        shipUI.blocks.add(block);
        ShipNode nextNode = node.getNext();
        int nextDir = node.getNextDir();
        if (nextDir == -1) {
            return;
        }
        switch (nextDir) {
            case 0:
                drawNode(shipUI, nextNode, col, row - 1);
                break;
            case 1:
                drawNode(shipUI, nextNode, col + 1, row);
                break;
            case 2:
                drawNode(shipUI, nextNode, col, row + 1);
                break;
            case 3:
                drawNode(shipUI, nextNode, col - 1, row);
                break;
        }
    }

    public static ShipUI drawShip(Ship ship, int x, int y) {
        ShipUI shipUI = new ShipUI();
        ShipNode node = ship.getFirstNode();
        drawNode(shipUI, node, x, y);
        return shipUI;
    }

    public void addDragAndDrop() {
        final Vector2d vector2d = new Vector2d();
        addEventFilter(MouseEvent.MOUSE_PRESSED, (
                final MouseEvent mouseEvent) -> {
            vector2d.setX(mouseEvent.getSceneX());
            vector2d.setY(mouseEvent.getSceneY());
        });
        addEventFilter(MouseEvent.MOUSE_DRAGGED, (
                final MouseEvent mouseEvent) -> {
            double deltaX = mouseEvent.getSceneX() - vector2d.getX();
            double deltaY = mouseEvent.getSceneY() - vector2d.getY();
            if(vector2d.getX() > GameUI.leftShipContainer.getPrefWidth()) {
            	for(int a = 0; a < getShipLength(); a++) {
                	((Rectangle)getChildren().get(a)).setWidth(BoardUI.getBlockWidth());
                	((Rectangle)getChildren().get(a)).setHeight(BoardUI.getBlockHeight());
                	GameUI.leftShipContainer.setMaxWidth(200);
                	GameUI.leftShipContainer.setMinWidth(200);
                	
                }
            }else {
            	for(int a = 0; a < getShipLength(); a++) {
                	((Rectangle)getChildren().get(a)).setWidth(getBlockWidth());
                	((Rectangle)getChildren().get(a)).setHeight(getBlockHeight());
                }
            }
            setTranslateX(getTranslateX() + deltaX);
            setTranslateY(getTranslateY() + deltaY);
            vector2d.setX(mouseEvent.getSceneX());
            vector2d.setY(mouseEvent.getSceneY());
        });
    }
    
    
    
    public double getMaxBoundX() {
        Rectangle firstBlock = blocks.getFirst();
        double max = firstBlock.getX() + firstBlock.getWidth();
        for (Rectangle block : blocks) {
            double x = block.getX() + block.getWidth();
            if (x > max) {
                max = x;
            }
        }
        return max + getLayoutX();
    }

    public double getMinBoundX() {
        Rectangle firstBlock = blocks.getFirst();
        double min = firstBlock.getX() + firstBlock.getWidth();
        for (Rectangle block : blocks) {
            double x = block.getX() + block.getWidth();
            if (x < min) {
                min = x;
            }
        }
        return min + getLayoutX();
    }

    public double getMaxBoundY() {
        Rectangle firstBlock = blocks.getFirst();
        double max = firstBlock.getY() + firstBlock.getHeight();
        for (Rectangle block : blocks) {
            double y = block.getY() + block.getHeight();
            if (y > max) {
                max = y;
            }
        }
        return max + getLayoutY();
    }

    public double getMinBoundY() {
        Rectangle firstBlock = blocks.getFirst();
        double min = firstBlock.getY() + firstBlock.getHeight();
        for (Rectangle block : blocks) {
            double y = block.getY() + block.getHeight();
            if (y < min) {
                min = y;
            }
        }
        return min + getLayoutY();
    }
    
    public int getBlockWidth() {
    	return BLOCK_WIDTH;
    }
    
    public int getBlockHeight() {
    	return BLOCK_HEIGHT;
    }
    
    public int getShipLength() {
    	return blocks.size();
    }
}
