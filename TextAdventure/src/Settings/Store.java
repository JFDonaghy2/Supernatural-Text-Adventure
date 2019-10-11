package Settings;

import java.util.ArrayList;

import DriverEtc.Player;
import Things.Friendly;
import Things.Key;
import Things.Monster;
import Things.PickUp;

public class Store extends Location {

    public Store(Square s) {
        this.location = "store";
        this.intoLoc = "Store";
        this.locked = false;
        this.info = "The store is empty except the clerk at the counter. Even the shelves have nothing on them.";
        this.n = s;
        this.s = null;
        this.e = null;
        this.w = null;
        this.items = new ArrayList<PickUp>();
        this.friendlies = new ArrayList<Friendly>();
        friendlies.add(new Friendly("Clerk", "You can buy a hairpin if you want. We are out of stock of everything else at the moment."));
        this.enemies = new ArrayList<Monster>();
        waits = 0;
    }

    public void talkTo(String s, Player p) {
        if (s.equalsIgnoreCase("Clerk")) {
            System.out.println(friendlies.get(0).getDialogue());
            if (p.hasItem("Wallet") && (!(hasItem("Hairpin") || p.hasItem("Hairpin")))) {
                friendlies.get(0).changeDialogue("I don't know why you need a hairpin, but have a nice day!");
                p.pickUp(new Key("Hairpin", "This is perfect for picking locks!"));
            }
            System.out.println(friendlies.get(0).getDialogue());
        } else {
            System.out.println("Are you talking to yourself again?");
        }
    }

    public void waitTurn(Player p) {
        waits++;
        if (waits % 2 == 1) {
            friendlies.remove(1);
            info = "The store closed. I guess they forgot to tell you while you waited.";
            System.out.println(info);
        } else {
            friendlies.add(new Friendly("Clerk", "Oh, you're still here?"));
            info = "The store is empty except the clerk at the counter. Even the shelves have nothing on them.";
            System.out.println(info);
        }
    }

    @Override
    public void unlock(Player p) {
        locked = false;
    }

}
