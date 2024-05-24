package monster;

import javafx.scene.paint.Color;

public enum MonsterEnum {
    MONSTER_1(1.0, 1.0, Color.RED, 40, 5, "event1"),
    MONSTER_2(1.5, 1.5, Color.GREEN, 50, 10, "event2"),
    MONSTER_3(2.0, 1.0, Color.BLUE, 60, 15, "event3"),
    MONSTER_4(1.0, 2.0, Color.YELLOW, 80, 20, "event4"),
    MONSTER_5(2.0, 2.0, Color.PURPLE, 120, 25, "event5"),
    MONSTER_6(2.0, 2.0, Color.BLACK, 150, 30, "event6"),
    MONSTER_7(2.0, 2.0, Color.WHITE, 200, 40, "event7"),
    MONSTER_8(2.0, 2.0, Color.ORANGE, 250, 50, "event8");

    // Add more monsters here
    private final double widthFactor;
    private final double heightFactor;
    private final Color color;
    private final int baseLife;
    private final int baseDamage;
    private final String event;

    MonsterEnum(double widthFactor, double heightFactor, Color color, int baseLife, int baseDamage, String event) {
        this.widthFactor = widthFactor;
        this.heightFactor = heightFactor;
        this.color = color;
        this.baseLife = baseLife;
        this.baseDamage = baseDamage;
        this.event = event;
    }

    // Other methods, such as getRandomMonster() and getMonster(), can be left unchanged
    public static MonsterEnum getRandomMonster() {
        return values()[(int) (Math.random() * values().length)];
    }

    public static MonsterEnum getMonster(MonsterEnum monsterEnum) {
        return monsterEnum;
    }

    public double getWidthFactor() {
        return widthFactor;
    }

    public double getHeightFactor() {
        return heightFactor;
    }

    public Color getMonsterColor() {
        return color;
    }

    public int getBaseLife() {
        return baseLife;
    }

    public int getBaseDamage() {
        return baseDamage;
    }

    public String getEvent() {
        return event;
    }
}


