 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package midterm;

import javafx.application.Application;
import javafx.scene.input.MouseEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.*;
import java.util.Random;
import javafx.event.EventHandler;

/**
 * @author gstev
 * @edited Edward Carrasco
 * @date 11/17/2017
 */
public class Midterm extends Application 
{
    private static final int MAXSHIPS = 16; // original 14
    private static final int GRIDSIZE  = 16;
    
    private final GridPane gamePane = new GridPane();
    private final Cell[][] cells = new Cell[GRIDSIZE][GRIDSIZE];
    private final Ship[] ships = new Ship[MAXSHIPS];
    //private char[][] charGrid = new char[GRIDSIZE][GRIDSIZE]; 
    
    private final Random random = new Random();
    private int shipsInGrid;
    private int clicksCounter = 0;
    private int missCounter = 0;
    private int sunkCounter = 0;
    
    GridPane uiPane = new GridPane();
    Label labelSunk = new Label("Ships Sunk:");
    Label labelHits = new Label("Hit Ratio:");
    Label labelSunkScore = new Label();
    Label labelHitsScore = new Label();
    Button buttonReset = new Button("Reset");
    
    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        int windowWidth = GRIDSIZE * 18;
        int windowHeight = GRIDSIZE * 18 + 25;
        Scene scene = new Scene(root, windowWidth, windowHeight); // original: 290
        
        primaryStage.setTitle("Battleship");
        primaryStage.setScene(scene);
        primaryStage.show();
        root.setCenter(gamePane);
        
        uiPane.setHgap(5);
        root.setBottom(uiPane);
        
        uiPane.add(buttonReset, 0, 0);
        
        uiPane.add(labelSunk, 1,0);
        uiPane.add(labelSunkScore, 2, 0);
        uiPane.add(labelHits, 3,0);
        uiPane.add(labelHitsScore, 4, 0);
        
        labelSunk.setStyle("-fx-font-weight:bold;");
        labelHits.setStyle("-fx-font-weight:bold;");
        
        this.createGamePanel();
        this.setupGame();
        this.updateScoreboard();
        System.out.println("Sucess! Placed " + shipsInGrid + " ships in the grid.");
        
