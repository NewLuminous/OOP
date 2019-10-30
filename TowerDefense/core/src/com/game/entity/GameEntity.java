package com.game.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class GameEntity extends Actor {
    protected Body body;

    public GameEntity(Body body) {
        this.body = body;
        body.setUserData(this);
    }

    public final World getWorld() {
        return body.getWorld();
    }

    public final Vector2 getPosition() {
        return body.getPosition();
    }
}
