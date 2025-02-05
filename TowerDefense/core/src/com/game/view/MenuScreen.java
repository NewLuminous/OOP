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
import com.game.util.loader.GameLoader;
import com.game.util.player.MusicPlayer;

public class MenuScreen implements Screen {
    private Main parent;
    private Stage stage;
    private Image background;

    public MenuScreen(Main Main) {
        parent = Main;

        // create stage and set it as input processor
        stage = new Stage(new ScreenViewport());    //produce errors
        background = new Image((Texture) GameLoader.manager.get(GameLoader.BACKGROUND));
        stage.addActor(background);

        // Create a table that fills the screen. Everything else will go inside this table.
        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(true);
        stage.addActor(table);

        // temporary until we have asset manager in
        Skin skin = GameLoader.getManager().get(GameLoader.GLASSY_SKIN);

        //create buttons
        TextButton newGame = new TextButton("New Game", skin);
        newGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.mainScreenContinued = false;
                parent.changeScreen(ScreenType.APPLICATION);
            }
        });
        TextButton _continue = new TextButton("Continue", skin);
        _continue.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.mainScreenContinued = true;
                parent.changeScreen(ScreenType.APPLICATION);
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
    public void show() {
        MusicPlayer.play(MusicPlayer.ALGORITHMS);
        Gdx.input.setInputProcessor(stage);
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
