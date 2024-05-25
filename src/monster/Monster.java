package monster;

import entity.ActionEntityBattle;
import entity.Entity;
import item.ItemGeneral;
import javafx.animation.Timeline;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import player.Player;
import world.TileMap;

import java.io.FileNotFoundException;
import java.util.Scanner;

/*
Si je veux modifier des interactions avec les monstres je vais les switchs correspondant

 */
public class Monster extends Entity implements ActionEntityBattle {
    private Timeline attackTimer;
    private MonsterEnum monsterEnum;
    private Player player;
    private Stage stage;
    private TileMap map;
    public Monster(MonsterEnum monsterEnum, Player player, TileMap map, Stage stage) throws FileNotFoundException {
        super(monsterEnum.name(), monsterEnum.getWidthFactor() * 25, monsterEnum.getHeightFactor() * 25, map);
        setLife(monsterEnum.getBaseLife());
        setMap(map);
        setMoney(50);
        setStage(stage);
        setStrength(30);
        setMonsterEnum(monsterEnum);
        setBoxEntity(boxMonster());
        setCanBeAttacked(true);
        setCollidable(true);
        setPlayer(player);
        setDamage(20 * (1 + getStrength() / 100));
        getBoxEntity().setOnMouseClicked(mouseEvent -> {
            if (getBoxEntity().contains(mouseEvent.getX(), mouseEvent.getY()) && isCanBeAttacked()) {
                receiveAttackFromEntity(this, map);
            }
        });

        getBoxEntity().setOnMouseEntered(mouseEvent -> {
            if (getBoxEntity().contains(mouseEvent.getX(), mouseEvent.getY())) {
                Image cursor = new Image("images/sword/weapon_golden_sword.png");
                ImageView cursorView = new ImageView(cursor);
                cursorView.setRotate(-45);
                map.setCursor(new ImageCursor(cursorView.snapshot(null, null)));

                // Display the name

            }
        });


        getBoxEntity().setOnMouseExited(event -> {
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
    public void receiveAttackFromEntity(Entity entity, TileMap map) {
        if (entity instanceof Monster monster) {
            String event = monster.getMonsterEnum().getEvent();
            // Trigger the event specified by the event field
            switch (event) {
                case "event1":
                    System.out.println("I can one shot you");
                    //getPlayer().setOneShot(true);
                    if (getPlayer().isOneShot()) {
                        setLife(0);
                    } else {
                        normalAttackFromPlayer(entity);
                    }
                    //actionAfterDeath(map, getPlayer());
                    break;
                case "event2":
                    normalAttackFromPlayer(entity);
                    //setLife(0);
                    break;
                case "event3":
                    Scanner scanner = new Scanner(System.in);
                    int solution;
                    displayDialog("Combien Fait 1+1:\n" +
                            "Indice: Il me faut 1 homme et 1 femme:\n");
                    do {
                        System.out.println("Combien Fait 1+1:\n" +
                        "Indice: Il me faut 1 homme et 1 femme:\n");
                        solution = scanner.nextInt();
                        System.out.println("%s vie restant: %f".formatted(getPlayer().getName(), getPlayer().getLife()));
                        getPlayer().loseLife(getDamage());
                    }while (solution != 3);
                    loseLife(getPlayer().getDamage());
                    System.out.println("Bonne réponse !\n" +
                            "%s vie restant: %f\n".formatted(getName(), getLife()));
                    //scanner.close();
                    break;
                case "event4":
                    // Code for event 4
                    break;
                case "event5":
                    // Code for event 5
                    break;
                case "event6":
                    // Code for event 6
                    break;
                case "event7":
                    // Code for event 7
                    break;
                case "event8":
                    // Code for event 8
                    break;
                default:
                    // Code for default behavior
                    break;
            }
            actionAfterDeath(map, entity);
        }

    }
    @Override
    public void actionAfterDeath(TileMap map, Entity entity) {
        if (entity instanceof Monster monster) {
            String event = monster.getMonsterEnum().getEvent();
            // Trigger the event specified by the event field
            switch (event) {
                case "event1":
                    if (entity.getLife() <= 0) {
                        ItemGeneral itemGeneral = entity.getInventory().getItemPotion(0);
                        System.out.printf("%s is dead%n", getName());
                        System.out.printf("%s earn %f%n", getPlayer().getName(), getMoney());
                        player.addMoney(getMoney(), getPlayer());
                        map.removeEntity(entity);
                        giveItem(getPlayer(), itemGeneral);
                        System.out.printf("%s%n", getPlayer().getInventory());
                        displayDialog("Félicitation tu as tué le monstre !");
                        System.out.printf("%s receive item %s\n", getPlayer().getName(), itemGeneral.getName());
                        //getStage().close();
                    }
                    break;
                case "event2":
                    actionAfterDeath1(map, entity);
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
                case "event6":
                    // Code for event 6
                    break;
                case "event7":
                    // Code for event 7
                    break;
                case "event8":
                    // Code for event 8
                    break;
                default:
                    // Code for default behavior
                    break;
            }
        }

    }
    private void normalAttackFromPlayer(Entity entity) {
        System.out.printf("%s attack %s%n", getPlayer().getName(), entity.getName());
        System.out.printf("Life of %s = %f%n", entity.getName(), entity.getLife());
        entity.loseLife(getPlayer().getDamage());
    }

    public void attackPlayer(Entity entity, TileMap map) {
        if (entity instanceof Monster monster) {
            String event = monster.getMonsterEnum().getEvent();
            switch (event) {
                case "event1":
                    System.out.printf("%s attack %s%n", getName(), getPlayer().getName());
                    System.out.printf("Life of %s = %f%n", getPlayer().getName(), getPlayer().getLife());
                    getPlayer().loseLife(getDamage());
                    getPlayer().getHitBox().setTranslateX(0);
                    getPlayer().getHitBox().setTranslateY(0);
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
                case "event6":
                    // Code for event 6
                    break;
                case "event7":
                    // Code for event 7
                    break;
                case "event8":
                    // Code for event 8
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
                System.out.printf("%s earn %f%n", getPlayer().getName(), getMoney());
                ItemGeneral itemGeneral = entity.getInventory().getItemPotion(0);
                giveItem(getPlayer(), itemGeneral);
                player.addMoney(getMoney(), getPlayer());
                map.removeEntity(entity);
                setDead(true);
                System.out.printf("%s receive %s\n", getPlayer().getName(), itemGeneral.getName());
                System.out.printf("%s%n", getPlayer().getInventory());
            }
        } catch (IndexOutOfBoundsException e) {
            map.removeEntity(entity);
            getPlayer().addMoney(entity.getMoney(), getPlayer());
        } catch (IllegalArgumentException e) {
            map.removeEntity(entity);
            System.out.print("You receive Directly the potion");
            entity.giveItem(this, entity.getInventory().getItemPotion(0));
        }
    }



    public MonsterEnum getMonsterEnum() {
        return monsterEnum;
    }

    public void setMonsterEnum(MonsterEnum monsterEnum) {
        this.monsterEnum = monsterEnum;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
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
}