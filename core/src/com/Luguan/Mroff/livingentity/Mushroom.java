package com.Luguan.Mroff.livingentity;

import com.Luguan.Mroff.Mroff;
import com.Luguan.Mroff.game.Map;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Lukas on 1/2/2017.
 */
public class Mushroom extends Item{

    private Sprite sprite;



    public Mushroom(Map map, int x, int y) {
        super(map);
        this.x = x;
        this.y = y;
        createSprite(x, y);
    }

    private void createSprite(int x, int y) {
        sprite = new Sprite(Mroff.getInstance().getTexture("items/Mushroom"));
        sprite.setSize(1.0f, 1.0f);
        sprite.setX(x);
        sprite.setY(y);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    public void draw(Batch batch) {
        sprite.draw(batch);
    }
}
