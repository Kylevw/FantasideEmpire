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
 * @author Kyle van Wiltenburg
 */
public class FEImageManager extends ImageManager implements ImageProviderIntf{
    
//<editor-fold defaultstate="collapsed" desc="Properties">
    
    public static String MISSING_TEXTURE = "MISSING_TEXTURE";
    
    public static String PLAYER_UP = "PLAYER_UP";
    public static String PLAYER_DOWN_01 = "PLAYER_DOWN_01";
    public static String PLAYER_DOWN_02 = "PLAYER_DOWN_02";
    public static String PLAYER_DOWN_03 = "PLAYER_DOWN_03";
    public static String PLAYER_DOWN_04 = "PLAYER_DOWN_04";
    public static String PLAYER_LEFT = "PLAYER_LEFT";
    public static String PLAYER_RIGHT = "PLAYER_RIGHT";
    
    public static String PLAYER_IDLE_LIST = "PLAYER_IDLE_LIST";
    
    private final ImageManager im;
    
    private final ArrayList<String> PLAYER_IDLE;
    
    private final HashMap<String, Image> imageMap;
    private final HashMap<String, ArrayList> imageListMap;
    
//</editor-fold>
    
    {
        imageListMap = new HashMap<>();
        imageMap = new HashMap<>();
        
        im = new ImageManager(imageMap);
        
        PLAYER_IDLE = new ArrayList<>();
        
        PLAYER_IDLE.add(PLAYER_DOWN_01);
        PLAYER_IDLE.add(PLAYER_DOWN_03);
        PLAYER_IDLE.add(PLAYER_DOWN_04);
        
        imageListMap.put(PLAYER_IDLE_LIST, PLAYER_IDLE);     
        
        imageMap.put(MISSING_TEXTURE, ResourceTools.loadImageFromResource("fantasideempire/resources/images/player/missing_texture.png"));
        
        BufferedImage playerSprites = (BufferedImage) ResourceTools.loadImageFromResource("fantasideempire/resources/images/player/player_spritesheet.png");
        imageMap.put(PLAYER_UP, playerSprites.getSubimage(0, 0, 26, 60));
        imageMap.put(PLAYER_DOWN_01, playerSprites.getSubimage(0, 60, 26, 60));
        imageMap.put(PLAYER_DOWN_02, playerSprites.getSubimage(26, 60, 26, 60));
        imageMap.put(PLAYER_DOWN_03, playerSprites.getSubimage(52, 60, 26, 60));
        imageMap.put(PLAYER_DOWN_04, playerSprites.getSubimage(78, 60, 26, 60));
        imageMap.put(PLAYER_LEFT, playerSprites.getSubimage(0, 120, 26, 60));
        imageMap.put(PLAYER_RIGHT, playerSprites.getSubimage(0, 180, 26, 60));
        
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

