package com.game.entity.bullet;

import com.badlogic.gdx.physics.box2d.Body;
import com.game.entity.BodyFactory;
import com.game.entity.tile.tower.Tower;

/**
 * @class   BulletFactory
 * @brief   A factory which creates <code>Bullet</code>
 */
final class BulletFactory {
    private BulletFactory() {}

    static Body getBody(Tower tower, int posx, int posy) {
        switch (tower.getType()) {
            case SNIPER:
                return BodyFactory.getInstance(tower.getWorld()).getCircleBody(posx, posy, 1);
            case MACHINE_GUN:
                return BodyFactory.getInstance(tower.getWorld()).getCircleBody(posx, posy, 1);
            default:
                return BodyFactory.getInstance(tower.getWorld()).getCircleBody(posx, posy, 1);
        }
    }
}
