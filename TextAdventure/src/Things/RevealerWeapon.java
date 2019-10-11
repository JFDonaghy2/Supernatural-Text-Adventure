/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Things;

/**
 * Implementing this has caused SO many problems, but I am still going to push
 * on! I better actually use this at some point! But, it wouldn't actually be
 * very difficult to remove. I think that there are probably still a few things
 * to add.
 *
 * @author mardisaa
 */
public class RevealerWeapon extends Revealer {

    private final int attStat;

    public RevealerWeapon(String name, String info, String location,
            String message, int attStat) {
        super(name, info, location, message, null, true);
        this.attStat = attStat;
    }

    public RevealerWeapon(String name, String info, String location,
            String message, PickUp item, Boolean droppedInLoc, int attStat) {
        super(name, info, location, message, item, droppedInLoc);
        this.attStat = attStat;
    }

    public int getAttack() {
        return attStat;
    }
}
