package Settings;

import java.util.ArrayList;

import DriverEtc.Player;
import Things.Checkpoint;
import Things.Friendly;
import Things.Item;
//import Things.Key;
import Things.Monster;
import Things.PickUp;

public class Street extends Location {

    public Street(Scrapyard s) {
        this.location = "street in front of Bobby's house";
        this.intoLoc = "street";
        this.locked = true;
        this.info = "It's the main street in Springfield. "
                + "There is a bar to the south, a lake to the east, "
                + "and a town square to the west. "
                + "You parked your Impala to the north, "
                + "so you can get back to Bobby's scrapyard.";
        this.n = s;
//		this.s = new Bar(this);
        this.e = new Lake(this);
        this.w = new Square(this);
        this.items = new ArrayList<PickUp>();
        this.friendlies = new ArrayList<Friendly>();
        this.enemies = new ArrayList<Monster>();
        waits = 0;
    }

    public Street(LivingRoom lr) {
        this.location = "street outside of your home";
        this.intoLoc = "onto the street";
        this.locked = true;
        this.info = "Through the front door and onto the main street in "
                + "Lawrence, Kansas.\n"
                + "The perfect place to watch a house burn down.";
        this.n = null;
        this.s = null;
        this.e = null;
        this.w = lr;
        this.items = new ArrayList<PickUp>();
        this.friendlies = new ArrayList<Friendly>();
        this.enemies = new ArrayList<Monster>();
        waits = 0;
    }

    public void unlock(Player p) {
        if (p.getName().equalsIgnoreCase("john")) {
            if (p.hasItem("Boys Out and Safe")) {

            } else {
                //System.out.println("Um, aren't you forgeting something?\n\n"
                //+ "Here's a hint: YOUR KIDS ARE INSIDE!!!!");

            }
        }
    }

    @Override
    public void waitTurn(Player p) {
        waits++;
        System.out.println(info);
    }

    @Override
    public void talkTo(String s, Player p) {
        if (s.equalsIgnoreCase(p.getName())) {
            System.out.println("While perfectly acceptable, talking to yourself "
                    + "is not going to help you here.");
        } else {
            System.out.println("There is nobody here to talk to.");
        }
    }

    @Override
    public void trigger(Player p) {
        if (p.getName().equalsIgnoreCase("john")) {
            if (p.getProgress("BoysOutAndSafe")) {
                this.locked = false;
                System.out.println("You run from the house, scooping up Dean,"
                        + " just as the windows burst from their frames.\n"
                        + "Together, the three of you sit on your '67 Chevy Impala,\n"
                        + "watching as firemen push the onlookers back.");
                p.checkPoint(new Checkpoint("AnOriginStory"));
            } else if (p.hasItem("Sam") && !p.hasItem("Dean")) {
                this.locked = true;
                System.out.println("You might want to make sure that you have BOTH "
                        + "of your boys.");
            } else if (!p.hasItem("Sam") && p.hasItem("Dean")) {
                this.locked = true;
                System.out.println("You don't want Sam dead...yet.");
            } else {
                this.locked = true;
                System.out.println("Don't be the kind of man who walks out on his family.\n"
                        + "Don't be your father, John.");
            }
        }

    }
}
