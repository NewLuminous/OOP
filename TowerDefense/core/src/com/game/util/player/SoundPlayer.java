package com.game.util.player;

import com.badlogic.gdx.audio.Sound;
import com.game.util.loader.GameLoader;

public class SoundPlayer extends AudioPlayer {
    public enum SoundType {
        BOING, PING
    }

    private static Sound sound;
    private static SoundType soundType;

    private static float volume;
    private static boolean enabled;

    private SoundPlayer() {}

    public static void play(SoundType soundType) {
        if (sound != null && SoundPlayer.soundType != soundType) sound.dispose();
        switch (soundType) {
            case BOING:
                sound = GameLoader.getManager().get(GameLoader.BOING_SOUND);
                break;
            case PING:
                sound = GameLoader.getManager().get(GameLoader.PING_SOUND);
                break;
            default:
                sound = GameLoader.getManager().get(GameLoader.PING_SOUND);
        }
        SoundPlayer.soundType = soundType;
        if (enabled) {
            long soundId = sound.play(volume);
            sound.setVolume(soundId, volume);
        }
    }

    public static void setVolume(float volume) {
        SoundPlayer.volume = volume;
    }

    public static void setEnabled(boolean enabled) {
        SoundPlayer.enabled = enabled;
    }
}
