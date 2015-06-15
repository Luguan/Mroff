package com.Luguan.Mroff.physics;

import com.Luguan.Mroff.livingentity.LivingEntity;
import com.Luguan.Mroff.screen.GameScreen;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Lukas on 6/12/2015.
 */
public class Collision {

    private TiledMap map;
    private TiledMapTileLayer layer;

    public Collision(TiledMap map) {
        this.map = map;
        layer = (TiledMapTileLayer)map.getLayers().get("Collision");
    }

    public boolean isCollidingEnemy(LivingEntity livingEntity) {
        return false;
    }

    public static Side intersects(Rectangle r1, Rectangle r2) {
        Rectangle intersection = new Rectangle();
        Intersector.intersectRectangles(r1, r2, intersection);
        if(intersection.x > r1.x) {
            //Collides with the right side of the block
            return Side.RIGHT;
        }
        if(intersection.y > r1.y) {
            //Collides with the top of the block
            return Side.TOP;
        }
        if(intersection.x + intersection.width < r1.x + r1.width) {
            //Collides with the left side of the block
            return Side.LEFT;
        }
        if(intersection.y + intersection.height < r1.y + r1.height) {
            //Collides with bottom of the block
            return Side.BOTTOM;
        }
        return Side.NONE;
    }

    public Side isCollidingTerrain(ObjectPhysics objectPhysics) {

        Rectangle r1 = objectPhysics.getRectangle();
        Rectangle r2;

        for(int x =0; x <layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                r2 = new Rectangle(x,y,(layer.getTileWidth()* GameScreen.TILE_SCALE),(layer.getTileHeight()*GameScreen.TILE_SCALE));
                Side side = intersects(r1, r2);
                if(side!=Side.NONE) {
                    return side;
                }
            }
        }
        return Side.NONE;
    }
}