        // Add event listeners to the Cell labels and the Button
        for (Cell[] row : cells) {
            for (Cell piece : row) {
                piece.setOnMousePressed(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event) {
                        // Get the Cell object that was clicked.
                        // Trigger its click() method to sink that Cell.
                        // Check if the entire Ship has been sunk.
                        clicksCounter++;
                        //Piece p = (Cell) event.getSource();
                        boolean pieceWasHit = piece.click();
                        if (pieceWasHit) {
                            boolean shipWasSunk = piece.checkShip();
                            if (shipWasSunk)
                                sunkCounter++;
                        }
                        else {
                            missCounter++;
                        }   Midterm.this.updateScoreboard();
                    }
                });
            }
        }
        buttonReset.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                // Turn all Pieces in the grid to water and update the graphics.
                for (Cell[] row : cells) {
                    for (Cell piece : row) {
                        piece.reset();
                        piece.update();
                    }
                }   // Create new ships and place their Pieces in the grid.
                Midterm.this.setupGame();
                // Reset the scoreboard
                clicksCounter = missCounter = sunkCounter = 0;
                Midterm.this.updateScoreboard();
            }
        });
    }
    
    private void createGamePanel() {
        // Sets the background and style of each cell in the 2d array of labels.
        // And places them in the screen by adding them to a gridPane.
        gamePane.setStyle("-fx-background-color:#FF00FF;");
        for(int row = 0; row < GRIDSIZE; row++) {
            for(int col = 0; col < GRIDSIZE; col++) {
                cells[row][col] = new Cell();
                cells[row][col].setGraphic(new ImageView(new Image("file:Images\\batt100.gif")));
                cells[row][col].setMaxSize(16.0, 16.0);
                cells[row][col].setStyle("-fx-border-width:1;-fx-border-color:black;");
                
                // Add each of the labels to the gamePane (which is a gridPane object).
                // This way each label is placed in the correct position.
                gamePane.add(cells[row][col], col, row);      
           }
        }
    }
    
    private void setupGame() {
        // Create ships of random type and orientation.
        // Try to place them in the grid. 
        // Count how many were successfully placed.
        shipsInGrid = 0;
        for (int i = 0; i < MAXSHIPS; i++) {
            ships[i] = this.createRandomShip();
            this.placeShip(ships[i]);
        }
    }
    
    public void updateScoreboard() {
        int hits = clicksCounter - missCounter;
        int percentage = (int)Math.floor((double)hits / clicksCounter * 100);
        labelSunkScore.setText(sunkCounter+"/"+shipsInGrid);
        labelHitsScore.setText(hits + "/" + clicksCounter + " (" + percentage + "%)");
    }
    
    private void createShips() {
        // Create the Frigate. 2 are created here, but there will be 3 total
        ships[0] = new MineSweep('H');
        ships[1] = new MineSweep('V');
        ships[2] = new Frigate('H');
        ships[3] = new Frigate('V');
        ships[4] = new Cruiser('H');
        ships[5] = new Cruiser('V');
        ships[6] = new Battleship('H');
        ships[7] = new Battleship('V');
    }
    
    private Ship createRandomShip() {
        // Create ships. Each has a random type and orientation.
        int type = random.nextInt(4);
        char o = random.nextInt(2) == 0 ? 'H' : 'V';
        return type == 0 ? new MineSweep(o) 
                : type == 1 ? new Frigate(o) 
                : type == 2 ? new Cruiser(o) 
                : new Battleship(o);
    }
    
    private void placeShip(Ship ship) {
        // Choose a random row and column. Check if the ship's pieces can be 
        // placed in that cell and the cells next to it.
        int row, col, direction, attempts;
        attempts = 0;
        do {
            row = random.nextInt(GRIDSIZE);
            col = random.nextInt(GRIDSIZE);
            direction = this.checkAdjacentPieces(ship, row, col);
            
            // If after many attempts, no place is found, skip it.
            attempts++;
            if (attempts > GRIDSIZE)
                return;
        } while (direction == 0);
        
        // Found a place for the Ship. Increase counter,  save direction, and
        // link pieces to Ship and update the graphics.
        shipsInGrid++;
        ship.setDirection(direction);
        ship.attachPieces(cells, row, col);
        
        //ship.update(); // Draw the ships. (commented out to hide ships)
        
        System.out.println(ship.getName() + " (" + row + "," + col + ")\t" 
                + direction + "\t" + ship.getOrientation());
    }
    
    private int checkAdjacentPieces(Ship ship, int row, int col) {
        // Initialize booleans that keep track of which directions the ship's 
        // pieces can be placed.
        boolean right, left, up, down;
        if (ship.getOrientation() == 'H') {
            left = right = true;
            up = down = false;
        } else {
            right = left = false;
            up = down = true;
        }
        
        // Check the row-cell parameter as the starting cell and then look at 
        // the adjacent cells in all directions (up, down, left, right).
        // If a cell is is outside the grid or if there's already something in 
        // that cell, that direction cannot be used to place the ship.
        for (int i = 0; i < ship.length(); i++) {
            if (row + i >= GRIDSIZE || cells[row+i][col].hasShip())
                down = false;
            if (row - i < 0         || cells[row-i][col].hasShip())
                up = false;
            if (col + i >= GRIDSIZE || cells[row][col+i].hasShip())
                right = false;
            if (col - i < 0         || cells[row][col-i].hasShip())
                left = false;
        }
        
        // Return 1 : ship's pieces can be placed to the right (if horizontal) or downwards (if vertical)
        //       -1 : ship's pieces can be placed to the left (if horizontal) or upwards (if vertical)
        //        0 : ship's pieces cannot be placed in any direction from this row-column
        if (right || down)
            return 1;
        if (left || up)
            return -1;
        return 0;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
