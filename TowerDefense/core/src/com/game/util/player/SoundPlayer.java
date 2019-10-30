package com.game.util.player;

import com.badlogic.gdx.audio.Sound;
import com.game.util.loader.GameLoader;

public class SoundPlayer extends AudioPlayer {
    public enum SoundType {
        BOING, PING
    }

    private SoundPlayer() {}

    public final static void play(SoundType soundType) {
        Sound sound;
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
        sound.play();
    }
}
