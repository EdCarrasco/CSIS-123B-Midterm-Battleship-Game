package midterm;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Ship
{
    private static final int IMAGESHIP = 10;
    private static final int IMAGESUNK = 10;
    private static final Image[] imagesShip = new Image[IMAGESHIP];
    private static final Image[] imagesSunk = new Image[IMAGESUNK];
    
    protected String name;
    protected char orientation;
    protected int direction;
    protected Image[] imgPieces;
    protected ImageView[] imgViewPieces;
    
    protected Ship(String name, char orientation, int size) {
        this.name = name;
        this.orientation = orientation;
        this.imgPieces = new Image[size];
        this.imgViewPieces = new ImageView[size];
    }
    
    protected void initPieces(int[] indexes) {
        for (int i = 0; i < indexes.length; i++) {
            int index = indexes[i];
            this.imgPieces[i] = Ship.imagesShip[index];
            this.imgViewPieces[i] = new ImageView(this.imgPieces[i]);
        }
    }
    
    public String getName() {
        return this.name;
    }

    public char getOrientation() {
        return this.orientation;
    }
    
    public void setDirection(int direction) {
        this.direction = direction;
    }
    
    public int getDirection() {
        return this.direction;
    }
    
    public void setVisible(int index) {
        String filepath;
        if (this.orientation == 'H')
            filepath = "file:Images\\batt" + (index + 1) + ".gif";
        else
            filepath = "file:Images\\batt" + (index + 6) + ".gif";
        Image img = new Image("file:Images\\batt" + (index + 1) + ".gif");
        ImageView imgView = new ImageView(img);
        this.imgViewPieces[index] = imgView;
    }
    
    public void setHidden(int index) {
        this.imgViewPieces[index] = new ImageView(new Image("file:Images\\batt100.gif"));;
    }
    
    public void setHit(int index) {
        this.imgViewPieces[index] = new ImageView(new Image("file:Images\\batt103.gif"));;
    }
    
    public ImageView getImageView(int index) {
        ImageView imgView = this.imgViewPieces[index];
        // If the ship faces the opposite direction, rotate the piece 180°
        if (this.direction == -1)
            imgView.setRotate(180);
        return imgView;
    }

    public int length() {
        return imgPieces.length;
    }

    static public void loadStaticImages() {
        for (int i = 0; i < IMAGESHIP ; i++) {
            imagesShip[i] = new Image("file:Images\\batt" + (i + 1) + ".gif");
            
        }
        for (int i = 0; i < IMAGESUNK; i++) {
            imagesSunk[i] = new Image("file:Images\\batt" + (201 + i) + ".gif");
        }
    }

    public Image getShipPiece(int index) {
        return this.imgPieces[index];
    }
    
    static public ImageView getHitImageView() {
        return new ImageView(new Image("file:Images\\batt103.gif"));
    }
    
    static public ImageView getMissImageView() {
        return new ImageView(new Image("file:Images\\batt102.gif"));
    }
}