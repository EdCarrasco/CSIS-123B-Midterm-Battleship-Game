package midterm;

public class MineSweep extends Ship {
    
    private final int[] indexH = {0,1,4};
    private final int[] indexV = {5,6,9};

    MineSweep(char orientation) {
        super("MineSweep ", orientation, 3);
        
        if (this.orientation == 'H')
            this.initPieces(indexH);
        else
            this.initPieces(indexV);
    }
}
