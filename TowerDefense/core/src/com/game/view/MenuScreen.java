package com.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.game.GameConfig;
import com.game.Main;

public class MenuScreen implements Screen {
    private Main parent;
    private Stage stage;

    public MenuScreen(Main Main) {
        parent = Main;

        // create stage and set it as input processor
        stage = new Stage(new ScreenViewport());    //produce errors
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Image background = new Image(new Texture(Gdx.files.internal(GameConfig.BACKGROUND_PATH)));
        stage.addActor(background);

        // Create a table that fills the screen. Everything else will go inside this table.
        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(true);
        stage.addActor(table);

        // temporary until we have asset manager in
        Skin skin = new Skin(Gdx.files.internal(GameConfig.SKIN_PATH));

        //create buttons
        TextButton newGame = new TextButton("New Game", skin);
        newGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(ScreenType.APPLICATION);
            }
        });
        TextButton _continue = new TextButton("Continue", skin);
        _continue.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(ScreenType.ENDGAME);
            }
        });
        TextButton settings = new TextButton("Settings", skin);
        settings.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(ScreenType.PREFERENCES);
            }
        });
        TextButton exit = new TextButton("Exit", skin);
        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        //add buttons to table
        table.add(newGame).width(GameConfig.BUTTON_WIDTH).fillX().uniformX();
        table.row().pad(GameConfig.BUTTON_PADDING, 0, 0, 0);
        table.add(_continue).fillX().uniformX();
        table.row().pad(GameConfig.BUTTON_PADDING, 0, 0, 0);
        table.add(settings).fillX().uniformX();
        table.row().pad(GameConfig.BUTTON_PADDING, 0, 0, 0);
        table.add(exit).fillX().uniformX();
        table.setDebug(false);
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
        // change the stage's viewport when teh screen size is changed
        stage.getViewport().update(width, height, true);
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
