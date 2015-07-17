package com.Luguan.Mroff.livingentity;

import com.Luguan.Mroff.Mroff;
import com.Luguan.Mroff.physics.ObjectPhysics;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

/**
 * Created by Lukas on 6/28/2015.
 */
public class Item extends ObjectPhysics{

    Texture item;

    public Item() {
        item = Mroff.getInstance().getTexture("items/Mushroom");
    }

    public void draw(Batch batch) {
        batch.begin();
        batch.draw(item,x,y);
        batch.end();
    }
}
