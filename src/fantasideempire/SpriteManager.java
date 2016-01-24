/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fantasideempire;

import images.ImageManager;
import images.ResourceTools;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Kyle
 */
public class SpriteManager implements SpriteProviderIntf {
    
    public static String PLAYER_UP = "PLAYER_UP";
    public static String PLAYER_DOWN = "PLAYER_DOWN";
    public static String PLAYER_LEFT = "PLAYER_LEFT";
    public static String PLAYER_RIGHT = "PLAYER_RIGHT";

    private final ImageManager im;
    
    private final ArrayList<String> images;
    
    {
        
        HashMap<String, Image> imageMap = new HashMap<>();
        BufferedImage playerSprites = (BufferedImage) ResourceTools.loadImageFromResource("fantasideempire/resources/images/player/player_spritesheet.png");
        imageMap.put(PLAYER_UP, playerSprites.getSubimage(0, 0, 26, 60));
        imageMap.put(PLAYER_DOWN, playerSprites.getSubimage(0, 60, 26, 60));
        imageMap.put(PLAYER_LEFT, playerSprites.getSubimage(0, 120, 26, 60));
        imageMap.put(PLAYER_RIGHT, playerSprites.getSubimage(0, 180, 26, 60));
        

        im = new ImageManager(imageMap);
        images = new ArrayList<>();
    }
    
    @Override
    public BufferedImage getImage(String name){
        return (BufferedImage) im.getImage(name);
    }
}

