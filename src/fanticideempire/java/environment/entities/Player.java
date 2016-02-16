/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fanticideempire.java.environment.entities;

import fanticideempire.java.environment.ActionStateE;
import fanticideempire.java.environment.Direction;
import fanticideempire.java.universal.resources.FEImageManager;
import fanticideempire.java.universal.resources.ImageProviderIntf;
import fanticideempire.java.environment.ScreenLimitProviderIntf;
import images.Animator;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

/**
 *
 * @author Kyle van Wiltenburg
 */
public class Player extends Entity {
    
    {
        actionState = ActionStateE.IDLE;
        facing = Direction.DOWN;
        drawObjectBoundary(true);
    }
    
    private final ScreenLimitProviderIntf screenLimiter;
    
    private final ArrayList<Direction> directions;
    private Direction facingDebug;
    private Direction facing;
    private ActionStateE actionState;
    private ActionStateE actionStateDebug;
    private static final int ANIMATION_SPEED = 80;
    
    private final Point environmentPosition;
    private final Point displacementPosition;
    
    private final Animator animator;
    
    /**
     * Constructor, returns an instance of the Player class
     *
     * @param position the current position of the entity on screen
     * @param screenLimiter inputs the minimum and maximum positions for the camera
     * @param ip the FEImageManager for the entity
     * 
     */
    
    public Player(Point position, ScreenLimitProviderIntf screenLimiter, ImageProviderIntf ip) {

        super(ip.getImage(FEImageManager.PLAYER_IDLE_DOWN_00), position, new Dimension(28, 64), ip);
        this.directions = new ArrayList<>();
        this.environmentPosition = new Point(position);
        this.displacementPosition = new Point(0, 0);
        this.screenLimiter = screenLimiter;
        screenLimiter.setMaxY(screenLimiter.getMaxY() + (getSize().height / 2));
        
        FEImageManager im = new FEImageManager();
        this.animator = new Animator(im, getImageProvider().getImageList(FEImageManager.PLAYER_WALK_DOWN_LIST), ANIMATION_SPEED);
    }
    
    @Override
    public void timerTaskHandler() {
        
        updateVelocity();
        move();
        
        updateActionState();
        updateFacingDirection();
        
        if (actionStateDebug != actionState || facingDebug != facing) {
            updateAnimator();
        }
        
        actionStateDebug = actionState;
        facingDebug = facing;
        
        updateImage();
        
        // Updates the player's position in the world
        setPosition(environmentPosition.x + displacementPosition.x, environmentPosition.y + displacementPosition.y);
    }
    
    private void updateVelocity() {
        
        // If the player's Directions list contains a certain direction, apply that direction to the velocity
        setVelocity(0, 0);
        if (directions.contains(Direction.UP)) accelerate(0, -3);
        if (directions.contains(Direction.DOWN)) accelerate(0, 3);
        if (directions.contains(Direction.LEFT)) accelerate(-3, 0);
        if (directions.contains(Direction.RIGHT)) accelerate(3, 0);
        
    }
    
    private void updateActionState() {
        if (!onGround()) actionState = ActionStateE.JUMPING;
        else if (getVelocity().x != 0 || getVelocity().y != 0) actionState = ActionStateE.WALKING;
        else actionState = ActionStateE.IDLE;
    }
    
