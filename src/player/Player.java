package player;

import entity.ActionEntityBattle;
import entity.Entity;
import inventory.Inventory;
import item.*;
import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import world.TileMap;

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;

public class Player extends Entity implements ActionEntityBattle {
    private final AnimationTimer movementPlayer;
    public SpriteData spriteData;
    private Rectangle hitBox;
    private double XSpawn;
    private double YSpawn;

    public Player(String name, TileMap tileMap, List<ItemEntity> itemEntities, List<Entity> entities) throws FileNotFoundException {
        // TODO Collision with walls
        super(name, 35, 35, tileMap);
        spriteData = new SpriteData();
        setName(name);
        setLife(100);
        setSpeed(1.2);
        setStrength(10);
        setDamage(20 * (1 + getStrength() / 100));
        setBoxEntity(boxEntity());
        setXSpawn(0);
        setYSpawn(0);
        setInventory(new Inventory(1));
        setMoney(0);
        movementPlayer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double newX = getHitBox().getTranslateX() + (spriteData.velocityX * getSpeed());
                double newY = getHitBox().getTranslateY() + (spriteData.velocityY * getSpeed());
                getHitBox().setTranslateX(newX);
                getHitBox().setTranslateY(newY);
                try {
                    checkCollision(entities, spriteData);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        movementPlayer.start();

        eventMovement(tileMap, spriteData);


        getBoxEntity().setFocusTraversable(true);
        getBoxEntity().requestFocus();
        eventInteractionItem(tileMap, itemEntities);
    }

    private void eventInteractionItem(TileMap tileMap, List<ItemEntity> itemEntities) {
        tileMap.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {

            if (keyEvent.getCode() == KeyCode.E) {
                Iterator<ItemEntity> iterator = itemEntities.iterator();
                while (iterator.hasNext()) {
                    ItemEntity itemEntity = iterator.next();
                    Shape intersect = Shape.intersect(getHitBox(), itemEntity.getHitBox());
                    if (intersect.getBoundsInLocal().getMinX() > 0) {
                        if (itemEntity instanceof ItemPotion potion) {
                            boolean potionInInventory = getInventory().getItemPotionList().contains(itemEntity);
                            boolean enoughSpace = getInventory().getItemPotionList().size() < getInventory().getQuantity();
                            if (!potionInInventory && enoughSpace) {
                                getInventory().addItemPotion(potion, getInventory().getQuantity());
                                System.out.printf("%s take potion %s%n", getName(), potion.getName());
                                tileMap.removeItemEntity(potion);
                                iterator.remove();
                            } else {
                                System.out.println("Cannot add Item potion not enough space");
                            }
                        } else if (itemEntity instanceof QuestItem questItem) {
                            boolean questItemInInventory = getInventory().getQuestItemList().contains(itemEntity);
                            boolean enoughSpace = getInventory().getQuestItemList().size() < getInventory().getQuantity();
                            if (!questItemInInventory && enoughSpace) {
                                getInventory().addQuestItem(questItem, getInventory().getQuantity());
                                System.out.printf("%s take itemQuest %s%n", getName(), questItem.getName());
                                tileMap.removeItemEntity(questItem);
                                iterator.remove();
                            } else {
                                System.out.println("Cannot add Item quest not enough space !");
                            }
                        }
                    }
                }
            }
        });
    }


    private static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public double getXSpawn() {
        return XSpawn;
    }

    public void setXSpawn(double xSpawn) {
        XSpawn = xSpawn;
    }

    public double getYSpawn() {
        return YSpawn;
    }

    public void setYSpawn(double ySpawn) {
        YSpawn = ySpawn;
    }

    public StackPane boxEntity() {
        StackPane stackPane = new StackPane();
        Image image = new Image("images/player/player.png");
        setHitBox(new Rectangle(getWidth(), getHeight()));
        getHitBox().setFill(new ImagePattern(image));
        getHitBox().setOpacity(1);
        stackPane.getChildren().addAll(getHitBox());
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
                }
                case Q, D -> {
                    spriteData.velocityX = 0;
                }
            }
        });
    }

    public void checkCollision(List<Entity> bounds, SpriteData spriteData) throws FileNotFoundException {
        for (Entity bound : bounds) {
            if (bound instanceof Wall wall) {
                //System.out.println(monster.getBoundsMonster());

                Shape intersection = Shape.intersect(getHitBox(), wall.getHitBox());
                boolean collisionX = intersection.getBoundsInLocal().getMinX() > 0 && intersection.getBoundsInLocal().getMaxX() > 0;
                boolean collisionY = intersection.getBoundsInLocal().getMinY() > 0 && intersection.getBoundsInLocal().getMaxY() > 0;
                if (collisionX && collisionY) {
                    double newX = getHitBox().getTranslateX() - spriteData.velocityX * getSpeed();
                    double newY = getHitBox().getTranslateY() - spriteData.velocityY * getSpeed();
                    getHitBox().setTranslateX(newX);
                    getHitBox().setTranslateY(newY);
                }
            } else if (bound instanceof Spike spike) {
                Shape intersection = Shape.intersect(spike.getHitBox(), getHitBox());
                boolean collisionX = intersection.getBoundsInLocal().getMinX() > 0 && intersection.getBoundsInLocal().getMaxX() > 0;
                boolean collisionY = intersection.getBoundsInLocal().getMinY() > 0 && intersection.getBoundsInLocal().getMaxY() > 0;
                if (collisionX && collisionY && getLife() > 0) {
                    setLife(0);
                    setLife(100);
                    System.out.printf("%s is dead%n", getName());
                    getHitBox().setTranslateX(getXSpawn());
                    getHitBox().setTranslateY(getYSpawn());
                }
            }

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
                ItemPotion itemPotion = entity.getInventory().getItemPotion(0);
                entity.giveItem(this, itemPotion);
                addMoney(entity.getMoney());
                map.removeEntity(entity);
                setDead(true);
                System.out.printf("%s receive %s\n", getName(), itemPotion.getName());
                System.out.printf("%s%n", getInventory());
            }
        } catch (IndexOutOfBoundsException e) {
            map.removeEntity(entity);
            addMoney(entity.getMoney());
        } catch (IllegalArgumentException e) {
            map.removeEntity(entity);
            System.out.printf("You receive Directly the %s\n", entity.getInventory().getItemPotion(0).getName());
            entity.giveItem(this, entity.getInventory().getItemPotion(0));
        }
    }

    public class SpriteData {
        double velocityX;
        double velocityY;
    }
}
