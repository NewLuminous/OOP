package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class GameStage {
    private GameField field;
    private Image map;

    public GameStage() {
        map = new Image(new Texture(Gdx.files.internal("maps/map1.png")));
        field = new GameField();
        field.addActor(map);
    }

    public void nextStage() {
        field.dispose();
        field = new GameField();
        field.addActor(map);
    }

    public void act(float delta) {
        field.act(delta);
    }

    public void draw() {
        field.draw();
    }

    public void dispose() {
        field.dispose();
    }
}