    private void updateAnimator() {
        
        switch (actionState) {
            
            case IDLE:
                switch (facing) {
                    case UP: 
                        animator.setImageNames(getImageProvider().getImageList(FEImageManager.PLAYER_IDLE_UP_LIST));
                        animator.setDelayDurationMillis(Integer.MAX_VALUE);
                        break;
                    case DOWN:
                     animator.setImageNames(getImageProvider().getImageList(FEImageManager.PLAYER_IDLE_DOWN_LIST));
                        animator.setDelayDurationMillis(Integer.MAX_VALUE);
                        break;
                        case LEFT:
                        animator.setImageNames(getImageProvider().getImageList(FEImageManager.PLAYER_IDLE_LEFT_LIST));
                        animator.setDelayDurationMillis(Integer.MAX_VALUE);
                        break;
                    case RIGHT:
                        animator.setImageNames(getImageProvider().getImageList(FEImageManager.PLAYER_IDLE_RIGHT_LIST));
                        animator.setDelayDurationMillis(Integer.MAX_VALUE);
                        break;
                }
                break;
                
            case WALKING:
                switch (facing) {
                    case UP: 
                        animator.setImageNames(getImageProvider().getImageList(FEImageManager.PLAYER_WALK_UP_LIST));
                        animator.setDelayDurationMillis(ANIMATION_SPEED);
                        break;
                    case DOWN: 
                        animator.setImageNames(getImageProvider().getImageList(FEImageManager.PLAYER_WALK_DOWN_LIST));
                        animator.setDelayDurationMillis(ANIMATION_SPEED);
                        break;
                    case LEFT: 
                        animator.setImageNames(getImageProvider().getImageList(FEImageManager.PLAYER_WALK_LEFT_LIST));
                        animator.setDelayDurationMillis(ANIMATION_SPEED);
                        break;
                    case RIGHT: 
                        animator.setImageNames(getImageProvider().getImageList(FEImageManager.PLAYER_WALK_RIGHT_LIST));
                        animator.setDelayDurationMillis(ANIMATION_SPEED);
                        break;
                }
                break;
            
            case JUMPING:
                switch (facing) {
                    case UP: 
                        animator.setImageNames(getImageProvider().getImageList(FEImageManager.PLAYER_JUMP_UP_LIST));
                        animator.setDelayDurationMillis(Integer.MAX_VALUE);
                        break;
                    case DOWN: 
                        animator.setImageNames(getImageProvider().getImageList(FEImageManager.PLAYER_JUMP_DOWN_LIST));
                        animator.setDelayDurationMillis(Integer.MAX_VALUE);
                        break;
                    case LEFT: 
                        animator.setImageNames(getImageProvider().getImageList(FEImageManager.PLAYER_JUMP_LEFT_LIST));
                        animator.setDelayDurationMillis(Integer.MAX_VALUE);
                        break;
                    case RIGHT: 
                        animator.setImageNames(getImageProvider().getImageList(FEImageManager.PLAYER_JUMP_RIGHT_LIST));
                        animator.setDelayDurationMillis(Integer.MAX_VALUE);
                        break;
                }
                break;
            
        }
        
    }
    
    private void updateFacingDirection() {
        
        if (getVelocity().y < 0) facing = Direction.UP;
        else if (getVelocity().y > 0) facing = Direction.DOWN;
        else {
            if (getVelocity().x < 0) facing = Direction.LEFT;
            else if (getVelocity().x > 0) facing = Direction.RIGHT;
        }
        
    }
    
    private void updateImage() {
        if (animator != null) {
            setImage(animator.getCurrentImage());
        }
    }
    
    @Override
    public void move() {
        
        if (getPosition().x + getVelocity().x <= screenLimiter.getMinX() || getPosition().x + getVelocity().x >= screenLimiter.getMaxX()) displacementPosition.x += getVelocity().x;
        else if (displacementPosition.x == 0) environmentPosition.x += getVelocity().x;
        else displacementPosition.x = 0;
        
        if (getPosition().y + getVelocity().y <= screenLimiter.getMinY() || getPosition().y + getVelocity().y >= screenLimiter.getMaxY()) displacementPosition.y += getVelocity().y;
        else if (displacementPosition.y == 0) environmentPosition.y += getVelocity().y;
        else displacementPosition.y = 0;
        
        applyZVelocity();
        
    }
    
    @Override
    public Rectangle getObjectBoundary() {
        return new Rectangle(getPosition().x - (getSize().width / 2) + 3, getPosition().y - getSize().height + 4 - getZDisplacement(), getSize().width - 6, getSize().height - 8);
    }
    
    public ArrayList<Direction> getDirections() {
        return directions;
    }
    
    public ActionStateE getActionState() {
        return actionState;
    }
    
    public void addDirection(Direction direction) {
        directions.add(direction);
    }
    
    public void removeDirection(Direction direction) {
        directions.remove(direction);
    }
    
    public Point getEnvironmentPosition() {
        return environmentPosition;
    }
    
    public Point getDisplacementPosition() {
        return displacementPosition;
    }
    
    public void Jump() {
        if (onGround()) setZVelocity(7);
    }
    
    public int getScreenMinX() {
        return screenLimiter.getMinX();
    }
    
    public int getScreenMaxX() {
        return screenLimiter.getMaxX();
    }
    
    public int getScreenMinY() {
        return screenLimiter.getMinY();
    }
    
    public int getScreenMaxY() {
        return screenLimiter.getMaxY() - (getSize().height / 2);
    }
    
}
