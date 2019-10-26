package com.game.entity.bullet;

import com.game.entity.GameEntity;
import com.game.entity.tile.tower.Tower;

public class Bullet extends GameEntity {
    public Bullet(Tower tower, int posx, int posy) {
        super(BulletFactory.getBody(tower, posx, posy));
    }
}
