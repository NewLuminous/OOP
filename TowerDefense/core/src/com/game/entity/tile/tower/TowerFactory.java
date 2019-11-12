package com.game.entity.tile.tower;

import com.badlogic.gdx.physics.box2d.World;

public final class TowerFactory {
    private TowerFactory() {}

    public static Tower getTower(World world, float posx, float posy, Tower.TowerType tower) {
        switch (tower) {
            case NORMAL:
                return new NormalTower(world, (int)posx, (int)posy);
            case MACHINE_GUN:
                return null;
            case SNIPER:
                return null;
            default:
                return new NormalTower(world, (int)posx, (int)posy);
        }
    }
}
