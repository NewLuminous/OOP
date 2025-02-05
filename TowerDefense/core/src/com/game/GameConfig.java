package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.game.util.player.MusicPlayer;
import com.game.util.player.SoundPlayer;

/**
 * @brief   This class stores game settings
 */
public final class GameConfig {
    public static final String GAME_NAME = "Tower Defense";

    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 720;

    /**
     * Resizable flag. Set <code>false</code> to simplify the code.
     */
    public static final boolean IS_RESIZABLE = true;

    /**
     * Viewport measurements.
     * @see     com.badlogic.gdx.graphics.OrthographicCamera
     */
    public static final int VIEWPORT_WIDTH = 32;
    public static final int VIEWPORT_HEIGHT = 18;
    public static final int GRID_SIZE = 40;

    /**
     * The width of buttons on main menu.
     * @see     com.game.view.MenuScreen#show()
     */
    public static final int BUTTON_WIDTH = 400;

    /**
     * Button's padding on main menu.
     * @see     com.game.view.MenuScreen#show()
     */
    public static final int BUTTON_PADDING = 50;

    public static final String MAP1 = "maps/map1.dat";

    private static final String PREF_MUSIC_VOLUME = "volume";
    private static final String PREF_MUSIC_ENABLED = "music.enabled";
    private static final String PREF_SOUND_ENABLED = "sound.enabled";
    private static final String PREF_SOUND_VOL = "sound";
    private static final String PREFS_NAME = "b2dtut";

    public GameConfig() {
        MusicPlayer.setVolume(getMusicVolume());
        MusicPlayer.setEnabled(isMusicEnabled());
        SoundPlayer.setVolume(getSoundVolume());
        SoundPlayer.setEnabled(isSoundEffectsEnabled());
    }

    public static float getScreenScaleX() {
        return (float)Gdx.graphics.getWidth() / SCREEN_WIDTH;
    }

    public static float getScreenScaleY() {
        return (float)Gdx.graphics.getHeight() / SCREEN_HEIGHT;
    }

    public static boolean insideViewport(double posx, double posy) {
        if (0 > posx || posx > VIEWPORT_WIDTH) return false;
        if (0 > posy || posy > VIEWPORT_HEIGHT) return false;
        return true;
    }

    private Preferences getPrefs() {
        return Gdx.app.getPreferences(PREFS_NAME);
    }

    public boolean isSoundEffectsEnabled() {
        return getPrefs().getBoolean(PREF_SOUND_ENABLED, true);
    }

    public void setSoundEffectsEnabled(boolean soundEffectsEnabled) {
        getPrefs().putBoolean(PREF_SOUND_ENABLED, soundEffectsEnabled);
        getPrefs().flush();
        SoundPlayer.setEnabled(soundEffectsEnabled);
    }

    public boolean isMusicEnabled() {
        return getPrefs().getBoolean(PREF_MUSIC_ENABLED, true);
    }

    public void setMusicEnabled(boolean musicEnabled) {
        getPrefs().putBoolean(PREF_MUSIC_ENABLED, musicEnabled);
        getPrefs().flush();
        MusicPlayer.setEnabled(musicEnabled);
    }

    public float getMusicVolume() {
        return getPrefs().getFloat(PREF_MUSIC_VOLUME, 0.5f);
    }

    public void setMusicVolume(float volume) {
        getPrefs().putFloat(PREF_MUSIC_VOLUME, volume);
        getPrefs().flush();
        MusicPlayer.setVolume(volume);
    }

    public float getSoundVolume() {
        return getPrefs().getFloat(PREF_SOUND_VOL, 0.5f);
    }

    public void setSoundVolume(float volume) {
        getPrefs().putFloat(PREF_SOUND_VOL, volume);
        getPrefs().flush();
        SoundPlayer.setVolume(volume);
    }
}
