public class Board{

    final int N = 8;
    Tag[][] board;
    Tag myStone;

    public Board(Tag myStone){
        this.board = new Tag[N][N];
        this.myStone = myStone;
        for(int y = 0; y < N; y++){
            for(int x = 0; x < N; x++){
                if((y == N/2 && x == N/2 - 1) || (y == N/2 - 1 && x == N/2)){
                    this.setPos(x,y,Tag.X);
                }else if((y == N/2 - 1 && x == N/2 - 1) || (y == N/2 && x == N/2)){
                    this.setPos(x,y,Tag.O);
                }else{
                    this.setPos(x,y,Tag.EMPTY);
                }
            }
        }
    }

    public Tag getPos(int x, int y){
        return this.board[x][y];
    }

    public void setPos(int x, int y, Tag pos){
        this.board[x][y] = pos;
    }

    public int getSize(){
        return this.board.length;
    }

    // Print the board
    public void printBoard(){
        System.out.printf("%s"," |");
        for(int t = 0; t < getSize(); t++){
            System.out.printf("%c|",t + 'A');
        }
        for(int y = 0; y < getSize(); y++){
            for(int x = 0; x < getSize(); x++){
                if(x == 0){
                    System.out.printf("\n%c|%c|",y + '1',board[x][y] == Tag.EMPTY ? '_' : board[x][y] == Tag.NEXT ? '+' : board[x][y] == Tag.X ? 'X' : 'O');
                }else{
                    System.out.printf("%c|",board[x][y] == Tag.EMPTY ? '_' : board[x][y] == Tag.NEXT ? '+' : board[x][y] == Tag.X ? 'X' : 'O');
                }
            }			
        }
        System.out.printf("%s","\n");
    }

    // Check whether position (x,y) is on the board
    public boolean out_of_bounds(int x, int y) {
        return x < 0 || x > N - 1 || y < 0 || y > N - 1;
    }

    // If it is X's turn, then "my stone" is 'X', otherwise it is 'O'
    public Tag myStone(){
        return this.myStone;
    }

    // If it is X's turn, then "your stone" is 'O', otherwise it is 'X'
    public Tag yourStone() {
        return (this.myStone == Tag.X) ? Tag.O : Tag.X;
    }

    // Switch the my stones and your stones ('X' <--> 'O')
    public void switchPlayer(){
        this.myStone = (this.myStone == Tag.X) ? Tag.O : Tag.X;
    }

    // Check whether (x,y) is a legal position to place a stone. A position is legal
    // if it is empty ('_'), is on the board, and has at least one legal direction.
    public boolean legal(int x, int y) {
        return !out_of_bounds(x,y) && (this.getPos(x,y) == Tag.EMPTY || this.getPos(x,y) == Tag.NEXT) 
                                   && ( legalDir(x,y,-1,-1)
                                        || legalDir(x,y,0,-1)
                                        || legalDir(x,y,1,-1)
                                        || legalDir(x,y,-1, 0)
                                        || legalDir(x,y,1,0)
                                        || legalDir(x,y,-1,1)
                                        || legalDir(x,y,0,1)
                                        || legalDir(x,y,1,1)
                                    );
    }

    // Check whether, if starting at (x,y), direction (dx,dy) is a legal direction
    // to reverse stones. A direction is legal if (assuming my stone is 'human_hand')
    // the pattern in that direction is: computer_hand+human_hand.* (one or more 'computer_hand's, followed by an 'human_hand', 
    // followed by zero or more arbitrary characters).
    // (dx,dy) is element of { (a,b) | a, b in {-1, 0, 1} and (a,b) != (0,0) }
    public boolean legalDir(int x, int y, int dx, int dy){
        x += dx ;
        y += dy ;
        while (!out_of_bounds(x,y) && this.getPos(x,y) == this.yourStone()){
            x += dx ;
            y += dy ;
            if (!out_of_bounds (x,y) && (this.getPos(x,y) == this.myStone())){
                return true ;
            }
        }
        return false ;
    }

    // Reverse stones starting at (x,y) in direction (dx,dy), but only if the 
    // direction is legal. May modify the state of the game.
    // (dx,dy) is element of { (a,b) | a, b in {-1, 0, 1} and (a,b) != (0,0) }
    public void reverse_dir(int x, int y, int dx, int dy) {
        if(!legalDir(x,y,dx ,dy)) return ;
        do {
            this.setPos(x,y,this.myStone());
            x += dx ;
            y += dy ;
        }
        while(!out_of_bounds(x,y) && (this.board[x][y] == this.yourStone()));
    }
    
    // Reverse the stones in all legal directions starting at (x,y).
    // May modify the state of the game.
    public void reverse(int x, int y) {
        for(int d1 = -1; d1 <= 1; d1++){
            for(int d2 = -1; d2 <= 1; d2++){
                if((d1 != 0 || d2 != 0) && legalDir(x,y,d1,d2) == true){
                    reverse_dir(x, y, d1, d2);
                }
            }
        }
    }

    //Count the number of each player's existing stones
    public int count_stones(Tag tag) {
        int X = 0;
        int O = 0;
        for(int y = 0; y < N; y++){
            for(int x = 0; x < N; x++){
                if(tag == Tag.X && this.board[x][y] == Tag.X){
                    X++;
                }else if(tag == Tag.O && this.board[x][y] == Tag.O){
                    O++;
                }
            }
        }
        return tag == Tag.X ? X : O;
    }

    // Input a position of the form D6 or d6, i.e., giving the column first and 
    // then the row. A1 corresponds to position (0,0). B1 corresponds to (1,0).
    public int possibleMoves(Tag myStone){
        int count = 0;
        for(int y = 0; y < N; y++){
            for(int x = 0; x < N; x++){
                if(legal(x,y)){
                    this.setPos(x,y,Tag.NEXT);
                    count++;
                }	
            }
        }
        return count;
    }

    //Set the cell with '+' mark to its original state
    public void reset(){
        for(int y = 0; y < N; y++){
            for(int x = 0; x < N; x++){
                if(this.board[x][y] == Tag.NEXT){
                    this.setPos(x,y,Tag.EMPTY);
                }
            }
        }	
    }

    //Check if the board is full
    public boolean full(){
        int legal_space = 0;
        for(int y = 0; y < N; y++){
            for(int x = 0; x < N ; x++){
                if(this.board[x][y] == Tag.EMPTY || this.legal(x,y) || this.board[x][y] == Tag.NEXT){
                    legal_space++;
                }
            }
        }
        return legal_space == 0;
    }
}