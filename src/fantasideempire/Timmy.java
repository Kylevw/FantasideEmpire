/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fantasideempire;

import environment.Actor;
import environment.Velocity;
import java.awt.Graphics2D;
import java.awt.Point;
import path.TrigonometryCalculator;
import timer.DurationTimer;

/**
 *
 * @author Kyle
 */
public class Timmy extends Actor{
    
    public static final int WIDTH = 28;
    public static final int HEIGHT = 56;
    
    private final int speed;
    
    private Direction direction;
    
    private boolean despawn;
    
    private Point destinationPos;
    
    private int floatDisplacement;
    private boolean floatDirection;
    private final DurationTimer floatTimer = new DurationTimer(200);
    
    private final ImageProviderIntf ip;
    
    public Timmy(Point position, ImageProviderIntf ip) {
        super(position, new Velocity(0, 0));
        direction = Direction.DOWN;
        this.ip = ip;
        setImage(ip.getImage(FEImageManager.TIMMY_DOWN));
        speed = 5;
    }
    
    public Timmy(Point startPosition, Point endPosition, ImageProviderIntf ip) {
        super(startPosition, new Velocity(0, 0));
        direction = Direction.DOWN;
        this.destinationPos = endPosition;
        this.ip = ip;
        setImage(ip.getImage(FEImageManager.TIMMY_DOWN));
        speed = 5;
    }
    
    @Override
    public void draw(Graphics2D graphics) {
        graphics.drawImage(getImage(), getPosition().x - (WIDTH / 2), getPosition().y + floatDisplacement - (HEIGHT / 2), WIDTH, HEIGHT, null);
    }
    
    public void timerTaskHandler() {
        
        setVelocity(0, 0);
        
        if (floatTimer.isComplete()) {
            floatTimer.start(200);
            if (floatDirection) floatDisplacement++;
            else floatDisplacement--;
        }
        if (floatDisplacement > 1 || floatDisplacement < -1){
            floatDisplacement = 0;
            floatDirection = !floatDirection;
        }
        
        else if (destinationPos != null) {
            if (destinationPos.x > getPosition().x - 3 && destinationPos.x < getPosition().x + 3 &&
                destinationPos.y > getPosition().y - 3 && destinationPos.y < getPosition().y + 3) {
                setPosition(destinationPos);
                destinationPos = null;
            } else {
                setVelocity(TrigonometryCalculator.calculateVelocity(getPosition(), destinationPos, speed));
            }
        }
        
        if (Math.abs(getVelocity().x) > Math.abs(getVelocity().y)) {
            if (getVelocity().x < 0) direction = Direction.LEFT;
            else if (getVelocity().x > 0) direction = Direction.RIGHT;
        } else {
            if (getVelocity().y < 0) direction = Direction.UP;
            else if (getVelocity().y > 0) direction = Direction.DOWN;
        }
        
        move();
        
        switch (direction) {
            
            case UP: setImage(ip.getImage(FEImageManager.TIMMY_UP));
                break;
                
            case DOWN: setImage(ip.getImage(FEImageManager.TIMMY_DOWN));
                break;
                
            case LEFT: setImage(ip.getImage(FEImageManager.TIMMY_LEFT));
                break;
                
            case RIGHT: setImage(ip.getImage(FEImageManager.TIMMY_RIGHT));
                break;    
        }
            
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
