package entity;

import world.TileMap;

public interface ActionEntityBattle {
    void basicAttack(Entity entity, TileMap map);

    void actionAfterDeath(TileMap map, Entity entity);
}
