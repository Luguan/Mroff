package com.Luguan.Mroff.physics;

import com.Luguan.Mroff.Mroff;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Lukas on 6/14/2015.
 */
public class ObjectPhysics {

    protected float x, y, accelerationY, accelerationX, width, height;

    Collision collision;
    public ObjectPhysics() {
        accelerationY = 0f;
        collision = new Collision(Mroff.getInstance().getMap("Level1"));
    }

    public void heightAcceleration(float delta) {
        accelerationY -= 0.4f * delta;
    }

    public void update(float delta) {
        Vector2 collidingTerrain = collision.isCollidingTerrain(this);
        System.out.println(collidingTerrain);
        if(collidingTerrain.len() == 0) {
            heightAcceleration(delta);
        }
        else {
            accelerationY = 0;
        }
        y+=accelerationY;
    }

    public Rectangle getRectangle() {
        return new Rectangle(x, y, width, height);
    }
}