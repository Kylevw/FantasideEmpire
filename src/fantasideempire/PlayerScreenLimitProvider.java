/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fantasideempire;

/**
 *
 * @author Kyle
 */
public class PlayerScreenLimitProvider implements ScreenLimitProviderIntf{
    
    private int minX, maxX, minY, maxY;
    
    public PlayerScreenLimitProvider(int minX, int maxX, int minY, int maxY){
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
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
