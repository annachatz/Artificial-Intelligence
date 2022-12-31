import java.util.ArrayList;
import java.util.Scanner;


public class Reversi {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Board b = new Board();
        b.setPlayer(1);
        int order;
        System.out.print("Choose max search Depth: ");
        int d = sc.nextInt();
        b.setMaxDepth(d);
        System.out.print("Player choose your order (1 for computer 2 for player): ");
        order = sc.nextInt();
        int row = -1;
        int col = -1;
        ArrayList<Move> Available_moves = new ArrayList<>();
        while (!b.isTerminal()) {
            Available_moves = b.getAvailableMoves();
            if(order ==1){
                b.print();
                Available_moves.clear();
            }
            else{
                b.print();
                if(Available_moves.isEmpty()){
                    System.out.println("No available moves");
                }
                else{
                    b.printAvailableMoves();
                    System.out.print("Player choose coordinates x y: ");
                    row = sc.nextInt();
                    col = sc.nextInt();
                    row--;
                    col--;
                }
            }
            order++;
            if (Available_moves.isEmpty()) {
                b.changePlayer();
                Move comp_move = b.pickMove(b);     //computer plays
                if (comp_move != null) {
                    b.makeMove(comp_move.getRow(), comp_move.getCol());
                }
            }
            else if (b.checkValidMove(row, col)) {
                b.makeMove(row, col);   //player plays
                b.print();
                Move comp_move = b.pickMove(b); //computer plays
                if (comp_move != null) {
                    b.makeMove(comp_move.getRow(), comp_move.getCol());
                } else {
                    b.changePlayer();
                }
            }
        }
        int scoreX = 0;
        int scoreO = 0;
        for(int i =0;i<b.size;i++){
            for(int j =0;j<b.size;j++){
                if(b.board[i][j]==1){
                    scoreX++;
                }
                else if(b.board[i][j]==2){
                    scoreO++;
                }
            }
        }
        if(scoreX>scoreO){
            System.out.printf("Human player wins with score: %d",scoreX);
        }
        else if(scoreO>scoreX){
            System.out.printf("Computer  wins with score: %d",scoreO);
        }
        else{
            System.out.printf("Draw with score %d",scoreX);
        }


    }
}
