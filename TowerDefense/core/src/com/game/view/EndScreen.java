package com.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.game.GameConfig;
import com.game.Main;
import com.game.util.loader.GameLoader;

public class EndScreen implements Screen {
    private Main parent;
    private Stage stage;
    private Image background, youwin, youlose;
    private TextButton menuButton;

    public EndScreen(Main Main) {
        parent = Main;

        // create stage and set it as input processor
        stage = new Stage(new ScreenViewport());    //produce errors
        background = new Image((Texture) GameLoader.manager.get(GameLoader.BACKGROUND));
        stage.addActor(background);

        youlose = new Image((Texture) GameLoader.manager.get(GameLoader.DEFEAT));
        youlose.setScale(2);
        youlose.setPosition((Gdx.graphics.getWidth() - youlose.getWidth() * youlose.getScaleX())/2,
                            (Gdx.graphics.getHeight() - youlose.getHeight() * youlose.getScaleY()));
        stage.addActor(youlose);

        youwin = new Image((Texture) GameLoader.manager.get(GameLoader.VICTORY));
        youwin.setScale(2);
        youwin.setPosition((Gdx.graphics.getWidth() - youwin.getWidth() * youwin.getScaleX())/2,
                (Gdx.graphics.getHeight() - youwin.getHeight() * youwin.getScaleY()));
        stage.addActor(youwin);

        Skin skin = GameLoader.getManager().get(GameLoader.GLASSY_SKIN);
        //create buttons
        menuButton = new TextButton("Main Menu", skin);
        menuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(ScreenType.MENU);
            }
        });
        menuButton.setPosition((Gdx.graphics.getWidth() - menuButton.getWidth())/2,
                (Gdx.graphics.getHeight() - menuButton.getHeight()) / 4);
        stage.addActor(menuButton);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        if (parent.endScreenLose) {
            youlose.setVisible(true);
            youwin.setVisible(false);
        }
        else {
            youlose.setVisible(false);
            youwin.setVisible(true);
        }
    }

    @Override
    public void render(float delta) {
        //Clear the screen before drawing the next screen to avoid flickering
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell our stage to do actions and draw itself
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // change the stage's viewport when the screen size is changed
        stage.getViewport().update(width, height, true);
        background.setHeight(Gdx.graphics.getHeight());
        background.setWidth(Gdx.graphics.getWidth());
        youlose.setScaleX(2 * GameConfig.getScreenScaleX());
        youlose.setScaleY(2 * GameConfig.getScreenScaleY());
        youlose.setPosition((Gdx.graphics.getWidth() - youlose.getWidth() * youlose.getScaleX())/2,
                (Gdx.graphics.getHeight() - youlose.getHeight() * youlose.getScaleY()));
        youwin.setScaleX(2 * GameConfig.getScreenScaleX());
        youwin.setScaleY(2 * GameConfig.getScreenScaleY());
        youwin.setPosition((Gdx.graphics.getWidth() - youwin.getWidth() * youwin.getScaleX())/2,
                (Gdx.graphics.getHeight() - youwin.getHeight() * youwin.getScaleY()));
        menuButton.setPosition((Gdx.graphics.getWidth() - menuButton.getWidth())/2,
                (Gdx.graphics.getHeight() - menuButton.getHeight()) / 4);
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
