import java.lang.*;
public class Position{
    int x;
    int y;
    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public void printPos(){
        System.out.printf("(%i, %i)\n", this.x, this.y);
    }

    //Create a position and check its legality 
    public void inputPosition(Board board, String s){
        char[] temp = s.toCharArray();
        if (s.length() >= 2) {
            this.x = Character.toLowerCase(temp[0]) - 'a';
            this.y = temp[1] - '1';
        }
        if (!board.legal(this.x, this.y)){
            System.out.println("Invalid position!");
            return;
        }        
    }
}