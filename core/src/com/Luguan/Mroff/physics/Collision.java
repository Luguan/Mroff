package com.Luguan.Mroff.physics;

import com.Luguan.Mroff.livingentity.LivingEntity;
import com.badlogic.gdx.maps.tiled.TiledMap;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Lukas on 6/12/2015.
 */
public class Collision {

    private TiledMap map;

    public Collision(TiledMap map) {
        this.map = map;
        map.getLayers().get("Collision");
    }

    public boolean isCollidingEnemy(LivingEntity livingEntity) {
    throw new NotImplementedException();
    }

    public boolean isCollidingTerrain(LivingEntity livingEntity) {
        throw new NotImplementedException();
    }
}
