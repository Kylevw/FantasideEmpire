/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fantasideempire;

import environment.Actor;
import environment.Velocity;
import images.Animator;
import images.ImageManager;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Kyle van Wiltenburg
 */
public class Player extends Actor {
    
//  <editor-fold defaultstate="collapsed" desc="Constructors">
    
    {
        actionState = ActionStateE.STAND_DOWN;
    }
    
    private final ScreenLimitProviderIntf screenLimiter;
    private final ImageProviderIntf ip;
    
    private final ArrayList<Direction> directions;
    private ActionStateE actionState;
    private static final int WIDTH = 26;
    private static final int HEIGHT = 60;
    
    private final Point environmentPosition;
    private final Point displacementPosition;
    
    private Animator animator;
    
    /**
     * Constructor, returns an instance of the Player class
     *
     * @param image the current sprite of the entity
     * @param position the current position of the entity on screen
     * @param screenLimiter inputs the minimum and maximum positions for the camera
     * @param spriteProvider the FEImageManager for the entity
     * 
     */
    
    public Player(BufferedImage image, Point position, ScreenLimitProviderIntf screenLimiter, ImageProviderIntf spriteProvider) {

        super(image, position, new Velocity(0, 0));
        this.directions = new ArrayList<>();
        this.environmentPosition = new Point(position);
        this.displacementPosition = new Point(0, 0);
        this.screenLimiter = screenLimiter;
        this.ip = spriteProvider;
        
        FEImageManager im = new FEImageManager();
        this.animator = new Animator(im, spriteProvider.getImageList(FEImageManager.PLAYER_IDLE_LIST), 120);
    }
    
//  </editor-fold>
    
//  <editor-fold defaultstate="collapsed" desc="Task Handler">
    
    public void timerTaskHandler() {
        
        updateVelocity();
        updateImage();
//        updateFacingDirection();
        applyVelocity();
        
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
    
    private void updateImage() {
        BufferedImage image = (BufferedImage) animator.getCurrentImage();
        if (image != null) {
            setImage(image);
        } else {
            setImage(ip.getImage(FEImageManager.MISSING_TEXTURE));
        }
    }
    
    private void updateFacingDirection() {
        
        // Determines what direction the player is facing relative to its velocity
//        if (getVelocity().y < 0) facing = Direction.UP;
//        else if (getVelocity().y > 0) facing = Direction.DOWN;
//        else {
//            if (getVelocity().x < 0) facing = Direction.LEFT;
//            else if (getVelocity().x > 0) facing = Direction.RIGHT;
//        }
        
        // Selects image relative to which direction the player is facing
//        FEImageManager im = new FEImageManager();
//        if (facing == Direction.UP) currentImageSet = ip.getAnimator(FEImageManager.PLAYER_IDLE_LIST, 200);
//        if (facing == Direction.DOWN) currentImageSet = ip.getAnimator(FEImageManager.PLAYER_IDLE_LIST, Integer.MAX_VALUE);
//        if (facing == Direction.LEFT) currentImageSet = ip.getAnimator(FEImageManager.PLAYER_LEFT, Integer.MAX_VALUE);
//        if (facing == Direction.RIGHT) currentImageSet = ip.getAnimator(FEImageManager.PLAYER_RIGHT, Integer.MAX_VALUE);
//        if (currentImageSet != null) {
//            BufferedImage image = (BufferedImage) currentImageSet.getCurrentImage();
//            if (image != null) setImage(image);
//        }
//        if (getImage() == null) {
//            setImage(ip.getImage(FEImageManager.MISSING_TEXTURE));
//        }
        
    }
    
    public void applyVelocity() {
        
        if (getPosition().x + getVelocity().x <= screenLimiter.getMinX() || getPosition().x + getVelocity().x >= screenLimiter.getMaxX()) displacementPosition.x += getVelocity().x;
        else if (displacementPosition.x == 0) environmentPosition.x += getVelocity().x;
        else displacementPosition.x = 0;
        
        if (getPosition().y + getVelocity().y <= screenLimiter.getMinY() || getPosition().y + getVelocity().y >= screenLimiter.getMaxY()) displacementPosition.y += getVelocity().y;
        else if (displacementPosition.y == 0) environmentPosition.y += getVelocity().y;
        else displacementPosition.y = 0;
        
    }
    
//  </editor-fold>
    
//  <editor-fold defaultstate="collapsed" desc="Paint">
    
    public void draw(Graphics2D graphics) {
        
        // Moves the player on the coordinates of the screen, not the world
        graphics.translate(-environmentPosition.x + Environment.DEFAULT_WINDOW_X - (WIDTH / 2), -environmentPosition.y + Environment.DEFAULT_WINDOW_Y - (HEIGHT / 2));
        
        // Draws the player
        graphics.drawImage(getImage(), getPosition().x, getPosition().y, null);
        
        // Translates images from coordinates of the player to coordinates of its drawing location
        // Used for objects such as the player's hitbox
        graphics.translate(WIDTH / 2, HEIGHT / 2);
        
        graphics.setColor(Color.RED);
        
        // Outlines the hitbox of the player
        graphics.draw(getObjectBoundary());
        
    }
    
//  </editor-fold>
    
//  <editor-fold defaultstate="collapsed" desc="Properties">
    
    @Override
    public Rectangle getObjectBoundary() {
        return new Rectangle(getPosition().x - (WIDTH / 2), getPosition().y - (HEIGHT / 2), WIDTH, HEIGHT);
    }
    
    public ArrayList<Direction> getDirections() {
        return directions;
    }
    
    public ActionStateE getActionState() {
        return actionState;
    }
    
    public void setActionState(ActionStateE actionState) {
        this.actionState = actionState;
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
    
//  </editor-fold>
    
}
