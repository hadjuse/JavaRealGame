package entity;

import inventory.Inventory;
import item.ItemEntity;
import item.ItemGeneral;
import item.QuestItem;
import item.UsableItem;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import world.TileMap;

import java.io.FileNotFoundException;

public abstract class Entity {
    private double life;
    private double strength;
    private double money;
    private double speed;
    private double damage;
    private StackPane boxEntity;
    private Inventory inventory;
    private boolean isAttacked;
    private boolean canBeAttacked;
    private boolean isDead;
    private double width;
    private double height;
    private String name;
    private Rectangle bounds;
    private ItemGeneral potionHeal;
    private ItemGeneral potionStrength;
    private ItemGeneral potionSpeed;
    private ItemGeneral potionDamage;
    private QuestItem questItem;
    private boolean collidable;
    private Alert dialog;
    private boolean open;

    public Entity(String name, double width, double height, TileMap map) throws FileNotFoundException {
        setMoney(0);
        setWidth(width);
        setHeight(height);
        setBoxEntity(getBoxEntity());
        setInventory(new Inventory(5));
        setName(name);
    }

    public double getLife() {
        return life;
    }

    public void setLife(double life) {
        this.life = life;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getStrength() {
        return strength;
    }

    public void setStrength(double strength) {
        this.strength = strength;
    }

    public StackPane getBoxEntity() {
        return boxEntity;
    }

    public void setBoxEntity(StackPane boxEntity) {
        this.boxEntity = boxEntity;
    }

    public boolean isDead() {
        return getLife() <= 0;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public boolean isAttacked() {
        return isAttacked;
    }

    public void setAttacked(boolean attacked) {
        isAttacked = attacked;
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

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void addLife(double life) {
        setLife(getLife() + life);
    }

    public void addStrength(double strength) {
        setStrength(getStrength() + strength);
    }

    public void addSpeed(double speed) {
        setSpeed(getSpeed() + speed);
    }

    public void addDamage(double damage) {
        setDamage(getDamage() + damage);
    }

    public void addMoney(double money, Entity entity) {
        //System.out.printf("%s Earn %f%n", getName(), money);
        entity.setMoney(getMoney() + money);
    }

    public void loseLife(double life) {
        setLife(getLife() - life);
    }

    public void loseStrength(double strength) {
        setStrength(getStrength() - strength);
    }

    public void loseSpeed(double speed) {
        setSpeed(getSpeed() - speed);
    }

    public void loseDamage(double damage) {
        setDamage(getDamage() - damage);
    }

    public void loseMoney(double money) {
        System.out.println("You Lose " + money);
        setMoney(getMoney() - money);
    }


    public void giveItem(Entity entity, ItemEntity item) {
        int quantityInventory = entity.getInventory().getQuantity();
        if (item instanceof ItemGeneral potion) {
            entity.getInventory().addItemPotion(potion, quantityInventory);
            getInventory().removeItemPotion(potion);
        } else if (item instanceof UsableItem usable) {
            entity.getInventory().addUsableItem(usable, quantityInventory);
            getInventory().removeUsableItem(usable);
        } else if (item instanceof QuestItem questItem) {
            entity.getInventory().addQuestItem(questItem, quantityInventory);
            getInventory().removeConsumableItem(questItem);
        }
    }

    public void takeItem(ItemEntity item) {
        if (item instanceof ItemGeneral potion) {
            getInventory().addItemPotion(potion, getInventory().getQuantity());
        } else if (item instanceof UsableItem usable) {
            getInventory().addUsableItem(usable, getInventory().getQuantity());
        } else if (item instanceof QuestItem questItem) {
            getInventory().addQuestItem(questItem, getInventory().getQuantity());
        }
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void UsePotion() {
        ItemGeneral itemGeneral = getInventory().getItemPotion(0);
        if (itemGeneral != null) {
            itemGeneral.applyEffectPotion(this);
            getInventory().removeItemPotion(itemGeneral);
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public ItemGeneral getPotionStrength() {
        return potionStrength;
    }

    public void setPotionStrength(ItemGeneral potionStrength) {
        this.potionStrength = potionStrength;
    }

    public ItemGeneral getPotionSpeed() {
        return potionSpeed;
    }

    public void setPotionSpeed(ItemGeneral potionSpeed) {
        this.potionSpeed = potionSpeed;
    }

    public ItemGeneral getPotionDamage() {
        return potionDamage;
    }

    public void setPotionDamage(ItemGeneral potionDamage) {
        this.potionDamage = potionDamage;
    }

    public ItemGeneral getPotionHeal() {
        return potionHeal;
    }

    public void setPotionHeal(ItemGeneral potionHeal) {
        this.potionHeal = potionHeal;
    }

    public QuestItem getQuestItem() {
        return questItem;
    }

    public void setQuestItem(QuestItem questItem) {
        this.questItem = questItem;
    }

    public boolean isCanBeAttacked() {
        return canBeAttacked;
    }

    public void setCanBeAttacked(boolean canBeAttacked) {
        this.canBeAttacked = canBeAttacked;
    }

    public boolean isCollidable() {
        return collidable;
    }

    public void setCollidable(boolean collidable) {
        this.collidable = collidable;
    }

    public void displayDialog(String dialog) {
        setDialog(new Alert(Alert.AlertType.INFORMATION));
        getDialog().setHeaderText(String.format("%s dialog", getName()));
        getDialog().setContentText(dialog);
        getDialog().showAndWait();
    }

    public Alert getDialog() {
        return dialog;
    }

    public void setDialog(Alert dialog) {
        this.dialog = dialog;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}