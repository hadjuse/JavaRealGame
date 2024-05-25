package Main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import world.TileMap;

// A la fin du projet ne pas oublier de changer les chemins pour le jar
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Cube War");
        TileMap level1 = new TileMap(stage);
        //TileMap level3 = new TileMap(Levels[2], stage);
        Scene scene = new Scene(level1, 793, 780);
        stage.sizeToScene();
        stage.setScene(scene);
        stage.centerOnScreen();
        //stage.setFullScreen(true);
        stage.setX(1280 / 2);
        stage.setY(0);
        stage.show();

    }
}