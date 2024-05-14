package pnj;

import entity.Entity;

import java.io.FileNotFoundException;

public class PnjQuest extends Entity {
    public PnjQuest(String name, double width, double height) throws FileNotFoundException {
        super(name, width, height);
    }

    @Override
    public void basicAttack(Entity entity) {

    }

    @Override
    public void actionAfterDeath() {

    }




}
