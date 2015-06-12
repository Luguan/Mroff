package com.Luguan.Mroff.screen;

import com.Luguan.Mroff.Mroff;
import com.Luguan.Mroff.character.Character;
import com.Luguan.Mroff.util.Utils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Lukas on 6/9/2015.
 */
public class GameScreen extends ScreenAdapter{
    OrthogonalTiledMapRenderer renderer;

    Character character;
    private final OrthographicCamera cam;

    public GameScreen(){
        Mroff.getInstance().getMap("Level1");

        spawnCharacter();

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
        character.Update(delta);

        moveCamera();
    }

    private void moveCamera() {
        cam.position.set(Utils.moveTowards(character.getPosition(), new Vector2(cam.position.x,cam.position.y)),0);
    }

    private void spawnCharacter() {
        character = new Character();
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
        float scale = 0.03f;
        cam.viewportWidth = scale * width;
        cam.viewportHeight = scale * height;
        cam.update();
    }
}