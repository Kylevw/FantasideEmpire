/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fantasideempire;

import environment.Actor;
import environment.Velocity;
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
        facing = Direction.DOWN;
    }
    
    private final ScreenLimitProviderIntf screenLimiter;
    private final SpriteProviderIntf spriteProvider;
    
    private final ArrayList<Direction> directions;
    private Direction facing;
    private static final int WIDTH = 26;
    private static final int HEIGHT = 60;
    
    private final Point environmentPosition;
    private final Point displacementPosition;
    
    /**
     * Constructor, returns an instance of the BattleEntity class
     *
     * @param image the current sprite of the entity
     * @param position the current position of the entity on screen
     * @param screenLimiter inputs the minimum and maximum positions for the camera
     * @param spriteProvider the SpriteManager for the entity
     * 
     */
    
    public Player(BufferedImage image, Point position, ScreenLimitProviderIntf screenLimiter, SpriteProviderIntf spriteProvider) {

        super(image, position, new Velocity(0, 0));
        this.directions = new ArrayList<>();
        this.environmentPosition = new Point(position);
        this.displacementPosition = new Point(0, 0);
        this.screenLimiter = screenLimiter;
        this.spriteProvider = spriteProvider;
    }
    
//  </editor-fold>
    
//  <editor-fold defaultstate="collapsed" desc="Task Handler">
    
    public void timerTaskHandler() {
        
        //Debugging Info
        System.out.println("Position = " + getPosition());
        System.out.println("Displacement = " + displacementPosition);
        System.out.println("Environment = " + environmentPosition);
        System.out.println("Hitbox = " + getObjectBoundary());
        
        updateVelocity();
        updateFacingDirection();
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
    
    private void updateFacingDirection() {
        
        // Determines what direction the player is facing relative to its velocity
        if (getVelocity().y < 0) facing = Direction.UP;
        else if (getVelocity().y > 0) facing = Direction.DOWN;
        else {
            if (getVelocity().x < 0) facing = Direction.LEFT;
            else if (getVelocity().x > 0) facing = Direction.RIGHT;
        }
        
        // Selects image relative to which direction the player is facing
        if (facing == Direction.UP) setImage(spriteProvider.getImage(SpriteManager.PLAYER_UP));
        if (facing == Direction.DOWN) setImage(spriteProvider.getImage(SpriteManager.PLAYER_DOWN));
        if (facing == Direction.LEFT) setImage(spriteProvider.getImage(SpriteManager.PLAYER_LEFT));
        if (facing == Direction.RIGHT) setImage(spriteProvider.getImage(SpriteManager.PLAYER_RIGHT));
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
    
//  <editor-fold defaultstate="collapsed" desc="DrawingIntf">
    
    public void draw(Graphics2D graphics) {
        
        // Moves the player on the coordinates of the screen, not the world
        graphics.translate(-environmentPosition.x + Environment.DEFAULT_WINDOW_X - (WIDTH / 2), -environmentPosition.y + Environment.DEFAULT_WINDOW_Y - (HEIGHT / 2));
        
        // Draws the player
        graphics.drawImage(getImage(), getPosition().x, getPosition().y, WIDTH, HEIGHT, null);
        
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
    
    public Direction getFacingDirection() {
        return facing;
    }
    
    public void setFacingDirection(Direction facing) {
        this.facing = facing;
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
