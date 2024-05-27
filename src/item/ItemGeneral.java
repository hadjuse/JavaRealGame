package item;

import entity.Entity;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import monster.Monster;
import player.Player;
import pnj.PnjQuest;
import pnj.PotionSeller;
import world.TileMap;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;


/*
Si je veux ajouter un nouvel item : NE PAS OUBLIER LES BREAAAAKS !
1-Je vais dans itemGeneralEnum puis je rajoute une nouvelle constante
2-Je reviens dans cette classe, je rajoute aux switchs corrspondant les différentes informations
3-Je vais dans les classes appropriées à la question pour modifier les caractéristiques
 */
// Si je veux ajouter un nouvel Item, je modifie ici et je rajoute les actions dans la classe player.
public class ItemGeneral extends ItemEntity {
    private final String directory = String.format("/images/potion/", System.getProperty("user.dir"));
    private final String[] SpritePath = new String[]{
            String.format("%sflask_big_blue.png", directory),
            String.format("%sflask_big_green.png", directory),
            String.format("%sflask_big_red.png", directory),
            String.format("%sflask_big_yellow.png", directory),
            String.format("%sflask_blue.png", directory),
            String.format("%sflask_green.png", directory),
            String.format("%sflask_red.png", directory),
            String.format("%sflask_yellow.png", directory),
    };
    private String[] Levels = new String[]{
            "level1.csv",
            "level2.csv",
            "level3.csv",
            "backroom.csv"
    };
    private double life;
    private double strength;
    private double speed;
    private double damage;
    private Rectangle hitBox;
    private ItemGeneralEnum itemGeneralEnum;
    private String name;
    private Player player;
    private Spike spike;
    private TileMap map;
    private PnjQuest pnjQuest;
    private Monster monster;
    private List<Entity> entities;
    private Stage stage;
    public ItemGeneral(String name, TileMap map, Entity entity, List<Entity> entities, Stage stage) throws FileNotFoundException {
        if (entity instanceof Player) {
            setPlayer((Player) entity);
        } else if (entity instanceof PnjQuest pnjQuest) {
            setPnjQuest(pnjQuest);
        } else if (entity instanceof Monster monstre) {
            setMonster(monstre);
        }
        setStage(stage);
        setEntities(entities);
        setMap(map);
        setName(name);
        setItemEnum(ItemGeneralEnum.valueOf(getName()));
        setItemStackPane(renderItem());

    }


    public void applyEffectPotion(Entity entity) {
        switch (getItemEnum()) {
            case POTION_HEAL:
                setLife(20);
                System.out.printf("You gain %f Life !%n", getLife());
                entity.addLife(getLife());
                break;
            case KILL:
                System.out.print("I can kill all entities");
                getPlayer().setOneShot(true);
                break;
            case INVINCIBLE:
                System.out.println("I am Invincible !");
                getPlayer().setCollidable(false);
                break;
            case TELEPORTATION:
                teleportation();
                break;
            case ITEM1:
                if (entity instanceof PotionSeller potionSeller ){
                    potionSeller.showPotionWindow(getPlayer());
                } else if (entity instanceof PnjQuest pnjQuest) {
                    pnjQuest.showPotionWindow(getPlayer());
                }
                break;
            case ITEM2:
                break;
            case ITEM4:
                break;
            default:
                throw new IllegalStateException("Unexpected value: %s".formatted(getItemEnum()));
        }
    }

    private void teleportation() {
        try{
            int levelRandom = (int) (Math.random() * getLevels().length);
            System.out.println("Tele");
            if (levelRandom == 0){
                getMap().moveEntity(getPlayer(), 1, 1);
            }else if (levelRandom == 1){
                getMap().moveEntity(getPlayer(),1,6 );
            }else if (levelRandom == 2){
                getMap().moveEntity(getPlayer(), 1, 9);
            } else if (levelRandom == 3) {
                getMap().moveEntity(getPlayer(), 1, 10);
            }
        }catch (Exception e){
            System.out.println("The player is teleport to the desired location");
        }
    }

    @Override
    public StackPane renderItem() throws FileNotFoundException {
        StackPane stackPane = new StackPane();
        setHitBox(new Rectangle(25, 25));
        Image image;
        ImageView imageView;
        switch (getItemEnum()) {
            case POTION_HEAL, ITEM4:
                setValueMoney(10);
                image = new Image(getClass().getResourceAsStream(getSpritePath()[0]));
                imageView = new ImageView(image);
                getHitBox().setFill(new ImagePattern(image));
                break;
            case KILL,ITEM5:
                setValueMoney(10);
                image = new Image(getClass().getResourceAsStream(getSpritePath()[3]));
                imageView = new ImageView(image);
                getHitBox().setFill(new ImagePattern(image));
                break;
            case INVINCIBLE:
                setValueMoney(10);
                image = new Image(getClass().getResourceAsStream(getSpritePath()[4]));
                imageView = new ImageView(image);
                getHitBox().setFill(new ImagePattern(image));
                break;
            case TELEPORTATION:
                setValueMoney(10);
                image = new Image(getClass().getResourceAsStream(getSpritePath()[5]));
                imageView = new ImageView(image);
                getHitBox().setFill(new ImagePattern(image));
                break;
            case ITEM1:
                setValueMoney(0);
                image = new Image(getClass().getResourceAsStream(getSpritePath()[7]));
                imageView = new ImageView(image);
                getHitBox().setFill(new ImagePattern(image));
                break;
            case ITEM2:
                setValueMoney(0);
                image = new Image(getClass().getResourceAsStream(getSpritePath()[7]));
                imageView = new ImageView(image);
                getHitBox().setFill(new ImagePattern(image));
                break;
            default:
                throw new IllegalStateException("Unexpected value: %s".formatted(getItemEnum()));
        }
        stackPane.getChildren().addAll(getHitBox(), imageView);
        return stackPane;
    }

    public String[] getSpritePath() {
        return SpritePath;
    }

    @Override
    public String toString() {
        return "ItemGeneral{" +
                "name='" + name + '\'' +
                ", life=" + life +
                ", strength=" + strength +
                ", speed=" + speed +
                ", damage=" + damage +
                "} " + super.toString();
    }

    @Override
    public Rectangle getHitBox() {
        return hitBox;
    }

    @Override
    public void setHitBox(Rectangle hitBox) {
        this.hitBox = hitBox;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Spike getSpike() {
        return spike;
    }

    public void setSpike(Spike spike) {
        this.spike = spike;
    }

    public TileMap getMap() {
        return map;
    }

    public void setMap(TileMap map) {
        this.map = map;
    }

    public PnjQuest getPnjQuest() {
        return pnjQuest;
    }

    public void setPnjQuest(PnjQuest pnjQuest) {
        this.pnjQuest = pnjQuest;
    }

    public Monster getMonster() {
        return monster;
    }

    public void setMonster(Monster monster) {
        this.monster = monster;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }
    public double getLife() {
        return life;
    }

    public void setLife(double life) {
        this.life = life;
    }

    public double getStrength() {
        return strength;
    }

    public void setStrength(double strength) {
        this.strength = strength;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public ItemGeneralEnum getItemEnum() {
        return itemGeneralEnum;
    }

    public void setItemEnum(ItemGeneralEnum itemGeneralEnum) {
        this.itemGeneralEnum = itemGeneralEnum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getLevels() {
        return Levels;
    }

    public void setLevels(String[] levels) {
        Levels = levels;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}