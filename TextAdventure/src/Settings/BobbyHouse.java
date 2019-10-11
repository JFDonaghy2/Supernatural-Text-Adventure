package Settings;

import java.util.ArrayList;

import DriverEtc.Player;
import Things.Friendly;
import Things.Item;
import Things.Key;
import Things.Medicine;
import Things.Monster;
import Things.PickUp;
import Things.Weapon;

public class BobbyHouse extends Location {

    public BobbyHouse() {
        this.location = "home of fellow hunter Bobby Singer";
        this.intoLoc = "Bobby's House";
        this.locked = false;
        this.info = "You know this place well. You are in Bobby's home. The keys to your Impala and a botle of pills are on the counter, and Bobby is waiting for you.";
        this.n = null;
        this.s = new Scrapyard(this);
        this.e = null;
        this.w = null;
        this.items = new ArrayList<PickUp>();
        items.add(new Key("Car Keys", "The keys to your beloved Impala."));
        items.add(new Medicine("Pills", "Not as good as a nice cool one, but pretty great in a pinch.", 30));
        this.friendlies = new ArrayList<Friendly>();
        friendlies.add(new Friendly("Bobby", "Hey, Dean. Your brother is off hunting, but we got word of some mass grave robbings in Springfield. I want you to check it out. Let me know if you find anything."));
        this.enemies = new ArrayList<Monster>();
        waits = 0;
    }

    public void talkTo(String s, Player p) {
        if (s.equalsIgnoreCase("Bobby")) {
            if (p.hasItem("Torn Clothes") && p.hasItem("Fang") && (!(hasItem("Shotgun") || p.hasItem("Shotgun")))) {
                friendlies.get(0).changeDialogue("This clinches it. The fang you found is from a ghoul, and the torn shirt is from a victim. Take this shotgun and blow the sunbich away.");
                p.pickUp(new Weapon("Shotgun", "A stupidly OP weapon with more shells than you can count.", 900));
            } else if (p.hasItem("Eyeball")) {
                friendlies.get(0).changeDialogue("Oh, a Ghoul's eye! I'll keep it safe for you. Congratulations of succesfully completing the hunt. Good job.");
                p.pickUp(new Item("Bobby's fatherly love", "The love from a fatherly figure."));
            }
            System.out.println(friendlies.get(0).getDialogue());
        } else {
            System.out.println("Are you talking to yourself again?");
        }
    }

    @Override
    public void waitTurn(Player p) {
        waits++;
        System.out.println(info);
    }

    @Override
    public void unlock(Player p) {
        locked = false;
    }

}
