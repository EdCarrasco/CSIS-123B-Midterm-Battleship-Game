package midterm;

public class Battleship extends Ship {
    
    private final int[] indexH = {0,1,2,3,4};
    private final int[] indexV = {5,6,7,8,9};

    Battleship(char orientation) {
        super("Battleship", orientation, 5);
        
        if (this.orientation == 'H')
            this.initPieces(indexH);
        else
            this.initPieces(indexV);
    }
}