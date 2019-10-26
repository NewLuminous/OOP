package com.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.game.GameConfig;
import com.game.GameStage;
import com.game.Main;
import com.game.controller.GameController;
import com.game.controller.KeyboardController;

public class MainScreen implements Screen {

    private Main parent;

    private OrthographicCamera camera;
    private Box2DDebugRenderer renderer;

    private GameStage stage;
    private GameController keyboard;

    public MainScreen(Main Main) {
        parent = Main;
        renderer = new Box2DDebugRenderer(true,true,true,true,true,true);
        setupCamera();

        stage = new GameStage();
        keyboard = new KeyboardController();
    }

    private void setupCamera() {
        camera = new OrthographicCamera(GameConfig.VIEWPORT_WIDTH, GameConfig.VIEWPORT_HEIGHT);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0f);
        camera.update();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage.getField());
    }

    @Override
    public void render(float delta) {
        //Clear the screen before drawing the next screen to avoid flickering
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.getField().act(delta);
        stage.getField().draw();
        renderer.render(stage.getField().getWorld(), camera.combined);
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
        stage.dispose();
    }
}
