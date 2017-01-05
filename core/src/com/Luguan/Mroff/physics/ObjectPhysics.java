package com.Luguan.Mroff.physics;

import com.Luguan.Mroff.game.Map;
import com.Luguan.Mroff.livingentity.LivingEntity;
import com.Luguan.Mroff.util.Debug;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Lukas on 6/14/2015.
 */
public class ObjectPhysics  {

    private Map map;

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }

    enum MovementResult {
        SUCCESS, PARTIAL, FAILURE
    }

    private  CollisionEvent event;
    protected float x, y, accelerationY, accelerationX, width, height;
    protected boolean inAir;
    protected boolean isAffectedByGravity = false;


    public ObjectPhysics(Map map) {
        this.map = map;
    }

    public ObjectPhysics(Map map, CollisionEvent event) {
        this.map = map;
        this.event = event;
        accelerationY = 0f;
    }


    /**
     * Moves the character on the y-axis
     * @param requestedMoveY The distance to move the character on the y-axis
     */
    public MovementResult moveY(float requestedMoveY) {
        boolean movingUp;
        float hitboxY;
        CollidingBlock block;

        movingUp = requestedMoveY > 0;

        if(movingUp) {
            hitboxY = y + height;
        }
        else {
            hitboxY = y;
        }

        block = findCollidingRowsY(hitboxY, movingUp, requestedMoveY);


        float distanceToMove = Math.min(Math.abs(block.distance()), Math.abs(requestedMoveY));

        if (distanceToMove <= 0) {
            // Could not move at all
            return MovementResult.FAILURE;
        }
        else if (Math.abs(block.distance()) < Math.abs(requestedMoveY)) {
            // Could not move the full distance, but moved a part of the requested distance
            y += block.distance();
            System.out.println(block.BlockType());
            return MovementResult.PARTIAL;
        } else {
            // Could move the full requested distance
            y += requestedMoveY;
            return MovementResult.SUCCESS;
        }
    }

    /**
     * Moves the character on the x-axis
     * @param moveX The distance to move the charactor on the x-axis
     */
    public void moveX(float moveX) {
        boolean facingRight;
        float hitBoxX;
        CollidingBlock block;


        facingRight = moveX > 0;

        if (facingRight) {
            hitBoxX = x + width;
        } else {
            hitBoxX = x;
        }

        block = findCollidingRowsX(hitBoxX, facingRight, moveX);
        System.out.println(block.BlockType());

        if (facingRight) {
            if(Math.min(block.distance(), moveX) > 0){
                x += Math.min(block.distance(), moveX);
            }
        } else {
            if(Math.max(block.distance(), moveX) < 0){
                x += Math.max(block.distance(), moveX);
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
    private CollidingBlock findCollidingRowsX(float hitboxX, boolean directionRight, float moveX) {
        int low = (int) Math.floor(y);
        int upper = (int) Math.floor(y + height - 0.00001f);
        int sideX = (int) Math.floor(hitboxX);
        TiledMapTileLayer collision = (TiledMapTileLayer) map.getLayers().get("Collision");
        TiledMapTileLayer itemBlocks = (TiledMapTileLayer) map.getLayers().get("ItemBlocks");

        Debug.addCheckedBox(hitboxX, 0, .001f, 1000, Color.BLUE);
        if(directionRight) {
            for (int posX = sideX; posX < sideX + moveX + 1; posX++) {
                for (int row = low; row <= upper; row++){
                    Debug.addCheckedBox(posX, row, 1, 1, Color.RED);
                    TiledMapTileLayer.Cell collisionCell = collision.getCell(posX, row);
                    TiledMapTileLayer.Cell itemBlockCell = itemBlocks.getCell(posX, row);
                    if (collisionCell != null) {
                        return new CollidingBlock(posX - hitboxX, "map");
                    }
                    else if (itemBlockCell != null) {
                        return new CollidingBlock(posX - hitboxX, "itemblock");
                    }
                }
            }
        }
        else {
            for (int posX = sideX; posX > sideX + moveX - 1; posX--) {
                for (int row = low; row <= upper; row++) {
                    Debug.addCheckedBox(posX, row, 1, 1, Color.RED);
                    TiledMapTileLayer.Cell collisionCell = collision.getCell(posX, row);
                    TiledMapTileLayer.Cell itemBlockCell = itemBlocks.getCell(posX, row);
                    if (collisionCell != null) {
                        return new CollidingBlock(posX - hitboxX + 1, "map");
                    }
                    else if (itemBlockCell != null) {
                        return new CollidingBlock(posX - hitboxX + 1, "itemblock");
                    }
                }
            }
        }
        return new CollidingBlock(moveX);
    }

    /**
     * Checks the y-axis for collision
     * @param hitboxY The location of the players hitbox
     * @param movingUp the direction to check for collision
     * @param moveY The distance to check for collision
     * @return The distance that the character is able to move
     */
    private CollidingBlock findCollidingRowsY(float hitboxY, boolean movingUp, float moveY) {
        int left = (int) Math.floor(x);
        int right = (int) Math.floor(x + width - 0.00001f);
        int sideY = (int) Math.floor(hitboxY);

        //TiledMapTileLayer itemBlocks = Mroff.getInstance().getMap("level2").getLayers().get("Object Layer 1").getObjects();
        TiledMapTileLayer collision = (TiledMapTileLayer) map.getLayers().get("Collision");
        TiledMapTileLayer itemBlocks = (TiledMapTileLayer) map.getLayers().get("ItemBlocks");


        if(movingUp) {
            for (int posY = sideY; posY < sideY + moveY + 1; posY++) {
                for (int row = left; row <= right; row++) {
                    Debug.addCheckedBox(row, posY, 1, 1, Color.BLUE);
                    TiledMapTileLayer.Cell collisionCell = collision.getCell(row, posY);
                    TiledMapTileLayer.Cell itemBlockCell = itemBlocks.getCell(row, posY);
                    if (collisionCell != null) {
                        return new CollidingBlock(posY - hitboxY, "map");
                    }
                    else if (itemBlockCell != null) {
                        event.onItemBlockCollision(row, posY);
                        return new CollidingBlock(posY - hitboxY, "itemblock");
                    }
                }
            }
        }
        else {
            for (int posY = sideY; posY>sideY + moveY - 1; posY--) {
                for (int row = left; row <= right; row++) {
                    Debug.addCheckedBox(row, posY, 1, 1, Color.BLUE);
                    TiledMapTileLayer.Cell collisionCell = collision.getCell(row, posY);
                    TiledMapTileLayer.Cell itemBlockCell = itemBlocks.getCell(row, posY);
                    if (collisionCell != null) {
                        return new CollidingBlock(posY - hitboxY + 1, "map");
                    }
                    else if (itemBlockCell != null) {
                        event.onItemBlockCollision(row, posY);
                        return new CollidingBlock(posY - hitboxY + 1, "itemblock");
                    }
                }
            }
        }
        return new CollidingBlock(moveY);
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
        if (isAffectedByGravity) {
            heightAcceleration(delta);
            MovementResult movementResult = moveY(accelerationY);

            boolean movingDown = accelerationY < 0;

            if (!movingDown && movementResult == MovementResult.PARTIAL)
                accelerationY = 0.0f;
            if (movingDown && (movementResult == MovementResult.PARTIAL || movementResult == MovementResult.FAILURE)) {
                // If we where blocked while moving downwards, we can assume that we hit the floor.
                inAir = false;
                accelerationY = 0.0f;
            }
        }
    }
}

class CollidingBlock {

    private float f;
    private String blockName;
    private Side side;

    public CollidingBlock(float position) {
        this.f = position;
    }

    public CollidingBlock(float f, String blockName) {
        this.f = f;
        this.blockName = blockName;
    }

    public float distance() {
        return f;
    }

    public String BlockType() {
        if (blockName != null)
            return blockName;
        else
            return null;
    }
}