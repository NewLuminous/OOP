package com.game.entity.tile.tower;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.game.GameConfig;
import com.game.entity.BodyFactory;
import com.game.entity.IActiveEntity;
import com.game.entity.bullet.Bullet;
import com.game.entity.enemy.Enemy;
import com.game.entity.tile.GameTile;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class Tower extends GameTile implements IActiveEntity {
    public enum TowerType {
        NORMAL, MACHINE_GUN, SNIPER
    }

    private TowerType type;

    private double rateOfFire;
    private double cooldown;
    private double range;
    private double damage;
    private float direction;
    Texture[] textures;

    public ArrayList<Enemy> enemies;
    private Enemy nearestEnemy;

    public Tower(World world, int posx, int posy, TowerType type, float range) {
        super(world, posx, posy);
        setRange(range);
        this.type = type;
        Body sensor = BodyFactory.getInstance(world).getCircleBody(posx, posy, range, true);
        sensor.setUserData(this);
        enemies = new ArrayList<Enemy>();
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

    public static int getBuildCost(TowerType type) {
        switch (type) {
            case NORMAL:
                return TowerConfig.NORMAL_TOWER_COST;
            case MACHINE_GUN:
                return TowerConfig.MACHINE_GUN_TOWER_COST;
            case SNIPER:
                return TowerConfig.SNIPER_TOWER_COST;
        }
        return Integer.MAX_VALUE;
    }

    @Override
    public final float getDirection() {
        return direction;
    }

    @Override
    public float getTextureHeight() {
        return 1 / GameConfig.getScreenScaleY();
    }

    @Override
    public float getTextureWidth() {
        return 1 / GameConfig.getScreenScaleX();
    }

    public void addEnemy(Enemy enemy) {
        if (!enemies.contains(enemy)) enemies.add(enemy);
    }

    public void removeEnemy(Enemy enemy) {
        if (enemies.contains(enemy)) enemies.remove(enemy);
    }

    public void removeDeadEnemies() {
        for (Iterator<Enemy> it = enemies.iterator(); it.hasNext();) {
            Enemy enemy = it.next();
            if (enemy.isDestroyed()) it.remove();
        }
    }

    private void updateNearestEnemy() {
        removeDeadEnemies();
        if (!enemies.contains(nearestEnemy)) {
            float minDistance = Float.POSITIVE_INFINITY;
            nearestEnemy = null;
            for (Enemy enemy: enemies) {
                float distance = this.getPosition().sub(enemy.getPosition()).len();
                if (distance <= minDistance) {
                    minDistance = distance;
                    nearestEnemy = enemy;
                }
            }
        }
    }

    public Bullet fire(float time) {
        updateNearestEnemy();
        if (nearestEnemy != null) {
            float deltaX = nearestEnemy.getPosition().x - getPosition().x;
            float deltaY = nearestEnemy.getPosition().y - getPosition().y;
            Vector2 delta = new Vector2(deltaX, deltaY);
            int count = (int)(Math.abs(cooldown) / time) + 1;
            direction += (delta.angle() - direction) / count;
            if (cooldown <= 0) {
                cooldown = 1 / getRateOfFire();
                return new Bullet(this, (int)getPosition().x, (int)getPosition().y, nearestEnemy);
            }
        }
        else if (cooldown <= 0) cooldown = .2f / getRateOfFire();
        if (cooldown > 0) cooldown -= time;
        return null;
    }
}
