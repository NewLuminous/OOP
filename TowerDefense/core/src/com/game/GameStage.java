package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.game.entity.enemy.Enemy;
import com.game.entity.tile.GameTile;
import com.game.util.loader.GameLoader;

public class GameStage {
    private GameField field;

    private float time;

    public GameStage() {
        Image mapImg = new Image((Texture) GameLoader.manager.get(GameLoader.MAP));
        mapImg.setWidth(Gdx.graphics.getWidth());
        mapImg.setHeight(Gdx.graphics.getHeight());
        field = new GameField(this);
        field.addActor(mapImg);
        loadMap();
    }

    private void loadMap() {
        FileHandle handle = Gdx.files.internal(GameConfig.MAP1);
        String[] content = handle.readString().split("\\W+");
        for (int i = 0, k = 0; i <= GameConfig.VIEWPORT_HEIGHT; ++i)
            for (int j = 0; j <= GameConfig.VIEWPORT_WIDTH; ++j) {
                GameTile.TileType tile = GameTile.TileType.values()[Integer.parseInt(content[k++])];
                field.setMap(j, GameConfig.VIEWPORT_HEIGHT - i, tile);
            }
    }

    public void nextStage() {
    }

    public void act(float delta) {
        /*time += delta;
        if (time >= 3) {
            field.spawnEnemy(Enemy.EnemyType.NORMAL);
            time -= 3;
        }*/
        field.act(delta);
    }

    public void draw() {
        field.draw();
    }

    public void dispose() {
        field.dispose();
    }
}
