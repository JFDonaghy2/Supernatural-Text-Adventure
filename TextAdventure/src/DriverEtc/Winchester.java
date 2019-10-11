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
public class Winchester extends Player {
    private static final int MAX_HEALTH = 100;       
    
    public Winchester(String n) {
        super(n);
        hand2hand = new Weapon("wrestling moves", "Perhaps it's "
                + "more mixed martial arts.", 35);        
        health = MAX_HEALTH; 
    }
    
}
