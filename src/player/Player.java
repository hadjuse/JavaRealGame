package player;

import entity.ActionEntityBattle;
import entity.Entity;
import item.ItemPotion;
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
import java.util.List;

public class Player extends Entity implements ActionEntityBattle {
    private boolean timelineDirectionX;
    private boolean timelineDirectionY;
    private Timeline movementX;
    private Timeline movementY;
    private Rectangle hitBox;
    private Timeline movementPlayer;
    private static class SpriteData {
        double velocityX;
        double velocityY;
    }
    public Player(String name, Stage stage, TileMap tileMap, List<Shape> bounds) throws FileNotFoundException {
        // TODO enable MOVE
        // TODO collision detection
        // TODO ATTACKING
        super(name, 20, 20, tileMap);
        SpriteData spriteData = new SpriteData();
        setName(name);
        setLife(100);
        setSpeed(1.2);
        setStrength(10);
        setDamage(20 * (1 + getStrength() / 100));
        setBoxEntity(boxEntity());
        getBoxEntity().setFocusTraversable(true);
        //getBoxEntity().requestFocus();

        movementPlayer = new Timeline(new KeyFrame(Duration.millis(20), event -> {
            double newX = getHitBox().getTranslateX() + (spriteData.velocityX * getSpeed()); // TODO on modifie les vitesses ici
            double newY = getHitBox().getTranslateY() + (spriteData.velocityY * getSpeed()); // TODO on modifie les vitesses ici
            getHitBox().setTranslateX(newX);
            getHitBox().setTranslateY(newY);

        }));
        eventMovement(tileMap, spriteData);

        movementPlayer.setCycleCount(movementPlayer.INDEFINITE);
        movementPlayer.play();
    }

    public StackPane boxEntity() {
        StackPane stackPane = new StackPane();
        setHitBox(new Rectangle(getWidth(), getHeight()));
        getHitBox().setFill(Color.BLUE);
        getHitBox().setOpacity(0.5);
        stackPane.getChildren().add(getHitBox());
        return stackPane;
    }


    public void eventMovement(TileMap map, SpriteData spriteData) {
        map.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            switch (keyEvent.getCode()) {
                case Z -> {
                    spriteData.velocityY = -3;
                }
                case Q -> {

                    spriteData.velocityX = -3;
                }
                case D -> {
                    spriteData.velocityX = 3;
                }
                case S -> {
                    spriteData.velocityY = 3;
                }
            }
        });
        map.addEventFilter(KeyEvent.KEY_RELEASED, keyEvent -> {
            switch (keyEvent.getCode()) {
                case Z, S -> {
                    spriteData.velocityY = 0;
                    movementPlayer.play();
                }
                case Q, D -> {
                    spriteData.velocityX = 0;
                    movementPlayer.play();
                }
            }
        });
    }



    public void checkCollision(Shape bound, SpriteData spriteData) {
            Shape intersection = Shape.intersect(getHitBox(),  bound);
            if (intersection.getBoundsInLocal().getWidth() != -1) {
                //TODO collision;
                double newX = getHitBox().getTranslateX() - spriteData.velocityX * getSpeed();
                double newY = getHitBox().getTranslateY() - spriteData.velocityY * getSpeed();
                getHitBox().setTranslateX(newX);
                getHitBox().setTranslateY(newY);
            }
    }
    public Rectangle getHitBox() {
        return hitBox;
    }

    public void setHitBox(Rectangle hitBox) {
        this.hitBox = hitBox;
    }

    @Override
    public void basicAttack(Entity entity, TileMap map) {
        System.out.printf("%s attack %s%n", getName(), entity.getName());
        System.out.printf("Life of %s = %f%n", entity.getName(), entity.getLife());
        entity.loseLife(this.getDamage());
        actionAfterDeath(map, entity);

    }

    @Override
    public void actionAfterDeath(TileMap map, Entity entity) {
        try {
            if (entity.getLife() <= 0) {
                System.out.printf("%s is dead%n", entity.getName());
                StackPane pane = entity.getInventory().getItemPotion(0).getItemStackPane();
                pane.setTranslateX(pane.getTranslateX());
                pane.setTranslateY(pane.getTranslateY());
                map.placeItemEntity(entity.getInventory().getItemPotion(0), 14 , 15/2);
                map.removeEntity(entity);
                addMoney(entity.getMoney());
            }
        } catch (IndexOutOfBoundsException e){
            map.removeEntity(entity);
            addMoney(entity.getMoney());
        }

    }

}
