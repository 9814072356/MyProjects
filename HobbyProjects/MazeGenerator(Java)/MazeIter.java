import java.util.*;
import java.util.Random;
public class MazeIter{ 
    int iter = 0;  
    public ArrayList<Pos> checkNeighbors(Maze maze, int x, int y){                                          
        ArrayList<Pos> neighbors = new ArrayList<Pos>();                                                //Check the availability of neighbors cells then 
        if(maze.getPos(x+1,y) == MazeEntries.PATH || maze.getPos(x+1,y) == MazeEntries.EXIT){           //put into array. Neighbors are available only when    
            Pos p = new Pos(x+1,y);                                                                     //they are PATH or EXIT.
            neighbors.add(p);
        }
        if(maze.getPos(x,y+1) == MazeEntries.PATH || maze.getPos(x,y+1) == MazeEntries.EXIT){
            Pos p = new Pos(x,y+1);
            neighbors.add(p);
        }
        if(y > 0 && (maze.getPos(x,y-1) == MazeEntries.PATH || maze.getPos(x,y-1) == MazeEntries.EXIT)){
            Pos p = new Pos(x,y-1);
            neighbors.add(p);
        }
        if(x > 0 && (maze.getPos(x-1,y) == MazeEntries.PATH || maze.getPos(x-1,y) == MazeEntries.EXIT)){
            Pos p = new Pos(x-1,y);
            neighbors.add(p);
        }
        return neighbors;
    }
    public WayPointList pathFinder(WayPointList wp,Maze maze, int x, int y){
        while(checkNeighbors(maze, x, y).size() > 0){                       //Iterate only when at least 1 neighbor is available
            ArrayList<Pos> neighbors = checkNeighbors(maze, x, y);
            for(int i = 0; i < neighbors.size(); i++){
                if(maze.getPos(neighbors.get(i).getPosX(), neighbors.get(i).getPosY()) == MazeEntries.EXIT){        //If one of the neighbors is EXIT, end the loop
                    System.out.printf("                   %d ITERATIONS | %d STEPS\n\n", iter,wp.length(wp));
                    return wp;
                }
            } 
            Random rand = new Random();
            int index = rand.nextInt(neighbors.size());             //Pick a random neighbor to go to
            int nX = neighbors.get(index).getPosX();
            int nY = neighbors.get(index).getPosY();
            maze.setPos(nX,nY,MazeEntries.WALKED);                  //Set the visited neighbor to WALKED
            wp.append(nX, nY);                                  //Append the neighbor to the Waypointlist
            x = nX;                                                 //Continue with the current cell
            y = nY;
        }
        maze.setPos(x, y, MazeEntries.DEADEND);                     //Set the cell to DEADEND when no neighbor is available
        /**
         * Iterate from last to first of Waypoint list.
         * Continue removing cells from the list until a cell is found,
         * which has at least one neighbor available.
         * If waypointlist is empty after removing cells, the maze is unsolvable.
         */
        while(checkNeighbors(maze,wp.getLast(wp).getX(),wp.getLast(wp).getY()).size() == 0){    
            wp = wp.pop(wp);                                                                    
            if(wp == null){                                                                     
                System.out.print("No solution");                                                
                return wp;
            }
        }
        iter++;
        return pathFinder(wp, maze, wp.getLast(wp).getX(), wp.getLast(wp).getY());              //Recursively call the function until EXIT is found
    }
}