package midterm;

public class Frigate extends Ship {
    
    private final int[] indexH = {0,4};
    private final int[] indexV = {5,9};

    Frigate(char orientation) {
        super("Frigate   ", orientation, 2);
        
        if (this.orientation == 'H')
            this.initPieces(indexH);
        else
            this.initPieces(indexV);
    }
}
