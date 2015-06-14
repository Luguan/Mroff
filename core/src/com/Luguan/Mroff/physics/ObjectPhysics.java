package com.Luguan.Mroff.physics;

/**
 * Created by Lukas on 6/14/2015.
 */
public class ObjectPhysics {

    protected float x, y, accelerationY, accelerationX;

    public ObjectPhysics() {
        accelerationY = 0f;
    }

    public void heightAcceleration(float delta) {
        accelerationY -= 0.01f * delta;
    }

    public void update(float delta) {
        heightAcceleration(delta);
    }
}