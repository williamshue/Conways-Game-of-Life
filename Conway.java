import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Conway extends Application  {

    private static final int ROWS = 64;
    private static final int COLUMNS = 32;

    /**
     * Create the matrix of cells which represent the games world
     * @param cellX
     * @param cellY
     * @param cellLen
     * @param cellWidth
     * @return
     */
    public Rectangle[][] createCells(int cellX, int cellY, int cellLen, int cellWidth){
        Rectangle cells[][] = new Rectangle[ROWS][COLUMNS];

        for(int i = 0; i < ROWS; i++) {
            for(int j = 0; j < COLUMNS; j++) {
                cells[i][j] = new Rectangle(cellX, cellY, cellLen, cellWidth);
                    cells[i][j].setStyle("-fx-fill: white;");
            }
        }
        return cells;
    }

    /**
     * Create the grid pane used by JavaFX to display the world
     * @param cells
     * @return
     */
    public GridPane createGridPane(Rectangle[][] cells){
        GridPane gridPane = new GridPane();
        gridPane.setHgap(1);
        gridPane.setVgap(1);
        gridPane.setStyle("-fx-background-color: #000000;");

        for(int i = 0; i < ROWS; i++) {
            for(int j = 0; j < COLUMNS; j++) {
                gridPane.add(cells[i][j], i, j);
            }
        }
        return gridPane;
    }

    /**
     * Update the cells matrix based on Conway's rules
     * @param cells
     * @return
     */
    public Rectangle[][] updateCells(Rectangle[][] cells){
        Rectangle newCells[][] = createCells(16, 16, 16, 16);

        for(int cellRow = 1; cellRow < ROWS-1; cellRow++){
            for(int cellCol = 1; cellCol < COLUMNS-1; cellCol++){
                int liveNeighbors = countLiveNeighbors(cells, cellRow, cellCol);

                if(cells[cellRow][cellCol].getStyle().equals("-fx-fill: black;")){
                    if(liveNeighbors < 2){
                        newCells[cellRow][cellCol].setStyle("-fx-fill: white;");
                    } else if (liveNeighbors > 1 && liveNeighbors < 4) {
                        newCells[cellRow][cellCol].setStyle("-fx-fill: black;");
                    } else if (liveNeighbors > 3){
                        newCells[cellRow][cellCol].setStyle("-fx-fill: white;");
                    }
                } else if (cells[cellRow][cellCol].getStyle().equals("-fx-fill: white;")){
                    if(liveNeighbors == 3){
                        newCells[cellRow][cellCol].setStyle("-fx-fill: black;");
                    }
                }
            }
        }

        for(int i = 1; i < ROWS-1; i++) {
            for (int j = 1; j < COLUMNS - 1; j++) {
                cells[i][j].setStyle(newCells[i][j].getStyle());
            }

        }
        return cells;
    }

    /**
     * Count and return the number of living cells surrounding the cell in question
     * @param cells
     * @param centerRow
     * @param centerCol
     * @return
     */
    public int countLiveNeighbors(Rectangle[][] cells, int centerRow, int centerCol){ //center cells row and column . . .
        int rowIdxs[] = {-1, 0, 1, 1, 1, 0, -1, -1};
        int colIdxs[] = {-1, -1, -1, 0, 1, 1, 1, 0}; //sequence used to assess neighbors
        int neighbors = 8; //number of neighbors to look at

        int aliveNeighbors = 0;
        for(int i = 0; i < neighbors; i++){
            String cellColor = cells[centerRow + rowIdxs[i]][centerCol + colIdxs[i]].getStyle();
            if(cellColor.equals("-fx-fill: black;")){
                aliveNeighbors++;
            }
        }
        //System.out.println(aliveNeighbors);
        return aliveNeighbors; //returns the number of alive neighbors for passed cell
    }


    /**
     * Sets random state for world passed
     * @param cells
     * @return
     */
    public Rectangle[][] setRandom(Rectangle[][] cells){
        Random random = new Random();
        for(int i = 1; i < ROWS-1; i++) {
            for(int j = 1; j < COLUMNS-1; j++) {
                int randNum = random.nextInt(ROWS+COLUMNS);
                if(randNum % 2 == 0){
                    cells[i][j].setStyle("-fx-fill: black;");
                }
            }
        }
        return cells;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Rectangle[][] cells = createCells(16, 16, 16, 16);
        setRandom(cells);
        GridPane gridPane = createGridPane(cells);

        Scene scene = new Scene(gridPane);
        primaryStage.setTitle("Conway's Game of Life");
        primaryStage.setScene(scene);
        primaryStage.show();


        //TODO: set delay for first animiation
        AnimationTimer runAnimation = new AnimationTimer() {
            private long lastUpdate = 0;
            @Override
            public void handle(long now) {
                if ((now - lastUpdate) >= TimeUnit.MILLISECONDS.toNanos(500)) {
                    updateCells(cells);
                    lastUpdate = now;
                }
            }
        };
//        cells[10][10].setStyle("-fx-fill: black;");
//        cells[10][11].setStyle("-fx-fill: black;");
//        cells[10][12].setStyle("-fx-fill: black;");

        runAnimation.start();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
