package itemObservable.itemList;

import itemObservable.ItemObservable;
import monster.ListMonster.Monster1;
import player.PlayerObservable;
import world.TileMap;

public class Item4 extends ItemObservable {
    public Item4(TileMap map, int i, int j) {
        super(map, i, j);
        setQuantity(1);
        setValueMoney(10);
        renderSkin(1);
    }

    @Override
    public void applyEffect() {
        getObservers().forEach(observer -> {
            if (observer instanceof Monster1 monster1) {// Check if the player is in combat with the monster
                PlayerObservable playerObservable = getObservers().stream()
                        .filter(PlayerObservable.class::isInstance)
                        .map(PlayerObservable.class::cast)
                        .findFirst()
                        .orElse(null); // Get the PlayerObservable from the observers list

                if (playerObservable != null) {
                    System.out.println("Using item4 on Monster1");
                    double monsterLife = monster1.getLife();
                    System.out.println("Life of Player: " + playerObservable.getLife());
                    System.out.println("Life of Monster1: " + monsterLife);
                    monster1.loseLife(monsterLife); // Absorb all remaining health points from the monster
                    playerObservable.addLife(monsterLife); // Give the absorbed health points to the player
                    System.out.println("Life of Player: " + playerObservable.getLife());
                    System.out.println("Life of Monster1: " + monster1.getLife());
                }
            }
        });
    }
}
