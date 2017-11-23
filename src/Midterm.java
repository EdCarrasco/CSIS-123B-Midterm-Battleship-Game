 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package midterm;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.*;
import java.awt.Color;
import javafx.geometry.Insets;
import java.util.Random;

/**
 * @author gstev
 * @edited Edward Carrasco
 * @date 11/17/2017
 */
public class Midterm extends Application {
    
    private Random random = new Random();
    
    private static final int MAXSHIPS = 16; // original 14
    private static final int GRIDSIZE  = 16;
    private static final int IMGSIZE = 16;
    private static final int BORDER = 1;
    
    private GridPane gamePane = new GridPane();
    private char[][] charGrid = new char[GRIDSIZE][GRIDSIZE]; 
    private Label[][] labelGrid = new Label[GRIDSIZE][GRIDSIZE];
    
    private Image[] shipImages = new Image[10];
    private Ship[] ships = new Ship[MAXSHIPS];
    
    @Override
    public void start(Stage primaryStage) {
                
        BorderPane root = new BorderPane();
        //int width = GRIDSIZE * (IMGSIZE + BORDER * 2);
        //int height = width + 50;
        Scene scene = new Scene(root, GRIDSIZE*18, GRIDSIZE*18); // 290
        
        primaryStage.setTitle("Battleship");
        primaryStage.setScene(scene);
        primaryStage.show();
        root.setCenter(gamePane);
        
        this.initOcean();
        this.createPlayerPanel();
        this.loadShipImages();
        Ship.loadStaticImages();
        this.createShipsRandomly();
        
        for (int i = 0; i < MAXSHIPS; i++) {
            this.placeShip(ships[i]);
        }
        System.out.println("Sucess! Placed " + MAXSHIPS + " ships in the grid.");
        
        for (int r = 0; r < labelGrid.length; r++) {
            for (int c = 0; c < labelGrid[r].length; c++) {
                final int row = r;
                final int col = c;
                labelGrid[row][col].setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent me) {
                        Label label = new Label();
                        label.setGraphic(new ImageView(new Image("file:Images\\batt101.gif")));
                        labelGrid[row][col] = new Label("xxx");
                        System.out.println(charGrid[row][col]);
                        /*
                        boolean shipWasHit = this.checkCell(row, col);
                        if (shipWasHit) {
                            
                        }
                        */
                    }
                    
                    public boolean checkCell(int row, int col) {
                        String message = "(" + row + "," + col + ") .. ";
                        boolean shipWasHit = false;
                        char c = charGrid[row][col];
                        /*if (c == 'O') {
                            message += "miss";
                            labelGrid[row][col].setGraphic(Ship.getMissImageView());
                        }
                        else if (c == 'X') {
                            message += "already hit";
                        }
                        else if (c == 'F' || c == 'M' || c == 'C' || c == 'B') {
                            message += "HIT";
                            charGrid[row][col] = 'X';
                            labelGrid[row][col].setGraphic(Ship.getHitImageView());
                            shipWasHit = true;
                        }
                        else {
                            message += "ERROR " + c;
                        }*/
                        System.out.println(message + " '" + c + "'");
                        return shipWasHit;
                    }
                });
            }
        }
    }
    
    private void initOcean() {
        // Set the value of each cell in the 2d array of characters.
        for (int row = 0; row < GRIDSIZE; row++) {
            for (int col = 0; col < GRIDSIZE; col++) {
                charGrid[row][col] = 'O';
            }
        }
    }
    
    private void createPlayerPanel() {
        // Sets the background and style of each cell in the 2d array of labels.
        // And places them in the screen by adding them to a gridPane.
        gamePane.setStyle("-fx-background-color:#0000FF;");
        for(int row = 0; row < GRIDSIZE; row++) {
            for(int col = 0; col < GRIDSIZE; col++) {
                labelGrid[row][col] = new Label();               
                labelGrid[row][col].setGraphic(new ImageView(new Image("file:Images\\batt100.gif")));
                labelGrid[row][col].setMaxSize(16.0, 16.0);
                labelGrid[row][col].setStyle("-fx-border-width:1;-fx-border-color:black;");
                
                // Add each of the labels to the gamePane (which is a gridPane object).
                // This way each label is placed in the correct position.
                gamePane.add(labelGrid[row][col], col, row);      
           }
       }
    }
    
    private void loadShipImages() {
        // Store the image pieces of the ship (in horizontal and vertical) in
        // an array, so that they can be retrieved later on more easily.
        for (int i = 0; i < 10 ; i++) {
            shipImages[i] = new Image("file:Images\\batt" + (i + 1) + ".gif");
        }
    }
    
    private void createShips() {
        // Create the Frigate. 2 are created here, but there will be 3 total
        
        // Create all the ships for the game.
        // A new Ship requires:
        // - a name (string)
        // - the indexes of the ship images to use (integer array)
        // - an orientation, either horizontal or vertical (character)
        ships[0] = new Frigate('H');
        ships[1] = new Frigate('V');
        ships[2] = new MineSweep('H');
        ships[3] = new MineSweep('V');
        ships[4] = new Cruiser('H');
        ships[5] = new Cruiser('V');
        ships[6] = new Battleship('H');
        ships[7] = new Battleship('V');
    }
    
    private void createShipsRandomly() {
        // Create ships. Each has a random type and orientation.
        for (int i = 0; i < MAXSHIPS; i++) {
            int type = random.nextInt(4);
            char o = random.nextInt(2) == 0 ? 'H' : 'V';
            ships[i] = type == 0 ? new Frigate(o) 
                    : type == 1 ? new MineSweep(o) 
                    : type == 2 ? new Cruiser(o) 
                    : new Battleship(o);
        }
    }
    
    private void placeShip(Ship ship) {
        // Choose a random row and column
        // Check if the ship's pieces can be placed in that cell and the cells next to it.
        int row, col, direction;
        do {
            row = random.nextInt(GRIDSIZE);
            col = random.nextInt(GRIDSIZE);
            direction = this.checkCells(ship, row, col);
        } while (direction == 0);
        ship.setDirection(direction); // Store the chosen direction.

        System.out.println(ship.getName() + " (" + Integer.toString(row) 
                + "," + Integer.toString(col) 
                + ")\t" + Integer.toString(direction)
                + "\t" + ship.getOrientation());

        // Place each of the ship pieces in their corresponding cells in the grid.
        if (ship.getOrientation() == 'H') {
            for(int i = 0, c = col; i < ship.length(); c += direction, i++) {                                                          
                this.placePiece(ship, row, c, i);
            }
        }
        else if (ship.getOrientation() == 'V') {
            for(int i = 0, r = row; i < ship.length(); r += direction, i++) {
                this.placePiece(ship, r, col, i);
            }
        }
    }
    
    private void placePiece(Ship ship, int row, int col, int index) {
        // Get the image of that ship's piece that goes in this cell.
        // Place the image in the grid. Update the char grid too.
        labelGrid[row][col].setGraphic(ship.getImageView(index));
        
        if (ship.getOrientation() == 'H') {
            if (ship.getDirection() == 1) {
                charGrid[row][col] = index == 0 ? '<' 
                        : index == ship.length()-1 ? '>' 
                        : '-';
            }
            else {
                charGrid[row][col] = index == 0 ? '>' 
                        : index == ship.length()-1 ? '<' 
                        : '-';
            }
        }
        else {
            if (ship.getDirection() == 1) {
                charGrid[row][col] = index == 0 ? '^' 
                        : index == ship.length()-1 ? 'v' 
                        : '|';
            }
            else {
                charGrid[row][col] = index == 0 ? 'v' 
                        : index == ship.length()-1 ? '^' 
                        : '|';
            }
        }
        System.out.println(charGrid[row][col]);
        //charGrid[row][col] = ship.getName().charAt(0);
    }
    
    private int checkCells(Ship ship, int row, int col) {
        // DEV NOTE: Merged checkDirection(), checkHorizontal(), and 
        // checkVertical() into this method and refactored the code.
        
        // Initialize booleans that keep track of which directions the ship's 
        // pieces can be placed.
        boolean right, left, up, down;
        if (ship.getOrientation() == 'H') {
            left = right = true;
            up = down = false;
        }
        else {
            right = left = false;
            up = down = true;
        }
        
        // Check the row-cell parameter as the starting cell and then look at 
        // the adjacent cells in all directions (up, down, left, right).
        // If a cell is is outside the grid or if there's already something in 
        // that cell, that direction cannot be used to place the ship.
        for (int i = 0; i < ship.length(); i++) {
            if (row + i >= GRIDSIZE || charGrid[row+i][col] != 'O')
                down = false;
            if (row - i < 0 || charGrid[row-i][col] != 'O')
                up = false;
            if (col + i >= GRIDSIZE || charGrid[row][col+i] != 'O')
                right = false;
            if (col - i < 0 || charGrid[row][col-i] != 'O')
                left = false;
        }
        
        // Return 1 : ship's pieces can be placed to the right (if horizontal) or downwards (if vertical)
        //       -1 : ship's pieces can be placed to the left (if horizontal) or upwards (if vertical)
        //        0 : ship's pieces cannot be placed in any direction from this row-column
        if (right || down)
            return 1;
        else if (left || up)
            return -1;
        else
            return 0;
    }
    
    /*
    private int checkDirection(Ship ship, int row, int col) {
        if (ship.getOrientation() == 'H')
            return this.checkHorizontal(ship, row, col);
        else
            return this.checkVertical(ship, row, col);
    }
    
    int checkHorizontal(Ship ship, int row, int col) {
        // Check that every column the ship could occupy in this row is within 
        // the board and not already occupied.
        // Check columns to the right and then columns to the left.
        // Return 0 : ship cannot be placed in any column in this row.
        //        1 : ship can be placed on this column and to the right.
        //       -1 : ship can be placed on this column and to the left.
        boolean clearPath = true;
        for (int c = col; c > col - ship.length(); c--) {
            if (c < 0 || charGrid[row][c] != 'O') {
                clearPath = false;
                break;
            }		
        }
        if (clearPath == true)
            return -1;
        
        for (int c = col; c < col + ship.length(); c++) {
            if (c >= GRIDSIZE || charGrid[row][c] != 'O') {
                clearPath = false;
                break;
            }
        }
        if (clearPath == true)
            return 1;
        
        return 0;	
    }
	
    int checkVertical(Ship ship, int row, int col) {
        // Check that every row the ship could occupy in this column is within 
        // the board and not already occupied.
        // Check rows below and then rows above.
        // Return 0 : ship cannot be placed in any row in this column.
        //        1 : ship can be placed on this row and below.
        //       -1 : ship can be placed on this row and above.
        boolean clearPath = true;
        for (int r = row; r < row + ship.length(); r++) {
            if (r >= GRIDSIZE || charGrid[r][col] != 'O') {
                clearPath = false;
                break;
            }
        }
        if (clearPath == true)
            return 1;

        for (int r = row; r > row - ship.length(); r--) {
            if (r < 0 || charGrid[r][col] != 'O')  {
                clearPath = false;
                break;
            }		
        }
        if (clearPath == true) 
            return -1;
        
        return 0;
    }
    
    */

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
