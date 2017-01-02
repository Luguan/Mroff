package com.Luguan.Mroff.livingentity;

import com.Luguan.Mroff.physics.ObjectPhysics;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Lukas on 6/10/2015.
 */
public class LivingEntity extends ObjectPhysics{

    Sprite sprite;


    public LivingEntity(Sprite character, CollisionEvent event) {
        super(event);
        inAir = false;
        this.sprite = character;
        sprite = new Sprite(character);
    }

    public void draw(Batch batch) {
        batch.begin();
        sprite.draw(batch);
        batch.end();
    }

    public void jump() {
        inAir = true;
        accelerationY = .1f;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        sprite.setX(x);
        sprite.setY(y);
    }

    /**
     * @return Position of the character
     */
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