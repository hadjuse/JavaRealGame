package player;

import entity.ActionEntityBattle;
import entity.Entity;
import inventory.Inventory;
import item.*;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import monster.Monster;
import pnj.PnjQuest;
import pnj.PotionSeller;
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
    private boolean oneShot;
    private Stage stage;
    private TileMap map;
    private List<Entity> entities;
    private int i;
    private int j;
    private PotionSeller potionSeller;
    private PnjQuest pnjQuest;
    public Player(String name, TileMap tileMap, List<ItemEntity> itemEntities, List<Entity> entities, Stage stage, int i, int j) throws FileNotFoundException {
        super(name, 30, 30, tileMap);
        spriteData = new SpriteData();
        setStage(stage);
        setMap(tileMap);
        setI(i);
        setJ(j);
        setEntities(entities);
        initInfoPlayer(name);
        getMap().moveEntity(this, getI(), getJ());
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

        eventMovement(tileMap, spriteData, entities);


        getHitBox().setFocusTraversable(true);
        //getHitBox().requestFocus();
        eventInteractionItem(tileMap, itemEntities);
        //getInventory().addItemPotion(new ItemGeneral("INVINCIBLE", tileMap, this, getEntities()), 1);
        //getInventory().addItemPotion(new ItemGeneral("TELEPORTATION", tileMap, this, getEntities()), 1);
        //getInventory().addItemPotion(new ItemGeneral("POTION_HEAL", tileMap, this, getEntities()), 1);
        //getInventory().addItemPotion(new ItemGeneral("POTION_STRENGTH", tileMap, this, getEntities()), 1);
        //getInventory().addItemPotion(new ItemGeneral("POTION_SPEED", tileMap, this, getEntities()), 1);
        getInventory().addItemPotion(new ItemGeneral("ITEM1", tileMap, this, getEntities()), 1);
        getInventory().addItemPotion(new ItemGeneral("ITEM2", tileMap, this, getEntities()), 1);
    }

    private void initInfoPlayer(String name) throws FileNotFoundException {
        setName(name);
        setLife(100);
        setSpeed(1.2);
        setStrength(10);
        setDamage(20 * (1 + getStrength() / 100));
        setBoxEntity(boxEntity());
        setXSpawn(0);
        setYSpawn(0);
        setInventory(new Inventory(5));
        setMoney(0);
        setCollidable(true);
        setOneShot(false);
        setMoney(50);
    }

    /**
 * Event listener for handling interaction with items.
 *
 * @param tileMap the tile map where the player is located
 * @param itemEntities the list of item entities in the game
 */
