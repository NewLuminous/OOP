package com.game.entity.tile.tower;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;
import com.game.GameConfig;
import com.game.entity.IActiveEntity;
import com.game.entity.tile.GameTile;

public abstract class Tower extends GameTile implements IActiveEntity {
    public enum TowerType {
        NORMAL, SNIPER, MACHINE_GUN
    }

    private TowerType type;

    private double rateOfFire;
    private double range;
    private double damage;

    protected Texture[] textures;

    public Tower(World world, int posx, int posy, TowerType type) {
        super(world, posx, posy);
        this.type = type;
    }

    public final TowerType getType() {
        return type;
    }

    public final double getRateOfFire() {
        return rateOfFire;
    }

    public void setRateOfFire(double rate) {
        if (rate < 0) throw new IllegalArgumentException("Rate of fire does not accept the negative value");
        this.rateOfFire = rate;
    }

    public final double getRange() {
        return range;
    }

    public void setRange(double range) {
        if (range < 0) throw new IllegalArgumentException("Range of fire does not accept the negative value");
        this.range = range;
    }

    public final double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        if (damage < 0) throw new IllegalArgumentException("Damage does not accept the negative value");
        this.damage = damage;
    }

    @Override
    public final float getDirection() {
        return 0;
    }

    @Override
    public float getTextureHeight() {
        return getTextures()[0].getHeight() * GameConfig.VIEWPORT_HEIGHT / Gdx.graphics.getHeight();
    }

    @Override
    public float getTextureWidth() {
        return getTextures()[0].getWidth() * GameConfig.VIEWPORT_WIDTH / Gdx.graphics.getWidth();
    }
}
