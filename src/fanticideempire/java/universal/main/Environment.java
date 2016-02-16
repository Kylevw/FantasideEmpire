/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fanticideempire.java.universal.main;

import fanticideempire.java.environment.Direction;
import fanticideempire.java.universal.resources.GameState;
import fanticideempire.java.universal.resources.FEImageManager;
import fanticideempire.java.environment.entities.Player;
import fanticideempire.java.environment.entities.Timmy;
import fanticideempire.java.environment.entities.Entity;
import grid.Grid;
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
    
    private int environmentTime;
    
    private static final int ENVIRONMENT_DAY_LENGTH = 24000;
    
    public final Grid environmentGrid;
    
    public GameState gameState;
    
    public static final int DEFAULT_WINDOW_WIDTH = 336;
    public static final int DEFAULT_WINDOW_HEIGHT = 192;
    public static final int DEFAULT_WINDOW_X = DEFAULT_WINDOW_WIDTH / 2;
    public static final int DEFAULT_WINDOW_Y = DEFAULT_WINDOW_HEIGHT / 2;
    
    public static final int GRID_CELL_SIZE = 24;
    
    FEImageManager im;

    public Environment() {
        
//        environmentTime = 8900;
        
        gameState = GameState.ENVIRONMENT;
        im = new FEImageManager();
        
        environmentGrid = new Grid
        (DEFAULT_WINDOW_WIDTH / GRID_CELL_SIZE, DEFAULT_WINDOW_HEIGHT / GRID_CELL_SIZE * 2, GRID_CELL_SIZE, GRID_CELL_SIZE / 2, new Point(-DEFAULT_WINDOW_X, -DEFAULT_WINDOW_Y), Color.BLACK);
        
        updateGrid(2, 2);
        
        player = new Player(new Point(0, 0), new PlayerScreenLimitProvider(environmentGrid.getGridSize().width - DEFAULT_WINDOW_WIDTH, environmentGrid.getGridSize().height - DEFAULT_WINDOW_HEIGHT), im);
        
    }
    
    private void updateGrid(double xScreens, double yScreens) {
        if (xScreens < 1) xScreens = 1;
        if (yScreens < 1) yScreens = 1;
        int x = (int) (xScreens * DEFAULT_WINDOW_WIDTH);
        int y = (int) (yScreens * DEFAULT_WINDOW_HEIGHT);
        environmentGrid.setPosition(new Point(-(x / 2), -(y / 2)));
        environmentGrid.setColumns(x / environmentGrid.getCellWidth());
        environmentGrid.setRows(y / environmentGrid.getCellHeight());
    }

    @Override
    public void initializeEnvironment() {
    }

    @Override
    public void timerTaskHandler() {
        
        if (player != null) {
            player.timerTaskHandler();
            if (timmy != null) System.out.println(player.intersects(timmy));
        }
        
        if (timmy != null) {
            timmy.timerTaskHandler();
            if (timmy.hasDespawned()) timmy = null;
        }
        
        environmentTime++;
        if (environmentTime > ENVIRONMENT_DAY_LENGTH) environmentTime = 0;
        
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
            
            if (e.getKeyCode() == KeyEvent.VK_SPACE) player.Jump();
        }
        
        if (e.getKeyCode() == KeyEvent.VK_E) {
            if (timmy != null) {
                if (player != null) timmy.despawn(player.getPosition());
                else timmy.despawn();
            } else if (player != null) timmy = new Timmy(new Point(player.getPosition()), new Point(player.getPosition().x, player.getPosition().y + 40), im);
            else timmy = new Timmy(new Point(0, 0), im);
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
        
        ArrayList<Entity> entities = new ArrayList<>();
        if (timmy != null) entities.add(timmy);
        if (player != null) entities.add(player);
        
        entities.sort((Entity e1, Entity e2) -> {
            final int y1 = e1.getPosition().y;
            final int y2 = e2.getPosition().y;
            return y1 < y2 ? -1 : y1 > y2 ? 1 : 0;
        });
        
        
        // Resizes the default window size to the current size of the JFrame
        AffineTransform atWindow;
        Graphics2D graphics = (Graphics2D) g;
        atWindow = AffineTransform.getScaleInstance((double) FanticideEmpire.getWindowSize().width / DEFAULT_WINDOW_WIDTH, (double) FanticideEmpire.getWindowSize().height / DEFAULT_WINDOW_HEIGHT);
        if (atWindow != null) graphics.setTransform(atWindow);
        
        int xTranslation = 0;
        int yTranslation = 0;
        
        if (player != null) {
            xTranslation = player.getPosition().x;
            yTranslation = player.getPosition().y - player.getZDisplacement() - (player.getSize().height / 2);
            if (xTranslation < player.getScreenMinX()) xTranslation = player.getScreenMinX();
            else if (xTranslation > player.getScreenMaxX()) xTranslation = player.getScreenMaxX();
            if (yTranslation < player.getScreenMinY()) yTranslation = player.getScreenMinY();
            else if (yTranslation > player.getScreenMaxY()) yTranslation = player.getScreenMaxY();
            xTranslation = DEFAULT_WINDOW_X - xTranslation;
            yTranslation = DEFAULT_WINDOW_Y - yTranslation;
        }
        
        // Translates all background images in reference to the player's current position
        graphics.translate(xTranslation, yTranslation);
        
        // Draws rectangles for debugging
        if (environmentGrid != null) {
            
//            drawGridBase(graphics);
            
            fillGrid(graphics, im.getImage(FEImageManager.GRASS_TILE), xTranslation, yTranslation);
            
        }
        
//        graphics.drawImage(im.getImage(FEImageManager.TEST_BACKGROUND), -environmentGrid.getGridSize().width / 2, -environmentGrid.getGridSize().height / 2, environmentGrid.getGridSize().width, environmentGrid.getGridSize().height, null);
        
        entities.stream().forEach((entity) -> {
            entity.draw(graphics);
        });      
        
        int environmentFactor = 0;
        if (environmentTime >= ENVIRONMENT_DAY_LENGTH * 3 / 8) {
            if (environmentTime >= ENVIRONMENT_DAY_LENGTH / 2 && environmentTime < ENVIRONMENT_DAY_LENGTH * 7 / 8) environmentFactor = 255;
            else {
                environmentFactor = environmentTime - (ENVIRONMENT_DAY_LENGTH * 3 / 8);
                if (environmentTime > ENVIRONMENT_DAY_LENGTH / 2) environmentFactor = Math.abs(environmentFactor - (ENVIRONMENT_DAY_LENGTH * 5 / 8));
                environmentFactor = environmentFactor * 255 * 8 / ENVIRONMENT_DAY_LENGTH;
            }
        }
        
        graphics.setColor(new Color(255 - environmentFactor, 128 - environmentFactor / 2, environmentFactor / 16, environmentFactor * 13 / 16));
        if (environmentFactor > 0) graphics.fillRect(-xTranslation, -yTranslation, DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
    }
    
    public void drawGridBase(Graphics2D graphics) {
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillRect(environmentGrid.getPosition().x + DEFAULT_WINDOW_X,
        environmentGrid.getPosition().y + DEFAULT_WINDOW_Y,
        environmentGrid.getGridSize().width - DEFAULT_WINDOW_WIDTH,
        environmentGrid.getGridSize().height - DEFAULT_WINDOW_HEIGHT);
        
        graphics.setColor(Color.GRAY);
        for (int i = 0; i < environmentGrid.getColumns(); i++) {
            int x = (int) environmentGrid.getCellSystemCoordinate(i, 0).x;
            graphics.fillRect(x, environmentGrid.getPosition().y, environmentGrid.getCellWidth(), environmentGrid.getCellHeight());
            graphics.fillRect(x, environmentGrid.getPosition().y + environmentGrid.getGridSize().height - environmentGrid.getCellHeight(), environmentGrid.getCellWidth(), environmentGrid.getCellHeight());
        }
        
        for (int i = 0; i < environmentGrid.getRows(); i++) {
            int y = (int) environmentGrid.getCellSystemCoordinate(0, i).y;
            graphics.fillRect(environmentGrid.getPosition().x, y, environmentGrid.getCellWidth(), environmentGrid.getCellHeight());
            graphics.fillRect(environmentGrid.getPosition().x + environmentGrid.getGridSize().width - environmentGrid.getCellWidth(), y, environmentGrid.getCellWidth(), environmentGrid.getCellHeight());
        }
        
        environmentGrid.paintComponent(graphics);
    }
    
    public void fillGrid(Graphics2D graphics, BufferedImage image, int xTranslation, int yTranslation) {
        for (int x = 0; x < environmentGrid.getColumns(); x++) {
                for (int y = 0; y < environmentGrid.getRows(); y++) {
                    
                    Point gridPoint = new Point(environmentGrid.getCellSystemCoordinate(x, y));
                    if (player != null &&
                        gridPoint.x + environmentGrid.getCellWidth() >= -xTranslation &&
                        gridPoint.x - DEFAULT_WINDOW_WIDTH <= -xTranslation &&
                        gridPoint.y + environmentGrid.getCellHeight() >= -yTranslation &&
                        gridPoint.y - DEFAULT_WINDOW_HEIGHT <= -yTranslation)
                      
                        graphics.drawImage(im.getImage(FEImageManager.GRASS_TILE),
                        gridPoint.x, gridPoint.y,
                        environmentGrid.getCellWidth(),
                        environmentGrid.getCellHeight(), null);
                    
                }
            }
    }
    
}
