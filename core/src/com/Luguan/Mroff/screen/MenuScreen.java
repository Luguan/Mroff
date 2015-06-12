package com.Luguan.Mroff.screen;

import com.Luguan.Mroff.Mroff;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

public class MenuScreen extends ScreenAdapter {

	private final Stage stage;
	private final Skin skin;
	private MenuAction action;

	public MenuScreen(final MenuAction action) {
		this.action = action;

		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		skin = Mroff.getInstance().getSkin();

		Group grp = createMainMenu();
		stage.addActor(grp);
	}

	private Group createMainMenu() {
		Group grp = new Group();

		Button bttn = createButton("New Game", 0);
		bttn.addListener(new EventListener() {
			@Override
			public boolean handle(Event event) {
				if (event instanceof InputEvent) {
					InputEvent inputEvent = (InputEvent) event;
					if (inputEvent.getType() == InputEvent.Type.touchUp && !inputEvent.isTouchFocusCancel()) {
						action.newGame();
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

		grp.setX(Gdx.graphics.getWidth() / 2);
		grp.setY(Gdx.graphics.getHeight() / 2 - (bttn.getY() - bttn.getHeight() * .5f) / 2);
		return grp;
	}

	private Button createButton(String label, float y) {
		TextButton button = new TextButton(label, skin);

		button.setPosition(0, y, Align.center);
		return button;
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
