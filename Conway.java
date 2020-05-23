
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

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
                cells[i][j].setStyle("-fx-fill: white; -fx-stroke: Null; -fx-stroke-width: 1;");
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
        gridPane.setStyle("-fx-background-color: black;");

        for(int i = 0; i < ROWS; i++) {
            for(int j = 0; j < COLUMNS; j++) {
                gridPane.add(cells[i][j], i, j);
            }
        }
        return gridPane;
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Conway's Game of Life");

        Rectangle[][] cells = createCells(16, 16, 16, 16);
        GridPane gridPane = createGridPane(cells);

        Scene scene = new Scene(gridPane);
        primaryStage.setTitle("Drawing a Rectangle");
        primaryStage.setScene(scene);
        primaryStage.show();


        AnimationTimer runAnimation = new AnimationTimer() {
            private long lastUpdate = 0;

            boolean isOn = false;

            @Override
            public void handle(long now) {
                // only update once every second
                if ((now - lastUpdate) >= TimeUnit.MILLISECONDS.toNanos(100)) {
                    if(isOn){
                        cells[10][10].setStyle("-fx-fill: black; -fx-stroke: Null; -fx-stroke-width: 1;");
                        isOn = false;
                    } else {
                        cells[10][10].setStyle("-fx-fill: white; -fx-stroke: Null; -fx-stroke-width: 1;");
                        isOn = true;
                    }
                    lastUpdate = now;
                }
            }
        };

        cells[10][10].setStyle("-fx-fill: white; -fx-stroke: Null; -fx-stroke-width: 1;");


        runAnimation.start();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
