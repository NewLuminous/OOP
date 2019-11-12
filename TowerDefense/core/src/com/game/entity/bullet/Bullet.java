package com.game.entity.bullet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.game.GameConfig;
import com.game.entity.GameEntity;
import com.game.entity.IMovingEntity;
import com.game.entity.enemy.Enemy;
import com.game.entity.tile.tower.Tower;
import com.game.util.loader.GameLoader;

public class Bullet extends GameEntity implements IMovingEntity {
    private static final float SPEED = 20f;

    private Tower tower;
    public Enemy target;
    private Texture[] textures;

    private boolean destroyed = false;

    public Bullet(Tower tower, int posx, int posy, Enemy target) {
        super(BulletBodyFactory.getBody(tower, posx, posy));
        this.tower = tower;
        this.target = target;
    }

    @Override
    public void targetAt(Vector2 pos) {
        float deltaX = pos.x - getPosition().x;
        float deltaY = pos.y - getPosition().y;
        Vector2 delta = new Vector2(deltaX, deltaY);
        body.setLinearVelocity(deltaX / delta.len() * SPEED, deltaY / delta.len() * SPEED);
    }

    @Override
    public float getDirection() {
        return body.getLinearVelocity().angle();
    }

    @Override
    public Texture[] getTextures() {
        if (textures == null) {
            textures = new Texture[1];
            switch (tower.getType()) {
                case NORMAL:
                    textures[0] = GameLoader.getManager().get(GameLoader.NORMAL_TOWER_BULLET);
                    break;
                case MACHINE_GUN:
                    textures[0] = GameLoader.getManager().get(GameLoader.MACHINE_GUN_BULLET);
                    break;
                case SNIPER:
                    textures[0] = GameLoader.getManager().get(GameLoader.SNIPER_TOWER_BULLET);
                    break;
                default:
                    textures[0] = GameLoader.getManager().get(GameLoader.NORMAL_TOWER_BULLET);
            }
        }
        return textures;
    }

    @Override
    public float getTextureHeight() {
        return getTextures()[0].getHeight()/2f * GameConfig.VIEWPORT_HEIGHT / Gdx.graphics.getHeight();
    }

    @Override
    public float getTextureWidth() {
        return getTextures()[0].getWidth()/2f * GameConfig.VIEWPORT_WIDTH / Gdx.graphics.getWidth();
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
