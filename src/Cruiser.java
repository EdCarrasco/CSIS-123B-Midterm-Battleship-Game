package midterm;

public class Cruiser extends Ship {
    
    private final int[] indexH = {0,1,2,4};
    private final int[] indexV = {5,6,7,9};

    Cruiser(char orientation) {
        super("Cruiser   ", orientation, 4);
        
        if (this.orientation == 'H')
            this.initPieces(indexH);
        else
            this.initPieces(indexV);
    }
}
