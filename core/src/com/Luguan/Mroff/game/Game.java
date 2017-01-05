package com.Luguan.Mroff.game;

import com.Luguan.Mroff.Mroff;
import com.Luguan.Mroff.gui.DebugGUI;
import com.Luguan.Mroff.livingentity.Item;
import com.Luguan.Mroff.livingentity.Mushroom;
import com.Luguan.Mroff.livingentity.Player;
import com.Luguan.Mroff.physics.ObjectPhysics;
import com.Luguan.Mroff.util.Debug;
import com.Luguan.Mroff.util.Utils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Game implements ObjectPhysics.CollisionEvent {
    private static final float TILE_SCALE = 1 / 16f;
    private static final float CAMERA_ZOOM = .015f;
    private OrthographicCamera cam;
    private OrthogonalTiledMapRenderer renderer;
    private Map map;
    private Player character;
    private List<Item> items;
    private DebugGUI debugGUI = new DebugGUI();
    private Vector2 cameraPosition = new Vector2();
    private float viewportHeight;
    private float viewportWidth;

    public Game() {
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

        loadMap("Level2");
    }

    public void loadMap(String mapName) {
        map = new Map(Mroff.getInstance().getMap(mapName));

        items = new ArrayList<Item>();
        cam = new OrthographicCamera();
        renderer = new OrthogonalTiledMapRenderer(map.getTiledMap(), TILE_SCALE);

        spawnCharacter();
        resizeCamera();
    }

    public void update(float delta) {
        character.setFlying(debugGUI.isFlightModeEnabled());

        character.update(delta);
        for (Item item : items) {
            item.update(delta);
        }

        moveCamera();

        if (character.getPosition().x > 50) {
            loadMap("Level1");
        }
    }


    private void moveCamera() {
        cameraPosition = Utils.moveTowards(character.getPosition(), new Vector2(cameraPosition.x, cameraPosition.y));
        cam.position.set(Utils.roundVector2(cameraPosition, CAMERA_ZOOM), 0);
    }

    private void spawnCharacter() {
        character = new Player(map, this);
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

    public void render(float delta) {
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
        TiledMapTileLayer collision = (TiledMapTileLayer) map.getTiledMap().getLayers().get("Collision");

        for (int x = 0; x < collision.getWidth(); x++) {
            for (int y = 0; y < collision.getHeight(); y++) {
                if (Player.intersects(new Rectangle(
                        character.getPosition().x,
                        character.getPosition().y,
                        3 / 4f,
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

    public void resize(int width, int height) {
        viewportWidth = CAMERA_ZOOM * width;
        viewportHeight = CAMERA_ZOOM * height;
        resizeCamera();
    }


    private void resizeCamera() {
        cam.viewportWidth = viewportWidth;
        cam.viewportHeight = viewportHeight;
        cam.update();
    }

    @Override
    public void onItemBlockCollision(int x, int y) {

        System.out.println("coll");
        items.add(new Mushroom(map, x, y));
    }

    public Player getCharacter() {
        return character;
    }
}
