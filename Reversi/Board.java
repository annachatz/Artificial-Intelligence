import java.util.ArrayList;
import java.util.Random;


public class Board {
    int all_plays = 0; // counter of all moves of both players is important for print (further explanation in function print)
    protected int[][]board;
    protected int size = 8;
    private int maxDepth;
    public int player;
    public int weights[][] = new int[size][size];

    public Board(){
        board = new int[size][size];
        for(int i =0;i<this.board.length;i++){
            for(int j =0;j<this.board.length;j++){
                this.board[i][j] = 0;
            }
        }
        board[(size/2)-1][(size/2)-1] = 1;//X
        board[(size/2)-1][(size/2)] = 2;//O
        board[(size/2)][(size/2)-1] = 2;
        board[size/2][size/2] = 1;
        setWeights();

    }

    public void setMaxDepth(int maxDepth){
        this.maxDepth = maxDepth;
    }
    public void setWeights(){ //the board of weights that is going to be used in our heuristic
        for(int row=0;row<size/2;row++){
            for(int col=0;col<size/2;col++){
                weights[row][col]=5*((10-row)+(10-col));
                weights[size-1-row][col]=5*((10-row)+(10-col));
                weights[row][size-1-col]=5*((10-row)+(10-col));
                weights[size-1-row][size-1-col]=5*((10-row)+(10-col));
            }
        }
    }

    Board(Board b){
        this.board = new int[size][size];
        for(int i =0;i<size;i++){
            for(int j =0;j<size;j++){
                this.board[i][j] = b.board[i][j];
            }
        }
        this.player = b.player;
    }

    public void setPlayer(int p){
        this.player = p;
    }

    public void changePlayer(){
        if(this.player==1){
            this.player =2;
        }
        else{
            this.player = 1;
        }
    }
    public int otherPlayer(){
        if(this.player==1){
            return 2;
        }
        else{
            return 1;
        }
    }

    public boolean isValidMove(int x,int y){
        return (x >=0) && (x < size) &&  (y >=0) && (y < size) && (board[x][y]==0);
    }

    public boolean checkValidMove(int x, int y){
        return isValidMove(x,y) && checkDirections(x,y);
    }

    public boolean checkDirections(int x, int y){
        return !(moveRight(x, y)+ moveLeft(x,y)+ moveUp(x, y)+ moveDown(x, y)+ moveUpRight(x, y)+ moveUpLeft(x, y)+ moveDownRight(x, y)+ moveDownLeft(x, y)==-8);
    }

    public ArrayList<Move> getAvailableMoves(){  //gets all Available moves for the current player

        ArrayList<Move> backup= new ArrayList<>();
        for(int i =0;i<size;i++){
            for(int j =0;j<size;j++){
                if(checkValidMove(i,j)){
                    backup.add(new Move(i,j));
                }
            }
        }
        return backup;
    }
    public int moveLeft(int x, int y){

        if(y-1>=0){ //out of bounds
            if(board[x][y-1]==otherPlayer()){
                int col = y-1;
                while(col>=0 && board[x][col]!= 0){
                    if(board[x][col] == player){
                        return col;
                    }
                    col--;
                }
            }
        }
        return -1;
    }
    public int moveRight(int x, int y){

        if(y+1<size){ // out of bounds
            if(board[x][y+1] == otherPlayer()){
                int c = y+1;
                while(c<size && board[x][c] != 0){
                    if(board[x][c] == player){
                        return c;
                    }
                    c++;
                }
            }
        }
        return -1; // doesn't exist
    }
    public int moveUp(int x, int y){

        if(x-1>=0){ //out of bounds
            if(board[x-1][y]==otherPlayer()){
                int c = x-1;
                while(c>=0 && board[c][y] != 0){
                    if(board[c][y] == player){
                        return c;
                    }
                    c--;
                }
            }
        }
        return -1;
    }
    public int moveDown(int x, int y){

        if(x+1<size){ //out of bounds
            if(board[x+1][y]==otherPlayer()){
                int c= x+1;
                while(c<size && board[c][y]!= 0){
                    if(board[c][y] == player) {
                        return c;
                    }
                    c++;
                }
            }
        }
        return -1;
    }
    public int moveUpLeft(int x, int y){

        if(y-1>=0 && x-1>=0){ //out of bounds
            if(board[x-1][y-1]==otherPlayer()){
                int row = x -1;
                int col = y -1;
                while(row>=0 && col >=0 && board[row][col] != 0){
                    if(board[row][col] == player){
                        return row;
                    }
                    row--;
                    col--;
                }
            }
        }
        return -1;
    }
    public int moveUpRight(int x, int y){

        if(y+1<size && x-1>=0){ //out of bounds
            if(board[x-1][y+1]==otherPlayer()){
                int row = x-1;
                int col = y+1;
                while(row>=0 && col<size && board[row][col] != 0){
                    if(board[row][col] == player){
                        return row;
                    }
                    row--;
                    col++;
                }
            }
        }
        return -1;
    }

