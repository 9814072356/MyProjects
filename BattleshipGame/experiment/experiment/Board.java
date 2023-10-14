package experiment;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Board {
    public Tag[][] cell;
    Rectangle[][] square;

    public Board(int size) {
        this.square = new Rectangle[size][size];
        this.cell = new Tag[size][size];

        for(int y = 0; y < size; ++y) {
            for(int x = 0; x < size; ++x) {
                this.cell[x][y] = Tag.EMPTY;
            }
        }

    }

    public Tag getCell(int x, int y) {
        return this.cell[x][y];
    }

    public Rectangle[][] getSquare() {
        return this.square;
    }

    public void hover(Group group, final int xPos, final int yPos, final int size, final int cellSpan) throws Exception {
        group.setOnMouseMoved(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if (event.getX() < (double)(xPos + cellSpan * size) && event.getX() > (double)xPos && event.getY() < (double)(yPos + cellSpan * size) && event.getX() > (double)yPos) {
                    final int x = (int)(event.getX() - (double)xPos) / cellSpan;
                    final int y = (int)(event.getY() - (double)yPos) / cellSpan;
                    Board.this.square[x][y].setOnMouseEntered(new EventHandler<MouseEvent>() {
                        public void handle(MouseEvent t) {
                            if (x == size - 1) {
                                if (y != size - 1) {
                                    Board.this.square[x][y].setFill(Color.LIGHTBLUE);
                                    Board.this.square[x][y + 1].setFill(Color.LIGHTBLUE);
                                } else if (y == 0) {
                                    Board.this.square[x][y].setFill(Color.LIGHTBLUE);
                                    Board.this.square[x][y + 1].setFill(Color.LIGHTBLUE);
                                } else {
                                    Board.this.square[x][y].setFill(Color.LIGHTBLUE);
                                }
                            } else if (y == size - 1) {
                                Board.this.square[x][y].setFill(Color.LIGHTBLUE);
                            } else if (y == 0) {
                                if (x != size - 1) {
                                    Board.this.square[x][y].setFill(Color.LIGHTBLUE);
                                    Board.this.square[x][y + 1].setFill(Color.LIGHTBLUE);
                                    Board.this.square[x + 1][y + 1].setFill(Color.LIGHTBLUE);
                                } else {
                                    Board.this.square[x][y].setFill(Color.LIGHTBLUE);
                                    Board.this.square[x][y + 1].setFill(Color.LIGHTBLUE);
                                }
                            } else if (x == 0) {
                                Board.this.square[x][y].setFill(Color.LIGHTBLUE);
                                Board.this.square[x][y + 1].setFill(Color.LIGHTBLUE);
                                Board.this.square[x + 1][y + 1].setFill(Color.LIGHTBLUE);
                            } else {
                                Board.this.square[x][y].setFill(Color.LIGHTBLUE);
                                Board.this.square[x][y + 1].setFill(Color.LIGHTBLUE);
                                Board.this.square[x + 1][y + 1].setFill(Color.LIGHTBLUE);
                            }

                        }
                    });
                    Board.this.square[x][y].setOnMouseExited(new EventHandler<MouseEvent>() {
                        public void handle(MouseEvent t) {
                            if (x == size - 1) {
                                if (y != size - 1) {
                                    Board.this.square[x][y].setFill(Color.LIGHTGREY);
                                    Board.this.square[x][y + 1].setFill(Color.LIGHTGREY);
                                } else if (y == 0) {
                                    Board.this.square[x][y].setFill(Color.LIGHTGREY);
                                    Board.this.square[x][y + 1].setFill(Color.LIGHTGREY);
                                } else {
                                    Board.this.square[x][y].setFill(Color.LIGHTGREY);
                                }
                            } else if (y == size - 1) {
                                Board.this.square[x][y].setFill(Color.LIGHTGREY);
                            } else if (y == 0) {
                                if (x != size - 1) {
                                    Board.this.square[x][y].setFill(Color.LIGHTGREY);
                                    Board.this.square[x][y + 1].setFill(Color.LIGHTGREY);
                                    Board.this.square[x + 1][y + 1].setFill(Color.LIGHTGREY);
                                } else {
                                    Board.this.square[x][y].setFill(Color.LIGHTGREY);
                                    Board.this.square[x][y + 1].setFill(Color.LIGHTGREY);
                                }
                            } else if (x == 0) {
                                Board.this.square[x][y].setFill(Color.LIGHTGREY);
                                Board.this.square[x][y + 1].setFill(Color.LIGHTGREY);
                                Board.this.square[x + 1][y + 1].setFill(Color.LIGHTGREY);
                            } else {
                                Board.this.square[x][y].setFill(Color.LIGHTGREY);
                                Board.this.square[x][y + 1].setFill(Color.LIGHTGREY);
                                Board.this.square[x + 1][y + 1].setFill(Color.LIGHTGREY);
                            }

                        }
                    });
                }

            }
        });
    }

    public void drawBoard(Group group, int xPos, int yPos, int size, int cellSpan) {
        int temp = xPos;
        this.square = new Rectangle[size][size];

        for(int y = 0; y < size; ++y) {
            for(int x = 0; x < size; ++x) {
                if (this.getCell(x, y) == Tag.EMPTY) {
                    this.square[x][y] = new Rectangle((double)temp, (double)yPos, (double)cellSpan, (double)cellSpan);
                    this.square[x][y].setFill(Color.LIGHTGREY);
                    this.square[x][y].setStroke(Color.BLACK);
                    this.square[x][y].setStrokeWidth(2.0D);
                }

                temp += cellSpan;
                group.getChildren().add(this.square[x][y]);
            }

            temp = xPos;
            yPos += cellSpan;
        }

    }

    public void drawUI(Group group, int xPos, int yPos, int cellSpan, int size, String title) {
        int wX = xPos + cellSpan / 3;
        int wY = yPos - 5;

        int i;
        Text text;
        for(i = 0; i < size; ++i) {
            char c = (char)(65 + i);
            text = new Text((double)wX, (double)wY, Character.toString(c));
            text.setFill(Color.BLACK);
            text.setFont(Font.font("Arial", 20.0D));
            wX += cellSpan;
            group.getChildren().add(text);
        }

        wX = xPos - 25;
        wY = yPos + 2 * cellSpan / 3;

        for(i = 0; i < size; ++i) {
            int a = 1 + i;
            text = new Text((double)wX, (double)wY, Integer.toString(a));
            text.setFill(Color.BLACK);
            text.setFont(Font.font("Arial", 20.0D));
            wY += cellSpan;
            group.getChildren().add(text);
        }

        Label label = new Label(title);
        label.setFont(new Font("Arial", 20.0D));
        label.setTranslateX((double)xPos);
        label.setTranslateY((double)(yPos + cellSpan * size + 30));
        group.getChildren().add(label);
    }
}

