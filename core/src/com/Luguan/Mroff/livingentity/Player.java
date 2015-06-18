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

    PlayerState playerState;

    public Player() {
        super(new Sprite(Mroff.getInstance().getTexture("Body")));

        width = 3/4f;
        height = 4/4f;
        getSprite().setSize(width,height);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            x -= 0.5f;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            x += 0.5f;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            jump();
        }
    }

    public boolean isSmall() {
        return playerState == PlayerState.SMALL;
    }

}