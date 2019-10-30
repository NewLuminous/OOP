package com.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.game.Main;
import com.game.util.loader.GameLoader;

public class PreferencesScreen implements Screen {

    private Main parent;
    private Stage stage;

    private Image background;

    private Label titleLabel;
    private Label volumeMusicLabel;
    private Label volumeSoundLabel;
    private Label musicOnOffLabel;
    private Label soundOnOffLabel;

    public PreferencesScreen(Main Main) {
        parent = Main;

        // create stage and set it as input processor
        stage = new Stage(new ScreenViewport());    //produce errors
        background = new Image((Texture) GameLoader.manager.get(GameLoader.BACKGROUND));
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.clear();
        stage.addActor(background);

        // Create a table that fills the screen. Everything else will go inside this table.
        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(true);
        stage.addActor(table);

        // temporary until we have asset manager in
        Skin skin = GameLoader.getManager().get(GameLoader.GLASSY_SKIN);

        //music
        final Slider volumeMusicSlider = new Slider( 0f, 1f, 0.1f,false, skin );
        volumeMusicSlider.setValue( parent.getPreferences().getMusicVolume() );
        volumeMusicSlider.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                parent.getPreferences().setMusicVolume( volumeMusicSlider.getValue() );
                return false;
            }
        });

        final CheckBox musicCheckbox = new CheckBox(null, skin);
        musicCheckbox.setChecked( parent.getPreferences().isMusicEnabled() );
        musicCheckbox.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = musicCheckbox.isChecked();
                parent.getPreferences().setMusicEnabled( enabled );
                return false;
            }
        });

        //sound
        final Slider soundMusicSlider = new Slider( 0f, 1f, 0.1f,false, skin );
        soundMusicSlider.setValue( parent.getPreferences().getSoundVolume() );
        soundMusicSlider.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                parent.getPreferences().setSoundVolume( soundMusicSlider.getValue() );
                return false;
            }
        });

        final CheckBox soundEffectsCheckbox = new CheckBox(null, skin);
        soundEffectsCheckbox.setChecked( parent.getPreferences().isSoundEffectsEnabled() );
        soundEffectsCheckbox.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = soundEffectsCheckbox.isChecked();
                parent.getPreferences().setSoundEffectsEnabled( enabled );
                return false;
            }
        });

        // return to main screen button
        final TextButton backButton = new TextButton("Back", skin, "small"); // the extra argument here "small" is used to set the button to the smaller version instead of the big default version
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(ScreenType.MENU);
            }
        });

        titleLabel = new Label( "Preferences", skin );
        titleLabel.setAlignment(Align.center);
        volumeMusicLabel = new Label( "Music Volume", skin );
        volumeSoundLabel = new Label( "Sound Volume", skin );
        musicOnOffLabel = new Label( "Music", skin );
        soundOnOffLabel = new Label( "Sound Effect", skin );

        table.add(titleLabel).colspan(2).width(Gdx.graphics.getWidth() / 2).height(Gdx.graphics.getHeight() / 10);
        table.row().pad(10, 0, 0, 10);
        table.add(volumeMusicLabel).height(Gdx.graphics.getHeight() / 10).left();
        table.add(volumeMusicSlider).fill();
        table.row().pad(10, 0, 0, 10);
        table.add(musicOnOffLabel).height(Gdx.graphics.getHeight() / 10).left();
        table.add(musicCheckbox).fill();
        table.row().pad(10, 0, 0, 10);
        table.add(volumeSoundLabel).height(Gdx.graphics.getHeight() / 10).left();
        table.add(soundMusicSlider).fill();
        table.row().pad(10, 0, 0, 10);
        table.add(soundOnOffLabel).height(Gdx.graphics.getHeight() / 10).left();
        table.add(soundEffectsCheckbox).fill();
        table.row().pad(10, 0, 0, 10);
        table.add(backButton).height(Gdx.graphics.getHeight() / 10).colspan(2);
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
