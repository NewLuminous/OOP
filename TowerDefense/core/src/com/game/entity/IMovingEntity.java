package com.game.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public interface IMovingEntity extends IActiveEntity {
    void targetAt(Vector2 pos);

    Body destroy();

    boolean isDestroyed();
}
