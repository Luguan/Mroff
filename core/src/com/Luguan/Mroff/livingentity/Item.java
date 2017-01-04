package com.Luguan.Mroff.livingentity;

import com.Luguan.Mroff.physics.ObjectPhysics;
import com.badlogic.gdx.graphics.g2d.Batch;

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
        //batch.draw();
    }
}
