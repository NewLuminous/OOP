package com.game.view;

import com.badlogic.gdx.Screen;
import com.game.Main;
import com.game.loader.GameLoader;

public class LoadingScreen implements Screen {
    private Main parent;

    public LoadingScreen(Main Main) {
        parent = Main;
    }

    @Override
    public void show() {
        // tells our asset manger that we want to load the images set in loadImages method
        GameLoader.queueAllImages();
        // tells the asset manager to load the images and wait until finsihed loading.
        GameLoader.manager.finishLoading();
    }

    @Override
    public void render(float delta) {
        parent.changeScreen(ScreenType.MENU);
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
        // TODO Auto-generated method stub
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
    }
}
