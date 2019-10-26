package com.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.game.GameStage;
import com.game.Main;

public class MainScreen implements Screen {
    private Main parent;
    private GameStage stage;

    public MainScreen(Main Main) {
        parent = Main;
    }

    @Override
    public void show() {
        stage = new GameStage();
    }

    @Override
    public void render(float delta) {
        //Clear the screen before drawing the next screen to avoid flickering
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
    }

    @Override
    public void hide() {
        stage.dispose();
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
    }
}
