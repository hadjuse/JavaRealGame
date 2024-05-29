package monster.ListMonster;

import monster.MonsterEntity;
import monster.MonsterEnum;
import obs.Observable;
import obs.Observer;
import player.PlayerObservable;
import world.TileMap;

import java.io.FileNotFoundException;

public class Monster2 extends MonsterEntity implements Observer {
    public Monster2(MonsterEnum monsterEnum, TileMap map, int i, int j) throws FileNotFoundException {
        super(monsterEnum, map, i, j);
        setLife(100);
        setStrength(10);
    }

    @Override
    public void update(Observable observable) {
            PlayerObservable player = (PlayerObservable) observable;
            if (isCollision()){
                if (player.getStrength()> getStrength()){
                    System.out.println("J'ai plus de force que le monstre2 donc j'attaque");
                    loseLife(player.getStrength());
                    System.out.println("Life of Monster2: " + getLife());
                }else if (player.getStrength()< getStrength()){
                    System.out.println("J'ai moins de force que le monstre2 donc je me fais attaquer");
                    player.loseLife(getStrength());
                    System.out.println("Life of Player: " + player.getLife());
                }else {
                    System.out.println("J'ai la même force que le monstre2 donc je me fais attaquer");
                    player.loseLife(getStrength());
                    System.out.println("Life of Player: " + player.getLife());
                }
                setCollision(false);
            }
    }
}
