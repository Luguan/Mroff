package com.Luguan.Mroff.input;

import com.Luguan.Mroff.Mroff;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

public class GlobalKeybindings extends InputAdapter {
	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
			case Input.Keys.ESCAPE:
				Mroff.getInstance().goFullscreen();
				return true;
		}

		return false;
	}
}
