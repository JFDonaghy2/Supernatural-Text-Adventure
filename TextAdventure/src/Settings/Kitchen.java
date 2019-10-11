package Settings;

import java.util.ArrayList;

import DriverEtc.Player;
import Things.Friendly;
//import Things.Item;
import Things.Medicine;
import Things.Monster;
import Things.PickUp;
import Things.Revealer;
import Things.Weapon;

public class Kitchen extends Location {

    public Kitchen(LivingRoom lr) {
        this.location = "kitchen";
        this.intoLoc = "into the kitchen";
        this.locked = false;
        this.info = "You need a better description of the kitchen."
                + "\nMaybe describe its rustic style and its...countertops?";
        this.n = null;
        this.s = null;
        this.e = lr;
        this.w = null;
        this.playerStanding = false;
        this.items = new ArrayList<PickUp>();
        items.add(new Medicine("a slice of Pie", "Just one slice left. Dean didn't want "
                + "it.", 15));
        this.friendlies = new ArrayList<Friendly>();
        this.enemies = new ArrayList<Monster>();
        waits = 0;
    }

    @Override
    public void waitTurn(Player p) {
        waits++;
        info = "If you're not going to make yourself a snack,\n"
                + "you should probably head upstairs.";
        System.out.println(info);
    }

    @Override
    public void unlock(Player p) {
        locked = false;
    }

    @Override
    public void talkTo(String s, Player p) {
        if (s.equalsIgnoreCase(p.getName())) {
            System.out.println("While perfectly acceptable, talking to yourself "
                    + "is not going to help you here.");
        } else {
            System.out.println("There doesn't seem to be anyone here, " + p.getName());
        }
    }

    /*
    * IN PROGRESS: Want so that when Mary has picked up the pie in the kitchen,
    * the angel blade is immediately available. You shouldn't have to leave with 
    * the pie and then bring it back to trigger the angel blade.
    * So, how to do? Well, the pie is added on trigger (which may not be a great
    * idea itself)
    * Maybe make a new class TriggeredItem that is only visible once client has
    * another object (or two), maybe a checkpoint?
    * TriggerHelper?
     */
    @Override
    public void trigger(Player p) {
        if (p.getName().equalsIgnoreCase("mary")) {
            if (!p.hasItem("Pie")) {
                items.remove(0);
                items.add(new Revealer("Pie", "Baked after Dean went to bed so "
                        + "that you can surprise him in the morning", "kitchen"
                ,"As you look for a knife to slice up the pie,"
                        + " you notice a strange-looking blade in the drawer.",
                new Weapon("Angel Blade", "Mixed among your kitchen "
                        + "utensils, you found this weird knife", 500), true));
            }/* else if (items.indexOf("Angel Blade") < 0 && 
                    !p.hasItem("Angel Blade")) {
                items.add();
                this.info = "You need a better description of the kitchen."
                + "\nMaybe describe its rustic style and its...countertops?\n"
                        + "Glinting in the knife drawer is a strange-looking "
                        + "blade.";
            }*/
        } else {

        }
    }

}
