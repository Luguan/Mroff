package com.Luguan.Mroff.screen;

import com.Luguan.Mroff.Mroff;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import java.awt.image.Kernel;

/**
 * Created by Lukas on 6/9/2015.
 */
public class GameScreen extends ScreenAdapter{
    SpriteBatch batch;
    Texture img;
    OrthogonalTiledMapRenderer renderer;

    private final OrthographicCamera cam;

    public GameScreen(){
        Mroff.getInstance().getMap("Level1");

        cam = createCam();

        float unitScale = 1 / 16f;
        renderer = new OrthogonalTiledMapRenderer(Mroff.getInstance().getMap("Level1"), unitScale);

        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
    }

    private OrthographicCamera createCam() {
        OrthographicCamera cam = new OrthographicCamera();
        return cam;
    }

    private void update(float delta) {
        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            cam.translate(-4,0,0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            cam.translate(4,0,0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            cam.translate(0,-4,0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            cam.translate(0,4,0);
        }
    }

    @Override
    public void render(float delta) {

        update(delta);

        cam.update();
        batch.setProjectionMatrix(cam.combined);

        renderer.render();

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(img, 0, 0);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        float scale = 0.03f;
        cam.viewportWidth = scale * width;
        cam.viewportHeight = scale * height;
        cam.update();
    }
}
