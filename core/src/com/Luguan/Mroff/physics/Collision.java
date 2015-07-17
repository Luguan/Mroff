package com.Luguan.Mroff.physics;

import com.Luguan.Mroff.livingentity.LivingEntity;
import com.Luguan.Mroff.screen.GameScreen;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

/**
 * Created by Lukas on 6/12/2015.
 */
public class Collision {

    private  CollisionEvent event;
    private TiledMap map;
    private TiledMapTileLayer layer;

    public Collision(TiledMap map, CollisionEvent event) {
        this.map = map;
        this.event = event;
        layer = (TiledMapTileLayer)map.getLayers().get("Collision");
    }

    public boolean isCollidingEnemy(LivingEntity livingEntity) {
        return false;
    }

    public static Vector2 intersects(Rectangle r1, Rectangle r2) {
        Rectangle intersection = new Rectangle();
        Intersector.intersectRectangles(r1, r2, intersection);
        Vector2 result = new Vector2(intersection.getWidth(), -intersection.getHeight());
        if(intersection.x > r1.x) {
            //Intersects with right side
            result.x *= -1;
        }
        if(intersection.y > r1.y) {
            //Intersects with top side
        }
        if(intersection.x + intersection.width < r1.x + r1.width) {
            //Intersects with left side
        }
        if(intersection.y + intersection.height < r1.y + r1.height) {
            //Intersects with bottom side
            result.y *= -1;
        }
        return result;
    }

    public Vector2 isCollidingTerrain(ObjectPhysics objectPhysics) {
        Rectangle r1 = objectPhysics.getRectangle();

        List<Vector2> overlaps = new ArrayList<Vector2>();

        for(int x =0; x <layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                if(cell != null) {
                    Rectangle r2 = new Rectangle(x, y, (layer.getTileWidth() * GameScreen.TILE_SCALE), (layer.getTileHeight() * GameScreen.TILE_SCALE));
                    Vector2 overlap = intersects(r1, r2);
                    if (overlap.x != 0 || overlap.y != 0) {
                        overlaps.add(overlap);
                    }
                    if(cell.getTile().getProperties().containsKey("ItemSpawn")) {
                        event.onCollision();
                    }
                }
            }
        }

        if (overlaps.size() == 0) {
            return new Vector2(0,0);
        }

        Vector2 smallestOverlap = overlaps.get(0);
        for(Vector2 overlap : overlaps) {
            if (overlap.len() < smallestOverlap.len()) {
                smallestOverlap = overlap;
            }
        }
        return smallestOverlap;
    }

    public interface CollisionEvent {
        void onCollision();
    }

}