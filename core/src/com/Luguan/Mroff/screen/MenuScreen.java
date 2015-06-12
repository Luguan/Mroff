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

public class MenuScreen extends ScreenAdapter {

	private final Stage stage;
	private final Skin skin;
	private MenuAction action;
	private Group grp;

	public MenuScreen(final MenuAction action) {
		this.action = action;

		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		skin = Mroff.getInstance().getSkin();

		Group grp = createMainMenu();
		stage.addActor(grp);
	}

	private Group createMainMenu() {
		grp = new Group();

		Button bttn = createButton("New Game", 0);
		bttn.addListener(new EventListener() {
			@Override
			public boolean handle(Event event) {
				if (event instanceof InputEvent) {
					InputEvent inputEvent = (InputEvent) event;
					if (inputEvent.getType() == InputEvent.Type.touchUp && !inputEvent.isTouchFocusCancel()) {
						action.newGame();
						return true;
					}
				}
				return true;

			}
		});
		grp.addActor(bttn);

		bttn = createButton("Load Game", bttn.getY() - bttn.getHeight() * .5f);
		grp.addActor(bttn);

		bttn = createButton("Settings", bttn.getY() - bttn.getHeight() * .5f);
		grp.addActor(bttn);

		bttn = createButton("Load custom map", bttn.getY() - bttn.getHeight() * .5f);
		grp.addActor(bttn);

		bttn = createButton("Full Screen", bttn.getY() - bttn.getHeight() * .5f);
		bttn.addListener(new EventListener() {
			@Override
			public boolean handle(Event event) {
				if (event instanceof InputEvent) {
					InputEvent inputEvent = (InputEvent) event;
					if (inputEvent.getType() == InputEvent.Type.touchUp && !inputEvent.isTouchFocusCancel()) {
						Gdx.graphics.setDisplayMode(Gdx.graphics.getDisplayModes()[0]);
						return true;
					}
				}
				return true;
			}
		});
		grp.addActor(bttn);
		grp.setHeight(bttn.getY());
		return grp;
	}

	private Button createButton(String label, float y) {
		TextButton button = new TextButton(label, skin);

		button.setPosition(0, y, Align.center);
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
	}

	public interface MenuAction {
		void newGame();
	}
}
