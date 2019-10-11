/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Things;

/**
 * In certain instances, being in possession of an item (possibly soon to 
 *    include Weapon, Key, or Medicine) causes the location to interact with a 
 *     character differently than normal.
 *      For instance: the Winchester kitchen is rather normal, but if a 
 *         character (Mary) picks up a pie, then the room changes to alert her
 *          attention to the angel blade hidden among the knives (as she goes to
 *           cut a piece).
 *        Also, Sam's powers may only work when he has his special knife and in
 *          certain locations at first before being a true power that he 
 *          temporarily possesses regardless of location or objects and Dean's
 *          necklace might do something, too.
 * @author mardisaa
 */

public class Revealer extends PickUp {

    /*
    * Here is where I would define the variable that will indicate WHERE this 
    *     item is to reveal something.
    *
    * It comes in a few flavors:
    * 
    * Revealer Classic, Medicine, Key, and Weapon
    */
    private String triggerSpot;
    private String revealMessage;
    private PickUp revealed;
    private boolean locOrCheckpoint; //true if revealed appears in room, false if it is a checkpoint
    
    	public Revealer(String name, String info, String location, 
                String message, PickUp item, boolean lOrC) {
		this.name = name;
		this.info = info;
                triggerSpot = location;
                revealMessage = message;
                revealed = item;
                locOrCheckpoint = lOrC;
	}
        
        public String checkLocation() {
            return triggerSpot;
        }
        
        public void printMessage() {
            System.out.println(revealMessage);
        }
        
        public PickUp getItem() {
            return revealed;
        }
        
        public boolean addToLocation() {
            return locOrCheckpoint;
        }
}
