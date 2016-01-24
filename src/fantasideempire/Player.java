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
 * @author Kyle
 */
public class Player extends Actor {
    
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
    
    public Player(BufferedImage image, Point position, ScreenLimitProviderIntf screenLimiter, SpriteProviderIntf spriteProvider) {

        super(image, position, new Velocity(0, 0));
        this.directions = new ArrayList<>();
        this.environmentPosition = new Point(position);
        this.displacementPosition = new Point(0, 0);
        this.screenLimiter = screenLimiter;
        this.spriteProvider = spriteProvider;
    }
    
    public void timerTaskHandler() {
        System.out.println("Position = " + getPosition());
        System.out.println("Displacement = " + displacementPosition);
        System.out.println("Environment = " + environmentPosition);
        System.out.println("Hitbox = " + getObjectBoundary());
        updateVelocity();
        updateFacingDirection();
        applyVelocity();
        setPosition(environmentPosition.x + displacementPosition.x, environmentPosition.y + displacementPosition.y);
    }
    
    @Override
    public Rectangle getObjectBoundary() {
        return new Rectangle(getPosition().x - (WIDTH / 2), getPosition().y - (HEIGHT / 2), WIDTH, HEIGHT);
    }
    
    private void updateVelocity() {
        setVelocity(0, 0);
        if (getDirections().contains(Direction.UP)) accelerate(0, -3);
        if (getDirections().contains(Direction.DOWN)) accelerate(0, 3);
        if (getDirections().contains(Direction.LEFT)) accelerate(-3, 0);
        if (getDirections().contains(Direction.RIGHT)) accelerate(3, 0);
    }
    
    private void updateFacingDirection() {
        if (getVelocity().y < 0) facing = Direction.UP;
        else if (getVelocity().y > 0) facing = Direction.DOWN;
        else {
            if (getVelocity().x < 0) facing = Direction.LEFT;
            else if (getVelocity().x > 0) facing = Direction.RIGHT;
        }
    }
    
    public void draw(Graphics2D graphics) {
        
        graphics.translate(-environmentPosition.x + Environment.DEFAULT_WINDOW_X - (WIDTH / 2), -environmentPosition.y + Environment.DEFAULT_WINDOW_Y - (HEIGHT / 2));
        
        if (facing == Direction.UP) setImage(spriteProvider.getImage(SpriteManager.PLAYER_UP));
        if (facing == Direction.DOWN) setImage(spriteProvider.getImage(SpriteManager.PLAYER_DOWN));
        if (facing == Direction.LEFT) setImage(spriteProvider.getImage(SpriteManager.PLAYER_LEFT));
        if (facing == Direction.RIGHT) setImage(spriteProvider.getImage(SpriteManager.PLAYER_RIGHT));
        
//        graphics.fillRect(displacementPosition.x, displacementPosition.y, WIDTH, HEIGHT);
        
        graphics.drawImage(getImage(), getPosition().x, getPosition().y, WIDTH, HEIGHT, null);
        
        graphics.translate(WIDTH / 2, HEIGHT / 2);
        
        graphics.setColor(Color.RED);
        graphics.draw(getObjectBoundary());        
    }
    
    public void applyVelocity() {
        if (displacementPosition.x == 0) environmentPosition.x += getVelocity().x;
        if (displacementPosition.y == 0) environmentPosition.y += getVelocity().y;
        if (environmentPosition.x < screenLimiter.getMinX() || environmentPosition.x > screenLimiter.getMaxX()) displacementPosition.x += getVelocity().x;
        if (environmentPosition.y < screenLimiter.getMinY() || environmentPosition.y > screenLimiter.getMaxY()) displacementPosition.y += getVelocity().y;
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
    
    public void clearDirections(Direction direction) {
        directions.clear();
    }
    
    public Point getEnvironmentPosition() {
        return environmentPosition;
    }
    
    public Point getDisplacementPosition() {
        return displacementPosition;
    }
    
}
