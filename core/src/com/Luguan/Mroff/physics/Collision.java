package com.Luguan.Mroff.physics;

import com.Luguan.Mroff.livingentity.LivingEntity;
import com.Luguan.Mroff.screen.GameScreen;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

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

    public static Vector2 intersects(Rectangle r1, Rectangle r2) {
        Rectangle intersection = new Rectangle();
        float overlapRight = 0,
                overlapTop = 0,
                overlapLeft = 0,
                overlapBottom = 0;
        Vector2 overlap = new Vector2();
        Intersector.intersectRectangles(r1, r2, intersection);

        overlapRight = intersection.x - r1.x;
        overlapTop = intersection.y - r1.y;
        overlapLeft = (intersection.x+intersection.width) - (r1.x +r1.width);
        overlapBottom = (intersection.y + intersection.height) - (r1.y + r1.height);

        if(overlapLeft < 0 && overlapRight < 0) {
            //none of the sides are overlapping
            overlap.x = 0;
        }
        else {
            if(overlapLeft>overlapRight) {
                overlap.x = -overlapLeft;
            }
            else {
                overlap.x = overlapRight;
            }
        }
        if(overlapBottom < 0 && overlapTop < 0) {
            overlap.y = 0;
        }
        else {
            if(overlapBottom>overlapTop) {
                overlap.y = -overlapBottom;
            }
            else {
                overlap.y = overlapTop;
            }
        }
        return overlap;
    }

    public Vector2 isCollidingTerrain(ObjectPhysics objectPhysics) {

        Rectangle r1 = objectPhysics.getRectangle();
        Rectangle r2;

        for(int x =0; x <layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                r2 = new Rectangle(x,y,(layer.getTileWidth()* GameScreen.TILE_SCALE),(layer.getTileHeight()*GameScreen.TILE_SCALE));
                Vector2 overlap = intersects(r1, r2);
                if(overlap.x!=0 && overlap.y != 0) {
                    return overlap;
                }
            }
        }
        return new Vector2(0,0);
    }
}