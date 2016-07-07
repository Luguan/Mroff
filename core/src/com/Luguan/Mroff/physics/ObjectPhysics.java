package com.Luguan.Mroff.physics;

import com.Luguan.Mroff.Mroff;
import com.Luguan.Mroff.livingentity.LivingEntity;
import com.Luguan.Mroff.screen.GameScreen;
import com.Luguan.Mroff.util.Debug;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.sun.glass.ui.SystemClipboard;

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


    /**
     * Moves the character on the y-axis
     * @param moveY The distance to move the character on the y-axis
     */
    public void moveY(float moveY) {
        boolean movingUp;
        float hitboxY;

        movingUp = moveY > 0;

        if(movingUp) {
            hitboxY = y + height;
        }
        else {
            hitboxY = y;
        }

        float distanceY = findCollidingRowsY(hitboxY,movingUp,moveY);

        if(movingUp) {
            if(Math.min(distanceY, moveY) > 0) {
                y += Math.min(distanceY, moveY);
            }
        }
        else {
            if(Math.max(distanceY,moveY) < 0) {
                y += Math.max(distanceY,moveY);
            }
        }
    }

    /**
     * Moves the character on the x-axis
     * @param moveX The distance to move the charactor on the x-axis
     */
    public void moveX(float moveX) {
        boolean facingRight;
        float hitboxX;


        facingRight = moveX > 0;

        if (facingRight) {
            hitboxX = x + width;
        } else {
            hitboxX = x;
        }

        float distanceX = findCollidingRowsX(hitboxX, facingRight, moveX);

        if (facingRight) {
            if(Math.min(distanceX, moveX) > 0){
                x += Math.min(distanceX, moveX);
            }
        } else {
            if(Math.max(distanceX, moveX) < 0){
                x += Math.max(distanceX, moveX);
            }
        }
    }

    /**
     * Checks the x-axis for collision with objects
     * @param hitboxX the location for the players hitbox
     * @param directionRight the direction to check
     * @param moveX the distance to check for collision
     * @return The distance that the character is able to move
     */
    private float findCollidingRowsX(float hitboxX, boolean directionRight, float moveX) {
        int low = (int) Math.floor(y);
        int upper = (int) Math.floor(y + height - 0.00001f);
        int sideX = (int) Math.floor(hitboxX);
        TiledMapTileLayer collision = (TiledMapTileLayer)((GameScreen) Mroff.getInstance().getScreen()).getLevel().getLayers().get("Collision");

        Debug.addCheckedBox(hitboxX, 0, .001f, 1000, Color.BLUE);
        if(directionRight) {
            for (int posX = sideX; posX < sideX + moveX + 1; posX++) {
                for (int row = low; row <= upper; row++){
                    Debug.addCheckedBox(posX, row, 1, 1, Color.RED);
                    TiledMapTileLayer.Cell cell = collision.getCell(posX, row);
                    if (cell != null) {
                        return posX - hitboxX;
                    }
                }
            }
        }
        else {
            for (int posX = sideX; posX > sideX + moveX - 1; posX--) {
                for (int row = low; row <= upper; row++) {
                    TiledMapTileLayer.Cell cell = collision.getCell(posX, row);
                    Debug.addCheckedBox(posX, row, 1, 1, Color.RED);
                    if (cell != null) {
                        return posX - hitboxX + 1;
                    }
                }
            }
        }
        return moveX;
    }

    /**
     * Checks the y-axis for collision
     * @param hitboxY The location of the players hitbox
     * @param movingUp the direction to check for collision
     * @param moveY The distance to check for collision
     * @return The distance that the character is able to move
     */
    private float findCollidingRowsY(float hitboxY, boolean movingUp, float moveY) {
        int left = (int) Math.floor(x);
        int right = (int) Math.floor(x + width - 0.00001f);
        int sideY = (int) Math.floor(hitboxY);
        TiledMapTileLayer collision = (TiledMapTileLayer)((GameScreen) Mroff.getInstance().getScreen()).getLevel().getLayers().get("Collision");
        if(movingUp) {
            for (int posY = sideY; posY < sideY + moveY + 1; posY++) {
                for (int row = left; row <= right; row++) {
                    Debug.addCheckedBox(row, posY, 1, 1, Color.BLUE);
                    TiledMapTileLayer.Cell cell = collision.getCell(row, posY);
                    if (cell != null) {
                        return posY - hitboxY;
                    }
                }
            }
        }
        else {
            for (int posY = sideY; posY>sideY + moveY - 1; posY--) {
                for (int row = left; row <= right; row++) {
                    Debug.addCheckedBox(row, posY, 1, 1, Color.BLUE);
                    TiledMapTileLayer.Cell cell = collision.getCell(row, posY);
                    if (cell != null) {
                        return posY - hitboxY + 1;
                    }
                }
            }
        }
        return moveY;
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

    public interface CollisionEvent {
        void onItemBlockCollision(int x, int y);
    }

    public void heightAcceleration(float delta) {
        accelerationY -= 0.2f * delta;
    }

    public void update(float delta) {
        /*if(inAir) {
            heightAcceleration(delta);

            moveY(accelerationY);
        }
        else {
            inAir = true;
            accelerationY = 0;
        }*/

    }
}