    public int moveDownLeft(int x, int y){

        if(x+1<size && y-1>= 0){ //out of bounds
            if(board[x+1][y-1]==otherPlayer()){
                int row = x+1;
                int col = y-1;
                while(row<size && col>= 0 && board[row][col]!= 0){
                    if(board[row][col] == player){
                        return row;
                    }
                    row++;
                    col--;
                }
            }
        }
        return -1;
    }
    public int moveDownRight(int x, int y){

        if(y+1<size && x+1<size ){ //out of bounds
            if(board[x+1][y+1]==otherPlayer()){
                int row = x+1;
                int col = y+1;
                while(row<size && col <size && board[row][col] != 0){
                    if(board[row][col] == player){
                        return row;
                    }
                    row++;
                    col++;
                }
            }
        }
        return -1;
    }
    public void setElement(int x1,int y1,int x2,int y2){
        //x1 stands for rowStart and x2 stands for rowEnd(the one one of the checks returns)
        //y1 stands for colStart and y2 stands for colEnd
        int high;
        int low;
        /*
        Here we are in 2 sets of cases either
        checkLeft or checkRight because row stays the same
         */
        if(x1 == x2){
            if(y1>y2) {         //colEnd is smaller than colStart -> moveLeft
                high = y1;
                low = y2;
            }
            else{               //colEnd is higher thn colStart -> moveRight
                high = y2;
                low = y1;
            }
            for(int i = low;i<=high;i++){
                board[x1][i] = player;
            }
        }
        /*
        Here we are in 2 sets of cases either checkUp
        or checkDown because column stays the same
         */
        else if(y1 == y2){
            if(x1>x2){          //rowEnd is smaller than rowStart -> moveUp
                high = x1;
                low = x2;
            }
            else{               //rowEnd is higher than rowStart -> moveDown
                high = x2;
                low = x1;
            }
            for(int i = low;i<=high;i++){
                board[i][y1] = player;
            }
        }
        /*
        2 other cases diagonally when the directions of the row and column are different
        for example row gets higher and col gets lower or the other way
         */
        else if((x1 + y1) == (x2 + y2)){
            int high_col;
            int low_col;
            if(x1<x2){             // if the rows are getting higher then the columns are getting lower -> moveDownLeft
                low = x1;
                high = x2;
                low_col = y2;
                high_col= y1;
            }
            else{                   //if the rows are getting smaller then the columns are getting higher -> moveUpRight
                low = x2;
                high = x1;
                low_col = y1;
                high_col = y2;
            }
            for(int i = low,j=high_col; i<=high;i++,j--){
                board[i][j] = player;
            }
        }
        /*
        another set of 2 cases where the directions of (x,y) are the same,
         for example row gets higher and col gets higher or the other way
         */
        else{
            int low_col;
            if(x1+y1<x2+y2){        //row and col get higher -> moveDownRight
                high = x2;
                low = x1;
                low_col = y1;
            }
            else{                   //row and col get smaller -> moveUpLeft
                high = x1;
                low = x2;
                low_col = y2;
            }
            for(int i =low,j = low_col;i<=high;i++,j++){
                board[i][j] = player;
            }
        }
    }
    public void makeMove(int row, int col){
        if(moveLeft(row,col)!=-1){
            setElement(row,col,row, moveLeft(row,col));// moveLeft changes(reduces) only column and row stays the same
        }
        if(moveRight(row,col)!=-1){
            setElement(row,col,row, moveRight(row,col));//moveRight changes(bigger) only column and row stays the same
        }
        if(moveUp(row,col)!=-1){
            setElement(row,col, moveUp(row,col),col);//moveUp changes(smaller) only row and column stays the same
        }
        if(moveDown(row,col)!=-1){
            setElement(row,col, moveDown(row,col),col);//moveDown changes(bigger) only row and column stays the same
        }
        /*
        moveUpLeft changes both the column and row and reduces both of them
        if changeX(Xstart = 4 and Xend = 2 then the dif= xstart-xend so the end col = col-dif
         */
        if(moveUpLeft(row,col)!=-1){
            setElement(row,col, moveUpLeft(row,col),col-(row- moveUpLeft(row,col)));
        }
        /*
        moveUpRight changes both the column and row.
        It makes row smaller and row higher so if(xstart = 3 and xEnd = 1 then endcol = Startcol+(xstart-Xend))
         */
        if(moveUpRight(row,col)!=-1){
            setElement(row,col, moveUpRight(row,col),col+(row- moveUpRight(row,col)));
        }
        /*
        moveDownLeft changes both the column and row.
        It makes the row higher and the column smaller so
        If(Xstart = 1 and Xend = 3 then the dif = 2 so Endcol = col-dif since column gets smaller)
         */
        if(moveDownLeft(row,col)!=-1){
            setElement(row,col, moveDownLeft(row,col),col-(moveDownLeft(row,col)-row));
        }
        /*
        moveDownRight changes both column and row.
        It makes both of them higher so
        if(Xstart = 2 and Xend = 4 then dif = 2 so Endcol = col+dif since col gets higher)
         */
        if(moveDownRight(row,col)!=-1){
            setElement(row,col, moveDownRight(row,col),col+(moveDownRight(row,col)-row));
        }
        changePlayer();
    }
    void print()
    {
        if(all_plays!=0){//its not the first time print() is called
            System.out.println("**************************");
            if(player==2){
                System.out.println("Human Played");
            }
            else{
                System.out.println("Computer Played");
            }
        }
        all_plays++;
        System.out.println("**************************");
        for(int i =0;i<board.length+1;i++){
            if(i !=0){
                System.out.printf("%s",i);
            }

            for(int j = 0;j<board.length;j++){
                if(i ==0){
                    System.out.printf("  %s",j+1);
                }
                else{
                    char hollowHeart = '\u2661';
                    String idle = "♥";
                    int check = board[i-1][j];
                    if(check == 0){
                        System.out.printf(" %s ","?");
                    }
                    else if(check == 1){
                        System.out.printf(" %s ","X");
                    }
                    else{
                        System.out.printf(" %s ","O");
                    }

                }
            }
            System.out.print("\n");
        }
        System.out.println("**************************");
    }

