package com.Luguan.Mroff.screen;

import com.Luguan.Mroff.Mroff;
import com.Luguan.Mroff.character.Character;
import com.Luguan.Mroff.util.Utils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Lukas on 6/9/2015.
 */
public class GameScreen extends ScreenAdapter{
    OrthogonalTiledMapRenderer renderer;

    Character character;
    private final OrthographicCamera cam;
    private final TiledMap level1;

    public GameScreen(){
        level1 = Mroff.getInstance().getMap("Level1");

        cam = createCam();

        float unitScale = 1 / 7.5f;
        renderer = new OrthogonalTiledMapRenderer(Mroff.getInstance().getMap("Level1"), unitScale);

        spawnCharacter();
    }

    private OrthographicCamera createCam() {
        OrthographicCamera cam = new OrthographicCamera();
        return cam;
    }

    private void update(float delta) {
        character.Draw(renderer.getBatch());
        character.Update(delta);

        moveCamera();
    }

    private void moveCamera() {
        cam.position.set(Utils.moveTowards(character.getPosition(), new Vector2(cam.position.x,cam.position.y)),0);
    }

    private void spawnCharacter() {
        character = new Character();

        MapLayers layers = level1.getLayers();

        for (MapLayer layer : layers) {
            TiledMapTileLayer tileLayer = (TiledMapTileLayer)layer;

            for(int y = 0; y <tileLayer.getHeight() - 1; y++) {
                for(int x =0; x <tileLayer.getWidth() - 1; x++) {
                    TiledMapTileLayer.Cell cell = tileLayer.getCell(x, y);
                    TiledMapTile tile = cell.getTile();
                    if(tile.getProperties().containsKey("Start")) {
                         character.setPosition((float)x+1,(float)y+16);
                    }
                }
            }
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cam.update();

        renderer.setView(cam);
        renderer.render();

        update(delta);
    }

    @Override
    public void resize(int width, int height) {
        float scale = 0.06f;
        cam.viewportWidth = scale * width;
        cam.viewportHeight = scale * height;
        cam.update();
    }
}