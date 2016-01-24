/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fantasideempire;

import images.ResourceTools;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 *
 * @author Kyle
 */
class Environment extends environment.Environment {
    
    public Player player;
    
    public static final int DEFAULT_WINDOW_WIDTH = 360;
    public static final int DEFAULT_WINDOW_HEIGHT = 200;
    public static final int DEFAULT_WINDOW_X = DEFAULT_WINDOW_WIDTH / 2;
    public static final int DEFAULT_WINDOW_Y = DEFAULT_WINDOW_HEIGHT / 2;
    
    SpriteManager spriteProvider;

    public Environment() {
        
        spriteProvider = new SpriteManager();
        
        player = new Player((BufferedImage) ResourceTools.loadImageFromResource("fantasideempire/player.png"), new Point(0, 0), new PlayerScreenLimitProvider(-DEFAULT_WINDOW_X, DEFAULT_WINDOW_X, -DEFAULT_WINDOW_Y, DEFAULT_WINDOW_Y), spriteProvider);
        
    }

    @Override
    public void initializeEnvironment() {
    }

    @Override
    public void timerTaskHandler() {
        
        if (player != null) {
            player.timerTaskHandler();
        }
    }

    @Override
    public void keyPressedHandler(KeyEvent e) {
        if (player != null) {
            if (e.getKeyCode() == KeyEvent.VK_UP && !player.getDirections().contains(Direction.UP)) player.addDirection(Direction.UP);
            else if (e.getKeyCode() == KeyEvent.VK_DOWN && !player.getDirections().contains(Direction.DOWN)) player.addDirection(Direction.DOWN);
            else if (e.getKeyCode() == KeyEvent.VK_LEFT && !player.getDirections().contains(Direction.LEFT)) player.addDirection(Direction.LEFT);
            else if (e.getKeyCode() == KeyEvent.VK_RIGHT && !player.getDirections().contains(Direction.RIGHT)) player.addDirection(Direction.RIGHT);
        }
    }

    @Override
    public void keyReleasedHandler(KeyEvent e) {
        if (player != null) {
            if (e.getKeyCode() == KeyEvent.VK_UP && player.getDirections().contains(Direction.UP)) player.removeDirection(Direction.UP);
            else if (e.getKeyCode() == KeyEvent.VK_DOWN && player.getDirections().contains(Direction.DOWN)) player.removeDirection(Direction.DOWN);
            else if (e.getKeyCode() == KeyEvent.VK_LEFT && player.getDirections().contains(Direction.LEFT)) player.removeDirection(Direction.LEFT);
            else if (e.getKeyCode() == KeyEvent.VK_RIGHT && player.getDirections().contains(Direction.RIGHT)) player.removeDirection(Direction.RIGHT);
        }
    }

    @Override
    public void environmentMouseClicked(MouseEvent e) {
    }

    @Override
    public void paintEnvironment(Graphics g) {
        
        AffineTransform atWindow;
        Graphics2D graphics = (Graphics2D) g;
        atWindow = AffineTransform.getScaleInstance((double) FantasideEmpire.getWindowSize().width / DEFAULT_WINDOW_WIDTH, (double) FantasideEmpire.getWindowSize().height / DEFAULT_WINDOW_HEIGHT);
        if (atWindow != null) graphics.setTransform(atWindow);
        
        graphics.translate(DEFAULT_WINDOW_X - player.getEnvironmentPosition().x, DEFAULT_WINDOW_Y - player.getEnvironmentPosition().y);
        
        graphics.setColor(Color.BLACK);
        graphics.fillRect(-DEFAULT_WINDOW_X, -DEFAULT_WINDOW_Y, DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, 1, 1);
        
//        graphics.drawImage((BufferedImage) ResourceTools.loadImageFromResource("fantasideempire/beetlegrassedit.png"), -DEFAULT_WINDOW_WIDTH - 3, -DEFAULT_WINDOW_HEIGHT - 3, (DEFAULT_WINDOW_WIDTH * 2) + 6, (DEFAULT_WINDOW_HEIGHT * 2) + 6, this);
        
        graphics.translate(player.getEnvironmentPosition().x - DEFAULT_WINDOW_X, player.getEnvironmentPosition().y - DEFAULT_WINDOW_Y);
        
        if (player != null) {
            player.draw(graphics);
        }
        
    }
    
}
