package com.game.entity.enemy;

import com.badlogic.gdx.physics.box2d.World;

public final class EnemyFactory {
    private EnemyFactory() {}

    public static Enemy getEnemy(World world, float posx, float posy, Enemy.EnemyType enemy) {
        switch (enemy) {
            case NORMAL:
                return new NormalEnemy(world, (int)posx, (int)posy);
            case TANKER:
                return null;
            case SMALLER:
                return null;
            case BOSS:
                return null;
            default:
                return new NormalEnemy(world, (int)posx, (int)posy);
        }
    }
}
