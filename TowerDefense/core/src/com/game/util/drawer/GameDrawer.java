package com.game.util.drawer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.game.GameConfig;
import com.game.entity.IActiveEntity;
import com.game.entity.tile.tower.Tower;

public class GameDrawer {
    private SpriteBatch batch;

    public GameDrawer() {
        batch = new SpriteBatch();
    }

    public GameDrawer(Matrix4 camera) {
        this();
        batch.setProjectionMatrix(camera);
    }

    public void draw(IActiveEntity[] entities) {
        batch.begin();
        for (IActiveEntity entity: entities) {
            boolean flag = entity instanceof Tower ? false: true;
            Texture[] textures = entity.getTextures();
            for (Texture texture: textures) {
                Vector2 size = new Vector2(entity.getTextureWidth(), entity.getTextureHeight());
                Vector2 center = new Vector2(size.x / 2, size.y / 2);
                Vector2 pos = new Vector2(entity.getPosition().x - center.x, entity.getPosition().y - center.y);
                Vector2 scale = new Vector2((float)Gdx.graphics.getWidth() / GameConfig.SCREEN_WIDTH, (float)Gdx.graphics.getHeight() / GameConfig.SCREEN_HEIGHT);
                TextureRegion region = new TextureRegion(texture, 0, 0, texture.getWidth(), texture.getHeight());
                batch.draw(region, pos.x, pos.y, center.x, center.y, size.x, size.y, scale.x, scale.y, flag? entity.getDirection(): 0, true);
                flag = true;
            }
        }
        batch.end();
    }
}