/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fanticideempire;

import images.ImageManager;
import images.ResourceTools;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Kyle van Wiltenburg
 */
public class FEImageManager extends ImageManager implements ImageProviderIntf{
    
    public static String MISSING_TEXTURE = "MISSING_TEXTURE";
    
    public static String PLAYER_UP = "PLAYER_UP";
    public static String PLAYER_DOWN = "PLAYER_DOWN";
    public static String PLAYER_LEFT = "PLAYER_LEFT";
    public static String PLAYER_RIGHT = "PLAYER_RIGHT";
    
    public static String TIMMY_UP = "TIMMY_UP";
    public static String TIMMY_DOWN = "TIMMY_DOWN";
    public static String TIMMY_LEFT = "TIMMY_LEFT";
    public static String TIMMY_RIGHT = "TIMMY_RIGHT";
    
    public static String PLAYER_WALK_DOWN_01 = "PLAYER_WALK_DOWN_01";
    public static String PLAYER_WALK_DOWN_02 = "PLAYER_WALK_DOWN_02";
    public static String PLAYER_WALK_DOWN_03 = "PLAYER_WALK_DOWN_03";
    public static String PLAYER_WALK_DOWN_04 = "PLAYER_WALK_DOWN_04";
    public static String PLAYER_WALK_DOWN_05 = "PLAYER_WALK_DOWN_05";
    public static String PLAYER_WALK_DOWN_06 = "PLAYER_WALK_DOWN_06";
    public static String PLAYER_WALK_DOWN_07 = "PLAYER_WALK_DOWN_07";
    public static String PLAYER_WALK_DOWN_08 = "PLAYER_WALK_DOWN_08";
    
    public static String PLAYER_IDLE_LIST = "PLAYER_IDLE_LIST";
    
    private final ImageManager im;
    
    private final ArrayList<String> PLAYER_IDLE;
    
    private final HashMap<String, Image> imageMap;
    private final HashMap<String, ArrayList> imageListMap;
    
    {
        imageListMap = new HashMap<>();
        imageMap = new HashMap<>();
        
        im = new ImageManager(imageMap);
        
        PLAYER_IDLE = new ArrayList<>();
        
        PLAYER_IDLE.add(PLAYER_WALK_DOWN_01);
        PLAYER_IDLE.add(PLAYER_WALK_DOWN_02);
        PLAYER_IDLE.add(PLAYER_WALK_DOWN_03);
        PLAYER_IDLE.add(PLAYER_WALK_DOWN_04);
        PLAYER_IDLE.add(PLAYER_WALK_DOWN_05);
        PLAYER_IDLE.add(PLAYER_WALK_DOWN_06);
        PLAYER_IDLE.add(PLAYER_WALK_DOWN_07);
        PLAYER_IDLE.add(PLAYER_WALK_DOWN_08);
        
        imageListMap.put(PLAYER_IDLE_LIST, PLAYER_IDLE);     
        
        imageMap.put(MISSING_TEXTURE, ResourceTools.loadImageFromResource("fanticideempire/resources/images/player/missing_texture.png"));
        
        BufferedImage playerSprites = (BufferedImage) ResourceTools.loadImageFromResource("fanticideempire/resources/images/player/player_spritesheet.png");
        BufferedImage timmySprites = (BufferedImage) ResourceTools.loadImageFromResource("fanticideempire/resources/images/player/timmy_spritesheet.png");
        imageMap.put(PLAYER_UP, playerSprites.getSubimage(0, 0, 28, 64));
        imageMap.put(PLAYER_DOWN, playerSprites.getSubimage(0, 64, 28, 64));
        imageMap.put(PLAYER_WALK_DOWN_01, playerSprites.getSubimage(28, 64, 28, 64));
        imageMap.put(PLAYER_WALK_DOWN_02, playerSprites.getSubimage(56, 64, 28, 64));
        imageMap.put(PLAYER_WALK_DOWN_03, playerSprites.getSubimage(84, 64, 28, 64));
        imageMap.put(PLAYER_WALK_DOWN_04, playerSprites.getSubimage(112, 64, 28, 64));
        imageMap.put(PLAYER_WALK_DOWN_05, playerSprites.getSubimage(140, 64, 28, 64));
        imageMap.put(PLAYER_WALK_DOWN_06, playerSprites.getSubimage(168, 64, 28, 64));
        imageMap.put(PLAYER_WALK_DOWN_07, playerSprites.getSubimage(196, 64, 28, 64));
        imageMap.put(PLAYER_WALK_DOWN_08, playerSprites.getSubimage(224, 64, 28, 64));
        imageMap.put(PLAYER_LEFT, playerSprites.getSubimage(0, 128, 28, 64));
        imageMap.put(PLAYER_RIGHT, playerSprites.getSubimage(0, 192, 28, 64));
        
        imageMap.put(TIMMY_UP, timmySprites.getSubimage(0, 0, 28, 56));
        imageMap.put(TIMMY_DOWN, timmySprites.getSubimage(28, 0, 28, 56));
        imageMap.put(TIMMY_LEFT, timmySprites.getSubimage(0, 56, 28, 56));
        imageMap.put(TIMMY_RIGHT, timmySprites.getSubimage(28, 56, 28, 56));
        
    }
    
    @Override
    public BufferedImage getImage(String name){
        BufferedImage image = (BufferedImage) im.getImage(name);
        if (image != null) return image;
        else return (BufferedImage) im.getImage(MISSING_TEXTURE);
    }
    
    @Override
    public ArrayList<String> getImageList(String listName){
        return imageListMap.get(listName);
    }
}

