package player;

import entity.Entity;
import item.ItemEntity;
import item.ItemPotion;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;
import world.TileMap;

import java.io.FileNotFoundException;

public class Player extends Entity {
    private boolean timelineDirectionX;
    private boolean timelineDirectionY;
    private Timeline movementX;
    private Timeline movementY;
    private Rectangle hitbox;
    private Timeline collisionTimeline;
    public Player(String name, Stage stage, ItemPotion potion, TileMap tileMap, Rectangle hitBox) throws FileNotFoundException {
        // TODO enable MOVE
        // TODO collision detection
        // TODO ATTACKING
        super(name, 20, 20);
        setLife(100);
        setSpeed(1.5);
        setStrength(10);
        setDamage(20 * (1 + getStrength() / 100));
        setBoxEntity(boxPlayer());
        eventMovement(stage);
        eventPotion(stage, potion, tileMap);
        collisionTimeline = new Timeline(new KeyFrame(Duration.millis(10), e -> {
            eventCollision(stage, hitBox);
        }));
        collisionTimeline.setCycleCount(collisionTimeline.INDEFINITE);
        collisionTimeline.play();

    }

    public StackPane boxPlayer() {
        StackPane stackPane = new StackPane();
        setHitbox(new Rectangle(getWidth(), getHeight()));
        getHitbox().setFill(Color.BLUE);
        getHitbox().setOpacity(0.5);
        stackPane.getChildren().add(getHitbox());
        return stackPane;
    }

    @Override
    public void basicAttack(Entity entity) {
        entity.loseLife(this.getDamage());
    }

    @Override
    public void actionAfterDeath() {

    }

    public void eventMovement(Stage stage) {
        movementX = new Timeline(new KeyFrame(Duration.millis(10), e -> {
            getBoxEntity().setTranslateX(getBoxEntity().getTranslateX() + (timelineDirectionX ? 1: -1) * getSpeed());
        }));
        movementX.setCycleCount(Timeline.INDEFINITE);

        movementY = new Timeline(new KeyFrame(Duration.millis(10), e -> {
            getBoxEntity().setTranslateY(getBoxEntity().getTranslateY() + (timelineDirectionY ? 1 : -1) * getSpeed());
        }));
        movementY.setCycleCount(Timeline.INDEFINITE);

        stage.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            switch (keyEvent.getCode()) {
                case Z -> {
                    timelineDirectionY = false;
                    movementY.play();
                }
                case S -> {
                    timelineDirectionY = true;
                    movementY.play();
                }
                case Q -> {
                    timelineDirectionX = false;
                    movementX.play();
                }
                case D -> {
                    timelineDirectionX = true;
                    movementX.play();
                }
            }
        });

        stage.addEventFilter(KeyEvent.KEY_RELEASED, keyEvent -> {
            if (keyEvent.getCode() == KeyCode.Z || keyEvent.getCode() == KeyCode.S) {
                movementY.stop();
            }
            if (keyEvent.getCode() == KeyCode.Q || keyEvent.getCode() == KeyCode.D) {
                movementX.stop();
            }
        });
    }

    public void eventPotion(Stage stage, ItemPotion itemPotion, TileMap tileMap){
        stage.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
            switch (keyEvent.getCode()) {
                case E -> {
                    Shape intersect = Shape.intersect(getHitbox(), itemPotion.getHitBox());
                    if (intersect.getBoundsInLocal().getWidth() > 0 && intersect.getBoundsInLocal().getHeight() > 0) {
                        System.out.println("I take this potion");
                        getInventory().addItemPotion(itemPotion);
                        tileMap.removeItemPotion(itemPotion);
                    }
                }
            }
        });
    }
    public void eventCollision(Stage stage, Rectangle hitBox){
        Shape intersection = Shape.intersect(getHitbox(), hitBox);
        if (intersection.getBoundsInLocal().getWidth() != -1 && intersection.getBoundsInLocal().getHeight() != -1){
            System.out.println("I collide");
            //TODO collision;
        }

    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public void setHitbox(Rectangle hitbox) {
        this.hitbox = hitbox;
    }
}
