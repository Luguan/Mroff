package com.Luguan.Mroff.livingentity;

import com.Luguan.Mroff.Mroff;
import com.Luguan.Mroff.physics.ObjectPhysics;
import com.Luguan.Mroff.screen.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Lukas on 6/10/2015.
 */
public class LivingEntity extends ObjectPhysics{

    Sprite sprite;


    public LivingEntity(Sprite character,CollisionEvent event) {
        super(event);
        inAir = false;
        this.sprite = character;
        sprite = new Sprite(Mroff.getInstance().getTexture("Body"));
    }

    public void draw(Batch batch) {
        batch.begin();
        sprite.draw(batch);
        batch.end();
    }

    public void jump() {
        accelerationY = 0.1f;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        sprite.setX(x);
        sprite.setY(y);
    }

    public Vector2 getPosition() {
        return new Vector2(sprite.getX(), sprite.getY());
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Sprite getSprite() {
        return sprite;
    }
}