package Settings;

import java.util.ArrayList;

import DriverEtc.Player;
import Things.Item;
import Things.PickUp;
import Things.Friendly;
import Things.Monster;

public class LivingRoom extends Location {

    public LivingRoom(Player p) {
        this.location = "living room";
        this.intoLoc = "into the living room";
        this.locked = false;
        this.info = "Your beloved lounge chair/bed sits in the middle of the living room"
                + " not far from the television.";
        this.n = new Hallway(this, p);
        this.s = null;
        this.e = new Street(this);
        this.w = new Kitchen(this);
        this.playerStanding = false;
        this.items = new ArrayList<PickUp>();
        items.add(new Item("a Television", "Can serve as a decent parental substitute"
                + "in a pinch."));
        this.friendlies = new ArrayList<Friendly>();
        this.enemies = new ArrayList<Monster>();
        waits = 0;
    }

    public LivingRoom(Hallway h) {
        this.location = "living room";
        this.intoLoc = "into the living room";
        this.locked = false;
        this.info = "John has fallen asleep watching an old war movie.\n"
                + "Wait. If John is here...?";
        this.n = h;
        this.s = null;
        this.e = new Street(this);
        this.w = new Kitchen(this);
        this.playerStanding = false;
        this.items = new ArrayList<PickUp>();
        items.add(new Item("a Television", "Can serve as a decent parental substitute"
                + "in a pinch."));
        this.friendlies = new ArrayList<Friendly>();
        friendlies.add(new Friendly("John", "Zzz"));
        this.enemies = new ArrayList<Monster>();
        waits = 0;
    }

    public void talkTo(String s, Player p) {
        if (s.equalsIgnoreCase(p.getName())) {
            System.out.println("While perfectly acceptable, talking to yourself "
                    + "is not going to help you here.");
        } else if (p.getName().equalsIgnoreCase("Mary")) {
            if (s.contains("john")) {
                System.out.println("\"John? Is that you?\"");
            } else if (s.equals("sam")) {
                System.out.println("\"Sammy! SAMMY!\"");
            }
        } else if (p.getName().equalsIgnoreCase("John")) {
            if (s.contains("mary")) {
                System.out.println("\"Mary! Mary?\"");
            }
        } else if (s.equalsIgnoreCase("skip")) {
            System.out.println("There is no dialogue to skip here.");
        } else {
            System.out.println("There doesn't seem to be anyone here, " + p.getName());
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
        waits++;
        info = "Was that Mary screaming?\n"
                + "It sounded like it came from the nursery upstairs.\n"
                + "Perhaps you should go investigate.";
        System.out.println(info);
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
        if (p.getName().equalsIgnoreCase("john")) {
            if (p.hasItem("sam")) {
                this.info = "Your beloved lounge chair/bed sits "
                        + "in the middle of the living room not far from the "
                        + "television.\nThe flames from the nursery are "
                        + "spreading down the hallway.\nHopefully there's "
                        + "nothing that you're forgetting upstairs...";
            } else if (p.getProgress("BoysOutAndSafe")) {
                this.info = "Dean has gotten Sam out safely. "
                        + "Perhaps it is time for you to leave, too.";
            }
        }
    }
}
