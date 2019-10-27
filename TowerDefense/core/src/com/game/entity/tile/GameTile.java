package com.game.entity.tile;

import com.badlogic.gdx.physics.box2d.World;
import com.game.entity.BodyFactory;
import com.game.entity.GameEntity;

public abstract class GameTile extends GameEntity {
    public GameTile(World world, int posx, int posy) {
        super(BodyFactory.getInstance(world).getBoxBody(posx, posy, TileConfig.TILE_SIZE, TileConfig.TILE_SIZE, true));
    }
}
