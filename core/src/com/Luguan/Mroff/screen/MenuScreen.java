package com.Luguan.Mroff.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

import java.util.ArrayList;
import java.util.List;

public class MenuScreen extends CenteredMenu {

	private MenuAction action;

	public MenuScreen(MenuAction action) {
		this.action = action;
	}

	@Override
	protected List<Button> getButtons() {
		List<Button> bttns = new ArrayList<Button>();
		bttns.add(createButton("New Game"));
		bttns.add(createButton("Load Game"));
		bttns.add(createButton("Settings"));
		bttns.add(createButton("Load custom map"));
		bttns.add(createButton("Full Screen"));
		return bttns;
	}

	@Override
	public void buttonClick(String button) {
		switch (button) {
			case "New Game":
				action.newGame();
				break;
			case "Full Screen":
				Gdx.graphics.setDisplayMode(Gdx.graphics.getDisplayModes()[0]);
				break;
		}
	}
}
