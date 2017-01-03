package com.Luguan.Mroff.livingentity;

import com.Luguan.Mroff.Mroff;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Lukas on 1/2/2017.
 */
public class Mushroom extends Item{

    private Texture item;

    private int x, y;

    public Mushroom(int x, int y) {
        this.x = x;
        this.y = y;
        createSprite(x, y);
    }

    private Sprite createSprite(int x, int y) {
        return new Sprite(Mroff.getInstance().getTexture("items/Mushroom.png"), x, y);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    public void draw(Batch batch) {
        batch.draw(item, x , y);
    }
}
