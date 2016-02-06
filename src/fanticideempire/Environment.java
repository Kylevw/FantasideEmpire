/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fanticideempire;

import environment.Actor;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Kyle van Wiltenburg
 */
class Environment extends environment.Environment {
    
    public Player player;
    public Timmy timmy;
    
    public GameState gameState;
    
    public static final int DEFAULT_WINDOW_WIDTH = 360;
    public static final int DEFAULT_WINDOW_HEIGHT = 200;
    public static final int DEFAULT_WINDOW_X = DEFAULT_WINDOW_WIDTH / 2;
    public static final int DEFAULT_WINDOW_Y = DEFAULT_WINDOW_HEIGHT / 2;
    
    FEImageManager spriteProvider;

    public Environment() {
        
        gameState = GameState.ENVIRONMENT;
        spriteProvider = new FEImageManager();
        player = new Player((BufferedImage) spriteProvider.getImage(FEImageManager.PLAYER_DOWN), new Point(0, 0), new PlayerScreenLimitProvider(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT), spriteProvider);
        
    }

    @Override
    public void initializeEnvironment() {
    }

    @Override
    public void timerTaskHandler() {
        
        if (player != null) {
            player.timerTaskHandler();
        }
        
        if (timmy != null) {
            timmy.timerTaskHandler();
            if (timmy.hasDespawned()) timmy = null;
        }
    }
    
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
        
        if (e.getKeyCode() == KeyEvent.VK_E) {
            if (timmy != null) {
                if (player != null) timmy.despawn(player.getPosition());
                else timmy.despawn();
            } else if (player != null) timmy = new Timmy(new Point(player.getPosition()), new Point(player.getPosition().x, player.getPosition().y + 40), spriteProvider);
            else timmy = new Timmy(new Point(0, 0), spriteProvider);
        }
        
        if (timmy != null) {
            if (e.getKeyCode() == KeyEvent.VK_A) timmy.setDestination(new Point(timmy.getPosition().x - 100, timmy.getPosition().y));
            if (e.getKeyCode() == KeyEvent.VK_D) timmy.setDestination(new Point(timmy.getPosition().x + 100, timmy.getPosition().y));
            if (e.getKeyCode() == KeyEvent.VK_W) timmy.setDestination(new Point(timmy.getPosition().x, timmy.getPosition().y - 100));
            if (e.getKeyCode() == KeyEvent.VK_S) timmy.setDestination(new Point(timmy.getPosition().x, timmy.getPosition().y + 100));
            if (e.getKeyCode() == KeyEvent.VK_Q) timmy.setDestination(new Point(player.getPosition()));
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
    
    @Override
    public void environmentMouseClicked(MouseEvent e) {
    }
    
    @Override
    public void paintEnvironment(Graphics g) {
        
        ArrayList<Actor> actors = new ArrayList<>();
        if (timmy != null) actors.add(timmy);
        if (player != null) actors.add(player);
        
        actors.sort((Actor a1, Actor a2) -> {
            final int y1 = a1.getPosition().y;
            final int y2 = a2.getPosition().y;
            return y1 < y2 ? -1 : y1 > y2 ? 1 : 0;
        });
        
        
        // Resizes the default window size to the current size of the JFrame
        AffineTransform atWindow;
        Graphics2D graphics = (Graphics2D) g;
        atWindow = AffineTransform.getScaleInstance((double) FantasideEmpire.getWindowSize().width / DEFAULT_WINDOW_WIDTH, (double) FantasideEmpire.getWindowSize().height / DEFAULT_WINDOW_HEIGHT);
        if (atWindow != null) graphics.setTransform(atWindow);
        
        // Translates all background images in reference to the player's current position
        graphics.translate(DEFAULT_WINDOW_X - player.getEnvironmentPosition().x, DEFAULT_WINDOW_Y - player.getEnvironmentPosition().y);
        
        // Draws rectangles for debugging
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillRect(-DEFAULT_WINDOW_X, -DEFAULT_WINDOW_Y, DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
        graphics.setColor(Color.BLACK);
        graphics.drawRect(-DEFAULT_WINDOW_X, -DEFAULT_WINDOW_Y, DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
        graphics.setColor(Color.RED);
        graphics.drawRect(-DEFAULT_WINDOW_WIDTH + 3, -DEFAULT_WINDOW_HEIGHT + 1, DEFAULT_WINDOW_WIDTH * 2 - 6, DEFAULT_WINDOW_HEIGHT * 2 - 2);
        graphics.drawRect(-DEFAULT_WINDOW_WIDTH + 4, -DEFAULT_WINDOW_HEIGHT + 2, DEFAULT_WINDOW_WIDTH * 2 - 8, DEFAULT_WINDOW_HEIGHT * 2 - 4);
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, 1, 1);
        
        actors.stream().forEach((actor) -> {
            actor.draw(graphics);
        });      
    }
    
}
