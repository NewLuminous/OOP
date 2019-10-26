package com.game.entity.enemy;

import com.badlogic.gdx.physics.box2d.Body;
import com.game.entity.GameEntity;

public abstract class Enemy extends GameEntity {
    private int hp;
    private int armor;
    private int bounty;

    protected double speed;

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
}
