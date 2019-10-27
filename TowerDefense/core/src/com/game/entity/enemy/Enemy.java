package com.game.entity.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.game.entity.GameEntity;
import com.game.entity.IActiveEntity;
import com.game.entity.IMovingEntity;

public abstract class Enemy extends GameEntity implements IMovingEntity, IActiveEntity {
    private int hp;
    private int armor;
    private int bounty;

    protected double speed;
    protected static Texture[] textures;

    public Enemy(Body body, int hp) {
        super(body);
        setHp(hp);
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        if (hp <= 0) throw new IllegalArgumentException("HP must be a positive value");
        this.hp = hp;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        if (armor < 0) throw new IllegalArgumentException("Armor does not accept the negative value");
        this.armor = armor;
    }

    public int getBounty() {
        return bounty;
    }

    public void setBounty(int bounty) {
        if (bounty < 0) throw new IllegalArgumentException("Bounty does not accept the negative value");
        this.bounty = bounty;
    }

    protected void targetAt(Vector2 pos, float factor) {
        Vector2 velocity = body.getLinearVelocity();
        float deltaX = pos.x - getPosition().x;
        float deltaY = pos.y - getPosition().y;
        body.setLinearVelocity((velocity.x + deltaX * factor) / 3, (velocity.y + deltaY * factor) / 3);
    }

    @Override
    public float getDirection() {
        return body.getLinearVelocity().angle();
    }

    public void die() {
        getWorld().destroyBody(body);
    }
}
