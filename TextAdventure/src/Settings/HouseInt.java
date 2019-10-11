package Settings;

import java.util.ArrayList;

import DriverEtc.Player;
import Things.Friendly;
import Things.Item;
import Things.Monster;
import Things.PickUp;

public class HouseInt extends Location {

    public HouseInt(HouseExt h) {
        this.location = "outside of SOMEBODY's house";
        this.intoLoc = "in fron of SOMEBODY's house";
        this.locked = true;
        this.info = "The house is extremely rundown. There are signs "
                + "that something unhuman was here "
                + "recently. There are some torn clothes on the ground.";
        this.n = null;
        this.s = h;
        this.e = null;
        this.w = null;
        this.items = new ArrayList<PickUp>();
        items.add(new Item("Torn Clothes", "These are tattered old clothes that were ripped to shreds. "
                + "Better go show Bobby!"));
        this.friendlies = new ArrayList<Friendly>();
        this.enemies = new ArrayList<Monster>();
        waits = 0;
    }

    public void unlock(Player p) {
        if (p.hasItem("Hairpin")) {
//		if (k.getName().equalsIgnoreCase("Hairpin")) {
            locked = false;
            System.out.println("You pick the lock with your hairpin. The door is now open.");
        } else {
            System.out.println("You need some way to open the door.");
        }
    }

    @Override
    public void waitTurn(Player p) {
        waits++;
        switch (waits) {
            case 1:
                info = "You hear a strange noise in the house...";
                System.out.println(info);
                break;
            case 2:
                info = "The noise is getting louder. You are not alone.";
                System.out.println(info);
                break;
            case 3:
                info = "Why are you looking around? There is a monster!";
                enemies = new ArrayList<Monster>();
                enemies.add(new Monster("Ghoul", "A terrifying humanoid figure. This thing is crazy strong.",
                         20, 1000, "Congratulations! Go tell Bobby about your success!",
                        new Item("Eyeball", "Legend says that the eyes of a Ghoul have medicinal properties. "
                                + "Better not try it without asking Bobby, though..."), 50));
                System.out.println("A Ghoul jumps out and surprises you. It begins to attack!");
        }
    }

    @Override
    public void talkTo(String s, Player p) {
        System.out.println("There is nobody here to talk to.");
    }

}
