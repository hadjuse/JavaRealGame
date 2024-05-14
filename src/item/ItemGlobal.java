package item;

public class ItemGlobal {
    private double life;
    private double strength;
    private double speed;
    private double damage;
    private ItemEnum itemEnum;
    private String name;
    public double getLife() {
        return life;
    }

    public double getStrength() {
        return strength;
    }

    public double getSpeed() {
        return speed;
    }

    public double getDamage() {
        return damage;
    }

    public void setLife(double life) {
        this.life = life;
    }

    public void setStrength(double strength) {
        this.strength = strength;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }
    public ItemEnum getItemEnum() {
        return itemEnum;
    }
    public void setItemEnum(ItemEnum itemEnum) {
        this.itemEnum = itemEnum;
    }
}