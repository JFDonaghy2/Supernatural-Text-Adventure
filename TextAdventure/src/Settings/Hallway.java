package Settings;

import java.util.ArrayList;

import DriverEtc.Player;
import Things.Checkpoint;
import Things.Item;
import Things.Key;
import Things.PickUp;
import Things.Friendly;
import Things.Monster;

public class Hallway extends Location {

    public Hallway(MasterBedroom mb) {
        this.location = "second floor hallway of your home";
        this.intoLoc = "into the second floor hallway";/* of your home */
        this.locked = false;
        this.info = "North is Dean's room.\n"
                + "Stairs to the first floor are to the south.\n"
                + "Back to the west lies your bedroom and "
                + "Sam's nursery lies to the east.\n"
                + "Through the open door, it looks like John is already at the crib.";
        this.n = new DeanRoom(this);
        this.s = new LivingRoom(this);
        this.e = new Nursery(this);
        this.w = mb;
        this.playerStanding = false;
        this.items = new ArrayList<PickUp>();
        items.add(new Item("Family portrait", "It was nearly impossible to get "
                + "everyone to sit still."));
        items.add(new Item("Flickering light", "Funny. It was perfectly fine "
                + "earlier..."));
        this.friendlies = new ArrayList<Friendly>();
        this.enemies = new ArrayList<Monster>();
        waits = 0;
    }

    public Hallway(LivingRoom lr, Player p) {
        this.location = "second floor hallway of your home";
        this.intoLoc = "into the hallway";
        this.locked = false;
        this.info = "The second floor hallway of your home.\nTo the west lies your "
                + "bedroom and Sam's nursery lies to the east.\nTo the north, "
                + "Dean sleeps in his room.\nStairs to the first floor are to the "
                + "south.";
        this.n = new DeanRoom(this, p);
        this.s = lr;
        this.e = new Nursery(this, p);
        this.w = new MasterBedroom(this);
        this.playerStanding = false;
        this.items = new ArrayList<PickUp>();
        items.add(new Item("Family portrait", "It was nearly impossible to get "
                + "everyone to sit still."));
        this.friendlies = new ArrayList<Friendly>();
        this.enemies = new ArrayList<Monster>();
        waits = 0;
    }

    public Hallway(Nursery nur, Player p) {
        this.location = "second floor hallway of your home";
        this.intoLoc = "into the hallway";
        this.locked = false;
        this.info = "The second floor hallway of your home.\nTo the west lies your "
                + "bedroom and Sam's nursery lies to the east.\nTo the north, "
                + "Dean sleeps in his room.\nStairs to the first floor are to the "
                + "south.";
        this.n = new DeanRoom(this, p);
        this.s = new LivingRoom(this);
        this.e = nur;
        this.w = new MasterBedroom(this);
        this.playerStanding = false;
        this.items = new ArrayList<PickUp>();
        items.add(new Item("Family portrait", "It was nearly impossible to get "
                + "everyone to sit still."));
        this.friendlies = new ArrayList<Friendly>();
        this.enemies = new ArrayList<Monster>();
        waits = 0;
    }

    public void talkTo(String s, Player p) {
        if (s.equalsIgnoreCase(p.getName())) {
            System.out.println("While perfectly acceptable, talking to yourself "
                    + "is not going to help you here.");
        } else if (p.getName().equalsIgnoreCase("Mary")) {
            if (s.equals("john")) {
                System.out.println("\"John, is he hungry?\"\n"
                        + "\"Shh,\" replies the figure.\n"
                        + "\"Alright.\""
                        + "\nThe hallway lights flicker.");
            } else if (s.equals("sam") || s.equalsIgnoreCase("sammy")) {
                System.out.println("\"Sammy? SAMMY!\"");
            } else {
                System.out.println("There is no \"" + s + "\" here, " + p.getName()
                        + ".");
            }
        } else if (p.getName().equalsIgnoreCase("John")) {
            if (s.equals("mary")) {
                System.out.println("\"Mary? Mary!\"");
            } else if (s.equals("sam")) {
                System.out.println("\"Everything's going to be okay, Sammy.\"");
            } else if (s.contains("dean") && friendlies.size() == 1) {
                System.out.println("\"Take your brother outside as fast you can.\"\n"
                        + "\"Now, Dean! Go!\"\n");
                p.giveItem("Sam", "Dean");
                friendlies.remove(0);
                p.checkPoint(new Checkpoint("BoysOutAndSafe"));
            } else if (s.equalsIgnoreCase("skip")) {
                System.out.println("There is no dialogue to skip here.");
            } else if (friendlies.isEmpty()) {
                System.out.println("There doesn't seem to be anyone here, " + p.getName());
            } else {
                System.out.println("There is no \"" + s + "\" here, " + p.getName()
                        + ".");
            }
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
                    info = "There is still noise coming from the nursery.\n"
                            + "Perhaps you should go investigate.";
                    System.out.println(info);
                    break;
                default:
                    info = "There's no turning back now, " + p.getName() + ".";
                    System.out.println(info);
                    break;
            }
        } else {
            waits++;
            switch (waits) {
                case 1:
                    info = "You need to move, " + p.getName() + "!";
                    System.out.println(info);
                    break;
                case 2:
                    info = "There isn't any time to wait! You need to get your "
                            + "boys out of there!";
                    System.out.println(info);
                    break;
                default:
                    info = "John, you ALL need to get out of there. Now!";
                    System.out.println(info);
                    break;
            }
        }
    }

    @Override
    public void unlock(Player p) {
        locked = false;
    }

    /**
     *
     * @param p A player who has checkpoint objects.
     */
    @Override
    public void trigger(Player p) {
        if (p.getName().equalsIgnoreCase("john")) {
            /*
                * John has entered the hallway from the Nursery.
             */
            if (p.hasItem("Sam")) {
                if (!p.getProgress("DeanInHallway")) {
                    System.out.println("Dean steps out into the hallway.\n"
                            + "You should probably tell him to leave.");
                    friendlies.add(new Friendly("Dean", "Quite the young rebel. "
                            + "Like father, like son."));
                    p.checkPoint(new Checkpoint("DeanInHallway"));
                }
                info = "\nFlames burst from the nursery!\n"
                        + "You need to get yourself and the boys out of there!";
                System.out.println(info);

                info = "The second floor hallway of your home.\nTo the west lies your "
                        + "bedroom and Sam's nursery lies to the east.\nTo the north, "
                        + "Dean's room lies open.\nStairs to the first floor are to the "
                        + "south.\nAny moment now, there will be fire everywhere.";
                /*
                * John is in hallway after passing Sam to Dean (might not be DIRECTLY after.
                * He may have gone into Dean's Room or the kitchen).
                 */
            } else if (p.getProgress("BoysOutAndSafe")) {
                System.out.println("Time to leave, John!");
                /*
                * The original condition where John has been woken up by Mary's scream.
                * He has not yet entered the nursery.
                 */
            } else {
                // Add something if necessary or delete if not.
            }
        } // What should Hallway update if not playing as John?
        else {
        }
    }

}
