package player;

import entity.ActionEntityBattle;
import entity.Entity;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;
import world.TileMap;

import java.io.FileNotFoundException;

public class Player extends Entity implements ActionEntityBattle {
    private boolean timelineDirectionX;
    private boolean timelineDirectionY;
    private Timeline movementX;
    private Timeline movementY;
    private Rectangle hitbox;

    public Player(String name, Stage stage, TileMap tileMap, Rectangle hitBox) throws FileNotFoundException {
        // TODO enable MOVE
        // TODO collision detection
        // TODO ATTACKING
        super(name, 20, 20, tileMap);
        setName(name);
        setLife(100);
        setSpeed(1.5);
        setStrength(10);
        setDamage(20 * (1 + getStrength() / 100));
        setBoxEntity(boxEntity());
        eventMovement(stage);
        eventCollision(hitBox);
    }

    public StackPane boxEntity() {
        StackPane stackPane = new StackPane();
        setHitbox(new Rectangle(getWidth(), getHeight()));
        getHitbox().setFill(Color.BLUE);
        getHitbox().setOpacity(0.5);
        stackPane.getChildren().add(getHitbox());
        return stackPane;
    }


    public void eventMovement(Stage stage) {
        movementX = new Timeline(new KeyFrame(Duration.millis(10), e -> {
            getBoxEntity().setTranslateX(getBoxEntity().getTranslateX() + (timelineDirectionX ? 1 : -1) * getSpeed());
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

    public void eventCollision(Rectangle hitBox) {
        Shape intersection = Shape.intersect(getHitbox(), hitBox);
        if (intersection.getBoundsInLocal().getWidth() != -1 && intersection.getBoundsInLocal().getHeight() != -1) {
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

    @Override
    public void basicAttack(Entity entity, TileMap map) {
        System.out.printf("%s attack %s%n", getName(), entity.getName());
        System.out.printf("Life of %s = %f%n", entity.getName(), entity.getLife());
        entity.loseLife(this.getDamage());
        actionAfterDeath(map, entity);
        addMoney(entity.getMoney());
    }

    @Override
    public void actionAfterDeath(TileMap map, Entity entity) {
        if (entity.getLife() <= 0) {
            System.out.printf("%s is dead%n", entity.getName());
            StackPane pane = entity.getInventory().getItemPotion(0).getItemStackPane();
            pane.setTranslateX(pane.getTranslateX());
            pane.setTranslateY(pane.getTranslateY());
            map.placeItemEntity(entity.getInventory().getItemPotion(0), 14 , 15/2);
            map.removeEntity(entity);
        }
    }

}
