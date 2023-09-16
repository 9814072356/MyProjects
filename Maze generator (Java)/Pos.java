/**
 * This class stores coordinates of neighbor cells
 */
public class Pos{
    int X;
    int Y;
    public Pos(int X, int Y){
        this.X = X;
        this.Y = Y;
    }
    public int getPosX(){
        return this.X;
    }
    public int getPosY(){
        return this.Y;
    }
}