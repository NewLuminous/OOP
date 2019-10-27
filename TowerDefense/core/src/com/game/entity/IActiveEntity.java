package com.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public interface IActiveEntity {
    Vector2 getPosition();

    float getDirection();

    Texture[] getTextures();

    float getTextureHeight();

    float getTextureWidth();
}
