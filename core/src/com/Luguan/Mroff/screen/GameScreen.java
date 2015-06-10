package com.Luguan.Mroff.screen;

import com.Luguan.Mroff.Mroff;
import com.Luguan.Mroff.character.Character;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;


/**
 * Created by Lukas on 6/9/2015.
 */
public class GameScreen extends ScreenAdapter{
    OrthogonalTiledMapRenderer renderer;

    Character character;
    private final OrthographicCamera cam;

    public GameScreen(){
        Mroff.getInstance().getMap("Level1");

        character = new Character();

        cam = createCam();

        float unitScale = 1 / 7.5f;
        renderer = new OrthogonalTiledMapRenderer(Mroff.getInstance().getMap("Level1"), unitScale);
    }

    private OrthographicCamera createCam() {
        OrthographicCamera cam = new OrthographicCamera();
        return cam;
    }

    private void update(float delta) {

        character.Draw(renderer.getBatch());

        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            cam.translate(-1,0,0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            cam.translate(1,0,0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            cam.translate(0,-1,0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            cam.translate(0,1,0);
        }
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cam.update();

        renderer.setView(cam);
        renderer.render();

        update(delta);

    }

    @Override
    public void resize(int width, int height) {
        float scale = 0.03f;
        cam.viewportWidth = scale * width;
        cam.viewportHeight = scale * height;
        cam.update();
    }
}
