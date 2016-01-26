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
 * @author Kyle van Wiltenburg
 */
class Environment extends environment.Environment {
    
    public Player player;
    
    public GameState gameState;
    
    public static final int DEFAULT_WINDOW_WIDTH = 360;
    public static final int DEFAULT_WINDOW_HEIGHT = 200;
    public static final int DEFAULT_WINDOW_X = DEFAULT_WINDOW_WIDTH / 2;
    public static final int DEFAULT_WINDOW_Y = DEFAULT_WINDOW_HEIGHT / 2;
    
    SpriteManager spriteProvider;

    public Environment() {
        
        gameState = GameState.ENVIRONMENT;
        spriteProvider = new SpriteManager();
        player = new Player((BufferedImage) spriteProvider.getImage(SpriteManager.PLAYER_DOWN), new Point(0, 0), new PlayerScreenLimitProvider(DEFAULT_WINDOW_X * 2, DEFAULT_WINDOW_Y * 2), spriteProvider);
        
    }

//  <editor-fold defaultstate="collapsed" desc="Environment Initializer">
    
    @Override
    public void initializeEnvironment() {
    }
    
//  </editor-fold>

//  <editor-fold defaultstate="collapsed" desc="Task Handler">
    
    @Override
    public void timerTaskHandler() {
        
        if (player != null) {
            player.timerTaskHandler();
        }
    }
    
//  </editor-fold>
    
//  <editor-fold defaultstate="collapsed" desc="Key Handler">
    
    @Override
    public void keyPressedHandler(KeyEvent e) {
        // Uses the Arrow Keys to control the player
        if (player != null) {
            // Once pressing a key, it adds said key to the Directions list.
            if (e.getKeyCode() == KeyEvent.VK_UP && !player.getDirections().contains(Direction.UP)) player.addDirection(Direction.UP);
            else if (e.getKeyCode() == KeyEvent.VK_DOWN && !player.getDirections().contains(Direction.DOWN)) player.addDirection(Direction.DOWN);
            else if (e.getKeyCode() == KeyEvent.VK_LEFT && !player.getDirections().contains(Direction.LEFT)) player.addDirection(Direction.LEFT);
            else if (e.getKeyCode() == KeyEvent.VK_RIGHT && !player.getDirections().contains(Direction.RIGHT)) player.addDirection(Direction.RIGHT);
        }
    }

    @Override
    public void keyReleasedHandler(KeyEvent e) {
        if (player != null) {
            // Once letting go of a key, it removes said key from the Directions list.
            if (e.getKeyCode() == KeyEvent.VK_UP && player.getDirections().contains(Direction.UP)) player.removeDirection(Direction.UP);
            else if (e.getKeyCode() == KeyEvent.VK_DOWN && player.getDirections().contains(Direction.DOWN)) player.removeDirection(Direction.DOWN);
            else if (e.getKeyCode() == KeyEvent.VK_LEFT && player.getDirections().contains(Direction.LEFT)) player.removeDirection(Direction.LEFT);
            else if (e.getKeyCode() == KeyEvent.VK_RIGHT && player.getDirections().contains(Direction.RIGHT)) player.removeDirection(Direction.RIGHT);
        }
    }
    
//  </editor-fold>
    
//  <editor-fold defaultstate="collapsed" desc="Mouse Handler">
    
    @Override
    public void environmentMouseClicked(MouseEvent e) {
    }
    
//  </editor-fold>

//  <editor-fold defaultstate="collapsed" desc="DrawingIntf">
    
    @Override
    public void paintEnvironment(Graphics g) {
        
        // Resizes the default window size to the current size of the JFrame
        AffineTransform atWindow;
        Graphics2D graphics = (Graphics2D) g;
        atWindow = AffineTransform.getScaleInstance((double) FantasideEmpire.getWindowSize().width / DEFAULT_WINDOW_WIDTH, (double) FantasideEmpire.getWindowSize().height / DEFAULT_WINDOW_HEIGHT);
        if (atWindow != null) graphics.setTransform(atWindow);
        
        // Translates all background images in reference to the player's current position
        graphics.translate(DEFAULT_WINDOW_X - player.getEnvironmentPosition().x, DEFAULT_WINDOW_Y - player.getEnvironmentPosition().y);
        
        // Draws rectangles for debugging
        graphics.setColor(Color.BLACK);
        graphics.fillRect(-DEFAULT_WINDOW_X, -DEFAULT_WINDOW_Y, DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, 1, 1);
        
        // Draws example background image
//        graphics.drawImage((BufferedImage) ResourceTools.loadImageFromResource("fantasideempire/beetlegrassedit.png"), -DEFAULT_WINDOW_WIDTH - 3, -DEFAULT_WINDOW_HEIGHT - 3, (DEFAULT_WINDOW_WIDTH * 2) + 6, (DEFAULT_WINDOW_HEIGHT * 2) + 6, this);
        
        // Centers the translation to draw the player
        graphics.translate(player.getEnvironmentPosition().x - DEFAULT_WINDOW_X, player.getEnvironmentPosition().y - DEFAULT_WINDOW_Y);
        
        // Draws the player
        if (player != null) {
            player.draw(graphics);
        }
        
    }
    
//  </editor-fold>
    
}
