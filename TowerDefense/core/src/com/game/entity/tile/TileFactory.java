package com.game.entity.tile;

import com.badlogic.gdx.physics.box2d.World;
import com.game.entity.tile.terrain.Mountain;
import com.game.entity.tile.terrain.Road;
import com.game.entity.tile.terrain.Spawner;
import com.game.entity.tile.terrain.Target;
import com.game.entity.tile.tower.Tower;
import com.game.entity.tile.tower.TowerFactory;

public final class TileFactory {
    private TileFactory() {}

    public static GameTile getTile(World world, float posx, float posy, GameTile.TileType tileType) {
        switch (tileType) {
            case MOUNTAIN:
                return new Mountain(world, (int)posx, (int)posy);
            case ROAD:
                return new Road(world, (int)posx, (int)posy);
            case SPAWNER:
                return new Spawner(world, (int)posx, (int)posy);
            case TARGET:
                return new Target(world, (int)posx, (int)posy);
            case NORMAL_TOWER:
                return TowerFactory.getTower(world, posx, posy, Tower.TowerType.NORMAL);
            case SNIPER_TOWER:
                return TowerFactory.getTower(world, posx, posy, Tower.TowerType.SNIPER);
            case MACHINE_GUN_TOWER:
                return TowerFactory.getTower(world, posx, posy, Tower.TowerType.MACHINE_GUN);
        }
        return null;
    }
}
