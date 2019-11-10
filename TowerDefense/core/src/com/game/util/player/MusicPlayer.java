package com.game.util.player;

import com.badlogic.gdx.audio.Music;
import com.game.util.loader.GameLoader;

public class MusicPlayer extends AudioPlayer {
    public static final int ALGORITHMS = 0;
    public static final int BATTLE = 1;
    public static final int BOSS = 2;

    private static Music music;
    private static int musicName;

    private static float volume;
    private static boolean enabled;

    private MusicPlayer() {}

    public static void play(int musicName) {
        if (music != null) {
            if (MusicPlayer.musicName == musicName) return;
            music.dispose();
        }
        switch (musicName) {
            case ALGORITHMS:
                music = GameLoader.getManager().get(GameLoader.ALGORITHMS_BY_CHAD_CROUCH);
                break;
            case BATTLE:
                music = GameLoader.getManager().get(GameLoader.BATTLE_NORMAL_BY_BOXCAT_GAMES);
                break;
            case BOSS:
                music = GameLoader.getManager().get(GameLoader.BATTLE_BOSS_BY_BOXCAT_GAMES);
                break;
            default:
                throw new IllegalArgumentException("Music not found");
        }
        MusicPlayer.musicName = musicName;
        music.setLooping(true);
        if (enabled) music.play();
        music.setVolume(volume);
    }

    public static void setVolume(float volume) {
        MusicPlayer.volume = volume;
        if (music != null) music.setVolume(volume);
    }

    public static void setEnabled(boolean enabled) {
        MusicPlayer.enabled = enabled;
        if (music != null)
            if (enabled) music.play(); else music.stop();
    }
}
