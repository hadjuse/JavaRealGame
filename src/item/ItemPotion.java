package item;

import entity.Entity;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import player.Player;
import world.TileMap;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
// Si je veux ajouter un nouvel Item je modifie ici et je rajoute les actions dans players.
public class ItemPotion extends ItemEntity {
    private final String directory = String.format("%s/src/images/potion/", System.getProperty("user.dir"));
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
    private double life;
    private double strength;
    private double speed;
    private double damage;
    private Rectangle hitBox;
    private ItemPotionEnum itemPotionEnum;
    private String name;

    public ItemPotion(String name, TileMap map) throws FileNotFoundException {
        setName(name);
        setItemEnum(ItemPotionEnum.valueOf(getName()));
        switch (getItemEnum()) {
            case POTION_HEAL:
                setLife(20);
                setValueMoney(10);
                break;
            case POTION_STRENGTH:
                setStrength(15);
                setValueMoney(20);

                break;
            case POTION_SPEED:
                setSpeed(2);
                setValueMoney(15);

                break;
            case POTION_DAMAGE:
                setDamage(20);
                setValueMoney(13);
                break;
            case KILL:
                setDamage(0);
                break;
            default:
                throw new IllegalStateException("Unexpected value: %s".formatted(getItemEnum()));
        }
        setItemStackPane(renderItem());
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

    public ItemPotionEnum getItemEnum() {
        return itemPotionEnum;
    }

    public void setItemEnum(ItemPotionEnum itemPotionEnum) {
        this.itemPotionEnum = itemPotionEnum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void applyEffectPotion(Entity entity) {
        switch (getItemEnum()) {
            case POTION_HEAL:
                System.out.printf("You gain %f Life !%n", getLife());
                entity.addLife(getLife());
                break;
            case POTION_STRENGTH:
                System.out.printf("You gain %f Strength!%n", getStrength());
                entity.addStrength(getStrength());
                break;
            case POTION_SPEED:
                System.out.printf("You gain %f Speed!%n", getSpeed());
                entity.setSpeed(getSpeed());
                break;
            case POTION_DAMAGE:
                System.out.printf("You gain %f Damage!%n", getDamage());
                entity.addDamage(getDamage());
                break;
            case KILL:
                System.out.printf("I can Kill all entities!%n");
                break;
            case Teleport:
                if (entity instanceof Player player){
                    System.out.println("I can teleport");
                }
            default:
                throw new IllegalStateException("Unexpected value: %s".formatted(getItemEnum()));
        }
    }

    @Override
    public StackPane renderItem() throws FileNotFoundException {
        StackPane stackPane = new StackPane();
        setHitBox(new Rectangle(25, 25));
        Image image;
        ImageView imageView;
        switch (getItemEnum()) {
            case POTION_HEAL:
                image = new Image(new FileInputStream(getSpritePath()[0]));
                imageView = new ImageView(image);
                getHitBox().setFill(new ImagePattern(image));
                break;
            case POTION_STRENGTH:
                image = new Image(new FileInputStream(getSpritePath()[1]));
                imageView = new ImageView(image);
                getHitBox().setFill(new ImagePattern(image));
                break;

            case POTION_SPEED:
                image = new Image(new FileInputStream(getSpritePath()[2]));
                imageView = new ImageView(image);
                getHitBox().setFill(new ImagePattern(image));
                break;

            case POTION_DAMAGE:
                image = new Image(new FileInputStream(getSpritePath()[3]));
                imageView = new ImageView(image);
                getHitBox().setFill(new ImagePattern(image));
                break;
            case KILL:
                image = new Image(new FileInputStream(getSpritePath()[4]));
                imageView = new ImageView(image);
                getHitBox().setFill(new ImagePattern(image));
                break;
            case Teleport:
                image = new Image(new FileInputStream(getSpritePath()[5]));
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
        return "ItemPotion{" +
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
}