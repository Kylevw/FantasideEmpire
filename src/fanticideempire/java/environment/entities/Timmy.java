/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fanticideempire.java.environment.entities;

import fanticideempire.java.environment.Direction;
import fanticideempire.java.universal.resources.FEImageManager;
import fanticideempire.java.universal.resources.ImageProviderIntf;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import path.TrigonometryCalculator;
import timer.DurationTimer;

/**
 *
 * @author Kyle
 */
public class Timmy extends Entity{
    
    {
        drawObjectBoundary(true);
    }
    
    private Direction direction;
    
    private boolean despawn;
    
    private Point destinationPos;
    
    private boolean floatDirection;
    private final DurationTimer floatTimer = new DurationTimer(200);
    
    private static final int FLY_SPEED = 5;
    private static final int FLOAT_DISPLACEMENT = 4;
    
    public Timmy(Point position, ImageProviderIntf ip) {
        super(ip.getImage(FEImageManager.TIMMY_DOWN), position, new Dimension(28, 56), ip);
        direction = Direction.DOWN;
        setImage(ip.getImage(FEImageManager.TIMMY_DOWN));
    }
    
    public Timmy(Point startPosition, Point endPosition, ImageProviderIntf ip) {
        super(ip.getImage(FEImageManager.TIMMY_DOWN), startPosition, new Dimension(28, 56), ip);
        direction = Direction.DOWN;
        this.destinationPos = endPosition;
        setImage(ip.getImage(FEImageManager.TIMMY_DOWN));
    }
    
    @Override
    public void timerTaskHandler() {
        
        setVelocity(0, 0);
        setZVelocity(0);
        
        if (floatTimer.isComplete()) {
            floatTimer.start(200);
            if (floatDirection) setZVelocity(1);
            else setZVelocity(-1);
        }
        
        if (destinationPos != null) {
            if (destinationPos.x > getPosition().x - 3 && destinationPos.x < getPosition().x + 3 &&
                destinationPos.y > getPosition().y - 3 && destinationPos.y < getPosition().y + 3) {
                setPosition(destinationPos);
                destinationPos = null;
            } else {
                setVelocity(TrigonometryCalculator.calculateVelocity(getPosition(), destinationPos, FLY_SPEED));
            }
        }
        
        if (getVelocity().y < 0) {
            if (getVelocity().x < 0) direction = Direction.UP_LEFT;
            else if (getVelocity().x > 0) direction = Direction.UP_RIGHT;
            else direction = Direction.UP;
        } else if (getVelocity().y > 0) {
            if (getVelocity().x < 0) direction = Direction.DOWN_LEFT;
            else if (getVelocity().x > 0) direction = Direction.DOWN_RIGHT;
            else direction = Direction.DOWN;
        } else {
            if (getVelocity().x < 0) direction = Direction.LEFT;
            else if (getVelocity().x > 0) direction = Direction.RIGHT;
        }
        
        move();
        
        switch (direction) {
            
            case UP: setImage(FEImageManager.TIMMY_UP);
                break;
            
            case UP_LEFT: setImage(FEImageManager.TIMMY_UP_LEFT);
                break;
            
            case LEFT: setImage(FEImageManager.TIMMY_LEFT);
                break;
                
            case DOWN_LEFT: setImage(FEImageManager.TIMMY_DOWN_LEFT);
                break;
            
            case DOWN: setImage(FEImageManager.TIMMY_DOWN);
                break;
            
            case DOWN_RIGHT: setImage(FEImageManager.TIMMY_DOWN_RIGHT);
                break;
            
            case RIGHT: setImage(FEImageManager.TIMMY_RIGHT);
                break;
                
            case UP_RIGHT: setImage(FEImageManager.TIMMY_UP_RIGHT);
                break;
        }
        
        if (getVelocity().x != 0 || getVelocity().y != 0) setZDisplacement(FLOAT_DISPLACEMENT);
        
        if (getZDisplacement() > FLOAT_DISPLACEMENT + 1|| getZDisplacement() < FLOAT_DISPLACEMENT - 1) {
            setZDisplacement(FLOAT_DISPLACEMENT);
            setZVelocity(0);
            floatDirection = !floatDirection;
        }
            
    }
    
    @Override
    public Rectangle getObjectBoundary() {
        return new Rectangle(getPosition().x - (getSize().width / 2) + 6, getPosition().y - getSize().height + 4 - getZDisplacement(), getSize().width - 12, getSize().height - 8);
    }
    
    public void setDestination(Point destinationPos) {
        this.destinationPos = destinationPos;
    }
    
    public void despawn() {
        this.destinationPos = getPosition();
        despawn = true;
    }
    
    public void despawn(Point despawnPos) {
        this.destinationPos = despawnPos;
        despawn = true;
    }
    
    public boolean hasDespawned() {
        return despawn == true && destinationPos == null;
    }
    
}
