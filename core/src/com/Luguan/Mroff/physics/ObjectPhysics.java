package com.Luguan.Mroff.physics;

import com.Luguan.Mroff.Mroff;
import com.Luguan.Mroff.livingentity.LivingEntity;
import com.Luguan.Mroff.screen.GameScreen;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Lukas on 6/14/2015.
 */
public class ObjectPhysics  {

    private  CollisionEvent event;
    protected float x, y, accelerationY, accelerationX, width, height;
    protected boolean inAir;


    public ObjectPhysics(CollisionEvent event) {
        this.event = event;
        accelerationY = 0f;
    }

    public void heightAcceleration(float delta) {
        accelerationY -= 0.2f * delta;
    }

    public void update(float delta) {
        heightAcceleration(delta);

        y+=accelerationY;

        handleCollision();
    }

    private void updateCollsionX(Vector2 collidingTerrain) {
        if(collidingTerrain.len() != 0) {
            if(collidingTerrain.x != 0) {
                x+=collidingTerrain.x;
            }
        }
    }

    private void updateCollisionY(Vector2 collidingTerrain) {
        if(collidingTerrain.len() != 0) {
            if(collidingTerrain.y > 0) {
                inAir = false;
            }
            if(collidingTerrain.y != 0) {
                y += collidingTerrain.y;
                //System.out.println(collidingTerrain);
                if(accelerationY<0) {
                    event.onItemBlockCollision((int)collidingTerrain.x, (int)collidingTerrain.y);
                }
                accelerationY = 0;
            }
        }
    }

    public boolean isCollidingEnemy(LivingEntity livingEntity) {
        return false;
    }

    public void handleCollision() {
        Rectangle r1 = getRectangle();
        TiledMapTileLayer collision = (TiledMapTileLayer)((GameScreen) Mroff.getInstance().getScreen()).getLevel().getLayers().get("Collision");
        for(int x =0; x < collision.getWidth(); x++) {
            for (int y = 0; y < collision.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = collision.getCell(x, y);
                if(cell != null) {
                    Rectangle r2 = new Rectangle(x, y, (collision.getTileWidth() * GameScreen.TILE_SCALE), (collision.getTileHeight() * GameScreen.TILE_SCALE));
                    Vector2 overlap = intersects(r1, r2);
                    if (overlap.x != 0 || overlap.y != 0) {
                        if(Math.abs(overlap.x) > Math.abs(overlap.y)) {
                            y+=overlap.y;
                        }
                        else {
                            x+=overlap.x;
                        }
                        /*if(cell.getTile().getProperties().containsKey("ItemSpawn")) {
                            event.onItemBlockCollision(x, y);
                        }*/
                    }
                }
            }
        }
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

    public Rectangle getRectangle() {
        return new Rectangle(x, y, width, height);
    }

    public interface CollisionEvent {
        void onItemBlockCollision(int x, int y);
    }
}