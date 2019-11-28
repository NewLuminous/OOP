package com.game.entity.enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.game.GameConfig;
import com.game.entity.GameEntity;
import com.game.entity.IMovingEntity;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

public abstract class Enemy extends GameEntity implements IMovingEntity {
    public enum EnemyType {
        NORMAL, TANKER, SMALLER, BOSS, RANDOM
    }

    private int hp;
    private int armor;
    private int bounty;

    double speed;
    Texture[] textures;

    private boolean destroyed = false;

    public Enemy(Body body, int hp) {
        super(body);
        setHp(hp);
    }

    public final int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        if (hp <= 0) throw new IllegalArgumentException("HP must be a positive value");
        this.hp = hp;
    }

    public void decreaseHp(int damage) {
        hp = hp - (Math.max(damage- armor,0 ));
    }

    public final int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        if (armor < 0) throw new IllegalArgumentException("Armor does not accept the negative value");
        this.armor = armor;
    }

    public final int getBounty() {
        return bounty;
    }

    public void setBounty(int bounty) {
        if (bounty < 0) throw new IllegalArgumentException("Bounty does not accept the negative value");
        this.bounty = bounty;
    }

    protected final void targetAt(Vector2 pos, float factor) {
        Vector2 velocity = body.getLinearVelocity();
        float deltaX = pos.x - getPosition().x;
        float deltaY = pos.y - getPosition().y;
        body.setLinearVelocity((velocity.x + deltaX * factor) / 3, (velocity.y + deltaY * factor) / 3);
    }

    @Override
    public final float getDirection() {
        return body.getLinearVelocity().angle();
    }

    @Override
    public float getTextureHeight() {
        return (float)getTextures()[0].getHeight() * GameConfig.VIEWPORT_HEIGHT / Gdx.graphics.getHeight();
    }

    @Override
    public float getTextureWidth() {
        return (float)getTextures()[0].getWidth() * GameConfig.VIEWPORT_WIDTH / Gdx.graphics.getWidth();
    }

    @Override
    public Body destroy() {
        destroyed = true;
        return body;
    }

    @Override
    public boolean isDestroyed() {
        return destroyed;
    }
}
