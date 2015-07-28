package com.Luguan.Mroff.livingentity;

import com.Luguan.Mroff.Mroff;
import com.Luguan.Mroff.physics.PlayerState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Lukas on 6/12/2015.
 */
public class Player extends LivingEntity{

    public static final float MOVEMENT_SPEED = 0.15f;
    PlayerState playerState;

    public Player(CollisionEvent event) {
        super(new Sprite(Mroff.getInstance().getSheet(0, 0)), event);

        width = 3/4f;
        height = 4/4f;
        getSprite().setSize(width,height);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            x -= MOVEMENT_SPEED;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            x += MOVEMENT_SPEED;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            if(!inAir) {
                inAir = true;
                jump();
            }
        }
    }

    public boolean isSmall() {
        return playerState == PlayerState.SMALL;
    }

}