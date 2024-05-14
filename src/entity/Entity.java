package entity;

import item.ConsumableItem;
import item.UsableItem;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import java.util.List;

public abstract class Entity {
    private double life;
    private double strength;
    private double money;
    private double speed;
    private double damage;
    private StackPane boxEntity;
    private List<ConsumableItem> consumableItemList;
    private List<UsableItem> usableItemList;
    private boolean isAttacked;
    private boolean isDead;
    private double width;
    private double height;
    public double getLife() {
        return life;
    }

    public double getMoney() {
        return money;
    }

    public double getStrength() {
        return strength;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public void setStrength(double strength) {
        this.strength = strength;
    }

    public void setLife(double life) {
        this.life = life;
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
    public abstract void basicAttack(Entity entity);
    public abstract void actionAfterDeath();
    public abstract void applyEffectFromList();
    public abstract void dropItem();
    public Entity(String name, double width, double height){
        setMoney(0);
        setWidth(width);
        setHeight(height);
        setBoxEntity(getBoxEntity());
    }



}