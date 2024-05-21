package monster;

import entity.ActionEntityBattle;
import entity.Entity;
import item.ItemPotion;
import javafx.animation.Timeline;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import player.Player;
import world.TileMap;

import java.io.FileNotFoundException;
import java.util.Random;

public class Monster extends Entity implements ActionEntityBattle {
    private Timeline attackTimer;
    private MonsterEnum monsterEnum;
    private Player player;
    public Monster(MonsterEnum monsterEnum, Player player, TileMap map) throws FileNotFoundException {
        super(monsterEnum.name(), monsterEnum.getWidthFactor()*25, monsterEnum.getHeightFactor()*25, map);
        setLife(monsterEnum.getBaseLife());
        setMoney(50);
        setStrength(30);
        setMonsterEnum(monsterEnum);
        setBoxEntity(boxMonster());
        setCanBeAttacked(true);
        setCollidable(true);
        setPlayer(player);
        setDamage(20 * (1 + getStrength() / 100));

        getBoxEntity().setOnMouseClicked(mouseEvent -> {
            if (getBoxEntity().contains(mouseEvent.getX(), mouseEvent.getY()) && isCanBeAttacked()) {
                basicAttack(this, map);
            }
        });
        getBoxEntity().setOnMouseEntered(mouseEvent -> {
            if (getBoxEntity().contains(mouseEvent.getX(), mouseEvent.getY())) {
                Image cursor = new Image("images/sword/weapon_golden_sword.png");
                ImageView cursorView = new ImageView(cursor);
                cursorView.setRotate(-45);
                map.setCursor(new ImageCursor(cursorView.snapshot(null, null)));
            }
        });

        getBoxEntity().setOnMouseExited(_ -> {
            map.setCursor(Cursor.DEFAULT);
        });
    }

    public StackPane boxMonster() {
        StackPane stackPane = new StackPane();
        setBounds(new Rectangle(getWidth(), getHeight()));
        getBounds().setFill(getMonsterEnum().getMonsterColor());
        getBounds().setOpacity(0.5);
        stackPane.getChildren().add(getBounds());
        return stackPane;
    }

    @Override
    public void basicAttack(Entity entity, TileMap map){
        if (entity instanceof Monster) {
            Monster monster = (Monster) entity;
            String event = monster.getMonsterEnum().getEvent();
            // Trigger the event specified by the event field
            switch (event) {
                case "event1":
                    System.out.println("I can one shot you");
                    //getPlayer().setOneShot(true);
                    if (getPlayer().isOneShot()){
                        setLife(0);
                    }else {
                        normalAttack(entity);
                    }
                    actionAfterDeath(map, getPlayer());
                    break;
                case "event2":
                    normalAttack(entity);
                    break;
                case "event3":
                    // Code for event 3
                    break;
                case "event4":
                    // Code for event 4
                    break;
                case "event5":
                    // Code for event 5
                    break;
                default:
                    // Code for default behavior
                    break;
            }
        }
        actionAfterDeath(map, entity);
    }

    private void normalAttack(Entity entity) {
        System.out.printf("%s attack %s%n", getName(), entity.getName());
        System.out.printf("Life of %s = %f%n", entity.getName(), entity.getLife());
        entity.loseLife(this.getDamage());
    }


    @Override
    public void actionAfterDeath(TileMap map, Entity entity) {
        if (entity instanceof Monster){
            Monster monster = (Monster) entity;
            String event = monster.getMonsterEnum().getEvent();
            // Trigger the event specified by the event field
            switch (event) {
                case "event1":
                    actionAfterDeath1(map, entity);
                    break;
                case "event2":
                    // Code for event 2
                    break;
                case "event3":
                    // Code for event 3
                    break;
                case "event4":
                    // Code for event 4
                    break;
                case "event5":
                    // Code for event 5
                    break;
                default:
                    // Code for default behavior
                    break;
            }
        }

    }

    private void actionAfterDeath1(TileMap map, Entity entity) {
        try {
            if (entity.getLife() <= 0) {
                System.out.printf("%s is dead%n", getName());
                System.out.println("%s earn %f".formatted(getPlayer().getName(),getMoney()));
                ItemPotion itemPotion = entity.getInventory().getItemPotion(0);
                giveItem(getPlayer(), itemPotion);
                player.addMoney(getMoney(), getPlayer());

                map.removeEntity(entity);
                setDead(true);
                System.out.printf("%s receive %s\n", getPlayer().getName(), itemPotion.getName());
                System.out.printf("%s%n", getPlayer().getInventory());
            }
        } catch (IndexOutOfBoundsException e) {
            map.removeEntity(entity);
            getPlayer().addMoney(entity.getMoney(), getPlayer());
        } catch (IllegalArgumentException e) {
            map.removeEntity(entity);
            System.out.printf("You receive Directly the potion");
            entity.giveItem(this, entity.getInventory().getItemPotion(0));
        }
    }

    public MonsterEnum getMonsterEnum() {
        return monsterEnum;
    }

    public void setMonsterEnum(MonsterEnum monsterEnum) {
        this.monsterEnum = monsterEnum;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}