package item;

import entity.Entity;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import world.TileMap;

import java.io.FileNotFoundException;

public class Wall extends Entity {
    private final String[] idlePath = {
            "/images/tile/wallAnim/wall_fountain_mid_blue_anim_f0.png",
            "/images/tile/wallAnim/wall_fountain_mid_blue_anim_f1.png",
            "/images/tile/wallAnim/wall_fountain_mid_blue_anim_f2.png",
    };
    private Rectangle hitBox;
    private int frameIndex;
    private ImageView spriteImage;
    private Timeline idle;

    public Wall(String name, double width, double height, TileMap map) throws FileNotFoundException {
        super(name, width, height, map);
        setBoxEntity(Wall());
        setCollidable(true);
        if (name.equals("fountain")) {
            spriteImage = new ImageView(new Image(getClass().getResourceAsStream(idlePath[0])));
            spriteImage.setFitWidth(width);
            spriteImage.setFitHeight(height);
            getBoxEntity().getChildren().add(spriteImage);
            idle = new Timeline(new KeyFrame(Duration.millis(80), event -> {
                idle();
            }));
            idle.setCycleCount(Timeline.INDEFINITE);
            idle.play();
        }
    }

    public StackPane Wall() {
        StackPane stackPane = new StackPane();
        Image image = new Image("images/tile/wall_mid.png");
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

    public void idle() {
        frameIndex = (frameIndex + 1) % idlePath.length;
        spriteImage.setImage(new Image(getClass().getResourceAsStream(idlePath[frameIndex])));
    }
}
