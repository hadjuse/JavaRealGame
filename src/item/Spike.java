package item;

import entity.Entity;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import obs.Observable;
import obs.Observer;
import player.PlayerObservable;
import world.TileMap;

import java.io.FileNotFoundException;

public class Spike extends Entity implements Observer{
    public Spike(String name, double width, double height, TileMap map, PlayerObservable playerObservable, int i , int j) throws FileNotFoundException {
        super(name, width, height, map);
        setBoxEntity(spikes());
        setCollision(false);
        playerObservable.getObservers().add(this);
        setGridI(i);
        setGridJ(j);
        getMap().placeEntity(this, getGridI(), getGridJ());
    }

    public StackPane spikes() {
        StackPane stackPane = new StackPane();
        Image image = new Image("images/tile/spike.png");
        ImageView imageView = new ImageView();
        setBounds(new Rectangle(getWidth(), getHeight()));
        getBounds().setFill(new ImagePattern(image));
        getBounds().setOpacity(1);
        stackPane.getChildren().addAll(imageView, getBounds());
        return stackPane;
    }


    @Override
    public void update(Observable observable) {
        if (observable instanceof PlayerObservable playerObservable) {
            if (isCollision()) {
                playerObservable.loseLife(10);
                System.out.println("Player hit by spike");
                setCollision(false);
            }
        }
    }
}
