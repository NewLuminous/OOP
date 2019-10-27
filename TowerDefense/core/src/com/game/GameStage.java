package com.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.game.entity.enemy.EnemyType;
import com.game.entity.tile.TileType;
import com.game.loader.GameLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GameStage {
    private GameField field;

    private float time;

    public GameStage() throws FileNotFoundException {
        Image mapImg = new Image((Texture) GameLoader.manager.get(GameLoader.MAP));
        field = new GameField(this);
        field.addActor(mapImg);
        loadMap();
    }

    private void loadMap() throws FileNotFoundException {
        File file = new File(GameConfig.MAP1);
        Scanner sc = new Scanner(file);
        for (int i = 0; i <= GameConfig.VIEWPORT_HEIGHT; ++i)
            for (int j = 0; j <= GameConfig.VIEWPORT_WIDTH; ++j) {
                TileType tile = TileType.values()[sc.nextInt()];
                field.setMap(j, GameConfig.VIEWPORT_HEIGHT - i, tile);
            }
    }

    public void nextStage() {
    }

    public void act(float delta) {
        time += delta;
        if (time >= 3) {
            field.spawnEnemy(EnemyType.NORMAL);
            time -= 3;
        }
        field.act(delta);
    }

    public void draw() {
        field.draw();
    }

    public void dispose() {
        field.dispose();
    }
}
