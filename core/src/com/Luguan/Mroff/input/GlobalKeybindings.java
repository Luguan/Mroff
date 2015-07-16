package com.Luguan.Mroff.input;

import com.Luguan.Mroff.Mroff;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

import java.awt.*;

public class GlobalKeybindings extends InputAdapter {

    @Override
    public boolean keyUp(int keycode) {
        if (Gdx.input.isKeyPressed(Input.Keys.ALT_LEFT) && keycode == Input.Keys.ENTER) {
            Mroff.getInstance().toggleFullscreen();
        }

        return false;
    }
}
