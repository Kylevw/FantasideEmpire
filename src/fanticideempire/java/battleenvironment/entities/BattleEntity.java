package fanticideempire.java.battleenvironment.entities;

import fanticideempire.java.battleenvironment.Element;
import environment.Actor;
import environment.Velocity;
import fanticideempire.java.battleenvironment.Element;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kyle van Wiltenburg
 */
public class BattleEntity extends Actor {
    
//  <editor-fold defaultstate="collapsed" desc="Constructors">
    
    private int width;
    private int height;
    
    private int hp;
    private final int maxHP;
    private final int speed;
    private final int defense;
    
    private final ArrayList<Element> invulElements;    
    /**
     * Constructor, returns an instance of the BattleEntity class
     *
     * @param image the current sprite of the entity
     * @param position the current position of the entity on screen
     * @param width the width of the entity
     * @param height the height of the entity
     * @param hp the current hp of the entity
     * @param maxHP the maximum hp the entity can have
     * @param speed the factor of determining if the entity attacks first
     * @param defense the defense factor the entity has
     * @param invulElements the elements the entity is immune to
     */

    public BattleEntity(BufferedImage image, Point position, int width, int height, int hp, int maxHP, int speed, int defense, ArrayList<Element> invulElements) {
        super(image, position, new Velocity(0, 0));
        this.invulElements = invulElements;
        this.hp = hp;
        this.maxHP = maxHP;
        this.speed = speed;
        this.defense = defense;
    }
    
//  </editor-fold>
    
//  <editor-fold defaultstate="collapsed" desc="Task Handler">
    
    public void timerTaskHandler() {
        if (hp > maxHP) hp = maxHP;
    }
    
//  </editor-fold>
    
//  <editor-fold defaultstate="collapsed" desc="Action Methods">
    
    /**
     * @param damageElement the type of element the attack is
     * @param damage the damage applied to the entity, factored by the entity's defense
     */
    
    public void damage(Element damageElement, int damage) {
        
        // TODO: Sounds for damage, display damageFactor int on screen
        
        double damageFactor;
        if (!invulElements.contains(damageElement)) damageFactor = damage / defense;
        else damageFactor = 0;
        hp -= (int) damageFactor;
    }
    
    /**
     * @param hp the amount of hp to heal the entity
     */
    
    public void heal(int hp) {
        
        // TODO: Sounds for healing, display heal int on screen
        
        this.hp += hp;
    }
    
//  </editor-fold>
    
//  <editor-fold defaultstate="collapsed" desc="Properties">
    
    /**
     * @param hp the amount of hp to set
     */
    
    public void setHealth(int hp) {
        this.hp = hp;
    }
    
//  </editor-fold>
    
}
