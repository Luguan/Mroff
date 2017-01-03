package com.Luguan.Mroff.livingentity;

import com.Luguan.Mroff.Mroff;
import com.Luguan.Mroff.physics.ObjectPhysics;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Lukas on 6/28/2015.
 */
public class Item extends ObjectPhysics{


    public Item() {
    }

    public Item(CollisionEvent event) {
        super(event);
    }


    public void draw(Batch batch) {
        batch.begin();
        //batch.draw();
        batch.end();
    }
}
