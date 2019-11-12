/**
 * @brief	This is where the game starts
 * @author	Nguyen Minh Tan
 * @author	Vu Minh Ngoc
 * @version	0.5.0
 * @date	11/12/2019 (November 12th, 2019)
 */
package com.game;

import com.badlogic.gdx.Game;
import com.game.util.loader.GameLoader;
import com.game.view.*;

public class Main extends Game {
	private GameConfig gameConfig;
	public boolean mainScreenContinued;

	private LoadingScreen loadingScreen;
	private PreferencesScreen preferencesScreen;
	private MenuScreen menuScreen;
	private MainScreen mainScreen;
	private EndScreen endScreen;

	/**
	 * @brief	Gets called when <code>Main</code> object being constructed.
	 * @details	Game starts with the loading screen. The main menu shows up later when the game gets ready.
	 */
	@Override
	public void create() {
		GameLoader.startManager();
		gameConfig = new GameConfig();
		loadingScreen = new LoadingScreen(this);
		setScreen(loadingScreen);
	}

	public void changeScreen(ScreenType screen){
		switch(screen){
			case MENU:
				if(menuScreen == null) menuScreen = new MenuScreen(this);
				this.setScreen(menuScreen);
				break;
			case PREFERENCES:
				if(preferencesScreen == null) preferencesScreen = new PreferencesScreen(this);
				this.setScreen(preferencesScreen);
				break;
			case APPLICATION:
				if(mainScreen == null) mainScreen = new MainScreen(this);
				this.setScreen(mainScreen);
				break;
			case ENDGAME:
				if(endScreen == null) endScreen = new EndScreen(this);
				this.setScreen(endScreen);
				break;
		}
	}

	public GameConfig getPreferences() {
		return gameConfig;
	}
}
