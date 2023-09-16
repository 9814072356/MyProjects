import java.util.*;
/**
 * this class stores the solution of the maze
 */
public class WayPointList{
    int X;
    int Y;
    WayPointList next;
    public WayPointList(int X, int Y){
        this.X = X;
        this.Y = Y;
        this.next = null;
    }
    public int getX(){
        return this.X;
    }
    public int getY(){
        return this.Y;
    }
    public WayPointList getNext(){
        return this.next;
    }
    /**
     * Append the node with x and y indexes into a list
     * @param wp The list to be appended
     * @param X Index x of the cell
     * @param Y Index y of the cell
     */
    public void append(int X, int Y){
        if(getNext() == null){
            this.next = new WayPointList(X,Y); 
         }else{
            next.append(X,Y); 
         }
    }
    /**
     * Remove the last element from a list
     * @param wp List, whose last element to be removed
     * @return
     */
    public WayPointList pop(WayPointList wp){
        if(wp.getNext() == null){
            return null;
        }else{
            WayPointList temp = new WayPointList(wp.getX(),wp.getY());
            while(wp.next.next != null){
               wp = wp.next;
               temp.append(wp.getX(),wp.getY()); 
            }   
            wp = temp;
        }
        return wp;
    }
    /**
     * Get the last element of a list
     * @param wp
     * @return
     */
    public WayPointList getLast(WayPointList wp){
        while(wp.next != null){
            wp = wp.getNext();
        }
        return wp; 
    }
    /**
     * Get length of a list
     * @param wp
     * @return
     */
    public int length(WayPointList wp){
        int i = 0;
        if(wp != null){
            for(WayPointList p = wp; p != null; p = p.getNext()){
                i++;
            }            
        }
        return i;
    }
}