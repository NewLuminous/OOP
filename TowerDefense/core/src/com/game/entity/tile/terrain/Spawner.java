package com.game.entity.tile.terrain;

import com.badlogic.gdx.physics.box2d.World;

public class Spawner extends Road {
    public Spawner(World world, int posx, int posy) {
        super(world, posx, posy);
    }
}