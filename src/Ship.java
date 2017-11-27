package midterm;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Ship
{
    protected String name;
    protected char orientation;
    protected int direction;
    private Cell[] pieces;
    
    // Constructors
    
    protected Ship(String name, char orientation, int size) {
        this.name = name;
        this.orientation = orientation;
        this.pieces = new Cell[size];
    }
    
    // Mutators
    
    public void attachPieces(Cell[][] labelGrid, int row, int col) {
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
    
    public void addPiece(Cell piece, int index) {
        this.pieces[index] = piece;
    }
    
    public void update() {
        for (Cell piece : this.pieces) {
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
        for (Cell piece : this.pieces) {
            if (!piece.isSunk())
                return false;
        }
        return true;
    }
}