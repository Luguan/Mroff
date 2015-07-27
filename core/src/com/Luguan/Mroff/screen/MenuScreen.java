package com.Luguan.Mroff.screen;

import com.Luguan.Mroff.Mroff;
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
		if (button.equals("New Game")) {
			action.newGame();
		} else if (button.equals("Full Screen")) {
			Mroff.getInstance().toggleFullscreen();
		}
	}

	public interface MenuAction {
		void newGame();
	}
}
