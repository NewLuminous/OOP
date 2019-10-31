package com.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.game.Main;
import com.game.util.loader.GameLoader;

public class LoadingScreen implements Screen {
    private Main parent;
    private SpriteBatch sb;
    private TextureAtlas.AtlasRegion title;
    private TextureAtlas.AtlasRegion dash;
    private Animation<TextureAtlas.AtlasRegion> flameAnimation;

    private int currentLoadingStage;
    private float stateTime;
    /**
     *Timer for exiting loading screen
     */
    private float countDown = 1f;

    public LoadingScreen(Main Main) {
        parent = Main;
        sb = new SpriteBatch();
        sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
    }

    @Override
    public void show() {
        stateTime = 0;
        /**
         * Load loading images and wait until finished.
         */
        GameLoader.queueAddLoadingImages();
        GameLoader.manager.finishLoading();
        /**
         * Get images used to display loading images
         */
        TextureAtlas atlas = GameLoader.manager.get(GameLoader.LOADING_IMAGES);
        title = atlas.findRegion("staying-alight-logo");
        dash = atlas.findRegion("loading-dash");
        flameAnimation = new Animation<>(0.07f, atlas.findRegions("flames/flames"), Animation.PlayMode.LOOP);

        System.out.println("Loading images...");
        GameLoader.queueAddImages();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        /**
         * Accumulate elapsed animation time
         */
        stateTime += delta;
        /**
         * Get current frame of animation for the current stateTime
         */
        TextureRegion currentFrame = flameAnimation.getKeyFrame(stateTime, true);

        sb.begin();
        drawLoadingBar(currentLoadingStage * 2, currentFrame);
        Vector2 titleSize = new Vector2(title.getRegionWidth() * 2, title.getRegionHeight() * 2);
        sb.draw(title, (Gdx.graphics.getWidth() - titleSize.x) / 2, (Gdx.graphics.getHeight() - titleSize.y) / 1.5f, titleSize.x, titleSize.y);
        sb.end();

        /**
         * Check if the asset manager has finished loading
         */
        if (GameLoader.manager.update()) {
            ++currentLoadingStage;
            if (currentLoadingStage == GameLoader.AssetType.values().length) {
                System.out.println("Finished");
            }
            else if (currentLoadingStage < GameLoader.AssetType.values().length) {
                switch (GameLoader.AssetType.values()[currentLoadingStage]) {
                    case IMAGE:
                        System.out.println("Loading images...");
                        GameLoader.queueAddImages();
                        break;
                    case SKIN:
                        System.out.println("Loading skin...");
                        GameLoader.queueAddSkin();
                        break;
                    case SOUND:
                        System.out.println("Loading sounds...");
                        GameLoader.queueAddSounds();
                        break;
                    case MUSIC:
                        System.out.println("Loading music...");
                        GameLoader.queueAddMusic();
                        break;
                    case FONT:
                        System.out.println("Loading fonts...");
                        GameLoader.queueAddFonts();
                        break;
                    case PARTY:
                        System.out.println("Loading particle effects...");
                        GameLoader.queueAddParticleEffects();
                        break;
                }
            }
            if (currentLoadingStage >= GameLoader.AssetType.values().length) {
                /**
                 * Timer to stay on loading screen for short period once done loading
                 */
                countDown -= delta;
                /**
                 * Cap loading stage as will use later to display progress bar.
                 */
                currentLoadingStage = GameLoader.AssetType.values().length;
                if (countDown < 0) {
                    parent.changeScreen(ScreenType.MENU);
                }
            }
        }
    }

    private void drawLoadingBar(int stage, TextureRegion currentFrame) {
        for (int i = 0; i < stage; ++i) {
            int size = Gdx.graphics.getWidth()/(GameLoader.AssetType.values().length * 4 + 3) * 2;
            Vector2 pos = new Vector2(size / 2 + i * size, Gdx.graphics.getHeight() / 3);
            sb.draw(currentFrame, pos.x, pos.y + size/6f, size*4/3f, size*4/3f);
            sb.draw(dash, pos.x + size/6f, pos.y, size, size);
        }
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
