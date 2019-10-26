package com.game.entity.enemy;

import com.badlogic.gdx.physics.box2d.Body;

public class NormalEnemy extends Enemy {
    public NormalEnemy(Body body) {
        super(body, EnemyConfig.NORMAL_ENEMY_HP);
        setArmor(EnemyConfig.NORMAL_ENEMY_ARMOR);
        setBounty(EnemyConfig.NORMAL_ENEMY_BOUNTY);
        speed = EnemyConfig.NORMAL_ENEMY_SPEED;
    }
}
