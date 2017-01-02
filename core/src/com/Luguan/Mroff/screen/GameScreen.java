package com.Luguan.Mroff.screen;

import com.Luguan.Mroff.Mroff;
import com.Luguan.Mroff.gui.DebugGUI;
import com.Luguan.Mroff.livingentity.Item;
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
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
public class GameScreen extends ScreenAdapter implements PauseMenuScreen.PauseMenuAction, ObjectPhysics.CollisionEvent {
    public static final float TILE_SCALE = 1 / 16f;
    private static final float CAMERA_ZOOM = .015f;
    private final OrthographicCamera cam;
    private final TiledMap level;
    OrthogonalTiledMapRenderer renderer;
    ScreenAdapter pauseMenu;
    Player character;
    List<Item> items;
    DebugGUI debugGUI = new DebugGUI();

    public GameScreen() {
        level = Mroff.getInstance().getMap("Level2");

        items = new ArrayList<Item>();
        cam = new OrthographicCamera();
        renderer = new OrthogonalTiledMapRenderer(Mroff.getInstance().getMap("Level2"), TILE_SCALE);

        spawnCharacter();

        debugGUI.setListener(new DebugGUI.OnAction() {
            @Override
            public void onKillAll() {
                // TODO
            }

            @Override
            public void onRestart() {
                // TODO
            }
        });
    }

    private void update(float delta) {
        if (isPaused()) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                menuResume();
            }

            // If the game is paused, don't update
            return;
        }

        character.setFlying(debugGUI.isFlightModeEnabled());

        character.update(delta);
        for (Item item : items) {
            item.update(delta);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            pauseMenu = new PauseMenuScreen(this);
            pauseMenu.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }

        moveCamera();
    }

    private boolean isPaused() {
        return pauseMenu != null;
    }

    private void moveCamera() {
        cam.position.set(Utils.moveTowards(character.getPosition(), new Vector2(cam.position.x, cam.position.y)), 0);
    }

    private void spawnCharacter() {
        character = new Player(this);
        character.setPosition(15, 128 - 17);
      /*  character = new Player(this);

        MapLayers layers = level.getLayers();

        for(MapLayer layer: layers) {

            TiledMapTileLayer tileLayer = (TiledMapTileLayer)layer;

            for (int y = 0; y < tileLayer.getHeight() - 1; y++) {
                for (int x = 0; x < tileLayer.getWidth() - 1; x++) {
                    TiledMapTileLayer.Cell cell = tileLayer.getCell(x, y);
                    if (cell != null) {
                        TiledMapTile tile = cell.getTile();

                        if (tile.getProperties().containsKey("Start")) {
                            character.setPosition((float) x, (float) y);
                        }
                    }
                }
            }
        }

        character.setPosition(5, 7);*/
    }

    @Override
    public void render(float delta) {
        update(delta);

        Batch batch = renderer.getBatch();

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cam.update();

        renderer.setView(cam);
        renderer.render();

        batch.begin();
        character.draw(batch);

        for (Item item : items) {
            item.draw(batch);
        }

        batch.end();

        if (pauseMenu != null) {
            pauseMenu.render(delta);
        }

        Debug.setEnabled(debugGUI.isPhysicsDebugEnabled());
        if (debugGUI.isPhysicsDebugEnabled()) {
            drawDebugOverlay();
        }



        debugGUI.draw();

    }

    private void drawDebugOverlay() {
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.setProjectionMatrix(cam.combined);
        shapeRenderer.begin();

        for (Debug.Vector box : Debug.checkedBoxes) {
            shapeRenderer.setColor(box.color);
            shapeRenderer.rect(box.x, box.y, box.width, box.height);
        }
        TiledMapTileLayer collision = (TiledMapTileLayer)((GameScreen) Mroff.getInstance().getScreen()).getLevel().getLayers().get("Collision");

        for (int x = 0; x < collision.getWidth(); x++) {
            for (int y = 0; y < collision.getHeight(); y++) {
                if (Player.intersects(new Rectangle(
                        character.getPosition().x,
                        character.getPosition().y,
                        3/4f,
                        1f
                ), new Rectangle(x, y, 1, 1)).len() != 0) {
                    shapeRenderer.setColor(Color.DARK_GRAY);
                    shapeRenderer.rect(x, y, 1, 1);
                }
            }
        }

        shapeRenderer.setColor(Color.MAGENTA);
        shapeRenderer.rect(character.getPosition().x,
                character.getPosition().y,
                character.getWidth(), character.getHeight());


        Debug.checkedBoxes.clear();

        shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {
        float scale = CAMERA_ZOOM;
        cam.viewportWidth = scale * width;
        cam.viewportHeight = scale * height;
        cam.update();
    }

    @Override
    public void dispose() {
        pauseMenu.dispose();
    }

    @Override
    public void menuResume() {
        pauseMenu.dispose();
        pauseMenu = null;
    }

    public TiledMap getLevel() {
        return level;
    }

    @Override
    public void menuReturnToMainMenu() {
        Mroff.getInstance().openMainMenu();
    }

    @Override
    public void onItemBlockCollision(int x, int y) {
        System.out.println("coll");
    }

    public Player getCharacter() {
        return character;
    }
}