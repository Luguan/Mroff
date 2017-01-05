package com.Luguan.Mroff.screen;

import com.Luguan.Mroff.Mroff;
import com.Luguan.Mroff.game.Game;
import com.Luguan.Mroff.gui.DebugGUI;
import com.Luguan.Mroff.livingentity.Item;
import com.Luguan.Mroff.livingentity.Mushroom;
import com.Luguan.Mroff.livingentity.Player;
import com.Luguan.Mroff.physics.ObjectPhysics;
import com.Luguan.Mroff.util.Debug;
import com.Luguan.Mroff.util.Utils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lukas on 6/9/2015.
 */
public class GameScreen extends ScreenAdapter implements PauseMenuScreen.PauseMenuAction {
    private ScreenAdapter pauseMenu;
    private final Game game;

    public GameScreen () {
        game = new Game();
    }

    private boolean isPaused() {
        return pauseMenu != null;
    }

    @Override
    public void render(float delta) {
        update(delta);

        game.render(delta);

        if (pauseMenu != null) {
            pauseMenu.render(delta);
        }
    }

    @Override
    public void resize(int width, int height) {
        game.resize(width, height);
    }


    @Override
    public void dispose() {
        pauseMenu.dispose();
    }

    private void update(float delta) {
        if (isPaused()) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                menuResume();
            }

            // If the game is paused, don't update
            return;
        }

        game.update(delta);

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            pauseMenu = new PauseMenuScreen(this);
            pauseMenu.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }

    }


    @Override
    public void menuResume() {
        pauseMenu.dispose();
        pauseMenu = null;
    }


    @Override
    public void menuReturnToMainMenu() {
        Mroff.getInstance().openMainMenu();
    }

    public Game getGame() {
        return game;
    }
}