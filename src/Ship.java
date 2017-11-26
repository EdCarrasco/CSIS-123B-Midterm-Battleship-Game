package midterm;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Ship
{
    protected String name;
    protected char orientation;
    protected int direction;
    private Piece[] pieces;
    
    // Constructors
    
    protected Ship(String name, char orientation, int size) {
        this.name = name;
        this.orientation = orientation;
        this.pieces = new Piece[size];
    }
    
    // Mutators
    
    public void attachPieces(Piece[][] labelGrid, int row, int col) {
        if (this.getOrientation() == 'H') {
            for(int i = 0, c = col; i < this.length(); c += this.direction, i++) {  
                labelGrid[row][c].linkToShip(this, i);
            }
        }
        else {
            for(int i = 0, r = row; i < this.length(); r += this.direction, i++) {
                labelGrid[r][col].linkToShip(this, i);
            }
        }
    }
    
    public void addPiece(Piece piece, int index) {
        this.pieces[index] = piece;
    }
    
    public void update() {
        for (Piece piece : this.pieces) {
            piece.update();
        }
    }
    
    public void setDirection(int direction) {
        this.direction = direction;
    }
    
    // Accessors
    
    public String getName() {
        return this.name;
    }

    public char getOrientation() {
        return this.orientation;
    }
    
    public int getDirection() {
        return this.direction;
    }

    public int length() {
        return pieces.length;
    }
    
    public boolean isSunk() {
        for (Piece piece : this.pieces) {
            if (!piece.isSunk())
                return false;
        }
        return true;
    }
}