    //returns the number of free positions on the board
    public int freeMoves(){
        int count = 0;
        for(int i =0;i<size;i++){
            for(int j =0;j<size;j++){
                if(board[i][j] ==0){
                    count++;
                }
            }
        }
        return count;
    }
    public void printAvailableMoves(){
        getAvailableMoves();
        for(int i =0;i<size;i++){
            for(int j =0;j<size;j++){
                if(isInList(getAvailableMoves(),i,j)){
                    System.out.printf("%d ",i+1);
                    System.out.printf("%d ",j+1);
                    System.out.println();
                }
            }
        }
    }

    //returns true if move is on Available moves list
    public static boolean isInList(final ArrayList<Move> list,int x,int y) {

        for(int i =0;i<list.size();i++){
            if(list.get(i).getCol()== y && list.get(i).getRow()== x){
                return true;
            }
        }
        return false;
    }
    public int evaluate(Board b){
        int sumX = 0;   //finds 1
        int sumO = 0;   //finds 2
        for(int i=0; i<size; i++){
            for(int j=0; j<size;j++){
                if(b.board[i][j]==1){
                    sumX += weights[i][j]+1;
                }
                else if(b.board[i][j]==2){
                    sumO += weights[i][j]+1;
                }
            }
        }
        return sumX-sumO;
    }
    public Move pickMove(Board b){

        ArrayList<Move> moves= b.getAvailableMoves();   //gets available moves for the computer
        if(moves.isEmpty())
            return null;
        int position = 0; // for the first value of the ArrayList of move
        int maxMove = Integer.MIN_VALUE;
        int minMove = Integer.MAX_VALUE;
        Move bestMove = null;
        int pos = 0;
        getChildren(moves);     //gets children of each move
        for(Board child: getChildren(moves)){   //for each child minimax is called
            int minimax = miniMax(child,0,maxMove,minMove,false);
            if(maxMove<minimax){
                maxMove=minimax;
                position = pos;
            }
            pos++;
        }
        bestMove = moves.get(position);
        return bestMove;
    }
    ArrayList<Board> getChildren(ArrayList<Move> move) {
        ArrayList<Board> children = new ArrayList<>();
        for(Move m : move) {
            Board child = new Board(this);
            child.makeMove(m.getRow(), m.getCol());
            children.add(child);
        }
        return children;
    }
    public int miniMax(Board b, int depth, int maxMove, int minMove, Boolean max_player){
        Random r = new Random();
        ArrayList<Move> move = b.getAvailableMoves();

        if(b.isTerminal()|| depth==maxDepth){
            //if miniMax is called on a state that is terminal or after endDepth is reached
            return evaluate(b);
        }

        else if(max_player){
            //max function is called when max_player == true
            maxMove = Integer.MIN_VALUE;
            for(Move m: move){
                if(maxMove >= minMove|| move.isEmpty()){
                    break;
                }
                Board backup = new Board(b);
                backup.makeMove(m.getRow(), m.getCol());
                int mini = miniMax(backup, depth+1, maxMove, minMove, false);
                if(mini>=maxMove){
                    if(mini==maxMove){
                        if(r.nextInt(2)==0){
                            maxMove = mini;
                        }
                    }
                    else{
                        maxMove=mini;
                    }
                }
            }
            return maxMove;
        }
        else{
            //min function is called when max_player == false
            minMove=Integer.MAX_VALUE;
            for(Move m: move){
                if(maxMove >= minMove||move.isEmpty()){
                    break;
                }
                Board temp = new Board(b);
                temp.makeMove(m.getRow(), m.getCol());
                int maxi = miniMax(temp, depth+1, maxMove, minMove, true);
                if(maxi<=minMove){
                    if(maxi==minMove){
                        if(r.nextInt(2)==0){
                            minMove= maxi;
                        }
                    }
                    else{
                        minMove=maxi;
                    }
                }
            }
            return minMove;

        }
    }

    boolean isTerminal(){   //both players no moves then end game else continues
        if(getAvailableMoves().isEmpty()){
            changePlayer();
            if(getAvailableMoves().isEmpty()){
                return true;
            }else{
                changePlayer();
            }
        }
        return false;
    }
}
