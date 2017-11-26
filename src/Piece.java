package midterm;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Piece extends Label
{
    private static final Image water = new Image("file:Images\\batt100.gif");
    private static final Image wave = new Image("file:Images\\batt102.gif");
    
    private static final Image stern = new Image("file:Images\\batt1.gif");
    private static final Image bow = new Image("file:Images\\batt5.gif");
    private static final Image[] deck = {
        new Image("file:Images\\batt2.gif"),
        new Image("file:Images\\batt3.gif"),
        new Image("file:Images\\batt4.gif")
    };
    
    private static final Image sternSunk = new Image("file:Images\\batt201.gif");
    private static final Image bowSunk = new Image("file:Images\\batt205.gif");
    private static final Image[] deckSunk = {
       new Image("file:Images\\batt202.gif"),
        new Image("file:Images\\batt203.gif"),
        new Image("file:Images\\batt204.gif")
    };
    private static final Image explosion = new Image("file:Images\\batt103.gif");
    
    private Ship ship;
    private int index;
    private boolean sunk;
    
    Piece() {
        this(null, -1);
    }
    
    Piece(Ship ship, int index) {
        this.ship = ship;
        this.index = index;
        this.sunk = false;
    }
    
    public void linkToShip(Ship ship, int index) {
        this.ship = ship;
        this.index = index;
        this.ship.addPiece(this, index);
    }
    
    public void update() {
        // Determine the graphic that corresponds to this Piece of the Ship.
        Image image;
        if (this.index == -1) {
            if (this.sunk)
                image = wave;
            else
                image  = water;
        }
        else {
            if (this.ship.isSunk()) {
                image = this.index == 0 ? Piece.sternSunk 
                      : this.index == this.ship.length()-1 ? Piece.bowSunk 
                      : Piece.deckSunk[index-1];
            }
            else if (this.sunk) {
                image = Piece.explosion;
            }
            else {
                image = this.index == 0 ? Piece.stern 
                      : this.index == this.ship.length()-1 ? Piece.bow 
                      : Piece.deck[index-1];
            }
        }
        ImageView imgView = new ImageView(image);
        
        // Rotate piece according to ship's orientation and direction
        if (this.ship != null) {
            if (this.ship.getOrientation() == 'V')
                if (this.ship.getDirection() == 1)
                    imgView.setRotate(90);
                else
                    imgView.setRotate(-90);
            else if (this.ship.getDirection() == -1)
                imgView.setRotate(180);
        }

        // Update the Label graphic
        this.setGraphic(imgView);
    }
    
    public Ship getShip() {
        return this.ship;
    }
    
    public boolean isSunk() {
        return this.sunk;
    }
    
    public void click() {
        // Sink this piece of the ship
        this.sunk = true;
        this.update();
        
        // Check if the rest of the ship has also been sunk
        if (this.ship != null && this.ship.isSunk()) {
            this.ship.update();
            System.out.println(this.ship.getName() + " has been sunk!");
        }
    }
    
    public int getIndex() {
        return this.index;
    }
    
    public boolean hasShip() {
        return this.ship != null;
    }
}
