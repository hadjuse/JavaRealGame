package player;

import entity.Entity;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Player extends Entity {
    private boolean timelineDirectionX;
    private boolean timelineDirectionY;
    private Timeline movementX;
    private Timeline movementY;
    public Player(String name, Stage stage) {
        // TODO enable MOVE
        // TODO collision detection
        // TODO ATTACKING
        super(name, 20, 20);
        setLife(100);
        setSpeed(2);
        setStrength(10);
        setDamage(20 * (1 + getStrength() / 100));
        setBoxEntity(boxPlayer());
        eventMovement(stage);
    }

    public StackPane boxPlayer() {
        StackPane stackPane = new StackPane();
        Rectangle rectangle = new Rectangle(getWidth(), getHeight());
        rectangle.setFill(Color.BLUE);
        rectangle.setOpacity(0.5);
        stackPane.getChildren().add(rectangle);
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
            getBoxEntity().setTranslateX(getBoxEntity().getTranslateX() + (timelineDirectionX ? 3 : -3) * getSpeed());
        }));
        movementX.setCycleCount(Timeline.INDEFINITE);

        movementY = new Timeline(new KeyFrame(Duration.millis(10), e -> {
            getBoxEntity().setTranslateY(getBoxEntity().getTranslateY() + (timelineDirectionY ? 3 : -3) * getSpeed());
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
}
