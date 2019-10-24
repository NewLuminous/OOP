package com.game;

import com.badlogic.gdx.Game;
import com.game.view.*;

public class Main extends Game {
	private GameConfig gameConfig;

	private LoadingScreen loadingScreen;
	private PreferencesScreen preferencesScreen;
	private MenuScreen menuScreen;
	private MainScreen mainScreen;
	private EndScreen endScreen;

	@Override
	public void create() {
		gameConfig = new GameConfig();
		loadingScreen = new LoadingScreen(this);
		setScreen(loadingScreen);
	}

	public void changeScreen(ScreenTypes screen){
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
