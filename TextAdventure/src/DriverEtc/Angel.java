/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DriverEtc;

import Things.Weapon;

/**
 *
 * @author mardisaa
 */
public class Angel extends Player {
    private int MAX_HEALTH = 500;
    
    public Angel(String n) {
        super(n);
        if (n.equalsIgnoreCase("castiel")) {
            MAX_HEALTH = 600;
        }
        equip =  new Weapon("Angel Blade", "An angel's best friend", 500);
        hand2hand = new Weapon("celestial punch", "Quite the heavenly haymaker", 140);        
        health = MAX_HEALTH; 
    }
    
}
