/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Things;

/**
 *
 * @author mardisaa
 */
public class RevealerMedicine extends Revealer {
    
    private final int healing;
    
    public RevealerMedicine(String name, String info, String location, 
            String message, int healing) {
        super(name, info, location, message, null, true);
        this.healing = healing;
    }
    
    public RevealerMedicine(String name, String info, String location, 
            String message, PickUp item, Boolean droppedInLoc, int healing) {
        super(name, info, location, message, item, droppedInLoc);
        this.healing = healing;
    }
    
    public int getHealing() {
        return healing;
    }
}
