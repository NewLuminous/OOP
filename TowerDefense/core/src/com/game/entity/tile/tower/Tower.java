package com.game.entity.tile.tower;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;
import com.game.GameConfig;
import com.game.entity.IActiveEntity;
import com.game.entity.tile.GameTile;

public abstract class Tower extends GameTile implements IActiveEntity {
    private TowerType type;

    private double rateOfFire;
    private double range;
    private double damage;

    protected static Texture[] textures;

    public Tower(World world, int posx, int posy, TowerType type) {
        super(world, posx, posy);
        this.type = type;
    }

    public TowerType getType() {
        return type;
    }

    public double getRateOfFire() {
        return rateOfFire;
    }

    public void setRateOfFire(double rate) {
        if (rate < 0) throw new IllegalArgumentException("Rate of fire does not accept the negative value");
        this.rateOfFire = rate;
    }

    public double getRange() {
        return range;
    }

    public void setRange(double range) {
        if (range < 0) throw new IllegalArgumentException("Range of fire does not accept the negative value");
        this.range = range;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        if (damage < 0) throw new IllegalArgumentException("Damage does not accept the negative value");
        this.damage = damage;
    }

    @Override
    public float getDirection() {
        return 0;
    }

    @Override
    public float getTextureHeight() {
        return getTextures()[0].getHeight() * GameConfig.VIEWPORT_HEIGHT / GameConfig.SCREEN_HEIGHT;
    }

    @Override
    public float getTextureWidth() {
        return getTextures()[0].getWidth() * GameConfig.VIEWPORT_WIDTH / GameConfig.SCREEN_WIDTH;
    }
}
