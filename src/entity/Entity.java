package entity;

import inventory.Inventory;
import item.ConsumableItem;
import item.ItemEntity;
import item.ItemPotion;
import item.UsableItem;
import javafx.scene.layout.StackPane;

import java.io.FileNotFoundException;
import java.lang.reflect.GenericSignatureFormatError;
import java.util.ArrayList;
import java.util.List;

public abstract class Entity {
    private double life;
    private double strength;
    private double money;
    private double speed;
    private double damage;
    private StackPane boxEntity;
    private Inventory inventory;
    private boolean isAttacked;
    private boolean isDead;
    private double width;
    private double height;

    public Entity(String name, double width, double height) throws FileNotFoundException {
        setMoney(0);
        setWidth(width);
        setHeight(height);
        setBoxEntity(getBoxEntity());
        setInventory(new Inventory(5));
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
        return isDead;
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

    public void addMoney(double money) {
        setMoney(getMoney() + money);
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
        setMoney(getMoney() - money);
    }

    public abstract void basicAttack(Entity entity);

    public abstract void actionAfterDeath();

    public void giveItem(Entity entity, ItemEntity item) {
        if (item instanceof ItemPotion potion) {
            entity.getInventory().addItemPotion(potion);
            getInventory().removeItemPotion(potion);
        } else if (item instanceof UsableItem usable) {
            entity.getInventory().addUsableItem(usable);
            getInventory().removeUsableItem(usable);
        } else if (item instanceof ConsumableItem consumable) {
            entity.getInventory().addConsumableItem(consumable);
            getInventory().removeConsumableItem(consumable);
        }
    }

    public void takeItem(ItemEntity item) {
        if (item instanceof ItemPotion potion) {
            getInventory().addItemPotion(potion);
        } else if (item instanceof UsableItem usable) {
            getInventory().addUsableItem(usable);
        } else if (item instanceof ConsumableItem consumable) {
            getInventory().addConsumableItem(consumable);
        }
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}