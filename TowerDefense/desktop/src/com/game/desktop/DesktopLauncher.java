package com.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.game.Main;
import com.game.GameConfig;

public class DesktopLauncher {
	/**
	 *Starts the game on desktop.
	 */
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = GameConfig.SCREEN_WIDTH;
		config.height = GameConfig.SCREEN_HEIGHT;
		config.resizable = GameConfig.IS_RESIZABLE;
		new LwjglApplication(new Main(), config);
	}
}
