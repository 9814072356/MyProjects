import java.io.EOFException;
import java.util.*;
public class Main{
    public static void main(String arg[]){
        Board board = new Board(Tag.X);
        int scoreMyStone = board.count_stones(board.myStone()) - board.count_stones(board.yourStone());
        int scoreYourStone = -scoreMyStone;
        board.possibleMoves(board.myStone());
        board.printBoard();
        System.out.printf("Score for " + board.myStone() + ": %d\n", scoreMyStone);
        System.out.printf("Score for " + board.yourStone() + ": %d\n", scoreYourStone);
        System.out.print(board.myStone() + "'s turn: ");
        while(!board.full() && (board.possibleMoves(Tag.X) != 0 || board.possibleMoves(Tag.O) != 0)) {
            try {
                Scanner sc = new Scanner(System.in);
                String s = sc.nextLine();
                Position pos = new Position(0,0);
                pos.inputPosition(board,s);
                board.reset();
                board.reverse(pos.getX(), pos.getY());
                if(board.getPos(pos.getX() , pos.getY()) == board.myStone())board.switchPlayer();
                board.possibleMoves(board.myStone());
                board.printBoard();
                scoreMyStone = board.count_stones(board.myStone()) - board.count_stones(board.yourStone());
                scoreYourStone = -scoreMyStone;
                System.out.printf("Score for " + board.myStone() + ": %d\n", scoreMyStone);
                System.out.printf("Score for " + board.yourStone() + ": %d\n", scoreYourStone);
                System.out.print(board.myStone() + "'s turn: ");      
            }catch(Exception e) {
               break;
            }
        }
        System.out.println("Endgame");
        System.out.println(board.count_stones(board.myStone()) > board.count_stones(board.yourStone()) ? board.myStone() + " wins!" : board.count_stones(board.myStone()) < board.count_stones(board.yourStone()) ? board.yourStone() + " wins!" : "Tie match!");
    }
}