private void eventInteractionItem(TileMap tileMap, List<ItemEntity> itemEntities) {
    tileMap.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {

        if (keyEvent.getCode() == KeyCode.E) {
            Iterator<ItemEntity> iterator = itemEntities.iterator();
            while (iterator.hasNext()) {
                ItemEntity itemEntity = iterator.next();
                Shape intersect = Shape.intersect(getHitBox(), itemEntity.getHitBox());
                if (intersect.getBoundsInLocal().getMinX() > 0) {
                    if (itemEntity instanceof ItemGeneral potion) {
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

    /**
 * Event listener for handling movement of the player.
 *
 * @param map the tile map where the player is located
 * @param spriteData the sprite data of the player
 * @param entities the list of entities in the game
 */
public void eventMovement(TileMap map, SpriteData spriteData, List<Entity> entities) {
    /**
     * Adds a KeyPressed event listener to the tile map.
     *
     * @param keyEvent the KeyEvent object that represents the pressed key
     */
    map.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
        switch (keyEvent.getCode()) {
            case Z -> spriteData.velocityY = -3;
            case Q -> spriteData.velocityX = -3;
            case D -> spriteData.velocityX = 3;
            case S -> spriteData.velocityY = 3;
            case P -> {
                showInventoryWindow(entities);
                spriteData.velocityX = 0;
                spriteData.velocityY = 0;
            }
        }
    });

    /**
     * Adds an EventFilter for the KeyEvent.KEY_RELEASED event.
     *
     * @param keyEvent the KeyEvent object that represents the released key
     */
    map.addEventFilter(KeyEvent.KEY_RELEASED, keyEvent -> {
        switch (keyEvent.getCode()) {
            case Z, S -> spriteData.velocityY = 0;
            case Q, D -> spriteData.velocityX = 0;
        }
    });
}

    /**
 * Checks for collision between the player and entities in the game world.
 *
 * @param bounds  List of entities in the game world.
 * @param spriteData  Sprite data of the player.
 * @throws FileNotFoundException  If there is an error reading the sprite data file.
 */
public void checkCollision(List<Entity> bounds, SpriteData spriteData) throws FileNotFoundException {
    for (Entity bound : bounds) {
        if (bound instanceof Wall wall) {
            // Checks for collision between the player's hitbox and a wall's hitbox.
            Shape intersection = Shape.intersect(getHitBox(), wall.getHitBox());
            boolean collisionX = intersection.getBoundsInLocal().getMinX() > 0 && intersection.getBoundsInLocal().getMaxX() > 0;
            boolean collisionY = intersection.getBoundsInLocal().getMinY() > 0 && intersection.getBoundsInLocal().getMaxY() > 0;
            if (collisionX && collisionY && wall.isCollidable()) {
                // If there is a collision and the wall is collidable, adjusts the player's hitbox position.
                double newX = getHitBox().getTranslateX() - spriteData.velocityX * getSpeed();
                double newY = getHitBox().getTranslateY() - spriteData.velocityY * getSpeed();
                getHitBox().setTranslateX(newX);
                getHitBox().setTranslateY(newY);
            }
        } else if (bound instanceof Spike spike) {
            // Checks for collision between the player's hitbox and a spike's hitbox.
            Shape intersection = Shape.intersect(getHitBox(), spike.getHitBox());
            boolean collisionX = intersection.getBoundsInLocal().getMinX() > 0 && intersection.getBoundsInLocal().getMaxX() > 0;
            boolean collisionY = intersection.getBoundsInLocal().getMinY() > 0 && intersection.getBoundsInLocal().getMaxY() > 0;
            if (collisionX && collisionY && getLife() > 0 && this.isCollidable()) {
                // If there is a collision and the player is alive and collidable, decreases the player's life to 0 and then increases it back to 100.
                setLife(0);
                setLife(100);
                System.out.printf("%s is dead%n", getName());
                getStage().close();
                getHitBox().setTranslateX(getXSpawn());
                getHitBox().setTranslateY(getYSpawn());
            }
        } else if (bound instanceof Monster monster && isCollidable()) {
            // Checks for collision between the player's hitbox and a monster's hitbox.
            Shape intersection = Shape.intersect(getHitBox(), monster.getBounds());
            boolean collisionX = intersection.getBoundsInLocal().getMinX() > 0 && intersection.getBoundsInLocal().getMaxX() > 0;
            boolean collisionY = intersection.getBoundsInLocal().getMinY() > 0 && intersection.getBoundsInLocal().getMaxY() > 0;
            if (collisionX && collisionY && getLife() > 0 && monster.isCollidable()) {
                // If there is a collision and the player is alive and the monster is collidable, calls the monster's attackPlayer method.
                monster.attackPlayer(monster, getMap());
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
    public void receiveAttackFromEntity(Entity entity, TileMap map) {
        /*
        if (isOneShot()){
            System.out.printf("%s attack %s%n", getName(), entity.getName());
            System.out.printf("Life of %s = %f%n", entity.getName(), entity.getLife());
            //entity.setLife(0);
            //actionAfterDeath(map, entity);
        } else {
            System.out.printf("%s attack %s%n", getName(), entity.getName());
            System.out.printf("Life of %s = %f%n", entity.getName(), entity.getLife());
            entity.loseLife(this.getDamage());
            //actionAfterDeath(map, entity);
        }*/
    }

    /**
 * This method is called after the player's life reaches 0. It handles the death of the player and the subsequent actions.
 *
 * @param map The tile map where the player is located.
 * @param entity The entity that caused the player's death.
 */
@Override
public void actionAfterDeath(TileMap map, Entity entity) {
    if (this.getLife() <= 0) {
        System.out.println("Game over");
        getStage().close();
    }
}

    /**
 * This method is called when the player presses the 'P' key. It shows the player's inventory window.
 * The inventory window displays the player's name, current money, and life. It also lists the player's inventory items.
 * The player can interact with the inventory items by clicking on them.
 * @param entities the list of entities in the game world
 */
private void showInventoryWindow(List<Entity> entities) {
    // Create a new stage for the inventory window
    Stage inventoryStage = new Stage();

    // Set the title and size of the stage
    inventoryStage.setTitle("Player Inventory");
    inventoryStage.setWidth(700);
    inventoryStage.setHeight(200);

    // Create a grid pane for the inventory items
    GridPane inventoryGridPane = new GridPane();
    inventoryGridPane.setHgap(10);
    inventoryGridPane.setVgap(10);

    // Create labels for the player's name, current money, and life
    Label nameLabel = new Label("Name: " + getName());
    Label moneyLabel = new Label("Money: " + getMoney());
    Label lifeLabel = new Label("Life: " + getLife());

    // Add the labels to the grid pane
    inventoryGridPane.add(nameLabel, 0, 0);
    inventoryGridPane.add(moneyLabel, 1, 0);
    inventoryGridPane.add(lifeLabel, 2, 0);

    int rowIndex = 1;
    for (ItemGeneral potion : getInventory().getItemPotionList()) {
        // Create an ImageView for the inventory item
        ImageView potionImageView = new ImageView(new Image(getClass().getResourceAsStream(potion.getSpritePath()[potion.getItemEnum().ordinal()])));

        // Create a Label for the name of the inventory item
        Label potionNameLabel = new Label(potion.getName());

        // Create a Button for the inventory item
        Button potionButton = UsePotionButton(potion, potionImageView, entities, inventoryStage);
        // Add the Button, the name Label, and the price Label to the grid pane
        inventoryGridPane.add(potionButton, 0, rowIndex);
        inventoryGridPane.add(potionNameLabel, 1, rowIndex);
        rowIndex++;
    }

    // Wrap the grid pane in a scroll pane
    ScrollPane scrollPane = new ScrollPane(inventoryGridPane);
    scrollPane.setFitToWidth(true);
    scrollPane.setFitToHeight(true);

    // Set the scene of the stage
    inventoryStage.setX(0);
    inventoryStage.setY(300);
    inventoryStage.setScene(new Scene(scrollPane));

    // Show the stage
    inventoryStage.show();
}


    /**
 * Creates a button for a potion that can be used by the player.
 *
 * @param potion the potion to be used
 * @param potionImageView the image view of the potion
 * @param entities the list of entities in the game world
 * @param stage the stage where the inventory window is displayed
 * @return a button that can be used to apply the effect of the potion
 */
private Button UsePotionButton(ItemGeneral potion, ImageView potionImageView, List<Entity> entities, Stage stage) {
    Button potionButton = new Button();
    potionButton.setGraphic(potionImageView);
    potionButton.setOnAction(event -> {
        boolean potionInInventory = getInventory().getItemPotionList().contains(potion);
        if (potionInInventory) {
            stage.close();
            //potion.applyEffectPotion(this);
            if (potion.getName().equals("ITEM1")){
                //System.out.println("I am a potion seller");
                for (Entity entity : entities){
                    if (entity instanceof PotionSeller potionSeller && potionSeller.isPnjRencontre()){
                        potionSeller.showPotionWindow(this);
                    }else {
                        System.out.println("Je ne peux pas utilisé l'item car pnj non rencontre");
                    }
                }
            }
            getInventory().removeItemPotion(potion);

        } else {
            stage.close();
            System.out.println("You don't have this potion");
        }
    });
    return potionButton;
}

    public boolean isOneShot() {
        return oneShot;
    }

    public void setOneShot(boolean oneShot) {
        this.oneShot = oneShot;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public TileMap getMap() {
        return map;
    }

    public void setMap(TileMap map) {
        this.map = map;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public PotionSeller getPotionSeller() {
        return potionSeller;
    }

    public void setPotionSeller(PotionSeller potionSeller) {
        this.potionSeller = potionSeller;
    }

    public PnjQuest getPnjQuest() {
        return pnjQuest;
    }

    public void setPnjQuest(PnjQuest pnjQuest) {
        this.pnjQuest = pnjQuest;
    }

    public static class SpriteData {
        double velocityX;
        double velocityY;
    }
}
