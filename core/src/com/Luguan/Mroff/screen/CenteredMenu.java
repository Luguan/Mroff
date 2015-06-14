package com.Luguan.Mroff.screen;

import com.Luguan.Mroff.Mroff;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.List;

public abstract class CenteredMenu extends ScreenAdapter {

	private final Stage stage;
	private final Skin skin;
	private Group grp;

	public CenteredMenu() {

		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		skin = Mroff.getInstance().getSkin();

		Group grp = createMainMenu();
		stage.addActor(grp);
	}

	protected abstract List<Button> getButtons();

	private Group createMainMenu() {
		grp = new Group();

		Button prevButton = null;
		List<Button> buttons = getButtons();
		for (Button button : buttons) {
			if (prevButton != null) {
				button.setPosition(0, prevButton.getY() - prevButton.getHeight() * .5f, Align.center);
			} else {
				button.setPosition(0, 0, Align.center);
			}
			grp.addActor(button);

			prevButton = button;
		}
		if (prevButton != null) {
			grp.setHeight(prevButton.getY());
		}
		return grp;
	}

	protected Button createButton(final String label) {
		TextButton button = new TextButton(label, skin);
		button.addListener(new EventListener() {
			@Override
			public boolean handle(Event event) {
				if (event instanceof InputEvent) {
					InputEvent inputEvent = (InputEvent) event;
					if (inputEvent.getType() == InputEvent.Type.touchUp && !inputEvent.isTouchFocusCancel()) {
						buttonClick(label);
						return true;
					}
				}
				return true;
			}
		});
		return button;
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);

		grp.setY(Gdx.graphics.getHeight() / 2 - grp.getHeight() / 2);
		grp.setX(Gdx.graphics.getWidth() / 2);

		super.resize(width, height);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act();
		stage.draw();
	}

	@Override
	public void dispose() {
		stage.dispose();
		Gdx.input.setInputProcessor(null);
	}


	abstract void buttonClick(String button);

	public interface MenuAction {
		void newGame();
	}
}
