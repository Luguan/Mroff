package com.Luguan.Mroff.livingentity;

import com.Luguan.Mroff.Mroff;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Lukas on 6/10/2015.
 */
public class LivingEntity {

    Sprite sprite;

    public LivingEntity(Sprite character) {
        this.sprite = character;
        sprite = new Sprite(Mroff.getInstance().getTexture("Body"));

    }

    public void Draw(Batch batch) {
        batch.begin();
        sprite.draw(batch);
        batch.end();
    }

    public void Update(float delta) {
        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            sprite.translate(-1, 0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            sprite.translate(1, 0);
        }
    }

    public Vector2 getPosition() {
        return new Vector2(sprite.getX(), sprite.getY());
    }

    public void setPosition(float x, float y) {
        sprite.setPosition(x, y);
    }

    public Sprite getSprite() {
        return sprite;
    }
}