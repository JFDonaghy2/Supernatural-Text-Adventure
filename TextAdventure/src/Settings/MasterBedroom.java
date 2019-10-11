package Settings;

import java.util.ArrayList;

import DriverEtc.Player;
import Things.Key;
import Things.PickUp;
import Things.Weapon;
import Things.Friendly;
import Things.Item;
import Things.Monster;

public class MasterBedroom extends Location {

    public MasterBedroom() {
        this.location = "master bedroom";
        this.intoLoc = "into the master bedroom";
        this.locked = false;
        this.info = "Over the baby monitor you hear some disturbing "
                + "noises coming from the nursery\nwhere your youngest,"
                + " Sam, is just a baby.\nOnly six months old.\nEXACTLY "
                + "six months old.\nIs Sam alright?\n"
                + "You can check on him by moving through the "
                + "hallway to the east.";
        this.n = null;
        this.s = null;
        this.e = new Hallway(this);
        this.w = null;
        this.playerStanding = false;
        this.items = new ArrayList<PickUp>();
        items.add(new Item("Baby monitor", "You can barely hear Sam over the "
                + "electric whining coming from the device."));
        items.add(new Item("John's journal", "There's not a lot in it. "
                + "Nothing really happens to John."));
        items.add(new Weapon("Iron Knuckles", "Wouldn't be caught DEAD without them. "
                + "Get it? Cause they're for fighting ghosts...", 40));
        items.add(new Key("Lock Pick", "Handy for getting into those "
                + "\"hard-to-reach\" places"));
        items.add(new Item("Photo of John and Mary", "A framed photgraph of John and Mary"
                + " smiling outside of their home in Lawrence."));
        this.friendlies = new ArrayList<Friendly>();
        this.enemies = new ArrayList<Monster>();
        waits = 0;
    }

    public MasterBedroom(Hallway h) {
        this.location = "master bedroom";
        this.intoLoc = "into the master bedroom";
        this.locked = false;
        this.info = "The bed is a mess and the room looks as if Mary left in a hurry.\n"
                + "Perhaps you should go check on the kids.";
        this.n = null;
        this.s = null;
        this.e = h;
        this.w = null;
        this.playerStanding = false;
        this.items = new ArrayList<PickUp>();
        this.friendlies = new ArrayList<Friendly>();
        this.enemies = new ArrayList<Monster>();
        waits = 0;
    }

    public void talkTo(String s, Player p) {
        if (s.equalsIgnoreCase(p.getName())) {
            System.out.println("While perfectly acceptable, talking to yourself "
                    + "is not going to help you here.");
        } else if (p.getName().equalsIgnoreCase("mary") && s.equalsIgnoreCase("john")) {
            System.out.println("John?");
        } else if (friendlies.isEmpty()) {
            System.out.println("There doesn't seem to be anyone here, " + p.getName());
        } else if (s.equalsIgnoreCase("john")) {
            System.out.println("Despite this little hiccup, your relationship with "
                    + "John remains strong.\n");
            p.pickUp((new Item("a good childhood for your boys.",
                    "Is it REALLY too much to ask for")));
        }
    }


    /*
	 * The rooms change their description for each call of wait. Here, Mary waiting 
	 * allows her to live and give her boys the life that she always wanted for them.
	 * However, this good childhood keeps Sam and Dean from being able to save Sam from
	 * the other psychic kids. 
	 * Thus, by waiting 4 times, the player skips straight ahead to Sam at Stanford.
	 * @see Rooms.Room#waitTurn()
     */
    @Override
    public void waitTurn(Player p) {
        if (p.getName().equalsIgnoreCase("Mary")) {
            waits++;
            switch (waits) {
                case 1:
                    info = "There is noise coming from the nursery.\n"
                            + "Perhaps you should go investigate.";
                    System.out.println(info);
                    break;
                case 2:
                    info = "Seriously, you should go check out what's going on in the "
                            + "nursery.";
                    System.out.println(info);
                    break;
                case 3:
                    info = "Really? Are you not going to go?!?"
                            + "You might not be a very good parent.";
                    System.out.println(info);
                    break;
                case 4:
                    info = "Your husband, John, enters the room.";
                    System.out.println(info);
                    friendlies.add(new Friendly("John", "Your loving husband.\n"
                            + "Currently upset about being awoken by your crying baby."));
                    System.out.println("\nDid you not hear Sam crying?, he says.");
                    break;
                case 5:
                    info = "You might want to speak to John.";
                default:
                    info = "Hopefully your marraige will survive this.";
                    break;
            }
        } else if (p.getName().equalsIgnoreCase("John")) {
            System.out.println("If Mary isn't here, then maybe she is checking on Sam in the nursery"
                    + "down the hall...");
        }
    }

    @Override
    public void unlock(Player p) {
        locked = false;
    }

    /*
	 * From the parser, if you leave this room, it should lock so that you now HAVE
	 * to face YellowEyes.
     */
    public void lock() {
        locked = true;
    }

    @Override
    public void trigger(Player p) {
    }
}
