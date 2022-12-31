
public class Move {

    // Just a board square
    public int row;
    public int col;
    private int value;

    public Move(int i, int j){

        row= i;
        col= j;
        this.value = 0;
    }
    public void setValue(int val){
        this.value = val;
    }

    public int getCol() {
        return col;
    }

    public int getValue() {
        return value;
    }

    public int getRow() {
        return row;
    }
}

