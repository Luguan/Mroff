package com.Luguan.Mroff.util;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Lukas on 6/10/2015.
 */
public class Utils {
    public static Vector2 moveTowards(Vector2 source, Vector2 target) {
        Vector2 diff = target.sub(source);
        return source.add(diff.scl(0.8f));
    }
}
