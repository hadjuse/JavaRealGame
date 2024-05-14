package monster;

import entity.Entity;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.FileNotFoundException;

public class Monster extends Entity {
    public Monster(String name, double width, double height) throws FileNotFoundException {
        super(name, width, height);
        setLife(120);
        setSpeed(2);
        setMoney(5);
        setStrength(30);
        setDamage(20 * (1 + getStrength() / 100));
        setBoxEntity(boxMonster());
    }

    public StackPane boxMonster() {
        StackPane stackPane = new StackPane();
        Rectangle rectangle = new Rectangle(getWidth(), getHeight());
        rectangle.setFill(Color.RED);
        rectangle.setOpacity(0.5);
        stackPane.getChildren().add(rectangle);
        return stackPane;
    }

    @Override
    public void basicAttack(Entity entity) {
        entity.loseLife(this.getDamage());
    }

    @Override
    public void actionAfterDeath() {
        System.out.println("I'm dead");
    }
}