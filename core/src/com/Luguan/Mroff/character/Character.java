package com.Luguan.Mroff.character;

import com.Luguan.Mroff.Mroff;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Lukas on 6/10/2015.
 */
public class Character {

    Sprite character;

    public Character() {
        character = new Sprite(Mroff.getInstance().getTexture("Body"));
        character.setScale((1.0f/7.5f)*2);
    }

    public void Draw(Batch batch) {
        batch.begin();
        character.draw(batch);
        batch.end();
    }

    public void Update(float delta) {
        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            character.translate(-1,0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            character.translate(1,0);
        }
    }

    public Vector2 getPosition() {
        return new Vector2(character.getX(),character.getY());
    }

}