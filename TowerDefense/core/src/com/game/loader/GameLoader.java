package com.game.loader;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

/**
 * @brief   This class loads resources for the game.
 * @warning Avoid making <code>AssetManager</code> field of this class static as textures may get corrupted then resuming an Android app.
 * @note    This class is implemented using Singleton pattern. Therefore, do not forget to restart the manager whenever the application starts.
 */
public final class GameLoader {
    public static AssetManager manager;

    public static final String BACKGROUND = "background.jpg";
    public static final String MAP = "maps/map1.png";

    private static final String ENEMY_FOLDER = "PNG/Default size/enemies/";
    public static final String NORMAL_ENEMY = ENEMY_FOLDER + "towerDefense_tile247.png";
    public static final String TANKER_ENEMY_HEAD = ENEMY_FOLDER + "towerDefense_tile245.png";
    public static final String TANKER_ENEMY_BODY = ENEMY_FOLDER + "towerDefense_tile269.png";
    public static final String SMALLER_ENEMY = ENEMY_FOLDER + "towerDefense_tile248.png";
    public static final String BOSS_ENEMY_HEAD = ENEMY_FOLDER + "towerDefense_tile291.png";
    public static final String BOSS_ENEMY_BODY = ENEMY_FOLDER + "towerDefense_tile268.png";

    public static final String TOWER_FOLDER = "PNG/Default size/towers/";
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

    private GameLoader() {}

    /**
     * @brief   Starts the manager.
     * @note    Call this method before everything!
     */
    public static void startManager() {
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

    public static void queueAllImages() {
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
}