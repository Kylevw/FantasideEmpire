/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fanticideempire.java.universal.main;

import fanticideempire.java.environment.ScreenLimitProviderIntf;

/**
 *
 * @author Kyle van Wiltenburg
 */
public class PlayerScreenLimitProvider implements ScreenLimitProviderIntf{
    
    private int minX, maxX, minY, maxY;
    
    public PlayerScreenLimitProvider(int width, int height){
        this.minX = -width + Environment.DEFAULT_WINDOW_X;
        this.maxX = width - Environment.DEFAULT_WINDOW_X;
        this.minY = -height + Environment.DEFAULT_WINDOW_Y;
        this.maxY = height - Environment.DEFAULT_WINDOW_Y;
    }
    
    @Override
    public int getMinX() {
        return minX;
    }

    @Override
    public int getMaxX() {
        return maxX;
    }

    @Override
    public int getMinY() {
        return minY;
    }

    @Override
    public int getMaxY() {
        return maxY;
    }

    @Override
    public void setMinX(int minX) {
        this.minX = minX;
    }

    @Override
    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }

    @Override
    public void setMinY(int minY) {
        this.minY = minY;
    }

    @Override
    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }
    
}
