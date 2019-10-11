package Settings;

import java.util.ArrayList;

import DriverEtc.Player;
import Things.Checkpoint;
import Things.Item;
import Things.Key;
import Things.PickUp;
import Things.Friendly;
import Things.Monster;

public class Nursery extends Location {

    public Nursery(Hallway h) {
        this.location = "nursery";
        this.intoLoc = "into the nursery";
        this.locked = false;
        this.info = "The figure at the crib was DEFINITELY not John.";
        /*"Baby Sam is sleeping soundly in his crib and- wait. 
                Who is that?";*/
        this.n = null;
        this.s = null;
        this.e = null;
        this.w = h;
        this.playerStanding = false;
        this.items = new ArrayList<PickUp>();
        //		items.add(new Key("Sam", ""));
        this.friendlies = new ArrayList<Friendly>();
        this.enemies = new ArrayList<Monster>();
        //		enemies.add(new Monster("Azazel", "Just wants 
        // some privacy while he gives baby Sam his blood.", "Wha? How 
        // did you beat me? Did you just cheat?", 350, 75, new 
        // Item("Azazel's secret plans", "Step 1: Bleed on babies..."))
        // );
        enemies.add(new Monster("The stranger", "He's a little late for"
                + "the baby shower.", 1000, 100, "Bruh, I am "
                + "literally dead.", new Item("Unattainable "
                        + "object", "You definitely should have"
                        + " been killed instead of "
                        + "getting this."), 2000));
        waits = 0;
    }

    //	info = "Baby Sam's nursery.\n"
    //			+ "Soon to be a pile of ashes.\n"
    //			+ "You might want to get out of there.\n";
    public Nursery(Hallway h, Player p) {
        this.location = "nursery";
        this.intoLoc = "into the nursery";
        this.locked = false;
        this.info = "You look around, but nothing seems to be there "
                + "except for your beautiful baby boy.\n"
                + "As you lean over his crib, something drips onto his "
                + "pillow.\nYou reach your hand out to touch it and "
                + "several more drops fall onto your hand.\nTurning "
                + "over your shoulder, you see that your wife, Mary, "
                + "is pinned to the ceiling.\nThere is a slash across "
                + "her stomach.\nFlames erupt from her body.\n"
                + "You might want to get out of there.";
        this.n = null;
        this.s = null;
        this.e = null;
        this.w = new Hallway(this, p);
        this.playerStanding = false;
        this.items = new ArrayList<PickUp>();
        items.add(new Key("Sam", "Your beautiful, bouncing baby boy.\n"
                + "One day, they'll tell stories of his greatness!"));
        this.friendlies = new ArrayList<Friendly>();
        friendlies.add(new Friendly("Mary", "Just hanging around. Get it?"));
        //		friendlies.add(new Friendly("Sam", "Your precious baby boy"));
        this.enemies = new ArrayList<Monster>();
        waits = 0;
    }

    public void talkTo(String s, Player p) {
        String name = p.getName();
        if (s.equalsIgnoreCase(name)) {
            System.out.println("While perfectly acceptable, talking"
                    + " to yourself is not going to help you here."
            );
        } else if (p.getName().equalsIgnoreCase("Mary")) {
            System.out.println("I don't think that you want to be "
                    + "talking right now, " + p.getName() + ".");
        } else if (p.getName().equalsIgnoreCase("John")) {
            if (s.contains("mary")) {
                System.out.println("\"No! Mary!\"");
            }

        } else if (s.contains("sam")) {
            System.out.println("...\nNot a lot of dialogue from "
                    + "the baby.");

        } else if (s.equalsIgnoreCase("skip")) {
            System.out.println("There is no dialogue to skip here."
            );
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
            //			info = "It was nice knowing you, Mary.";
            //			System.out.println(info);
        } else if (p.getName().equalsIgnoreCase("john")) {
            waits++;
            switch (waits) {
                case 1:
                    System.out.println("Fire begins to consume the room.");
                    enemies.add(new Monster("Fire", "A great heat source, but not "
                            + "what you need right now", 33, 100, "Tsss",
                            new Item("", ""), 0));
                    p.attackedBy(enemies.get(0));
                    break;
                case 2:
                    System.out.println("Fire is consuming more of the room.");
                    p.attackedBy(enemies.get(0));
                    System.out.println("It is really not safe to stay.");
                    break;
                case 3:
                    System.out.println("Fire has consumed the entire room.");
                    p.attackedBy(enemies.get(0));
                    break;
                default:
                    System.out.println("The fire spread to the rest of the house.\n");
                    System.out.println("If only you had left and saved your boys!");
                    p.attackedBy(enemies.get(0));
                    break;
            }
        }
    }

    @Override
    public void unlock(Player p) {
    }

    public Monster trigger() {
        return enemies.remove(0);
    }

    @Override
    public void trigger(Player p) {
        if (p.getName().equalsIgnoreCase("mary")) {
            if (p.hasItem("Dean")) {
                this.locked = true;
                System.out.println("Dean is tired. "
                        + "How about you put him down first?");
                /* Early game fight for those in the know who want to punch 
                *something right off the bat.
                */
            } else if (p.hasItem("Angel Blade") && p.hasItem("Iron Knuckles")) {
                this.locked = false;
                info += "\nOh, so it's been X years already. "
                        + "Time to kick a little BUTT for your family.";
            } else {
                p.attackedBy(enemies.get(0));
                enemies.remove(0);
            }
        } else if (p.getName().equalsIgnoreCase("john")) {
            if (p.getProgress("DeanInHallway")) {
                this.locked = true;
                System.out.println("You REALLY don't want to go in there. It's on fire!");
            }

            if (!p.getProgress("RoomOnFire")) {
                p.checkPoint(new Checkpoint("RoomOnFire"));
            } else if (!p.hasItem("Sam")) {
                this.info = "The fire is spreading from the ceiling. Pick Sam up "
                        + "and get out of there!";
            }
        }
    }

}
