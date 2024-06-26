package item;

import entity.Entity;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import world.TileMap;

import java.io.FileNotFoundException;

public class Spike extends Entity {
    private Rectangle hitBox;

    public Spike(String name, double width, double height, TileMap map) throws FileNotFoundException {
        super(name, width, height, map);
        setBoxEntity(spikes());
        setCollidable(true);
    }

    public StackPane spikes() {
        StackPane stackPane = new StackPane();
        Image image = new Image("images/tile/spike.png");
        ImageView imageView = new ImageView();
        setHitBox(new Rectangle(getWidth(), getHeight()));
        getHitBox().setFill(new ImagePattern(image));
        getHitBox().setOpacity(1);
        stackPane.getChildren().addAll(imageView, getHitBox());
        return stackPane;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public void setHitBox(Rectangle hitBox) {
        this.hitBox = hitBox;
    }
}
