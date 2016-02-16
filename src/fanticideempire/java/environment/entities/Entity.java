/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fanticideempire.java.environment.entities;

import environment.Actor;
import environment.Velocity;
import fanticideempire.java.universal.resources.FEImageManager;
import fanticideempire.java.universal.resources.ImageProviderIntf;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author Kyle
 */
public class Entity extends Actor{
    
    {
        zDisplacement = 0;
        zVelocity = 0;
        drawBoundary = false;
    }
    
    private boolean onGround;
    
    private final Dimension size;
    private boolean drawBoundary;
    private int zDisplacement;
    private int zVelocity;
    
    private final ImageProviderIntf ip;
    
    public Entity(BufferedImage image, Point position, Dimension size, ImageProviderIntf ip) {
        super(image, position, new Velocity(0, 0));
        this.size = size;
        this.ip = ip;
    }
    
    public Dimension getSize() {
        return size;
    }
    
    public void timerTaskHandler() {
        
    }
    
    @Override
    public void draw(Graphics2D graphics) {
        BufferedImage shadow = null;
        if (zDisplacement <= size.height / 3) shadow = ip.getImage(FEImageManager.ENTITY_SHADOW_BIG);
        else if (zDisplacement <= size.height * 2 / 3) shadow = ip.getImage(FEImageManager.ENTITY_SHADOW_MEDIUM);
        else if (zDisplacement <= size.height) shadow = ip.getImage(FEImageManager.ENTITY_SHADOW_SMALL);
        else if (zDisplacement <= size.height * 1.5) shadow = ip.getImage(FEImageManager.ENTITY_SHADOW_TINY);
        
        if (shadow != null) graphics.drawImage(shadow, getObjectGroundBoundary().x, getObjectGroundBoundary().y, getObjectGroundBoundary().width, getObjectGroundBoundary().height, null);
        graphics.drawImage(getImage(), getPosition().x - (size.width / 2), getPosition().y - (size.height) - zDisplacement, size.width, size.height, null);
        if (drawBoundary) {
            graphics.setColor(Color.RED);
            graphics.draw(getObjectGroundBoundary());
            graphics.setColor(Color.BLUE);
            graphics.draw(getObjectBoundary());
        }
    }
    
    @Override
    public Rectangle getObjectBoundary() {
        return new Rectangle(getPosition().x - (size.width / 2),
        getPosition().y - (size.height) - zDisplacement,
        size.width, size.height);
    }
    
    public Rectangle getObjectGroundBoundary() {
        return new Rectangle(getObjectBoundary().x,
        getObjectBoundary().y + getObjectBoundary().height - (getObjectBoundary().width / 4) + zDisplacement,
        getObjectBoundary().width, getObjectBoundary().width / 2);
    }
    
    public void drawObjectBoundary(boolean drawBoundary) {
        this.drawBoundary = drawBoundary;
    }
    
    public int getZDisplacement() {
        return zDisplacement;
    }
    
    public int getZVelocity() {
        return zVelocity;
    }
    
    public void setZVelocity(int zVelocity) {
        this.zVelocity = zVelocity;
    }
    
    public void accelerateZVelocity(int zVelocity) {
        this.zVelocity += zVelocity;
    }
    
    public void setZDisplacement(int zDisplacement) {
        this.zDisplacement = zDisplacement;
    }
    
    
    @Override
    public void move() {
        applyZVelocity();
        super.move();
    }
    
    public void applyZVelocity() {
        zDisplacement += zVelocity;
        
        if (zDisplacement <= 0) {
            zDisplacement = 0;
            zVelocity = 0;
        } else accelerateZVelocity(-1);
    }
    
    public boolean intersects(Entity entity) {
        return getObjectBoundary().intersects(entity.getObjectBoundary()) &&
        getObjectGroundBoundary().intersects(entity.getObjectGroundBoundary());
    }
    
    
    public void setImage(String image) {
        super.setImage(ip.getImage(image));
    }
    
    public ImageProviderIntf getImageProvider() {
        return ip;
    }
    
    public boolean onGround() {
        return zDisplacement == 0;
    }
    
}
