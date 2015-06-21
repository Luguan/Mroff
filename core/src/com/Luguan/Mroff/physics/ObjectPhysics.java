package com.Luguan.Mroff.physics;

import com.Luguan.Mroff.Mroff;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Lukas on 6/14/2015.
 */
public class ObjectPhysics {

    protected float x, y, accelerationY, accelerationX, width, height;
    protected boolean inAir;

    Collision collision;
    public ObjectPhysics() {
        accelerationY = 0f;
        collision = new Collision(Mroff.getInstance().getMap("Level2"));
    }

    public void heightAcceleration(float delta) {
        accelerationY -= 0.2f * delta;
    }

    public void update(float delta) {
        heightAcceleration(delta);

        y+=accelerationY;
        Vector2 collidingTerrain = collision.isCollidingTerrain(this);
        System.out.println(collidingTerrain);

        //Y Direction
        if(collidingTerrain.len() != 0) {
            accelerationY = 0;
            if(collidingTerrain.y > 0) {
                inAir = false;
            }
            if(collidingTerrain.y != 0) {
                y += collidingTerrain.y;
            }
        }


        //X Direction
        collidingTerrain = collision.isCollidingTerrain(this);
        if(collidingTerrain.len() != 0) {
            if(collidingTerrain.x != 0) {
                x+=collidingTerrain.x;
            }
        }
    }
    public Rectangle getRectangle() {
        return new Rectangle(x, y, width, height);
    }
}