package com.game.entity.tile;

import com.badlogic.gdx.physics.box2d.World;
import com.game.entity.BodyFactory;
import com.game.entity.GameEntity;

public abstract class GameTile extends GameEntity {
    public enum TileType {
        MOUNTAIN, ROAD, SPAWNER, TARGET, OBSTACLE,
        NORMAL_TOWER, SNIPER_TOWER, MACHINE_GUN_TOWER
    }

    public GameTile(World world, int posx, int posy) {
        super(BodyFactory.getInstance(world).getBoxBody(posx, posy, TileConfig.TILE_SIZE, TileConfig.TILE_SIZE, true));
    }
}
