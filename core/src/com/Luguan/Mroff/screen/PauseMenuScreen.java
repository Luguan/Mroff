package com.Luguan.Mroff.screen;

import com.badlogic.gdx.scenes.scene2d.ui.Button;

import java.util.ArrayList;
import java.util.List;

public class PauseMenuScreen extends CenteredMenu {

	public static final String RESUME = "Resume";
	public static final String RETURN_TO_MAIN_MENU = "Return to main menu";

	private PauseMenuAction action;

	public PauseMenuScreen(PauseMenuAction action) {
		this.action = action;
		clear = false;
	}

	@Override
	protected List<Button> getButtons() {
		List<Button> bttns = new ArrayList<Button>();
		bttns.add(createButton(RESUME));
		bttns.add(createButton(RETURN_TO_MAIN_MENU));
		return bttns;
	}

	@Override
	void buttonClick(String button) {
		if (button.equals(RESUME)) {
			action.menuResume();
		} else if (button.equals(RETURN_TO_MAIN_MENU)) {
			action.menuReturnToMainMenu();
		}
	}

	public interface PauseMenuAction {
		void menuResume();

		void menuReturnToMainMenu();
	}
}
