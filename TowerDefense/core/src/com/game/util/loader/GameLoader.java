package com.game.util.loader;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * @brief   This class loads resources for the game.
 * @warning Avoid making <code>AssetManager</code> field of this class static as textures may get corrupted then resuming an Android app.
 * @note    This class is implemented using Singleton pattern. Therefore, do not forget to restart the manager whenever the application starts.
 */
public final class GameLoader {
    public enum AssetType {
        IMAGE, SKIN, SOUND, MUSIC, FONT, PARTY
    }

    public static AssetManager manager;

    private GameLoader() {}

    /**
     * @brief   Starts the manager.
     * @note    Call this method before everything!
     */
    public static void startManager() {
        if (manager != null) manager.dispose();
        manager = new AssetManager();
    }

    /**
     * @brief   Gets the mananger.
     * @note    Restart the manager whenever the application starts before calling this.
     * @return  <code>null</code> if <code>startManager()</code> has never been called.
     *          <code>AssetManager</code> otherwise.
     * @see     #startManager()
     */
    public static AssetManager getManager() {
        return manager;
    }

    /**
     * Loading
     */
    public static final String LOADING_IMAGES = "loading/loading.atlas";

    public static void queueAddLoadingImages() {
        manager.load(LOADING_IMAGES, TextureAtlas.class);
    }

    /**
     * Images
     */
    public static final String BACKGROUND = "background.png";
    public static final String MAP = "maps/map1.png";

    private static final String ENEMY_FOLDER = "PNG/Default size/enemies/";
    public static final String NORMAL_ENEMY = ENEMY_FOLDER + "towerDefense_tile245.png";
    public static final String TANKER_ENEMY_HEAD = ENEMY_FOLDER + "towerDefense_tile246.png";
    public static final String TANKER_ENEMY_BODY = ENEMY_FOLDER + "towerDefense_tile269.png";
    public static final String SMALLER_ENEMY = ENEMY_FOLDER + "towerDefense_tile248.png";
    public static final String BOSS_ENEMY_HEAD = ENEMY_FOLDER + "towerDefense_tile291.png";
    public static final String BOSS_ENEMY_BODY = ENEMY_FOLDER + "towerDefense_tile268.png";

    private static final String TOWER_FOLDER = "PNG/Default size/towers/";
    public static final String NORMAL_TOWER_HEAD = TOWER_FOLDER + "towerDefense_tile249.png";
    public static final String NORMAL_TOWER_BODY = TOWER_FOLDER + "towerDefense_tile180.png";
    public static final String SNIPER_TOWER_HEAD = TOWER_FOLDER + "towerDefense_tile206.png";
    public static final String SNIPER_TOWER_BODY = TOWER_FOLDER + "towerDefense_tile181.png";
    public static final String MACHINE_GUN_TOWER_HEAD = TOWER_FOLDER + "towerDefense_tile203.png";
    public static final String MACHINE_GUN_TOWER_BODY = TOWER_FOLDER + "towerDefense_tile182.png";

    public static final String BULLET_FOLDER = "PNG/Default size/bullets/";
    public static final String NORMAL_TOWER_BULLET = BULLET_FOLDER + "towerDefense_tile295.png";
    public static final String SNIPER_TOWER_BULLET = BULLET_FOLDER + "towerDefense_tile252.png";
    public static final String MACHINE_GUN_BULLET = BULLET_FOLDER + "towerDefense_tile273.png";

    public static void queueAddImages() {
        manager.load(BACKGROUND, Texture.class);
        manager.load(MAP, Texture.class);

        manager.load(NORMAL_ENEMY, Texture.class);
        manager.load(TANKER_ENEMY_HEAD, Texture.class);
        manager.load(TANKER_ENEMY_BODY, Texture.class);
        manager.load(SMALLER_ENEMY, Texture.class);
        manager.load(BOSS_ENEMY_HEAD, Texture.class);
        manager.load(BOSS_ENEMY_BODY, Texture.class);

        manager.load(NORMAL_TOWER_HEAD, Texture.class);
        manager.load(NORMAL_TOWER_BODY, Texture.class);
        manager.load(SNIPER_TOWER_HEAD, Texture.class);
        manager.load(SNIPER_TOWER_BODY, Texture.class);
        manager.load(MACHINE_GUN_TOWER_HEAD, Texture.class);
        manager.load(MACHINE_GUN_TOWER_BODY, Texture.class);

        manager.load(NORMAL_TOWER_BULLET, Texture.class);
        manager.load(SNIPER_TOWER_BULLET, Texture.class);
        manager.load(MACHINE_GUN_BULLET, Texture.class);
    }

    /**
     * Skin
     */
    private static final String SKIN_FOLDER = "skin/";
    private static final String GLASSY_SKIN_ATLAS = SKIN_FOLDER + "glassy-ui.atlas";
    public static final String GLASSY_SKIN = SKIN_FOLDER + "glassy-ui.json";

    public static void queueAddSkin() {
        SkinLoader.SkinParameter params = new SkinLoader.SkinParameter(GLASSY_SKIN_ATLAS);
        manager.load(GLASSY_SKIN, Skin.class, params);
    }

    /**
     * Sounds
     */
    private static final String SOUND_FOLDER = "sounds/";
    public static final String BOING_SOUND = SOUND_FOLDER + "boing.wav";
    public static final String PING_SOUND = SOUND_FOLDER + "ping.wav";

    public static void queueAddSounds() {
        manager.load(BOING_SOUND, Sound.class);
        manager.load(PING_SOUND, Sound.class);
    }

    /**
     * Music
     */
    private static final String MUSIC_FOLDER = "music/";

    /**
     * @brief       Algorithms
     * @author      Chad Crouch
     * @copyright   CC BY-NC 3.0
     */
    public static final String ALGORITHMS_BY_CHAD_CROUCH = MUSIC_FOLDER + "Chad_Crouch_-_Algorithms.mp3";

    /**
     * @brief       Battle (Normal)
     * @authro      BoxCat Games
     * @copyright   CC BY 3.0
     */
    public static final String BATTLE_NORMAL_BY_BOXCAT_GAMES = MUSIC_FOLDER + "BoxCat_Games_-_20_-_Battle_Normal.mp3";

    /**
     * @brief       Battle (Boss)
     * @authro      BoxCat Games
     */
    public static final String BATTLE_BOSS_BY_BOXCAT_GAMES = MUSIC_FOLDER + "BoxCat_Games_-_05_-_Battle_Boss.mp3";

    public static void queueAddMusic() {
        manager.load(ALGORITHMS_BY_CHAD_CROUCH, Music.class);
        manager.load(BATTLE_NORMAL_BY_BOXCAT_GAMES, Music.class);
        manager.load(BATTLE_BOSS_BY_BOXCAT_GAMES, Music.class);
    }

    public static void queueAddFonts(){
    }

    public static void queueAddParticleEffects(){
    }
}