package com.Luguan.Mroff.character;

import com.Luguan.Mroff.Mroff;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Lukas on 6/10/2015.
 */
public class Character {

    Sprite character;

    public Character() {
        character = new Sprite(Mroff.getInstance().getTexture("Body"));
    }

    public void Draw(Batch batch) {
        batch.begin();
        character.draw(batch);
        batch.end();
    }
}
