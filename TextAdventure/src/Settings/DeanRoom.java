package Settings;

import java.util.ArrayList;

import DriverEtc.Player;
import Things.PickUp;
import Things.Weapon;
//import Things.Weapon;
import Things.Friendly;
import Things.Item;
import Things.Key;
import Things.Monster;

public class DeanRoom extends Location {

    public DeanRoom(Hallway h) {
        this.location = "Dean's room";
        this.intoLoc = "into Dean's room";
        this.locked = true;
        this.info = "This is the bedroom of your oldest child, Dean.\nAlready at this "
                + "young age he's beginning to show signs of the Campbell family "
                + "rebellion.\nEach wall is adorned with a poster of a rock band.";
        this.n = null;
        this.s = h;
        this.e = null;
        this.w = null;
        this.playerStanding = false;
        this.items = new ArrayList<PickUp>();
        /*
                * I don't remember why I made Dean a key.
                * It was probably a mistake, but now I want to believe that it
                * was because Mary can use him to leave the house and start a 
                * new life.
         */
        items.add(new Key("Dean", ""));
        items.add(new Weapon("an Angel Blade", "Wait. What is this doing here? "
                + "Has Castiel messed with time?", 300));
        this.friendlies = new ArrayList<Friendly>();
        this.enemies = new ArrayList<Monster>();
        waits = 0;
    }

    public DeanRoom(Hallway h, Player p) {
        this.location = "Dean's room";
        this.intoLoc = "into Dean's room";
        this.locked = true;
        this.info = "This is the bedroom of your oldest child, Dean.\n"
                + "\nEach wall is adorned with a poster of a rock band."
                + "\nThat's more than enough proof that he really is your kid.";
        this.n = null;
        this.s = h;
        this.e = null;
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
        } else {
            System.out.println("Perhaps you should let Dean sleep, " + p.getName());
        }
    }

    /*
	 * The rooms change their description for each call of wait.
	 * @see Rooms.Room#waitTurn()
     */
    @Override
    public void waitTurn(Player p) {
        if (p.getName().equalsIgnoreCase("Mary")) {
            waits++;
            switch (waits) {
                case 1:
                    info = "Are you just going to stand there watching Dean sleep?";
                    System.out.println(info);
                    break;
                default:
                    info = "There is still noise coming from the nursery. "
                            + "Perhaps you should go investigate.";
                    System.out.println(info);
                    break;
            }
        } else if (p.getName().equalsIgnoreCase("John")) {
            waits++;
            switch (waits) {
                case 1:
                    info = "Are you just going to stand there watching Dean sleep?";
                    System.out.println(info);
                    break;
                default:
                    info = "John, your house in on fire!\nYou need to evacuate.";
                    System.out.println(info);
                    break;
            }
        }

    }

    @Override
    public void unlock(Player p) {
        if (p.getName().equalsIgnoreCase("Mary")) {
            if (p.hasItem("Lock pick")) {
                locked = false;
                System.out.println("You open the door to Dean's Room with your lock pick.\n"
                        + "Old habits die hard, huh?.");
            } else {
                System.out.println("Dean is asleep. You shouldn't disturb him.");
            }
        } else if (p.getName().equalsIgnoreCase("John")) {
            if (p.hasItem("Sam")) {
                locked = false;
                /*
				System.out.println("Try that again.\n"
						+ "It's hard to open a door and carry a baby at the same time.");
                 */
            } else {
                System.out.println("Dean's locked his door, but he'd probably open it for Sam.");
            }
        } else {
            locked = false;
        }
    }

    /**
     *
     * @param p, The current player who may be carrying a key or has reached a
     * higher level in the story.
     */
    @Override
    public void trigger(Player p) {
        if (p.hasItem("lock pick") || p.getProgress("DeanInHallway")) {
            this.locked = false;
        }

        if (this.locked == true) {
            System.out.println("Dean only comes out of his room these days"
                    + " for CLASSIC ROCK BAND, pie, and his brother Sam.");
        }
    }

}
