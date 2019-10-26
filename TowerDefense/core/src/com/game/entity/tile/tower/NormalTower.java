package com.game.entity.tile.tower;

import com.badlogic.gdx.physics.box2d.World;

public class NormalTower extends Tower {
    public NormalTower(World world, int posx, int posy) {
        super(world, posx, posy, TowerType.NORMAL);
        setRateOfFire(TowerConfig.NORMAL_TOWER_RATE_OF_FIRE);
        setRange(TowerConfig.NORMAL_TOWER_RANGE);
        setDamage(TowerConfig.NORMAL_TOWER_DAMAGE);
    }
}
