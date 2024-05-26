package monster;

import entity.ActionEntityBattle;
import entity.Entity;
import item.ItemGeneral;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import player.Player;
import world.TileMap;

import java.io.FileNotFoundException;
import java.util.List;

/*
Si je veux modifier des interactions avec les monstres je vais les switchs correspondant

 */
public class Monster extends Entity implements ActionEntityBattle {
    private Timeline attackTimer;
    private MonsterEnum monsterEnum;
    private Player player;
    private Stage stage;
    private TileMap map;
    private int i;
    private int j;
    private List<Entity> entities;

    public Monster(MonsterEnum monsterEnum, Player player, TileMap map, Stage stage, int i, int j, List<Entity> entities) throws FileNotFoundException {
        super(monsterEnum.name(), monsterEnum.getWidthFactor() * 25, monsterEnum.getHeightFactor() * 25, map);
        setEntities(entities);
        setLife(monsterEnum.getBaseLife());
        setMap(map);
        setMoney(50);
        setStage(stage);
        setStrength(30);
        setI(i);
        setJ(j);
        setMonsterEnum(monsterEnum);
        setBoxEntity(boxMonster());
        setCanBeAttacked(true);
        setCollidable(true);
        setPlayer(player);
        getMap().moveEntity(this, i, j);
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
            }
        });


        getBoxEntity().setOnMouseExited(event -> {
            map.setCursor(Cursor.DEFAULT);
        });
        getEntities().add(this);
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
                    questionAttack();
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

    public void questionAttack() {
        Stage stage = new Stage();

        stage.setTitle("Dialog with this monster: " + getName());
        TextField textField = new TextField("initialText");
        textField.setPrefColumnCount(7);
        TilePane tilePane = new TilePane();
        Label question = new Label("Combien font 3+3");
        Label l = new Label("no text");
        Scene scene = new Scene(tilePane, 200, 200);
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                l.setText(textField.getText());
                if (l.getText().equals("6")) {
                    displayDialog("Bonne réponse!");
                    //getPlayer().setOneShot(true);
                    loseLife(getPlayer().getDamage());
                    System.out.printf("%s vie restant: %f%n", getName(), getLife());

                } else {
                    displayDialog("Mauvaise réponse Tu perds de la vie!");
                    loseLife(getPlayer().getDamage());
                    System.out.printf("%s vie restant: %f%n", getPlayer().getName(), getPlayer().getLife());
                }
            }
        };
        textField.setOnAction(event);
        tilePane.getChildren().add(question);
        tilePane.getChildren().add(textField);
        tilePane.getChildren().add(l);
        stage.setScene(scene);
        stage.show();
        if (getLife() <= 0) {
            stage.close();
            getMap().removeEntity(this);
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
                        map.moveItemEntity(itemGeneral, getI(), getJ());
                        getEntities().remove(entity);
                        //giveItem(getPlayer(), itemGeneral);
                        displayDialog("Félicitation tu as tué le monstre !");
                        //System.out.printf("%s receive item %s\n", getPlayer().getName(), itemGeneral.getName());
                        //getStage().close();
                    }
                    break;
                case "event2":
                    actionAfterDeath1(map, entity);
                    break;
                case "event3":
                    if (entity.getLife() <= 0) {
                        System.out.println("Dead");
                        map.removeEntity(this);
                    }
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
                case "event1", "event2", "event3":
                    attack();
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

    private void attack() {
        System.out.printf("%s attack %s%n", getName(), getPlayer().getName());
        System.out.printf("Life of %s = %f%n", getPlayer().getName(), getPlayer().getLife());
        getPlayer().loseLife(getDamage());
        getPlayer().getHitBox().setTranslateX(0);
        getPlayer().getHitBox().setTranslateY(0);
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
                getEntities().remove(entity);
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

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }
}