package com.Luguan.Mroff.physics;

import com.Luguan.Mroff.Mroff;
import com.Luguan.Mroff.screen.GameScreen;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Lukas on 6/14/2015.
 */
public class ObjectPhysics  {

    protected float x, y, accelerationY, accelerationX, width, height;
    protected boolean inAir;


    public ObjectPhysics() {
        accelerationY = 0f;

    }

    public void heightAcceleration(float delta) {
        accelerationY -= 0.2f * delta;
    }

    public void update(float delta) {
        heightAcceleration(delta);

        y+=accelerationY;

        Collision collision = ((GameScreen) Mroff.getInstance().getScreen()).getCollision();
        Vector2 collidingTerrain = collision.isCollidingTerrain(this);
        //System.out.println(collidingTerrain);

        if (Math.abs(collidingTerrain.x) > Math.abs(collidingTerrain.y)) {
            updateCollisionY(collidingTerrain);
            updateCollsionX(collision.isCollidingTerrain(this));
        } else {
            updateCollsionX(collidingTerrain);
            updateCollisionY(collision.isCollidingTerrain(this));
        }
    }

    private void updateCollsionX(Vector2 collidingTerrain) {
        if(collidingTerrain.len() != 0) {
            if(collidingTerrain.x != 0) {
                x+=collidingTerrain.x;
            }
        }
    }

    private void updateCollisionY(Vector2 collidingTerrain) {
        if(collidingTerrain.len() != 0) {
            if(collidingTerrain.y > 0) {
                inAir = false;
            }
            if(collidingTerrain.y != 0) {
                y += collidingTerrain.y;
                System.out.println(collidingTerrain);
                accelerationY = 0;
            }
        }
    }

    public Rectangle getRectangle() {
        return new Rectangle(x, y, width, height);
    }
}