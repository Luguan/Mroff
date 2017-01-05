package com.Luguan.Mroff.livingentity;

import com.Luguan.Mroff.game.Map;
import com.Luguan.Mroff.physics.ObjectPhysics;
import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * Created by Lukas on 6/28/2015.
 */
public class Item extends ObjectPhysics{


    public Item(Map map) {
        super(map);
    }

    public Item(Map map, CollisionEvent event) {
        super(map, event);
    }


    public void draw(Batch batch) {
        //batch.draw();
    }
}
