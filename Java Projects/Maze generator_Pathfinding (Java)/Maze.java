public class Maze{
    public MazeEntries[][] mazeInterface;
    public Maze(String s){
        mazeInterface = new MazeEntries[getHeight(s)][getWidth(s)];
        char[] samples = s.toCharArray();
        int track = 0;
        while(samples[track] != '|'){          //Skip line which gives out the width and height of maze
            track++;
        }
        for(int x = 0; x < mazeInterface.length; x++){
            for(int y = 0; y < mazeInterface[x].length; y++){
                if(samples[track] != '\n'){                     //Skipp '\n'
                    switch(samples[track]){
                        case ' ':  setPos(x,y,MazeEntries.PATH); break;
                        case '|':  setPos(x,y,MazeEntries.WALL); break;
                        case 'e':  setPos(x,y,MazeEntries.ENTRY); break;
                        case 'E':  setPos(x,y,MazeEntries.EXIT); break;
                    };
                }
                track++;
            }
            track++;
        }
    }

    public void setPos(int x, int y, MazeEntries data){
        mazeInterface[x][y] = data;
    }

    public MazeEntries getPos(int x, int y){
        return mazeInterface[x][y];
    }

    /**
     * Get height of maze based on the given string format
     * @param s
     * @return
     */
    public int getHeight(String s){                             
        String str = new String(s);
        char[] samples = s.toCharArray();
        int track1;
        int track2 = 0;
        while(samples[track2] != ' '){
            track2++;
        }
        track1 = track2+1;
        while(samples[track2] != '\n'){
            track2++;
        }
        return Integer.parseInt(str.substring(track1,track2));
    }

    /**
     * Get width of maze based on the given string format
     * String format: e.g "33 33\n"
     * @param s
     * @return
     */
    public int getWidth(String s){
        String str = new String(s);

        char[] samples = s.toCharArray();
        int track = 0;
        while(samples[track] != ' '){
            track++;
        }
        return Integer.parseInt(str.substring(0,track));
    }
    
    /**
     * Get the coordinates of the entry
     * @return
     */
    public int[] getStart(){
        int[] start = new int[2];
        for(int x = 0; x < this.mazeInterface.length; x++){
            for(int y = 0; y < this.mazeInterface[x].length; y++){
                if(this.mazeInterface[x][y] == MazeEntries.ENTRY){
                    start[0] = x;
                    start[1] = y;
                }
            }
        }
        return start;
    } 

    /**
     * Print out the maze
     */
    public void print(){
        for(int x = 0; x < mazeInterface.length; x++){
            for(int y = 0; y < mazeInterface[x].length; y++){
                if(getPos(x,y) == MazeEntries.WALL){
                    System.out.print("|");
                }else if(getPos(x,y) == MazeEntries.PATH){
                    System.out.print(" ");
                }else if(getPos(x,y) == MazeEntries.ENTRY){
                    System.out.print(">");
                }else if(getPos(x,y) == MazeEntries.EXIT){
                    System.out.print("_");
                }else if(getPos(x,y) == MazeEntries.SOLUTION){
                    System.out.print("°");
                }else if(getPos(x,y) == MazeEntries.DEADEND){
                    System.out.print("X");
                }else if(getPos(x,y) == MazeEntries.WALKED){
                    System.out.print(".");
                }else{
                    System.out.print("-");
                }
            }
            System.out.print("\n");
        }
    }
    /**
     * Set a cell of the maze to SOLUTION
     * @param path List contains the solutions to the maze
     */
    public void setSolution(WayPointList path){
        for(WayPointList p = path; p != null; p = p.getNext()){
            mazeInterface[p.getX()][p.getY()] = MazeEntries.SOLUTION;
        }
    }
}