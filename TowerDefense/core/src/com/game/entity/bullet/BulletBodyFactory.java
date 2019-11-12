package com.game.entity.bullet;

import com.badlogic.gdx.physics.box2d.Body;
import com.game.entity.BodyFactory;
import com.game.entity.tile.tower.Tower;

/**
 * @class   BulletBodyFactory
 * @brief   A factory which creates <code>Bullet</code>'s <code>Body</code>
 */
final class BulletBodyFactory {
    private static final float BULLET_SIZE = 0.2f;

    private BulletBodyFactory() {}

    static Body getBody(Tower tower, int posx, int posy) {
        switch (tower.getType()) {
            case MACHINE_GUN:
                return BodyFactory.getInstance(tower.getWorld()).getCircleBody(posx, posy, BULLET_SIZE, true);
            case SNIPER:
                return BodyFactory.getInstance(tower.getWorld()).getCircleBody(posx, posy, BULLET_SIZE, true);
            default:
                return BodyFactory.getInstance(tower.getWorld()).getCircleBody(posx, posy, BULLET_SIZE, true);
        }
    }
}
