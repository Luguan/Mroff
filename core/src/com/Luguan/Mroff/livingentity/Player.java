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

    boolean lookingRight;

    int spriteFrame;
    float frameTime;

    public Player(CollisionEvent event) {
        super(new Sprite(Mroff.getInstance().getSheet(0, 0)), event);

        spriteFrame = 0;

        lookingRight = true;

        width = 3/4f;
        height = 4/4f;
        getSprite().setSize(width,height);
    }



    @Override
    public void update(float delta) {
        frameTime+=delta;
        super.update(delta);
        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            lookingRight=false;
            if(frameTime>0.2f) {
                frameTime = 0;
                if(spriteFrame == 0) {
                    spriteFrame++;
                }
                else {
                    spriteFrame = 0;
                }
                getSprite().setRegion(Mroff.getInstance().getSheet(0, spriteFrame));
                if(!lookingRight) {
                    getSprite().flip(true, false);
                }
            }
            moveX(-MOVEMENT_SPEED);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            if(!lookingRight) {
                getSprite().flip(true,false);
                lookingRight = true;
            }
            if(frameTime>0.2f) {
                frameTime = 0;
                if(spriteFrame == 0) {
                    spriteFrame++;
                }
                else {
                    spriteFrame = 0;
                }
                getSprite().setRegion(Mroff.getInstance().getSheet(0, spriteFrame));
            }

            moveX(MOVEMENT_SPEED);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            //if(!inAir) {
              //  inAir = true;
                //jump();
                y+=0.2f;
            //}
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S))
        {
            y-=0.2f;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.C))
        {
            moveX(-60);
        }
    }

    public boolean isSmall() {
        return playerState == PlayerState.SMALL;
    }

}